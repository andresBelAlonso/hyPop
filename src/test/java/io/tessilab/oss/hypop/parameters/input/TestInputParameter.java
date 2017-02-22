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
package io.tessilab.oss.hypop.parameters.input;

import io.tessilab.oss.hypop.parameters.input.NominativeInputParameter;
import io.tessilab.oss.hypop.parameters.input.IntegerInterval;
import io.tessilab.oss.hypop.parameters.input.Interval;
import io.tessilab.oss.hypop.parameters.input.InputParametersSet;
import io.tessilab.oss.hypop.parameters.input.InputParameter;
import io.tessilab.oss.hypop.parameters.input.ContinuousInterval;
import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.openutils.treedisplaying.TreeLinuxConsoleDisplay;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Andres BEL ALONSO
 */
public class TestInputParameter {
    
    private InputParametersSet inputParams;
    private InputParameter classifierParameter;
    private InputParameter minToKnow;
    private InputParameter kParam;
            
            
    
    public void setUp() throws Interval.EmptyInterval, InputParameter.NotValidParameterValue {
        inputParams = new InputParametersSet();
        Map values = new HashMap<>();
        values.put("knn","knn");
        values.put("random forest", "random forest");
        values.put("dummy classifier", "dummy classifier");
        classifierParameter = new NominativeInputParameter(new ParameterName("classifier"), values);
        kParam = new IntegerInterval(0, 30, new ParameterName("kParam"), true, true);
        minToKnow = new ContinuousInterval(0.0, 1.0, new ParameterName("mintToKnow"), true, true);
        InputParameter selectedParamsPart = new ContinuousInterval(0.0, 1.0, new ParameterName("selectedParamsPart"), true, true);
        InputParameter forestSize = new IntegerInterval(10, 150, new ParameterName("forestSize"), true, true);
        //Adding the parameters
        inputParams.addParameter(kParam);
        inputParams.addParameter(classifierParameter);
        inputParams.addParameter(minToKnow);
        inputParams.addParameter(selectedParamsPart);
        inputParams.addParameter(forestSize);
        inputParams.addRelation(classifierParameter, "knn", kParam);
        inputParams.addRelation(classifierParameter,"knn",minToKnow);
        inputParams.addRelation(classifierParameter,"random forest",minToKnow);
        inputParams.addRelation(classifierParameter,"random forest",selectedParamsPart);
        inputParams.addRelation(classifierParameter, "random forest", forestSize);
        inputParams.displayTreeStructure(new TreeLinuxConsoleDisplay());
    }
    
    @Test
    public void testGetAssociatedSubParams() throws Interval.EmptyInterval, InputParameter.NotValidParameterValue {
        setUp();
        assertEquals(5,classifierParameter.getSubParameters().size());
        assertEquals(2,classifierParameter.getAssociatedSubParams("knn").size());
        List subParams = classifierParameter.getAssociatedSubParams("knn");
        assertEquals(true, subParams.contains(kParam));
        assertEquals(true, subParams.contains(minToKnow));
        
    }
}
