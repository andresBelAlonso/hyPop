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

/**
 * The simpliest stop condition that adds no more stops condition than the parameters
 * manager one. 
 * @author Andres BEL ALONSO
 */
public class NoMoreStopCondition<SCORE extends Comparable<SCORE>,PROCESSRESULT extends ProcessResult<SCORE>>
        extends StopCondition<SCORE,PROCESSRESULT>{
    
    public static class Config<SCORE extends Comparable<SCORE>,PROCESSRESULT extends
            ProcessResult<SCORE>> extends StopCondition.Config<SCORE,PROCESSRESULT> {

        @Override
        protected StopCondition<SCORE,PROCESSRESULT> build() {
            return new NoMoreStopCondition<>();
        }
    }

    @Override
    public boolean continueWork() {
        return true;
    }

    @Override
    public void updateObserver(PROCESSRESULT obj) {
        // We do not care about new results
    }

    @Override
    public int getJobsToDo() {
        return -1;
    }
    
    
    
    
    
}
