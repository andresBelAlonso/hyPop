/*
 * Copyright 2017 Tessi lab.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.tessilab.oss.hypop.execution;

import io.tessilab.oss.hypop.exceptions.HyperParameterSearchError;
import io.tessilab.oss.hypop.exceptions.HyperParameterSearchRuntimeException;
import io.tessilab.oss.hypop.execution.progress.ExecutionProgress;
import io.tessilab.oss.hypop.execution.stopCondition.StopCondition;
import io.tessilab.oss.hypop.extinterface.ProcessInterface;
import io.tessilab.oss.hypop.parameters.control.ExecParametersFilterSet;
import io.tessilab.oss.hypop.parameters.managers.ParametersManager;
import io.tessilab.oss.hypop.results.ProcessResult;
import io.tessilab.oss.hypop.results.saver.ResultsSaver;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;
import io.tessilab.oss.hypop.parameters.input.InputParametersSet;
import io.tessilab.oss.hypop.results.analyzer.ResultAnalyzer;
import io.tessilab.oss.openutils.designpatterns.observer.ParametrizedObserver;
import io.tessilab.oss.openutils.designpatterns.observer.ParametrizedSubject;
import io.tessilab.oss.openutils.locker.JobLocker;
import java.util.LinkedList;
import java.util.List;

/**
 * The principal class of an execution. To run an hyperparameter search is to set
 * the different elements of the {@link io.tessilab.oss.hypop.execution.ExecutionConfig}
 * and to run this class.
 * <p>
 * This class simply uses the {@link io.tessilab.oss.hypop.execution.ExecutionConfig} 
 * to build them an execute them. 
 * @author Andres BEL ALONSO
 */
public class ExecutionRun implements Runnable, ParametrizedSubject<ProcessResult> {

    private static final Logger LOGGER = LogManager.getLogger(ExecutionRun.class);

    public static String createID() {
        // Get the mac address
        LOGGER.debug("Creating the id of the execution");
        InetAddress ip;
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        /*
         * This fonction can change, depending on the JVM implementation, but
         * usualy matches with this regular expresion
         */
        if (!pid.matches("[0-9]+@.+")) {
            throw new HyperParameterSearchError("The JVM does not return the pid with the correct regular expression.");
        } else {
            pid = pid.substring(0, pid.lastIndexOf("@"));
        }
        String ipString = "";
        try {
            ip = InetAddress.getLocalHost();
            ipString = ip.getHostAddress();
        } catch (UnknownHostException ex) {
            LOGGER.warn(ex);
            LOGGER.warn("Unable to compute the ip address of the current execution.");
            ipString = "127.0.0.1";
        }
        return ipString + ":" + pid;
    }

    private final String executionID;

    private ExecutionConfig config;

    private ProcessResult lastResult = null;

    private final List<ParametrizedObserver> voyeurs;

    public ExecutionRun(ExecutionConfig config) {
        this.config = config;
        executionID = createID();
        this.voyeurs = new LinkedList();
    }

    @Override
    public void run() {
        JobLocker<ExecutionParametersSet> locker = new JobLocker<>(config.getLockerDao(),
                executionID,
                config.isDoEndedJobs(),
                config.getTooOldLock());

        locker.setWaitTime(50);

        LOGGER.info("Building the interface with the problem");
        ProcessInterface externalPb = config.buildProcessInterface();

        LOGGER.info("Building the paramaeters manager");
        InputParametersSet paramSet = externalPb.createInputParameters();
        config.getParamsConfig().setInputParameters(paramSet);
        ParametersManager paramManager = config.buildParametersManager();
        this.attach(paramManager);
        
        LOGGER.info("Building the execution parameters filter");
        config.getExecParamsFiltersConfig().setInputParameterSet(paramSet);
        ExecParametersFilterSet execParamsFilter = config.buildExecutionParamsFilter();

        LOGGER.info("Building the document saver");
        ResultsSaver saver = config.buildResultsSaver();

        LOGGER.info("Building the stop condition");
        StopCondition stopCondition = config.buildStopCondition();
        ProcessResult res = null;
        this.attach(stopCondition);

        LOGGER.info("Builging the results analyzer");
        ResultAnalyzer analyzer = config.buildResultAnalyzer();
        this.attach(analyzer);

        LOGGER.info("Building the progress follower");
        ExecutionProgress progress = config.buildExecutionProgress();
        this.attach(progress);
        progress.init(paramManager, stopCondition);
        while (paramManager.hasJobsToExplore() && stopCondition.continueWork()) {
            try {
                ExecutionParametersSet params = paramManager.getNonBuildParameters();
                if(!execParamsFilter.isValidExecutionParameter(params)) {
//                    LOGGER.debug("The parameters {} are not valid, continuing",params.getDescriptionParameters());
                    paramManager.notifyAsNotValidParameter(params);
                    continue;
                }
                progress.showProgress();
                LOGGER.info("Trying to lock {}", params.getDescriptionParameters());
                if (locker.lockJob(params)) {
                    // we have the job
                    LOGGER.info("The job {} has been locked. Processing it.", params.getDescriptionParameters());
                    res = externalPb.computeIt(params);
                    LOGGER.info("The job {} has been computed. Saving the results", params.getDescriptionParameters());
                    saver.saveResult(params, res);
                    locker.releaseJob();
                } else {
                    //the job was already taken 
                    LOGGER.info("The job was already taken");
                    if (locker.isEndedJob(params)) {
                        res = externalPb.createResult(saver.getJobDone(params));
                    } else {
                        // The job is not ended, the parameter manager is notify 
                        paramManager.canNotBeDoneNow(params);
                    }
                }
                // we notify all the interested about the new know result

                if (res != null) {
                    //if the result has not parameters, it is ignore
                    if (!res.getResultInDBFormat().isEmpty()) {
                        this.lastResult = res;
                        this.notifyObservers(res);
                    } else {
                        LOGGER.warn("A result is ignored because its process result is empty");
                    }
                }
                res = null;
            } catch (HyperParameterSearchRuntimeException ex) {
                LOGGER.warn(ex);
                LOGGER.warn("A parameter has create an erro skypping it");
                locker.releaseJob();
            }
        }
        analyzer.analyseResults();
    }

    /**
     *
     * @return the last resutl computed during the execution.Never null. If this
     * method was getted, and there is not a lastResult(is null) an error is
     * throw.
     */
    public ProcessResult getLastResult() {
        if (lastResult == null) {
            throw new HyperParameterSearchError("A last result was asked before it exists !");
        }
        return lastResult;
    }

    @Override
    public void notifyObservers(ProcessResult info) {
        this.voyeurs.forEach(e -> e.updateObserver(info));
    }

    @Override
    public void attach(ParametrizedObserver<ProcessResult> observer) {
        this.voyeurs.add(observer);
    }

    @Override
    public void removeObserver(ParametrizedObserver<ProcessResult> observer) {
        this.voyeurs.remove(observer);
    }

}
