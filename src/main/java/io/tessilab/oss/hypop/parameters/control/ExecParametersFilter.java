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
import java.util.Map;

/**
 * An interface representing a condition that a group of parameters must complete
 * to be valid 
 * @author Andres BEL ALONSO
 */
@FunctionalInterface
public interface ExecParametersFilter {
    
    /**
     * Verifies that all the values for the parameter are correcy or not
     * @param parameters  The parameters of the execution
     * @return true if the parameters values are valid
     */
    public boolean isValidExecutionParameter(Map<ParameterName,ExecutionParameter> parameters);
    
}
