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
import io.tessilab.oss.openutils.locker.JobParameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The set of parameters to do one execution.
 *
 * @author Andres BEL ALONSO
 */
public class ExecutionParametersSet implements JobParameter {
    
    private static final Logger LOGGER = LogManager.getLogger(ExecutionParametersSet.class);
    

    private final Map<ParameterName,ExecutionParameter> parameters;

    public ExecutionParametersSet(List<ExecutionParameter> posibleValues) {
        this();
        posibleValues.stream()
                .filter(e -> {return e!= null;})
                .forEach(e -> {parameters.put(e.getParamName(),e);});
    }

    /**
     * Creates the execution parameter set simply doing the fusion of the execution
     * parameters set.
     * @param groupedParams  The list of execution parameters that would form this execution parameters
     * @param dummy  this param does nothing... but is here 
     */
    public ExecutionParametersSet(List<ExecutionParametersSet> groupedParams,boolean dummy) {
        this();
        groupedParams.stream()
                .filter(e -> {return e!= null;})
                .map(ExecutionParametersSet::getParameters)
                .forEach(e -> {e.stream().filter(f -> {return f!= null;}).forEach(j-> parameters.put(j.getParamName(),j));});
    }

    public ExecutionParametersSet() {
        parameters = new TreeMap<>();
    }

    @Override
    public String getDescriptionParameters() {
        String description = "";
        for(ExecutionParameter param : parameters.values()) {
            // Check if the parameter will be use or not
            if(!(param instanceof NoContentExecutionParameter)) {
                description +=param.getStringValue() + "_";
            }
        }
        return description;
    }

    private Collection<ExecutionParameter> getParameters() {
        return parameters.values();
    }

    public int size() {
        return parameters.size();
    }

    //As a parameter to this executionParametersSet
    public void addParameter(ExecutionParameter param) {
        if(param != null ) {
            parameters.put(param.getParamName(),param);
        }
    }
    
    public boolean isEmpty() {
        return parameters.isEmpty();
    }
    
    public ExecutionParameter getExecParameter(ParameterName paramName) {
        return this.parameters.get(paramName);
    }
    
    public boolean containsParameter(ParameterName param) {
        return this.parameters.containsKey(param);
    }
    
    /**
     * 
     * @return A map containing all the parameters. Keys are the name of the 
     * parameters and the values are always the value of the corresping execution
     * parameter.
     */
    public Map<String,Object> getParametersAsJSON() {
        Map<String,Object> res = new HashMap<>();
        parameters.entrySet().stream().forEach(e -> {
            res.put(e.getKey().getParameterName(),e.getValue().getValue());
        });
        return res;
    }
    
    public List<ExecutionParameter> getExecutionParameters() {
        return new LinkedList<>(this.parameters.values());
    }

   public Map<ParameterName,ExecutionParameter> getExecutionParametersAsMap() {
       return this.parameters;
   }
    
    

}
