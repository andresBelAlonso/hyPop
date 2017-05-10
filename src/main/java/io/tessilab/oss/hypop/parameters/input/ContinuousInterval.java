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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * The parameter who is represented by a real continous interval.
 * @author Andres BEL ALONSO
 */
public class ContinuousInterval extends Interval<Double>{

    public ContinuousInterval(Double lowerBorder, Double higherBorder, ParameterName paramName, boolean includeLower, boolean includeHigher) throws EmptyInterval {
        super(lowerBorder, higherBorder, paramName, includeLower, includeHigher);
    }

    @Override
    public List<ExecutionParameter<Double>> getPosibleValues(int maxValues) {
        // case maxvalues = 1
        if(maxValues == 0) {
            return new LinkedList<>();
        } else if(maxValues == 1) {
            double val = (this.higherBorder + lowerBorder)/2;
            return Arrays.asList(new ExecutionParameter<>(this.getParameterName(), 
                    val, 
                    String.valueOf(val)));
        }
        int i;
        double step;
        int maxVal;
        if(includeHigher && includeLower) {
            i=0;
            maxVal = maxValues-1;
            step =  (this.higherBorder - this.lowerBorder) /  (maxValues - 1);
        } else if(includeHigher) {
            i = 1;
            step = (this.higherBorder - this.lowerBorder) /  maxValues;
            maxVal = maxValues;
        } else if(includeLower) {
            i = 0;
            maxVal = maxValues - 1;
            step = (this.higherBorder - this.lowerBorder) /  maxValues;
        } else {
            step = (this.higherBorder - this.lowerBorder) /  (maxValues + 1);
            i = 1;
            maxVal = maxValues;
        }
        List<ExecutionParameter<Double>> res = new LinkedList<>();
        while(res.size() < maxValues ) {
            double val = lowerBorder + i * step;
            res.add(new ExecutionParameter<>(this.getParameterName(), val, String.valueOf(val)));
            i++;
        }
        return res;
    } 

    @Override
    public int nbValues() {
        return -1;
    }


    
}
