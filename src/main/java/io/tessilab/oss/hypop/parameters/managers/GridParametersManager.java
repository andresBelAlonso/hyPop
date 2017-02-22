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

import io.tessilab.oss.hypop.exceptions.HyperParameterSearchError;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParameter;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;
import io.tessilab.oss.hypop.parameters.input.InputParameter;
import io.tessilab.oss.hypop.parameters.input.InputParametersSet;
import io.tessilab.oss.hypop.results.ProcessResult;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * The {@link io.tessilab.oss.hypop.parameters.managers.ParametersManager} who 
 * retrives the parameters in order to performm a grid search ( combination of 
 * all the possible values of all the possible parameters) 
 * <p>
 * (For more details, see the Grid Search paragraph here : 
 * https://en.wikipedia.org/wiki/Hyperparameter_optimization ) 
 * 
 * 
 * @author Andres BEL ALONSO
 */
public class GridParametersManager extends ParametersManager {

    private static final Logger LOGGER = LogManager.getLogger(GridParametersManager.class);

    public static class Config extends ParametersManager.Config {


        private final int nbValsInInterval;

        public Config(int nbvals) {
            super();
            this.nbValsInInterval = nbvals;
        }

        @Override
        protected GridParametersManager build() {
            return new GridParametersManager(this.getInputParameters(), nbValsInInterval);
        }

    }
    

    // The list of remaining parameters to do 
    private List<ExecutionParametersSet> parametersToDo;

    private final Random randomGenerator;

    private int totalJobs;

    private final int nbValsInInterval;

    /**
     *
     * @param set : the input parameter entry set
     * @param nbValsInInterval : the number of maximal elements that we are
     * going to take from a continuous interval.
     */
    public GridParametersManager(InputParametersSet set, int nbValsInInterval) {
        super(set);
        this.nbValsInInterval = nbValsInInterval;
        List<ExecutionParametersSet>[] inputParams = new List[set.getIndependentParametersSize()];
        int counter = 0;
        // first we compute de input parameters of each independent parameter
        LOGGER.trace("Computing the root execution parameters");
        for (InputParameter curParam : set.getIndependentParameters()) {
            inputParams[counter] = computeExecutionParameters(curParam);
            counter++;
        }
        // we fusion it
        LOGGER.trace("Making the fusion of the root parameters");
        parametersToDo = fusionExecParams(inputParams);
        LOGGER.trace("The fusion has been ended");
        randomGenerator = new Random();
    }

    public final int getNbRemainingExecutions() {
        return parametersToDo.size();
    }

    public int getTotalJobs() {
        return totalJobs;
    }

    @Override
    public int getJobsTodo() {
        return this.parametersToDo.size();
    }
    
    

    public void treatParameter(Function<ExecutionParametersSet, ExecutionParametersSet> processing) {
        LOGGER.info("Applying a treatment to the execution parameters");
        parametersToDo = parametersToDo.stream().map(processing).filter(Objects::nonNull).filter(p -> !p.isEmpty()).collect(Collectors.toList());
        this.totalJobs = parametersToDo.size();
        LOGGER.info("The treatment has been done. There are {} execution parameters", totalJobs);
    }

    /**
     * Checks to builded parameters and returns a parameter. This method will
     * never return two times the same values. It suposes that if a value is
     * give, it will be use or it will be a job already done.
     *
     * @return the parameters used by the classifier to be build. Returns null
     * if the map is empty
     */
    @Override
    public ExecutionParametersSet getNonBuildParameters() {
        // The vectorization method selection?
        if (!hasJobsToExplore()) {
            throw new HyperParameterSearchError("No more parameters to get. Plase call has jobs to explore before calling this method");
        }
        int index = randomGenerator.nextInt(parametersToDo.size());
        return parametersToDo.remove(index);
    }

    @Override
    public boolean hasJobsToExplore() {
        return !parametersToDo.isEmpty();
    }

    @Override
    public void updateObserver(ProcessResult res) {
        // Nothing to do , this parameters manager does not care about the results
    }

    @Override
    public void canNotBeDoneNow(ExecutionParametersSet params) {
        // The job could not be done, it is simply added to the job list
        if(!this.parametersToDo.contains(params)) {
            LOGGER.trace("Reading the job {} to the to do list",params.getDescriptionParameters());
            parametersToDo.add(params);
        } else {
            LOGGER.warn("No need to add the job {} to the to param list because it was already.",params.getDescriptionParameters());
        }
    }

    @Override
    public void notifyAsNotValidParameter(ExecutionParametersSet params) {
        // Nothing to do
    }
    
    

    
    
    
    

    private List<ExecutionParametersSet> computeExecutionParameters(InputParameter param) {
        LOGGER.trace("Computing the parameters for {}",param.getParameterName().getParameterName());
        int parameterDiffVals = param.nbValues() == -1?this.nbValsInInterval:param.nbValues();
        if (param.isLeafParameter()) {
            return (List<ExecutionParametersSet>) param.getPosibleValues(parameterDiffVals)
                    .stream()
                    .map(e -> {
                        return new ExecutionParametersSet(Arrays.asList((ExecutionParameter) e))
                        ;})
                    .collect(Collectors.toCollection(LinkedList<ExecutionParametersSet>::new));                                                     
        } else {
            // the parameter has some subparameters
            List<ExecutionParametersSet> resultSet = new LinkedList();
            List<ExecutionParameter> execParamList =  param.getPosibleValues(parameterDiffVals);
            for(ExecutionParameter curExecutionParameter : execParamList) {
                // We look for each value if there is a subparameter
                List<InputParameter> subParameters  = param.getAssociatedSubParams(curExecutionParameter.getValue());
                if(subParameters.isEmpty()) {
                    // for this value there are not subparameters
                    resultSet.add(new ExecutionParametersSet(Arrays.asList(curExecutionParameter)));
                } else {
                    List<ExecutionParametersSet>[] outParams = new List[subParameters.size() + 1];
                    int counter = 0;
                    for(InputParameter subParam : subParameters) {
                        outParams[counter] = computeExecutionParameters(subParam);
                        counter++;
                    }
                    outParams[outParams.length-1] = Arrays.asList(new ExecutionParametersSet(Arrays.asList(curExecutionParameter)));
                    resultSet.addAll(fusionExecParams(outParams));
                }
            }
            return resultSet; 
        }
    }

    /**
     * Fusions all the parameters at the same level
     * @param inputParams
     * @return 
     */
    private List<ExecutionParametersSet> fusionExecParams(List<ExecutionParametersSet>[] inputParams) {
        int[] maxPointers = new int[inputParams.length];
        int[] pointers = new int[inputParams.length];
        for (int j = 0; j < inputParams.length; j++) {
            maxPointers[j] = inputParams[j].size() - 1;
        }
        List<ExecutionParametersSet> res = new LinkedList<>();
        do {
            List<ExecutionParametersSet> midResult = new LinkedList<>();
            for (int i = 0; i < inputParams.length; i++) {
                midResult.add(inputParams[i].get(pointers[i]));
            }
            res.add(new ExecutionParametersSet(midResult, true));
        } while (increase(pointers, maxPointers));
        return res;
    }

    /**
     * Increases like the hours, minutes and seconds
     *
     * @param pointers
     * @return if the array was modified
     */
    private static boolean increase(int[] pointers, int[] maxPointers) {
        int counter = 0;
        while (counter < pointers.length && pointers[counter] >= maxPointers[counter]) {
            pointers[counter++] = 0;
        }
        if (counter == pointers.length) {
            return false;
        }
        pointers[counter]++;
        return true;
    }
    
    

}
