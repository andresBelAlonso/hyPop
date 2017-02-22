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

import io.tessilab.oss.hypop.parameters.input.IntegerInterval;
import io.tessilab.oss.hypop.parameters.input.Interval;
import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParameter;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Andres BEL ALONSO
 */
public class IntegerIntervalTest {

    private IntegerInterval openInterval; // ]0,10[
    private IntegerInterval closeInterval; // [0,10]
    private IntegerInterval halfInterval; // ]0,10]

    private void setUp() throws Interval.EmptyInterval {
        this.openInterval = new IntegerInterval(0, 10, new ParameterName("open"), false, false);
        this.closeInterval = new IntegerInterval(0, 10, new ParameterName("close"), true, true);
        this.halfInterval = new IntegerInterval(0, 10, new ParameterName("half"), true, false);
    }

    @Test
    public void testNbValues() throws Interval.EmptyInterval {
        setUp();
        assertEquals(11, closeInterval.nbValues());
        assertEquals(10, halfInterval.nbValues());
        assertEquals(9, openInterval.nbValues());
    }

    @Test
    public void testFullInterval() throws Interval.EmptyInterval {
        setUp();
        List<ExecutionParameter> execParams = closeInterval.getPosibleValues(12);
        assertEquals(11, execParams.size());
        for (int i = 0; i < 11; i++) {
            assertEquals(true, execParams.contains(new ExecutionParameter( new ParameterName("close"), i, String.valueOf(i))));
        }
        execParams = openInterval.getPosibleValues(9);
        assertEquals(9, execParams.size());        
        for(int i=1; i<10; i++) {
            assertEquals(true, execParams.contains(new ExecutionParameter( new ParameterName("open"), i, String.valueOf(i))));           
        }
        execParams = halfInterval.getPosibleValues(12);
        assertEquals(10, execParams.size());         
        for(int i=1; i<11; i++) {
            assertEquals(true, execParams.contains(new ExecutionParameter( new ParameterName("half"), i, String.valueOf(i))));            
        }
        IntegerInterval hugeInteverval = new IntegerInterval(0, 100000, new ParameterName("huge"), true, true);
        execParams = hugeInteverval.getPosibleValues(10000);
        assertEquals(10000,execParams.size());
    }
    
    @Test
    public void testPartInterval() throws Interval.EmptyInterval {
        setUp();
        List<ExecutionParameter> execParams = closeInterval.getPosibleValues(8);
        assertEquals(8, execParams.size());
        for (int i = 0; i < 10; i++) {
            if(i==4 || i==9 ) {
                continue;
            }
            assertEquals(true, execParams.contains(new ExecutionParameter( new ParameterName("close"), i, String.valueOf(i))));
        }
    }

}
