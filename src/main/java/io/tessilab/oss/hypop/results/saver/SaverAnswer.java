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

import java.util.HashMap;
import java.util.Map;

/**
 * The class representing the answer of a {@link io.tessilab.oss.hypop.results.saver.ResultsSaver}
 * containing all the informations. 
 * @author Andres BEL ALONSO
 */
public class SaverAnswer {
    
    private final String jobId;
    
    private final Map<String,Object> content;

    public SaverAnswer(String jobId) {
        content = new HashMap<>();
        this.jobId = jobId;
    }

    public SaverAnswer(Map<String, Object> content,String jobId) {
        this.content = content;
        this.jobId = jobId;
    }
    
    public Object getValue(String paramName) {
        return content.get(paramName);
    }

    public String getJobId() {
        return jobId;
    }
    
    public Map<String,Object> getContent() {
        return this.content; 
    }
    
}
