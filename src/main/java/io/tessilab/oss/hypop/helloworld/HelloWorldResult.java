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
package io.tessilab.oss.hypop.helloworld;

import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;
import io.tessilab.oss.hypop.results.ProcessResult;
import io.tessilab.oss.hypop.results.saver.SaverAnswer;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Andres BEL ALONSO
 */
public class HelloWorldResult implements ProcessResult<Double>{
    
    private final String printedMsg;
    // The source and target variables 
    private final String source;
    private final String target;
    private final String executionId;
    
    // it is always a good idea to pass to the process result the parameters that 
    // was used during the execution, but not essential
    public HelloWorldResult(String printedMsg, ExecutionParametersSet executionParameters) {
        this.printedMsg = printedMsg;
        // it will be necesary to save the parameters
        this.source = (String) executionParameters.getExecParameter(new ParameterName("source")).getValue();
        this.target = (String) executionParameters.getExecParameter(new ParameterName("target")).getValue();
        // another good idea idea is to use the job id provided by this method, in
        // order to be sure that we have an unique execution id
        this.executionId = executionParameters.getDescriptionParameters();
    }

    // The constructor used when the execution is not computed in this computer
    // but in another. In this case we also need to build a ProcessResult, but
    // with the the results of the database
    public HelloWorldResult(SaverAnswer jobDone) {
        // We expect to have to same fields thant the fields writed in the method
        // getResultInDBFormat in this method
        Map<String,Object> map = jobDone.getContent();
        this.printedMsg = (String) map.get("msg");
        this.source = (String) map.get("source");
        this.target = (String) map.get("target");
        this.executionId = (String) map.get("executionId");
    }

    @Override
    public Double getResultScore() {
        //The score of a result is 0 except when the printed message was the correct one
        return printedMsg.equals("Hello World. This is HyPop.")?1.0:0.0;
    }

    @Override
    public String getJobId() {
        // This is the string that will identify the execution on the database.
        // It will be necesary to create different id for different executions
        // ExecutionParamtersSet.getDescriptionParameters() is a good source
        return executionId;
    }
    
    @Override
    public Map<String, Object> getResultInDBFormat() {
        // The informations that will be saved in the database
        Map<String,Object> res = new HashMap<>();
        // We add the used parameters
        res.put("target", target);
        res.put("source", source);
        // We add the score
        res.put("score", this.getResultScore());
        // We add the the printed message
        res.put("msg", printedMsg);
        return res;
    }

    @Override
    public long getLongScoreRepresentation() {
        // In this case we simply cast or double score on a long one.
        // This function is used by another methods, and the implementation is 
        // not so trivial if the object representing the score is not a number.
        return this.getResultScore().longValue();
    }
    
}
