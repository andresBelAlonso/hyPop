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
package io.tessilab.oss.hypop.results;

import java.util.Map;

/**
 * A result is a combination of several parameters that indicates the quality 
 * of the execution and an id of the corresponding execution.  
 * <p>
 * Some times this class also contains the parameter that produced this result. 
 * @author Andres BEL ALONSO
 * @param <SCORE> : the type of score
 */
public interface ProcessResult<SCORE extends Comparable> {
    
    /**
     * 
     * @return The object representing the score of a job. 
     */
    public SCORE getResultScore();
    
    public String getJobId();
    
    /**
     * A map only with the parameters of the execution
     * @return 
     */
    public Map<String,Object> getResultInDBFormat();
    
    /**
     * 
     * @return A long representing the score of the process result
     */
    public long getLongScoreRepresentation();
    
}
