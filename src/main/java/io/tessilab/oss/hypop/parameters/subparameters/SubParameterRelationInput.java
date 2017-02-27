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
 * The class that that represents the subparameter relation between two
 * parameters. In fact the relation of two parameters is the association of a
 * value of the father node with the parameter of the child.
 *
 * @author Andres BEL ALONSO
 * @param <T> : value type
 */
public interface SubParameterRelationInput<T> {
    
    /**
     * 
     * @param maxValues The maximun number of values than the list can contain. 
     * @return The list of values of the father parameter related to the subparameter. 
     */
    public List<T> getFatherValues(int maxValues);
    
    /**
     * 
     * @return The child {@link io.tessilab.oss.hypop.parameters.input.InputParameter} 
     * of this relation
     */
    public InputParameter getSubParameter();
    
    /**
     * 
     * @return The maximun number of father values or -1 if there is no limit
     */
    public int maxFatherValues();
    
    /**
     * 
     * @return The name of the father parameter of this relation
     */
    public ParameterName getFatherParamName();
    
    /**
     * 
     * @param value The concerned value
     * @return true if the value is a father value
     */
    public boolean isFatherValue(T value);
    
    
}
