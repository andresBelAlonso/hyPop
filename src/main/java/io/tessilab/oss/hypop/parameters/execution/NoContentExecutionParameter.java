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
package io.tessilab.oss.hypop.parameters.execution;

import io.tessilab.oss.hypop.parameters.ParameterName;

/**
 * The parameter to represent the fact that an {@link io.tessilab.oss.hypop.parameters.input.InputParameter} can return something
 * that must not be used during the execution.
 * @author Andres BEL ALONSO
 */
public class NoContentExecutionParameter extends ExecutionParameter{
    
    public static Object NO_CONTENT_VALUE = "";
    
    public NoContentExecutionParameter(ParameterName paramName) {
        super(paramName, NO_CONTENT_VALUE, "");
    }
    
    
    
}
