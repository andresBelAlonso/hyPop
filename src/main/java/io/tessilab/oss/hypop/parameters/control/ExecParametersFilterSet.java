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
package io.tessilab.oss.hypop.parameters.control;

import io.tessilab.oss.hypop.execution.BlockConfiguration;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;
import io.tessilab.oss.hypop.parameters.input.InputParametersSet;

/**
 * A group of conditions than the current execution parameters must acomplish 
 * to be consider as correct.
 * @author Andres BEL ALONSO
 */
public abstract class ExecParametersFilterSet {
    
    public static abstract class Config extends BlockConfiguration<ExecParametersFilterSet> {
        
        private InputParametersSet inputParams;
        
        
        /**
         * @param params 
         */
        public void setInputParameterSet(InputParametersSet params) {
//            This is set from the execution run, before te building 
            this.inputParams = params;
        } 

        public InputParametersSet getInputParams() {
            return inputParams;
        }
        
        
    }
    
    private final InputParametersSet inputParams;
    
    public ExecParametersFilterSet(InputParametersSet params) {
        this.inputParams = params;
    }
    
    public abstract boolean isValidExecutionParameter(ExecutionParametersSet execParams);

    public InputParametersSet getInputParams() {
        return inputParams;
    }
    
    
    
    
}
