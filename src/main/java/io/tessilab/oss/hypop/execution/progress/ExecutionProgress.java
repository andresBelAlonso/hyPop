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

import io.tessilab.oss.hypop.execution.BlockConfiguration;
import io.tessilab.oss.hypop.execution.stopCondition.StopCondition;
import io.tessilab.oss.hypop.parameters.managers.ParametersManager;
import io.tessilab.oss.hypop.results.ProcessResult;
import io.tessilab.oss.openutils.designpatterns.observer.ParametrizedObserver;

/**
 * The class that monitor the progress of the execution.
 * @author Andres BEL ALONSO
 * @param <SCORE> The score of an execution
 * @param <PROCESSRESULT> The class containing all the data about an execution
 */
public interface ExecutionProgress<SCORE extends Comparable<SCORE>,PROCESSRESULT extends ProcessResult<SCORE>>
        extends ParametrizedObserver<PROCESSRESULT>{
    
    public static abstract class Config<SCORE extends Comparable<SCORE>,PROCESSRESULT 
            extends ProcessResult<SCORE>> extends BlockConfiguration<ExecutionProgress<SCORE,PROCESSRESULT>> {
    }
    
    /**
     * Shows the progress of the execution.
     */
    public void showProgress();
    
    /**
     * Inits the progress monitoring. This allow this object to know the two class
     * that are able to stop the execution ( {@link io.tessilab.oss.hypop.execution.stopCondition.StopCondition}
     * because a condition to stop was reached and 
     * {@link io.tessilab.oss.hypop.parameters.managers.ParametersManager} because 
     * there are not more jobs to do) and synchronyze with them. 
     * @param paramManager : The parameter manager of the current execution
     * @param stopCondition : The conditin to stop the execution
     */
    public void init(ParametersManager<SCORE,PROCESSRESULT> paramManager,
            StopCondition<SCORE,PROCESSRESULT> stopCondition);
  
}
