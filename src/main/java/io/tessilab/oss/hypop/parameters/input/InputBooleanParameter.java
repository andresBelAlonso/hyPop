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
package io.tessilab.oss.hypop.parameters.input;

import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParameter;
import io.tessilab.oss.hypop.parameters.execution.NoContentExecutionParameter;
import io.tessilab.oss.hypop.parameters.subparameters.BooleanSubParameterRelation;
import io.tessilab.oss.hypop.parameters.subparameters.SingleSubParameterRelationInput;
import java.util.LinkedList;
import java.util.List;

/**
 * The InputBoolean parameter represents the fact that a parameter can be use, 
 * or not during an hyperparameterResearch
 * @author Andres BEL ALONSO
 * @param <T> : The type of Object returned when an object is returned
 */
public class InputBooleanParameter<T> extends InputParameter<T>{
    
    private final T value;
    private final String representingString;
    
    /**
     * 
     * @param parameterName The name of the parameter
     * @param value The value returned whe the parameter is true
     * @param representingString  the string representing the value
     */
    public InputBooleanParameter(ParameterName parameterName, T value, String representingString) {
        super(parameterName);
        this.value = value;
        this.representingString = representingString;
    }
    
    /**
     * If maxValues equals only to one, the associated object is returned
     * @param maxValues The maximun values to get
     * @return A list containing (or not) the associated object
     */
    @Override
    public List<ExecutionParameter<T>> getPosibleValues(int maxValues) {
        List<ExecutionParameter<T>> res = new LinkedList<>();
        if(maxValues == 0 ) {
            return res;
        }
        res.add(new ExecutionParameter<>(this.getParameterName(), value, representingString));
        if(maxValues == 1) {
            return res;
        }
        res.add(new NoContentExecutionParameter<>(this.getParameterName(),value));
        return res;
    }

    @Override
    public int nbValues() {
        return 2;
    }

    /**
     * 
     * @param param The son parameter
     * @param value  true when the value is related with the existence of the parameter
     * and false if the parameter is related to the absence of the value
     */
    protected void addSubparameter(InputParameter<?> param, Boolean value) {
        if(value) {
            this.subParameters.
                    add(new BooleanSubParameterRelation<>(value,
                            this.getParameterName(),this.value,param));
        } else {
            // I do not know if this works... the idea to work is that when the value does not exist, we create a
            // execution parameter with the null value
            this.subParameters.add(new BooleanSubParameterRelation<>(value,this.getParameterName(),
                    this.value,param));
        }
    }

    @Override
    protected void addSubparameter(InputParameter<?> param, T value) throws NotValidParameterValue {
        if(!this.value.equals(value)) {
            throw new NotValidParameterValue("The value passed in argument is not a correct value");
        }
        this.subParameters.add(new SingleSubParameterRelationInput<>(value, param, this.getParameterName()));
    }
    
    
        
}
