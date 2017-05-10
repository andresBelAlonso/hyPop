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

import io.tessilab.oss.hypop.parameters.managers.RandomSearchParamManager;
import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;
import io.tessilab.oss.hypop.parameters.input.ContinuousInterval;
import io.tessilab.oss.hypop.parameters.input.InputParameter;
import io.tessilab.oss.hypop.parameters.input.InputParametersSet;
import io.tessilab.oss.hypop.parameters.input.IntegerInterval;
import io.tessilab.oss.hypop.parameters.input.Interval;
import io.tessilab.oss.hypop.parameters.input.NominativeInputParameter;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Andres BEL ALONSO
 */
public class TestRandomSearch {
    
    private InputParametersSet inputParams;
    private final ParameterName classifierName = new ParameterName("classifier");
    private final ParameterName kParamName = new ParameterName("kParam");
    private final ParameterName minToKnowName = new ParameterName("mintToKnow");
    private final ParameterName selectedParamsName = new ParameterName("selectedParamsPart");
    private final ParameterName forestSizeName = new ParameterName("forestSize");
    
    public void setUp() throws Interval.EmptyInterval, InputParameter.NotValidParameterValue {
        inputParams = new InputParametersSet();
        Map<String,String> values = new HashMap<>();
        values.put("knn","knn");
        values.put("random forest","random forest");
        values.put("dummy classifier","dummy classifier");
        InputParameter<String> classifierParameter = new NominativeInputParameter<>(classifierName, values);
        InputParameter<Integer> kParam = new IntegerInterval(1, 30, kParamName, true, true);
        InputParameter<Double> minToKnow = new ContinuousInterval(0.0, 1.0, minToKnowName, true, true);
        InputParameter<Double> selectedParamsPart = new ContinuousInterval(0.0, 1.0, selectedParamsName, true, true);
        InputParameter<Integer> forestSize = new IntegerInterval(11, 150, forestSizeName, true, true);
        //Adding the parameters
        inputParams.addParameter(kParam);
        inputParams.addParameter(classifierParameter);
        inputParams.addParameter(minToKnow);
        inputParams.addParameter(selectedParamsPart);
        inputParams.addParameter(forestSize);
        inputParams.addRelation(classifierParameter, "knn", kParam);
        inputParams.addRelation(classifierParameter, "knn", minToKnow);
        inputParams.addRelation(classifierParameter, "random forest", minToKnow);
        inputParams.addRelation(classifierParameter, "random forest", selectedParamsPart);
        inputParams.addRelation(classifierParameter, "random forest", forestSize);
    }
    
    @Test
    public void testManager() throws Interval.EmptyInterval, InputParameter.NotValidParameterValue {
        setUp();
        RandomSearchParamManager<?,?> manager = new RandomSearchParamManager<>(inputParams, 10);
        ExecutionParametersSet params = manager.getNonBuildParameters();
        assertEquals(true, params.containsParameter(classifierName));
        if(params.getExecParameter(classifierName).getValue().equals("knn")) {
            assertEquals(true, params.containsParameter(kParamName) && params.containsParameter(minToKnowName));
            assertEquals(false, params.containsParameter(selectedParamsName) || params.containsParameter(forestSizeName));
        } else if(params.getExecParameter(classifierName).getValue().equals("random forest")) {
            assertEquals(true, params.containsParameter(selectedParamsName) && params.containsParameter(forestSizeName) && params.containsParameter(minToKnowName));
            assertEquals(false, params.containsParameter(kParamName));
        } else if(params.getExecParameter(classifierName).getValue().equals("dummy classifier")) {
            assertEquals(false, params.containsParameter(selectedParamsName) || params.containsParameter(forestSizeName) || params.containsParameter(kParamName) || params.containsParameter(minToKnowName));
        } else {
            fail("This classifier is unknow");
        }
    }
}
