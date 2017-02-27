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
package io.tessilab.oss.hypop.math;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author Andres BEL ALONSO
 */
public class Derivatives {
    
    public static final double DEFAULT_EPSILON = 2.2 * Math.pow(10, -16);
    
    /**
     * Computes an aproximation of a derivative by the formula : 
     *  ( f(x + h) - f(x) )/h where y1 = f(x + h) and y2 = f(x) 
     * <p>
     * Not enought precision with doubles
     * @param y1 y1
     * @param y2 y2 
     * @param h The h value
     * @return The 
     */
    @Deprecated
    public static double computePseudoDerivative(double y1,double y2, double h) {
        return (y1-y2)/h;
    }
    
    public static BigDecimal computePseudoDerivative(BigDecimal y1,BigDecimal y2, BigDecimal h, MathContext context) {
        return y1.subtract(y2,context).divide(h,context);
    }
    
}
