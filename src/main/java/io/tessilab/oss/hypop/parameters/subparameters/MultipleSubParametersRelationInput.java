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
import java.util.List;

/**
 * The relation between n values of the father parameter and a subparameter
 * @author Andres BEL ALONSO
 */
public class MultipleSubParametersRelationInput<T> implements SubParameterRelationInput<T>{
    
    private final List<T> fatherValues;
    
    private final InputParameter subParameter;
    
    private final ParameterName fatherParameterName;

    public MultipleSubParametersRelationInput(List<T> fatherValues, InputParameter subParameter,ParameterName fatherParameterName) {
        this.fatherValues = fatherValues;
        this.subParameter = subParameter;
        this.fatherParameterName = fatherParameterName;
    }

    @Override
    public List<T> getFatherValues(int maxValues) {
        return fatherValues;
    }

    @Override
    public InputParameter getSubParameter() {
        return subParameter;
    }

    @Override
    public int maxFatherValues() {
        return fatherValues.size();
    }


    @Override
    public ParameterName getFatherParamName() {
        return fatherParameterName;
    }

    @Override
    public boolean isFatherValue(T value) {
        for(T fatherVal : this.fatherValues) {
            if(fatherVal.equals(value)) {
                return true;
            }
        }
        return false;
    }

    
}
