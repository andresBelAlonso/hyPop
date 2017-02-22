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

import io.tessilab.oss.hypop.parameters.managers.GridParametersManager;
import io.tessilab.oss.hypop.parameters.managers.ParametersManager;
import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParameter;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;
import io.tessilab.oss.hypop.parameters.input.ContinuousInterval;
import io.tessilab.oss.hypop.parameters.input.InputBooleanParameter;
import io.tessilab.oss.hypop.parameters.input.InputParameter;
import io.tessilab.oss.hypop.parameters.input.InputParametersSet;
import io.tessilab.oss.hypop.parameters.input.IntegerInterval;
import io.tessilab.oss.hypop.parameters.input.Interval;
import io.tessilab.oss.hypop.parameters.input.NominativeInputParameter;
import io.tessilab.oss.openutils.treedisplaying.TreeLinuxConsoleDisplay;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Andres BEL ALONSO
 */
public class TestGridManager {

    private InputParametersSet inputParams;
    private final ParameterName classifierName = new ParameterName("classifier");
    private final ParameterName kParamName = new ParameterName("kParam");
    private final ParameterName minToKnowName = new ParameterName("mintToKnow");
    private final ParameterName selectedParamsName = new ParameterName("selectedParamsPart");
    private final ParameterName forestSizeName = new ParameterName("forestSize");

    public void setUp() throws Interval.EmptyInterval, InputParameter.NotValidParameterValue {
        inputParams = new InputParametersSet();
        Map values = new HashMap();
        values.put("knn","knn");
        values.put("random forest","random forest");
        values.put("dummy classifier","dummy classifier");
        InputParameter classifierParameter = new NominativeInputParameter(classifierName, values);
        InputParameter kParam = new IntegerInterval(1, 30, kParamName, true, true);
        InputParameter minToKnow = new ContinuousInterval(0.0, 1.0, minToKnowName, true, true);
        InputParameter selectedParamsPart = new ContinuousInterval(0.0, 1.0, selectedParamsName, true, true);
        InputParameter forestSize = new IntegerInterval(11, 150, forestSizeName, true, true);
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
        inputParams.displayTreeStructure(new TreeLinuxConsoleDisplay());
    }

    @Test
    public void testJobComputing() throws Interval.EmptyInterval, InputParameter.NotValidParameterValue {
        setUp();
        GridParametersManager parametersManager = new GridParametersManager(inputParams, 10);
        assertEquals(14301, parametersManager.getNbRemainingExecutions());
    }

    @Test
    public void testSimpleJobComputing() throws Interval.EmptyInterval, InputParameter.NotValidParameterValue {
        setUp();
        GridParametersManager manager = new GridParametersManager(inputParams, 10);
        assertEquals(14301, manager.getNbRemainingExecutions());
        // verify the parameters
        while (manager.hasJobsToExplore()) {
            ExecutionParametersSet execParams = manager.getNonBuildParameters();
            verifyBasicParameters(execParams);
            // depending on the classifier, we verify that all the parameters exist
            ExecutionParameter classifier = execParams.getExecParameter(classifierName);
            if ("knn".equals(classifier.getValue())) {
                if (!execParams.containsParameter(kParamName) || !execParams.containsParameter(minToKnowName)) {
                    fail("The parameters are not complete : there is a knn without a kParam");
                }
            } else if ("random forest".equals(classifier.getValue())) {
                if (!execParams.containsParameter(forestSizeName) || !execParams.containsParameter(selectedParamsName) ||  !execParams.containsParameter(minToKnowName)) {
                    fail("The randomforest does not contains a fundamental parameter");
                }
            } else if("dummy classifier".equals(classifier.getValue())) {
                // nothing to verify
            } else {
                fail("This classifier is not know");
            }
        }
    }

    private void verifyBasicParameters(ExecutionParametersSet execParams) {
        // Verify that the parameters that must always exist, exist really
        if (!execParams.containsParameter(classifierName)) {
            fail("Parameters who always exist were not found");
        }
    }
    
    @Test
    public void testWithBooleanParameters() throws InputParameter.NotValidParameterValue {
        ParameterName param1Name = new ParameterName("param1");
        ParameterName param2Name = new ParameterName("param2");
        ParameterName param3Name = new ParameterName("param3");
        ParameterName param4Name = new ParameterName("param4");
        InputBooleanParameter param1 = new InputBooleanParameter(param1Name, "hola", "hola");
        InputBooleanParameter param2 = new InputBooleanParameter(param2Name, "que", "que");
        InputBooleanParameter param3 = new InputBooleanParameter(param3Name, "ase", "ase");
        InputBooleanParameter param4 = new InputBooleanParameter(param4Name, "folla uste", "folla uste");
        
        InputParametersSet parameterSet = new InputParametersSet();
        parameterSet.addParameter(param1);
        parameterSet.addParameter(param2);
        parameterSet.addParameter(param3);
        parameterSet.addParameter(param4);
        parameterSet.addRelation(param2, true, param3);
        parameterSet.addRelation(param2, false,param4);
        
        ParametersManager paramManager = new GridParametersManager(parameterSet, 0);
        
        assertEquals(8, paramManager.getJobsTodo());
        
        while(paramManager.hasJobsToExplore()) {
            ExecutionParametersSet paramSet = paramManager.getNonBuildParameters();
        }
    }

}
