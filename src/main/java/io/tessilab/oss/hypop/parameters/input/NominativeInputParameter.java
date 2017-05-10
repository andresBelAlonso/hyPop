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
import io.tessilab.oss.hypop.parameters.subparameters.SingleSubParameterRelationInput;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The class who represents a nominative value
 * @author Andres BEL ALONSO
 * @param <T> The type of parameter
 */
public class NominativeInputParameter<T> extends InputParameter<T> {
    
    private final Map<String,T> values;
    
    /**
     * 
     * @param parameterName : the name of the parameter
     * @param values : the different values than the object can take
     * 
     */
    public NominativeInputParameter(ParameterName parameterName,Map<String,T> values) {
        super(parameterName);
        this.values = values;
    }

    @Override
    public List<ExecutionParameter<T>> getPosibleValues(int maxValues) {
        List<ExecutionParameter<T>> res = new LinkedList<>();
        values.entrySet().forEach((entry) -> {
            res.add(new ExecutionParameter<>(this.getParameterName(), entry.getValue(),entry.getKey()));
        });
        return res;
    }

    @Override
    public int nbValues() {
        return values.size();
    }

    @Override
    protected void addSubparameter(InputParameter<?> param, T value) throws NotValidParameterValue {
        if(!this.values.containsValue(value)) {
            throw new NotValidParameterValue("The value passed in argument is not a possible value of this parameter ");
        }
        this.subParameters.add(new SingleSubParameterRelationInput<>(value,param,this.getParameterName()));
    }
}
