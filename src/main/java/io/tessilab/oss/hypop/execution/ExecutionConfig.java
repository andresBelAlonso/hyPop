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

import io.tessilab.oss.hypop.execution.progress.ExecutionProgress;
import io.tessilab.oss.hypop.execution.stopCondition.StopCondition;
import io.tessilab.oss.hypop.extinterface.ProcessInterface;
import io.tessilab.oss.hypop.parameters.control.ExecParametersFilterSet;
import io.tessilab.oss.hypop.parameters.managers.ParametersManager;
import io.tessilab.oss.hypop.results.saver.ResultsSaver;
import io.tessilab.oss.hypop.results.analyzer.ResultAnalyzer;
import io.tessilab.oss.openutils.locker.DAO;

/**
 * The config of an execution containing all the blocks and building in 
 * depending on the related {@link io.tessilab.oss.hypop.execution.BlockConfiguration}
 * @author Andres BEL ALONSO
 */
public class ExecutionConfig {
    
    public static final boolean DEFAULT_DO_ENDED_JOBS = true;
    
    public static final long DEFAULT_TOO_OLD_LOCK = 5000000;
    
    private ParametersManager.Config paramsConfig;
    private ResultsSaver.Config resultsSaverConfig;
    private StopCondition.Config stopConditionConfig;
    private ProcessInterface.Config processInterface;
    private ResultAnalyzer.Config resultAnalyzerConfig;
    private ExecutionProgress.Config executionProgressConfig;
    private ExecParametersFilterSet.Config execParamsFiltersConfig;
    
    /*Locker config*/
    private DAO lockerDao;
    private long tooOldLock = DEFAULT_TOO_OLD_LOCK;
    private boolean doEndedJobs = DEFAULT_DO_ENDED_JOBS;
    
    ParametersManager buildParametersManager() {
        return paramsConfig.synchroBuild();
    }
    
    ResultsSaver buildResultsSaver() {
        return resultsSaverConfig.synchroBuild();
    }
    
    ProcessInterface buildProcessInterface() {
        return processInterface.synchroBuild();
    }
    
    StopCondition buildStopCondition() {
        return stopConditionConfig.build();
    }
    
    ResultAnalyzer buildResultAnalyzer() {
        return resultAnalyzerConfig.synchroBuild();
    }
    
    ExecutionProgress buildExecutionProgress() {
        return executionProgressConfig.synchroBuild();
    }
    
    ExecParametersFilterSet buildExecutionParamsFilter() {
        return execParamsFiltersConfig.synchroBuild();
    }
 

    public DAO getLockerDao() {
        return lockerDao;
    }

    public void setLockerDao(DAO lockerDao) {
        this.lockerDao = lockerDao;
    }

    public ProcessInterface.Config getProcessInterface() {
        return processInterface;
    }

    public void setProcessInterface(ProcessInterface.Config processInterface) {
        this.processInterface = processInterface;
    }

    public long getTooOldLock() {
        return tooOldLock;
    }

    public void setTooOldLock(long tooOldLock) {
        this.tooOldLock = tooOldLock;
    }

    public boolean isDoEndedJobs() {
        return doEndedJobs;
    }

    public void setDoEndedJobs(boolean doEndedJobs) {
        this.doEndedJobs = doEndedJobs;
    }

    public ParametersManager.Config getParamsConfig() {
        return paramsConfig;
    }

    public void setParametersManagerConfig(ParametersManager.Config paramsConfig) {
        this.paramsConfig = paramsConfig;
    }

    public ResultsSaver.Config getResultsSaverConfig() {
        return resultsSaverConfig;
    }

    public void setResultsSaverConfig(ResultsSaver.Config resultsSaverConfig) {
        this.resultsSaverConfig = resultsSaverConfig;
    }

    public StopCondition.Config getStopConditionConfig() {
        return stopConditionConfig;
    }

    public void setStopConditionConfig(StopCondition.Config stopConditionConfig) {
        this.stopConditionConfig = stopConditionConfig;
    }

    public ResultAnalyzer.Config getResultAnalyzerConfig() {
        return resultAnalyzerConfig;
    }

    public void setResultAnalyzerConfig(ResultAnalyzer.Config resultAnalyzerConfig) {
        this.resultAnalyzerConfig = resultAnalyzerConfig;
    }

    public ExecutionProgress.Config getExecutionProgressConfig() {
        return executionProgressConfig;
    }

    public void setExecutionProgressConfig(ExecutionProgress.Config executionProgressConfig) {
        this.executionProgressConfig = executionProgressConfig;
    }

    public ExecParametersFilterSet.Config getExecParamsFiltersConfig() {
        return execParamsFiltersConfig;
    }

    public void setExecParamsFiltersConfig(ExecParametersFilterSet.Config execParamsFiltersConfig) {
        this.execParamsFiltersConfig = execParamsFiltersConfig;
    }
    
    
    
    
}
