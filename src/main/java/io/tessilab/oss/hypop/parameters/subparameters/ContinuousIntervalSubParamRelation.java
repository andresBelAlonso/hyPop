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
package io.tessilab.oss.hypop.parameters.subparameters;

import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParameter;
import io.tessilab.oss.hypop.parameters.input.ContinuousInterval;
import io.tessilab.oss.hypop.parameters.input.InputParameter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The relation of a continuous interval with a subparameter.
 * @author Andres BEL ALONSO
 */
public class ContinuousIntervalSubParamRelation implements SubParameterRelationInput<Double>{
    
    private final ContinuousInterval fatherInterval;
    
    private final InputParameter subParameter;

    public ContinuousIntervalSubParamRelation(ContinuousInterval interval, InputParameter subParameter) {
        this.fatherInterval = interval;
        this.subParameter = subParameter;
    }
    

    @Override
    public List<Double> getFatherValues(int maxValues) {
        return fatherInterval.getPosibleValues(maxValues).stream().map(e->{
            return (double) ((ExecutionParameter) e).getValue();
        }).collect(Collectors.toList());
    }

    @Override
    public InputParameter getSubParameter() {
        return subParameter;
    }

    @Override
    public int maxFatherValues() {
        return -1;
    }

    @Override
    public ParameterName getFatherParamName() {
        return fatherInterval.getParameterName();
    }

    @Override
    public boolean isFatherValue(Double value) {
        return this.fatherInterval.isInInterval(value);
    }
    
    
}
