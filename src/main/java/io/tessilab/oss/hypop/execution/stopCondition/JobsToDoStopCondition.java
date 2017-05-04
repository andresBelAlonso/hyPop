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
package io.tessilab.oss.hypop.execution.stopCondition;

import io.tessilab.oss.hypop.results.ProcessResult;
import org.apache.logging.log4j.LogManager;

/**
 * A stop condition based on the number of jobs to do. 
 * @author Andres BEL ALONSO
 * @param <SCORE> The score of the quality of an execution
 * @param <PROCESSRESULT> The class containing all the information about a process result
 */
public class JobsToDoStopCondition<SCORE extends Comparable<SCORE>, PROCESSRESULT extends ProcessResult<SCORE>> extends StopCondition<SCORE,PROCESSRESULT> {
    
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(JobsToDoStopCondition.class);

    
    public static class Config<SCORE extends Comparable<SCORE>, PROCESSRESULT extends ProcessResult<SCORE>> 
            extends StopCondition.Config {
        
        private final int nbWorksToDo;

        public Config(int nbWorksToDo) {
            this.nbWorksToDo = nbWorksToDo;
        }

        @Override
        protected StopCondition<SCORE,PROCESSRESULT> build() {
            return new JobsToDoStopCondition<>(nbWorksToDo);
        }
        
    }
    
    private final int nbWorksToDo;
    private int computedWorks =0;

    public JobsToDoStopCondition(int nbWorksToDo) {
        this.nbWorksToDo = nbWorksToDo;
    }
    
    

    @Override
    public boolean continueWork() {
        if(computedWorks<nbWorksToDo) {
            return true;
        } else {
            LOGGER.debug("The job is done is done. The {} jobs have been compute",this.nbWorksToDo);
            return false;
        }
    }

    @Override
    public int getJobsToDo() {
        return nbWorksToDo - computedWorks;
    }

    @Override
    public void updateObserver(PROCESSRESULT obj) {
        computedWorks ++;
    }
    
}
