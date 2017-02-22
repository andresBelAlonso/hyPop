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

import io.tessilab.oss.hypop.parameters.input.Interval;
import io.tessilab.oss.hypop.parameters.input.ContinuousInterval;
import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParameter;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Andres BEL ALONSO
 */
public class TestContinuosInterval {
    private ContinuousInterval openInterval; // ]0,10[
    private ContinuousInterval closeInterval; // [0,10]
    private ContinuousInterval halfInterval1; // ]0,10]
    private ContinuousInterval halfInterval2; // [0;10[
    
    private void setUp() throws Interval.EmptyInterval {
        this.openInterval = new ContinuousInterval(0.0, 10.0, new ParameterName("open"), false, false);
        this.closeInterval = new ContinuousInterval(0.0, 10.0, new ParameterName("close"), true, true);
        this.halfInterval1 = new ContinuousInterval(0.0, 10.0, new ParameterName("half1"), false, true);
        this.halfInterval2 = new ContinuousInterval(0.0, 10.0, new ParameterName("half2"), true, false);        
    }
    
    @Test
    public void testNbValues() throws Interval.EmptyInterval {
        setUp();
        assertEquals(-1, closeInterval.nbValues());
        assertEquals(-1, halfInterval1.nbValues());
        assertEquals(-1, openInterval.nbValues());
    }
    
    @Test
    public void testInterval() throws Interval.EmptyInterval {
        setUp();
        List<ExecutionParameter> execParams = closeInterval.getPosibleValues(11);
        assertEquals(11, execParams.size());
        for (double i = 0; i < 11; i++) {
            assertEquals(true, execParams.contains(new ExecutionParameter( new ParameterName("close"), i, String.valueOf(i))));
        }
        execParams = openInterval.getPosibleValues(9);
        assertEquals(9, execParams.size());        
        for(double i=1; i<10; i++) {
            assertEquals(true, execParams.contains(new ExecutionParameter( new ParameterName("open"), i, String.valueOf(i))));           
        }
        execParams = halfInterval1.getPosibleValues(10);
        assertEquals(10, execParams.size());         
        for(double i=1; i<11; i++) {
            assertEquals(true, execParams.contains(new ExecutionParameter( new ParameterName("half1"), i, String.valueOf(i))));            
        }
        execParams = halfInterval2.getPosibleValues(10);
        assertEquals(10, execParams.size());            
        for(double i=0; i<10; i++) {
            assertEquals(true, execParams.contains(new ExecutionParameter( new ParameterName("half2"), i, String.valueOf(i))));            
        }
        
    }
}
