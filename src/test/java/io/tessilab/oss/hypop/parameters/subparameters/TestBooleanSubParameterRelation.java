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

import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.hypop.parameters.input.ContinuousInterval;
import io.tessilab.oss.hypop.parameters.input.InputParameter;
import io.tessilab.oss.hypop.parameters.input.IntegerInterval;
import io.tessilab.oss.hypop.parameters.input.Interval;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Andres BEL ALONSO
 */
public class TestBooleanSubParameterRelation {
    
    private BooleanSubParameterRelation<String> relation;
    
    public void setUpTrue() throws Interval.EmptyInterval {
        InputParameter<Integer> subParam = new IntegerInterval(0,10,new ParameterName("hijico"),true,true);
        this.relation = new BooleanSubParameterRelation<>(true,new ParameterName("papito"),"la_base_value",subParam);
    }
    
    public void setUpFalse() throws Interval.EmptyInterval {
        InputParameter<Double> subParam = new ContinuousInterval(0.0, 2.0, new ParameterName("bastardo"), true, true);
        this.relation = new BooleanSubParameterRelation<>(false,new ParameterName("papito_putero"),
                "la_fake_base_value",subParam);
    }
    
    @Test
    public void testIsFatherValue() throws Interval.EmptyInterval {
        setUpTrue();
        assertEquals(true,relation.isFatherValue(Optional.ofNullable("la_base_value")));
        assertEquals(false, relation.isFatherValue(Optional.ofNullable("topotamadre")));
        setUpFalse();
        assertEquals(true,relation.isFatherValue((Optional.ofNullable(null))));
        assertEquals(false,relation.isFatherValue(Optional.ofNullable("papito")));
    }
    
    @Test
    public void testGetFatherValues() throws Interval.EmptyInterval {
        setUpFalse();
        assertEquals(true,relation.getFatherValues(0).isEmpty());
        List<String> list = relation.getFatherValues(3);
        assertEquals(1, list.size());
        assertEquals(true,"la_fake_base_value".equals(list.get(0)));
    }
}
