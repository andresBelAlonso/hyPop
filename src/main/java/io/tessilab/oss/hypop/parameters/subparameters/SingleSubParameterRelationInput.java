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
import io.tessilab.oss.hypop.parameters.input.InputParameter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * The simpliest subrelation who associates an {@link io.tessilab.oss.hypop.parameters.input.InputParameter} with 
 * a single value.
 * @author Andres BEL ALONSO
 * @param <T> : value type of the father parameter
 */
public class SingleSubParameterRelationInput<T> implements SubParameterRelationInput<T>{
    
    private final T fatherValue;
    private final InputParameter<?> param;
    private final ParameterName fatherName;

    public SingleSubParameterRelationInput(T fatherValue, InputParameter<?> param,ParameterName fatherParamName) {
        this.fatherValue = fatherValue;
        this.param = param;
        this.fatherName = fatherParamName;
    }

    @Override
    public List<T> getFatherValues(int maxValues) {
        if(maxValues > 0) {
            return Arrays.asList(fatherValue);
        } else {
            return new LinkedList<>();
        }
    }
 
    @Override
    public InputParameter<?> getSubParameter() {
        return param;
    }

    @Override
    public int maxFatherValues() {
        // by definition of the class
        return 1;
    }

    @Override
    public ParameterName getFatherParamName() {
        return fatherName;
    }

    @Override
    public boolean isFatherValue(Optional<T> value) {
        if(value.isPresent()) {
            return this.fatherValue.equals(value.get());
        } else {
            return false;
        }
    }
}
