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
package io.tessilab.oss.hypop.parameters.managers;

import io.tessilab.oss.hypop.exceptions.HyperParameterSearchError;
import io.tessilab.oss.hypop.execution.BlockConfiguration;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;
import io.tessilab.oss.hypop.parameters.input.InputParametersSet;
import io.tessilab.oss.hypop.results.ProcessResult;
import io.tessilab.oss.openutils.designpatterns.observer.ParametrizedObserver;

/**
 * The abstract class that represents the class to provide the parameters to 
 * the {@link io.tessilab.oss.hypop.execution.ExecutionRun} to be tested. 
 * What parameters give and when to give them are computed by this subclasses. 
 * 
 * @author Andres BEL ALONSO
 */
public abstract class ParametersManager implements ParametrizedObserver<ProcessResult>{
    
    public static abstract class Config extends BlockConfiguration<ParametersManager> {
        
        private InputParametersSet inputParameters = null;

        public Config() {
        }

        public InputParametersSet getInputParameters() {
            return inputParameters;
        }

        public void setInputParameters(InputParametersSet inputParameters) {
            this.inputParameters = inputParameters;
        }
        
        
        
    }
    
    protected final InputParametersSet params;

    public ParametersManager(InputParametersSet params) {
        if(params != null) {
            this.params = params;
        } else {
            throw new HyperParameterSearchError("The InputParameterSet was not added to the parameters manager");
        }
    }
    
    public abstract boolean hasJobsToExplore();

    /**
     * Execute this method after calling hasJobsToExplore method. If there are no 
     * more jobs to explore, a RuntimeException is throw. 
     * @return A set of parameters to execute. 
     */
    public abstract ExecutionParametersSet getNonBuildParameters();
    
    /**
     * 
     * @return the number of jobs than the parameter manager thinks it remains, or
     * -1 if there is no a know limit
     */
    public abstract int getJobsTodo(); 
    
    /**
     * The job passed in argument can not be compute now (probably because it is 
     * compute by another instance of the hyperparameter research).
     * @param params 
     */
    public abstract void canNotBeDoneNow(ExecutionParametersSet params);
    
    /**
     * The parameter manager is notify that the parameters in argument are not 
     * a set of parameters that can be compute (because they are not coherent, 
     * because they contain banned values ...) 
     * @param params 
     */
    public abstract void notifyAsNotValidParameter(ExecutionParametersSet params);
}
