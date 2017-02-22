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
package io.tessilab.oss.hypop.parameters.subparameters;

import io.tessilab.oss.hypop.parameters.subparameters.SingleSubParameterRelationInput;
import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.hypop.parameters.input.InputParameter;
import io.tessilab.oss.hypop.parameters.input.IntegerInterval;
import io.tessilab.oss.hypop.parameters.input.Interval;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Andres BEL ALONSO
 */
public class SingleSubParamTest {
    
    private SingleSubParameterRelationInput<String> singleSubParameterRel;
    private InputParameter param;
    
    public void setUp() throws Interval.EmptyInterval {
        param =  new IntegerInterval(0, 10, new ParameterName("adio"), true, true);
        singleSubParameterRel = new SingleSubParameterRelationInput("hola", 
                param,
                new ParameterName("holis"));
    }
    
    @Test
    public void TestIsFatherValue() throws Interval.EmptyInterval {
        setUp();
        assertEquals(true,singleSubParameterRel.isFatherValue("hola"));
    }
}
