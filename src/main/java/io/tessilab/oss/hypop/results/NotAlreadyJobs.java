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

import io.tessilab.oss.hypop.exceptions.HyperParameterSearchError;
import java.util.Map;

/**
 * The class representing the fact thar there is not already a job
 * @author Andres BEL ALONSO
 */
public class NotAlreadyJobs implements ProcessResult<Double>{
    
    private NotAlreadyJobs() {
    }
    
    public static NotAlreadyJobs getInstance() {
        return NotAlreadyJobsHolder.INSTANCE;
    }

    @Override
    public Double getResultScore() {
        throw new HyperParameterSearchError("A result was get before it exists");
    }

    @Override
    public String getJobId() {
        return "noJobs";
    }

    @Override
    public Map<String, Object> getResultInDBFormat() {
        // this method should not be call
        throw new HyperParameterSearchError("A result was get before it exists");        
    }

    @Override
    public long getLongScoreRepresentation() {
        // TODO : implemente cette méthode
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static class NotAlreadyJobsHolder {

        private static final NotAlreadyJobs INSTANCE = new NotAlreadyJobs();
    }
}
