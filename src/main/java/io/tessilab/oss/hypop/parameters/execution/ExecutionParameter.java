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

import io.tessilab.oss.hypop.exceptions.HyperParameterSearchError;
import io.tessilab.oss.hypop.parameters.ParameterName;
import java.util.Objects;

/**
 * A single value can take a single parameter, for a single execution. 
 * @author Andres BEL ALONSO
 * @param <T> The type of the value of this execution parameter
 */
public class ExecutionParameter<T> implements Comparable<ExecutionParameter<T>>{
    
    /**
     * The name of the current parameter
     */
    private final ParameterName paramName;
    /**
     * The value of the parameter
     */
    private final T value;
    
    /**
     * A string that represents the value taken by the parameter
     */
    private final String stringValue;

    public ExecutionParameter(ParameterName paramName, T value, String stringValue) {
        if(paramName ==  null || value == null) {
            throw new HyperParameterSearchError("A parameter name is null");
        }
        this.paramName = paramName;
        this.value = value;
        this.stringValue = stringValue;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.paramName);
        hash = 89 * hash + Objects.hashCode(this.value);
        hash = 89 * hash + Objects.hashCode(this.stringValue);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExecutionParameter other = (ExecutionParameter) obj;
        if (!Objects.equals(this.stringValue, other.stringValue)) {
            return false;
        }
        if (!Objects.equals(this.paramName, other.paramName)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }



    public ParameterName getParamName() {
        return paramName;
    }

    public T getValue() {
        return value;
    }

    /**
     * Only compares the names of the execution parameters.
     * <p>
     * So, <U><B>this compareTo does not complain with the equals (and the hashCode) 
     * contract.</B></U> In fact compareTo is only use by the class who sort all the values take
     * by all the parameters, and we want an order only over the name. 
     * @param o : The other execution parameters
     * @return negative integer, 0 or possitive if it is respectively lower than,
     * if this object is equal than or greater than the object passed in argument.
     */
    @Override
    public int compareTo(ExecutionParameter<T> o) {
        return this.paramName.compareTo(paramName);   
    }

    public String getStringValue() {
        return stringValue;
    }
    
    /**
     * 
     * @return true if the value contained in this execution parameter is a value that must be consider as it is, and
     * not as an absent value for the current execution
     */
    public boolean containsARealValue() {
        // Here we always return true, because it is always real
        return true;
    }
    
    
    
    
}
