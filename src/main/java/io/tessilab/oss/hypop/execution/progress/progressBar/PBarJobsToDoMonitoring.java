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
package io.tessilab.oss.hypop.execution.progress.progressBar;

import io.tessilab.oss.hypop.execution.progress.ExecutionProgress;
import io.tessilab.oss.hypop.execution.stopCondition.StopCondition;
import io.tessilab.oss.hypop.parameters.managers.ParametersManager;
import io.tessilab.oss.hypop.results.ProcessResult;
import io.tessilab.oss.openutils.progressbar.ProgressBar;
import io.tessilab.oss.openutils.progressbar.ProgressBarStyle;
import java.io.PrintStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A way to monitorize the progress of the search printing with a {@link java.io.PrintStream}
 * a progress bar related with the number of jobs to do. The amount of jobs to do 
 * is related gived by {@link io.tessilab.oss.hypop.execution.stopCondition.StopCondition}
 * or the {@link io.tessilab.oss.hypop.parameters.managers.ParametersManager} in case
 * one of two knows how many executions there are. 
 * <p>
 * After each execution of the search, this class is notify to update the bar. 
 * <p>
 * It is evident that if the {@link io.tessilab.oss.hypop.parameters.managers.ParametersManager} 
 * and the {@link io.tessilab.oss.hypop.execution.stopCondition.StopCondition} have not 
 * a defined stop number, this bar will make not sense,but the program will run.
 * @author Andres BEL ALONSO
 */
public class PBarJobsToDoMonitoring implements ExecutionProgress {
    
    private static final Logger LOGGER = LogManager.getLogger(PBarJobsToDoMonitoring.class);
    
    public static class Config extends ExecutionProgress.Config {
        
        private final ProgressBarStyle progressBarStyle;
        private final PrintStream printStream;

        public Config(ProgressBarStyle progressBarStyle, PrintStream printStream) {
            this.progressBarStyle = progressBarStyle;
            this.printStream = printStream;
        }

        @Override
        protected ExecutionProgress build() {
            return new PBarJobsToDoMonitoring(progressBarStyle, printStream);
        }
        
    }
    
    
    private final ProgressBarStyle style;
    private final PrintStream printStream;
    private ProgressBar progressBar;

    public PBarJobsToDoMonitoring(ProgressBarStyle style, PrintStream printStream) {
        this.style = style;
        this.printStream = printStream;
    }

    @Override
    public void showProgress() {
        progressBar.refreshBar();
    }

    @Override
    public void updateObserver(ProcessResult obj) {
        // A job has ended, increase the bar
        progressBar.stepBy(1);
    }

    @Override
    public void init(ParametersManager paramManager, StopCondition stopCondition) {
        // TODO : update this when they will be other conditions
        if(paramManager.getJobsTodo() == -1 && stopCondition.getJobsToDo() == -1) {
            LOGGER.warn("Progress could not be monitorize because the stop condition and the parameters manager does not depend on the number of jobs");
        }
        progressBar = new ProgressBar(style, printStream, decideJobsToDo(paramManager.getJobsTodo(), stopCondition.getJobsToDo()), "Search Progress");
    }
    
    private long decideJobsToDo(int paramJobs, int stopConditionJobs) {
        if(stopConditionJobs == - 1) {
            if(paramJobs == -1) {
                return Integer.MAX_VALUE;
            } else {
                return paramJobs;
            }
        } else {
            if(paramJobs == -1 ) {
                return stopConditionJobs;
            } else {
                return stopConditionJobs>paramJobs?paramJobs:stopConditionJobs;
            }
        }
    }

}
