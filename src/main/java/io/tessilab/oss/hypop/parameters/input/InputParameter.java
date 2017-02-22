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
import io.tessilab.oss.hypop.parameters.subparameters.SubParameterRelationInput;
import io.tessilab.oss.openutils.treedisplaying.TreeStructure;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The class representing an input parameter, describing all the values than a 
 * single parameter can take. 
 * <p>
 * An input parameter has a tree
 * structure showing that the existance of some parameters may cause another
 * parameters to exists.
 *
 * @author Andres BEL ALONSO
 * @param <PARAM_TYPE> : the type of parameter of this object
 */
public abstract class InputParameter<PARAM_TYPE> implements Comparable<InputParameter>,TreeStructure {
    
    /**
     * The exception is throw we a non existing value of this parameter is associated
     * with this parameter
     */
    public static class NotValidParameterValue extends Exception {
        public NotValidParameterValue() {
        }

        public NotValidParameterValue(String message) {
            super(message);
        }
        
        
    }

    private final ParameterName parameterName;
    protected final List<SubParameterRelationInput> subParameters;

    /**
     *
     * @return a set with the depending suparameters or an empty list if it is a
     * leaf parameter
     */
    public List<SubParameterRelationInput> getSubParameters() {
        return subParameters;
    }

    /**
     *
     * @param maxValues : the maximun number of values to return
     * @return A set of possible values. This method is call with no warantly
     * than two calls with the same parameter will give the same values.
     * especially when the parameter maxvalues is lower than the number of 
     * different possible values
     */
    public abstract List<ExecutionParameter> getPosibleValues(int maxValues);

    /**
     *
     * @return the number of different values that this class can take. If there
     * is not a limit of values (like in continuous intervals), -1 is returned
     */
    public abstract int nbValues();

    public InputParameter(ParameterName parameterName) {
        this.parameterName = parameterName;
        this.subParameters = new ArrayList<>();
    }

    public ParameterName getParameterName() {
        return parameterName;
    }

    @Override
    public final int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.parameterName);
        return hash;
    }

    /**
     * Equals only verifies (apart the null and class verification) that name of 
     * the parameter name. So, two parameters of the same type with the same name 
     * but with different values (like intervals with different borders) are equal
     * @param obj
     * @return 
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InputParameter other = (InputParameter) obj;
        if (!Objects.equals(this.parameterName, other.parameterName)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(InputParameter o) {
        return parameterName.compareTo(o.getParameterName());
    }

    public int getCompleteSize() {
        int size = 0;
        for(SubParameterRelationInput subParam : subParameters) {
            size += subParam.getSubParameter().getCompleteSize();
        }
        return size;
    }

    /**
     *
     * @return true if this parameter has not other subparameters
     */
    public boolean isLeafParameter() {
        return getSubParameters().isEmpty();
    }
    
    public abstract void addSubparameter(InputParameter param,PARAM_TYPE value) throws NotValidParameterValue;
    
    public void addSubparameter(InputParameter param,List<PARAM_TYPE> values) throws NotValidParameterValue {
        for(PARAM_TYPE val : values) {
            addSubparameter(param, val);
        }
    }
    
    /**
     * Returns the input parameters related to the value or an empty list if there are not. 
     * @param value
     * @return 
     */
    public List<InputParameter> getAssociatedSubParams(PARAM_TYPE value) {
        return this.subParameters.stream()
          .filter( e -> {
              return ((SubParameterRelationInput) e).isFatherValue(value);
          }).map(SubParameterRelationInput::getSubParameter)
          .collect(Collectors.toList());
    }

    @Override
    public String getNodeName() {
        return this.parameterName.getParameterName();
    }

    @Override
    public List<TreeStructure> getChilds() {
        return this.subParameters.stream().map(SubParameterRelationInput::getSubParameter)
                                          .distinct()
                                          .collect(Collectors.toList());
    }

    @Override
    public boolean isLeaf() {
        return this.subParameters.isEmpty();
    }
    


}
