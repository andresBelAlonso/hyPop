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
package io.tessilab.oss.hypop.extinterface;

import io.tessilab.oss.hypop.execution.BlockConfiguration;
import io.tessilab.oss.hypop.results.ProcessResult;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;
import io.tessilab.oss.hypop.parameters.input.InputParametersSet;
import io.tessilab.oss.hypop.results.saver.SaverAnswer;

/**
 * The interface that makes the link between hyPop and the problem that must optimize.
 * <p>
 * Usually this class and {@link io.tessilab.oss.hypop.results.ProcessResult} are 
 * the two class that must be override to make the link with the external problem.
 * @author Andres BEL ALONSO
 */
public interface ProcessInterface {

    public static abstract class Config extends BlockConfiguration<ProcessInterface> {
    }

    /**
     * Computes one execution of the problem with the parameters pass to this method.
     * @param params  The parameters of the execution
     * @return The 
     */
    public abstract ProcessResult computeIt(ExecutionParametersSet params);
    
    /**
     * This function creates the global parameters of the optimisation. In other 
     * words a representation of all the possible values than the different parameters
     * of the problem can take. 
     * <p>
     * See the wiki for more precise considerations
     * @return The representation of the problems parameters
     */
    public abstract InputParametersSet createInputParameters();
    
    /**
     * Creates a process result from the result of a database
     * <p>
     * It is very common, for some parameters managers (like {@link io.tessilab.oss.hypop.parameters.managers.ParametersManager}
     * that in a multiexecution context, one thread has already done the job. In 
     * this case the the {@link io.tessilab.oss.hypop.execution.ExecutionRun} wants 
     * the unknow result and will use this method to have a @link io.tessilab.oss.hypop.results.ProcessResult}
     * who must contain the same fields than the method {@link io.tessilab.oss.hypop.extinterface.ProcessInterface#computeIt(io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet) }
     * will have, with the output of the database
     * @param jobDone : The database contain that has been saved after the 
     * execution of a job. 
     * @return An object with the information of the execution, with the same information than
     * {@link io.tessilab.oss.hypop.extinterface.ProcessInterface#computeIt(io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet) produce}
     */
    public ProcessResult createResult(SaverAnswer jobDone);

    
}
