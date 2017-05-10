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
import io.tessilab.oss.hypop.parameters.subparameters.IntervalSubParameterRelation;
import io.tessilab.oss.hypop.parameters.subparameters.SingleSubParameterRelationInput;
import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Andres BEL ALONSO
 */
public class IntegerInterval extends Interval<Integer> {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(IntegerInterval.class);

    public IntegerInterval(Integer lowerBorder, Integer higherBorder, ParameterName paramName, boolean includeHigherBorder, boolean includeLowerBorder) throws EmptyInterval {
        super(lowerBorder, higherBorder, paramName, includeLowerBorder, includeHigherBorder);
    }

    public Integer getElement(int index) {
        if (lowerBorder + index <= higherBorder) {
            return lowerBorder + index;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public List<ExecutionParameter<Integer>> getPosibleValues(int maxValues) {
        double step = (this.higherBorder - this.lowerBorder) / ((double) maxValues);
        List<ExecutionParameter<Integer>> res = new LinkedList<>();
        if (maxValues == 0) {
            return res;
        }
        if (this.includeLower) {
            res.add(new ExecutionParameter<>(this.getParameterName(), lowerBorder, String.valueOf(lowerBorder)));
        }
        if (this.nbValues() <= maxValues) {
            for (int j = 1; j + lowerBorder < higherBorder; j++) {
                int val = j + lowerBorder;
                res.add(new ExecutionParameter<>(this.getParameterName(), val, String.valueOf(j)));
            }
        } else {
            int lastValue = lowerBorder;
            int i = 1;
            while (res.size() < maxValues && lowerBorder + i * step < higherBorder) {
                // We compute in a inexact way to prevent values to be skyped because the step was to huge
                // The inloop value prevents infinite loops
                int curValue = (int) (lowerBorder + i * step);
                if (curValue != lastValue) {
                    res.add(new ExecutionParameter<>(this.getParameterName(), curValue, String.valueOf(curValue)));
                    lastValue = curValue;
                }
                i++;
            }
        }
        if (res.size() < maxValues && includeHigher) {
            res.add(new ExecutionParameter<>(this.getParameterName(), higherBorder, String.valueOf(higherBorder)));
        }
        return res;
    }

    @Override
    public int nbValues() {
        int courVal = this.higherBorder - lowerBorder + 1;
        if (!includeHigher) {
            courVal--;
        }
        if (!includeLower) {
            courVal--;
        }
        return courVal;
    }
    
}
