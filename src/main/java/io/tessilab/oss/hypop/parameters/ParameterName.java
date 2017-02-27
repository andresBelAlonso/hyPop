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
package io.tessilab.oss.hypop.parameters;

import java.util.Objects;

/**
 * A representation of the name of a parameter. 
 * @author Andres BEL ALONSO
 */
public class ParameterName implements Comparable<ParameterName>{
    

    private final String parameterName;

    /**
     * The string representing the name of a parameter
     * @param parameterName A string with the name
     */
    public ParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterName() {
        return parameterName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.parameterName);
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
        final ParameterName other = (ParameterName) obj;
        if (!Objects.equals(this.parameterName, other.parameterName)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(ParameterName o) {
        return o==null?-1:this.parameterName.compareTo(o.parameterName);
    }
    
    
    
}
