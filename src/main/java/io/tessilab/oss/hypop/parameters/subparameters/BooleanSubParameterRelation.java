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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Andres BEL ALONSO
 */
public class BooleanSubParameterRelation<T> implements SubParameterRelationInput<T>{
    
    private final ParameterName fatherParamName;
    private final InputParameter<?> subParameter;
    private final boolean present;
    private final T fatherBaseValue;
    
    public BooleanSubParameterRelation(boolean presence, ParameterName fatherParamName, T fatherBaseValue,
            InputParameter<?> subParameter) {
        this.fatherParamName = fatherParamName;
        this.subParameter = subParameter;
        this.present = presence;
        this.fatherBaseValue = fatherBaseValue;
    }


    @Override
    public InputParameter<?> getSubParameter() {
        return subParameter;
    }

    @Override
    public int maxFatherValues() {
        return 1;
    }

    @Override
    public ParameterName getFatherParamName() {
        return this.fatherParamName;
    }

    @Override
    public boolean isFatherValue(Optional<T> value) {
        if(value.isPresent()) {
            // maybe the second component of the present is not needed.
            return present && value.get().equals(fatherBaseValue);
        } else {
            return !present;
        }
    }

    @Override
    public List<T> getFatherValues(int maxValues) {
        List<T> resList = new ArrayList<>(1);
        if(maxValues <= 0) {
            return resList;
        }
        resList.add(fatherBaseValue);
        return resList;
    }

    

    
}
