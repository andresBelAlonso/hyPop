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
package io.tessilab.oss.hypop.parameters.managers;

import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParameter;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;
import io.tessilab.oss.hypop.parameters.input.InputParameter;
import io.tessilab.oss.hypop.parameters.input.InputParametersSet;
import io.tessilab.oss.hypop.results.ProcessResult;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * A manager who simply gives random values for each parameter set. 
 * @author Andres BEL ALONSO
 * @param <SCORE> : The score of the process result
 * @param <PROCESSRESULT> : The process result class
 */
public class RandomSearchParamManager<SCORE extends Comparable<SCORE>,PROCESSRESULT extends ProcessResult<SCORE>>
        extends ParametersManager<SCORE,PROCESSRESULT>{
    
    public static class Config<SCORE extends Comparable<SCORE>,PROCESSRESULT extends ProcessResult<SCORE>>
            extends ParametersManager.Config<SCORE,PROCESSRESULT> {
        
        private final int diffValues;
        
        public Config(int diffValues ) {
            this.diffValues = diffValues;
        }

        @Override
        protected ParametersManager<SCORE,PROCESSRESULT> build() {
            return new RandomSearchParamManager<>(this.getInputParameters(), diffValues);
        }
        
    }
    
    private final Map<ParameterName,List<? extends ExecutionParameter<?>>> possibleValues;
    private final Random randomGenerator;
    

    /**
     * 
     * @param params The parameter set
     * @param diffValues  the number of different values that are going to be use
     */
    public RandomSearchParamManager(InputParametersSet params, int diffValues) {
        super(params);
        possibleValues = new TreeMap<>();
        for(InputParameter<?> param : params.getAllParameters()) {
            possibleValues.put(param.getParameterName(), 
                    param.getPosibleValues(param.nbValues()==-1?diffValues:param.nbValues()));
        }
        randomGenerator = new Random(Calendar.getInstance().getTimeInMillis());
    }

    @Override
    public boolean hasJobsToExplore() {
        return true;
    }

    @Override
    protected ExecutionParametersSet doGetNonBuildParameters() {
        List<ExecutionParametersSet> execParams = new LinkedList<>();
        for(InputParameter<?> inputParam : this.params.getIndependentParameters()) {
            execParams.add(computeParameterAndSubParams(inputParam));
        }     
        return new ExecutionParametersSet(execParams,true);
    }

    @Override
    public int getJobsTodo() {
        return -1;
    }

    @Override
    public void canNotBeDoneNow(ExecutionParametersSet params) {
        // do not care
    }

    @Override
    public void updateObserver(PROCESSRESULT obj) {
        // do not care 
    }
     
    public<T> ExecutionParametersSet computeParameterAndSubParams(InputParameter<T> param) {
        ExecutionParametersSet parametersSet = new ExecutionParametersSet();
        List<ExecutionParameter<T>> values = (List<ExecutionParameter<T>>) this.possibleValues.get(param.getParameterName());
        ExecutionParameter<T> execParam = values.get(randomGenerator.nextInt(values.size()));
        parametersSet.addParameter(execParam);
        //Add the subparameters
        List<ExecutionParametersSet> executionParam = new LinkedList<>();
        for(InputParameter<?> subParam : param.getAssociatedSubParams(execParam))  {
            executionParam.add(computeParameterAndSubParams(subParam));
        }
        executionParam.stream().forEach(e -> {
            e.getExecutionParameters().stream().forEach(f -> {
                parametersSet.addParameter(f);
            });
        });
        return parametersSet;
    }

    @Override
    public void notifyAsNotValidParameter(ExecutionParametersSet params) {
        // OK... we do not care about
    }
    
    
    
}
