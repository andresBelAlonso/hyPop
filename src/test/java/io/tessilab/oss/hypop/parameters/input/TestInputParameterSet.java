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
import io.tessilab.oss.hypop.parameters.input.InputParametersSet;
import io.tessilab.oss.hypop.parameters.input.Interval;
import io.tessilab.oss.hypop.parameters.input.InputParameter;
import io.tessilab.oss.hypop.parameters.input.ContinuousInterval;
import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.openutils.treedisplaying.TreeLinuxConsoleDisplay;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Andres BEL ALONSO
 */
public class TestInputParameterSet {
    
    @Test
    public void testTreeBuilding() throws Interval.EmptyInterval, InputParameter.NotValidParameterValue {
        // test the javadoc test building
        InputParametersSet paramSet = new InputParametersSet();
        Integer param1Val1 = 3;
        Integer param1Val2 = 12;
        Integer param1Val3 = 15;
        Integer param1Val4 = 27;
        Integer param1Val5 = 42;
        Map<String,Integer> param1Set = new HashMap<>();
        // param1 building
        param1Set.put(String.valueOf(param1Val1),param1Val1);
        param1Set.put(String.valueOf(param1Val2),param1Val2);
        param1Set.put(String.valueOf(param1Val3),param1Val3);
        param1Set.put(String.valueOf(param1Val4),param1Val4);
        param1Set.put(String.valueOf(param1Val5),param1Val5);
        NominativeInputParameter<Integer> param1 = 
                new NominativeInputParameter<>(new ParameterName("param1"), param1Set);
        // param 2 building
        ContinuousInterval param2 = new ContinuousInterval(0.0, 10.0, new ParameterName("param2"), true, true);
        // param 3 building
        IntegerInterval param3 = new IntegerInterval(0, 10, new ParameterName("param3"), true, true);
        // param 4 building
        ContinuousInterval param4 = new ContinuousInterval(0.0, 10.0, new ParameterName("param4"), true, true);
        // param 5 building
        File file1 = new File("");
        BufferedImage image = new BufferedImage(1, 1, 1);
        Map<String,Object> param5Set = new HashMap<>();
        param5Set.put("file1",file1);
        param5Set.put("image",image);
        NominativeInputParameter<Object> param5 = new NominativeInputParameter<>(new ParameterName("param5"), param5Set);
        //param 6 building
        IntegerInterval param6 = new IntegerInterval(0, 10, new ParameterName("param6"), true, true);
        // paramset building
        paramSet.addParameter(param1);
        paramSet.addParameter(param2);
        paramSet.addParameter(param3);
        paramSet.addParameter(param4);
        paramSet.addParameter(param5);
        paramSet.addParameter(param6);
        paramSet.addRelation(param1, param1Val1, param2);
        paramSet.addRelation(param2,new ContinuousInterval(0.0, 1.0, new ParameterName(""), true, true), param3);
        paramSet.addRelation(param1, Arrays.asList(param1Val3,param1Val4,param1Val1), param4);
        paramSet.addRelation(param5, image, param6);
        // displaying
        paramSet.displayTreeStructure(new TreeLinuxConsoleDisplay());
        
        //verification
        assertEquals(6, paramSet.getCompleteSize());
        assertEquals(true, paramSet.getAllParameters().contains(param1));
        assertEquals(true, paramSet.getAllParameters().contains(param2));
        assertEquals(true, paramSet.getAllParameters().contains(param3));
        assertEquals(true, paramSet.getAllParameters().contains(param4));        
        assertEquals(true, paramSet.getAllParameters().contains(param5));
        assertEquals(true, paramSet.getAllParameters().contains(param6));
        assertEquals(2, paramSet.getIndependentParametersSize());
        assertEquals(true, paramSet.getIndependentParameters().contains(param1));
        assertEquals(true, paramSet.getIndependentParameters().contains(param5));        
        //Relations  
        
    }
    
}
