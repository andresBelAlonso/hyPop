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
package io.tessilab.oss.hypop.results.saver;

import io.tessilab.oss.hypop.execution.BlockConfiguration;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;
import io.tessilab.oss.hypop.results.ProcessResult;

/**
 * The interface to access to the database, or other classes that ensure the 
 * saving of the results of each execution of the problem
 * @author Andres BEL ALONSO
 * @param <SCORE> : The score of an execution result
 * @param <PROCESSRESULT> The class containing all the information about one execution
 */
public interface ResultsSaver<SCORE extends Comparable<SCORE>,PROCESSRESULT extends ProcessResult<SCORE>> {
    
    public static abstract class Config<SCORE extends Comparable<SCORE>,PROCESSRESULT extends ProcessResult<SCORE>>
            extends BlockConfiguration<ResultsSaver<SCORE,PROCESSRESULT>> {
    }
    
    /**
     * 
     * @param params The parameters of the job
     * @return The process result of an ended job do by other process than this one.
     */
    public SaverAnswer getJobDone(ExecutionParametersSet params);
    
    public void saveResult(ExecutionParametersSet param, PROCESSRESULT res);
    
}
