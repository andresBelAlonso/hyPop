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
import io.tessilab.oss.hypop.parameters.input.Interval;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The relation betwee, an integer interval (as father parameter) and a 
 * @author Andres BEL ALONSO
 * @param <T> The type of values of an interval 
 */
public class IntervalSubParameterRelation<T extends Comparable<T>> implements SubParameterRelationInput<T>{

    private final ParameterName fatherParameterName;
    private final Interval<T> interval;
    private final InputParameter<?> subparameter;

    public IntervalSubParameterRelation(ParameterName fatherParameterName, Interval<T> interval,
            InputParameter<?> subparameter) {
        this.fatherParameterName = fatherParameterName;
        this.interval = interval;
        this.subparameter = subparameter;
    }
    
    
    
  
    @Override
    public InputParameter<?> getSubParameter() {
        return subparameter;
    }

    @Override
    public int maxFatherValues() {
        return interval.nbValues();
    }

    @Override
    public ParameterName getFatherParamName() {
        return fatherParameterName;
    }

    @Override
    public List<T> getFatherValues(int maxValues) {
        // TODO : This is not optimal, to verify
        return interval.getPosibleValues(maxValues).stream()
                .map( e -> e.getValue()).collect(Collectors.toList());
    }

    @Override
    public boolean isFatherValue(Optional<T> value) {
       if(!value.isPresent()) {
           return false;
       } else {
            return interval.isIncludedInterval(value.get());
       }
    }

}
