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
package io.tessilab.oss.hypop.parameters.managers.GradientDescent;

import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;

/**
 * 
 * @author Andres BEL ALONSO
 */
public class Gradient {
    
    public static class NotAlreadyComputedGradient extends Exception {
        
    }

    /**
     * 
     * @return True if all the components of this gradient have been compute
     */
    boolean isAlreadyComputed() {
        // TODO : implemente cette méthode
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method should be call after hasJobsToGive in order to prevent null 
     * pointers
     * @return The ne
     */
    ExecutionParametersSet getParameterToCompute() {
        // TODO : implemente cette méthode
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return true if the gradient has not already give all the jobs to compute all they components
     */
    boolean hasJobsToGive() {
        // TODO : implemente cette méthode
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return the euclidean norm of this gradient
     */
    double getNorm() throws NotAlreadyComputedGradient{
        // TODO : implemente cette méthode
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
