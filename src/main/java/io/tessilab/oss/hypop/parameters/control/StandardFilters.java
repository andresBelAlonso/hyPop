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

import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParameter;
import java.util.List;
import java.util.Map;

/**
 * This class contains the must commun filters of execution parameters
 *
 * @author Andres BEL ALONSO
 */
public class StandardFilters {

    /**
     * Creates a filter who filters integer values and returns true only if all
     * the parameters exists AND they are less than maxValue
     *
     * @param params A list with all the names of the parameters
     * @param maxValue the maximun value than a parameter can take
     * @return The filter that makes this
     */
    public static ExecParametersFilter integersLesThanN(List<ParameterName> params, int maxValue) {
        return (Map<ParameterName, ExecutionParameter> e) -> {
            int counter = 0;
            for (ParameterName param : params) {
                if (!e.containsKey(param)) {
                    return false;
                }
                counter += (int) e.get(param).getValue();
            }
            return counter < maxValue;
        };
    }

    /**
     * Creates a filter that returns true only if the value is equal to some
     * value, with some tolerance. If a parameter does not exists, it returns
     * false
     *
     * @param params The names of the parameters to filter
     * @param targetValue  The value of the sum of all parameters
     * @param tolerance The tolerance over the sum of all values
     * @return The filter that does this
     */
    public static ExecParametersFilter doubleEqualToN(List<ParameterName> params, double targetValue, double tolerance) {
        return (Map<ParameterName, ExecutionParameter> e) -> {
            double counter = 0;
            for (ParameterName param : params) {
                if (!e.containsKey(param)) {
                    return false;
                }
                counter += (double) e.get(param).getValue();
            }
            return counter > targetValue - tolerance && counter < targetValue + tolerance;
        };
    }

}
