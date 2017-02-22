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
import io.tessilab.oss.hypop.parameters.control.ExecParametersFilter;
import io.tessilab.oss.openutils.treedisplaying.TreeDisplayer;
import io.tessilab.oss.openutils.treedisplaying.TreeStructure;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * The class to describe the set of possible parameters give to the execution
 * manager
 *
 * @author Andres BEL ALONSO
 */
public class InputParametersSet implements TreeStructure{
    

    private final TreeMap<ParameterName,InputParameter> allParameters;
    /**
     * Here there are only the parameters that have not subparameters(so soons of
     * root)
     */
    private final TreeMap<ParameterName,InputParameter> orderedParams;
    private final List<ExecParametersFilter> filters;

    public InputParametersSet() {
        this.allParameters = new TreeMap<>();
        this.orderedParams = new TreeMap<>();
        this.filters = new ArrayList<>();
    }

    /**
     *
     * @return the total number of parameters (each subparameter is counted)
     */
    public int completeSize() {
        int size = 0;
        for (InputParameter param : allParameters.values()) {
            size += 1 + param.getCompleteSize();
        }
        return size;
    }

    public Collection<InputParameter> getAllParameters() {
        return allParameters.values();
    }
    
    public Collection<InputParameter> getIndependentParameters() {
        return orderedParams.values();
    }

    /**
     * 
     * @return : The total number of single parameters of this inputparameterSet 
     */
    public int getCompleteSize() {
        return allParameters.size();
    }
    
    public int getIndependentParametersSize() {
        return orderedParams.size();
    }
    
    /***** Add API *****/ 
    
    /**
     * 
     * @param parameter 
     */
    public void addParameter(InputParameter parameter) {
        this.allParameters.put(parameter.getParameterName(),parameter);
        this.orderedParams.put(parameter.getParameterName(),parameter);
    }
    
    /**
     * Adds a relation between two parameters. 
     * A relation is some values related with an input parameter who is a subparameter
     * @param subParameter
     * @param singleValue
     * @param fatherParameter
     */
    public void addRelation(InputParameter fatherParameter, Object singleValue,InputParameter subParameter) throws InputParameter.NotValidParameterValue {
        fatherParameter.addSubparameter(subParameter, singleValue);
        verifyParamsAndAddNonExistingOnes(Arrays.asList(subParameter,fatherParameter));
        deleteDependParameter(subParameter);
    }
    
    public void addRelation(InputParameter fatherParameter, List values,InputParameter subParameter) throws InputParameter.NotValidParameterValue {
        fatherParameter.addSubparameter(subParameter, values);
        verifyParamsAndAddNonExistingOnes(Arrays.asList(subParameter,fatherParameter)); 
        deleteDependParameter(subParameter);
    }
    
    public void addRelation(InputParameter fatherParameter,ContinuousInterval values, ContinuousInterval subParameter) throws InputParameter.NotValidParameterValue {
        fatherParameter.addSubparameter(subParameter, values);
        verifyParamsAndAddNonExistingOnes(Arrays.asList(subParameter,fatherParameter));        
        deleteDependParameter(subParameter);
    }
    
    public void displayTreeStructure(TreeDisplayer displayer) {
        displayer.displayTree(this);
    }

    @Override
    public String getNodeName() {
        return "root";
    }

    @Override
    public List<TreeStructure> getChilds() {
        return this.orderedParams.values().stream().collect(Collectors.toList());
    }

    @Override
    public boolean isLeaf() {
        return this.orderedParams.isEmpty();
    }
    
    private void verifyParamsAndAddNonExistingOnes(List<InputParameter> params) {
        for(InputParameter param : params) {
            if(!this.allParameters.containsKey(param.getParameterName())) {
                this.allParameters.put(param.getParameterName(),param);
            }
        }
    }

    private void deleteDependParameter(InputParameter subParameter) {
        if(this.orderedParams.containsKey(subParameter.getParameterName())) {
            orderedParams.remove(subParameter.getParameterName());
        }
    }

    /**
     * Looks in all the parameters to see if there is a parameter with this name
     * @param inputParameter
     * @return True if there a parameter with this name. 
     */
    public boolean hasParameter(ParameterName inputParameter) {
        return allParameters.containsKey(inputParameter);
    }
    
    /**
     * After call this, please call hasParameter to prevent to have null as 
     * result return. 
     * @param inParam
     * @return The parameter with this name. Note that there can not be two parameters
     * with the same name, or null if it does not exist.
     */
    public InputParameter getParameter(ParameterName inParam) {
       return allParameters.get(inParam);
    }
    
    /**
     * 
     * @param inParam
     * @return true if the parameterName passed in argument is the name of a direct
     * root child parameter (i.e. : there is not another param that has the parameter 
     * name passed in argument as his child)
     */
    public boolean isRootParameter(ParameterName inParam) {
        return this.orderedParams.containsKey(inParam);
    }
    
    /**
     * Adds a filter to these input parameters
     * @param filter 
     */
    public void addParametersConstraint(ExecParametersFilter filter) {
        this.filters.add(filter);
    }
    
    /**
     * 
     * @return All the filters added to these inputParameters 
     */
    public List<ExecParametersFilter> getAllFilters() {
        return this.filters;
    }
    
    
    
    

}
