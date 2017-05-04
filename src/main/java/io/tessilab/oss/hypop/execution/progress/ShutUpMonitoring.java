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
package io.tessilab.oss.hypop.execution.progress;

import io.tessilab.oss.hypop.execution.stopCondition.StopCondition;
import io.tessilab.oss.hypop.parameters.managers.ParametersManager;
import io.tessilab.oss.hypop.results.ProcessResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The class that do not give a fuck about the execution and does not follow it.
 * (all the methods are mute, and do nothing, if you are not enought clever to 
 * understand it).
 * @author Andres BEL ALONSO
 * @param <SCORE> The score of an execution
 * @param <PROCESSRESULT> The class containing all the data about an execution
 */
public class ShutUpMonitoring<SCORE extends Comparable<SCORE>,PROCESSRESULT extends ProcessResult<SCORE>> 
        implements ExecutionProgress<SCORE,PROCESSRESULT>{
    
    private static final Logger LOGGER = LogManager.getLogger(ShutUpMonitoring.class);

    @Override
    public void init(ParametersManager<SCORE,PROCESSRESULT> paramManager,
            StopCondition<SCORE,PROCESSRESULT> stopCondition) {
        //this monitoring does not need an init
        // and for this monitoring, paramManager and stopCondition are not my friend... anymore
    }

    
    public static class Config<SCORE extends Comparable<SCORE>,PROCESSRESULT extends ProcessResult<SCORE>>
            extends ExecutionProgress.Config<SCORE,PROCESSRESULT> {

        @Override
        protected ExecutionProgress<SCORE,PROCESSRESULT> build() {
            return new ShutUpMonitoring<>();
        }
        
    }

    @Override
    public void showProgress() {
        // Always not work 
    }
    
    @Override
    public void updateObserver(PROCESSRESULT obj) {
        // This class is borried about non usefull updates
    }
}
