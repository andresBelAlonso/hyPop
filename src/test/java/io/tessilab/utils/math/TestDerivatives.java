/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.tessilab.utils.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Andres BEL ALONSO
 */
public class TestDerivatives {
    
    public static final double PRECISION = 0.00001;
    
//    @Test
    public void testLinearFonction() {
        double epsilon = 2.2 * Math.pow(10, -16);
        assertEquals(7,Derivatives.computePseudoDerivative(f(1 + epsilon),f(1), epsilon),PRECISION);
    }
    
    // the linear function f(x) = 7 * x;
    private double f(double x) {
        return 7 * x;
    }
    
    // the quadratic function g(x) = x ^3;
    private double g(double x) {
        return Math.pow(x, 3);
    }
    
//    @Test
    public void testCubicDoubleFucntion() {
        double epsilon = 2.2 * Math.pow(10, -16);
        assertEquals(6.75,Derivatives.computePseudoDerivative(Math.pow(1.5 + epsilon, 3), Math.pow(1.5, 3), epsilon),PRECISION);
    }
            
    
    @Test
    public void testCubicFunction() {
        MathContext context = new MathContext(24);
        BigDecimal epsilon = new BigDecimal(2.2 * Math.pow(10, -16),context);
        BigDecimal oneHalf = new BigDecimal(1.5,context);
        BigDecimal y1 = oneHalf.add(epsilon,context).pow(3,context);
        BigDecimal y2 = new BigDecimal(1.5*1.5*1.5,context);
        
        assertEquals(6.75,Derivatives.computePseudoDerivative(y1,y2,epsilon,context).doubleValue(),PRECISION);
    }
   
    
    
}
