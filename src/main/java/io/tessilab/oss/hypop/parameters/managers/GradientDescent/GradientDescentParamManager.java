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

import io.tessilab.oss.hypop.exceptions.HyperParameterSearchError;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;
import io.tessilab.oss.hypop.parameters.input.InputParametersSet;
import io.tessilab.oss.hypop.parameters.managers.ParametersManager;
import io.tessilab.oss.hypop.results.ProcessResult;
import java.util.List;
import org.apache.logging.log4j.LogManager;

/**
 * This class implements the gradient descent optimization as a parameter
 * Manager. This class is totally NOT thread safe.
 *
 * @author Andres BEL ALONSO
 * @param <SCORE> : The score of the process result
 * @param <PROCESSRESULT> : The process result class
 */
public class GradientDescentParamManager<SCORE extends Comparable<SCORE>,PROCESSRESULT extends ProcessResult<SCORE>>
        extends ParametersManager<SCORE,PROCESSRESULT> {
    
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(GradientDescentParamManager.class);


    private static class Vector {

        public final double[] values;

        private Vector(ExecutionParametersSet params) {
            throw new UnsupportedOperationException();
        }
    }

    public static enum State {
        GRADIENT_COMPUTE,
        STEP_COMPUTE;

        private int subStep = 0;

        public int getSubStep() {
            return subStep;
        }

        public void setSubStep(int subStep) {
            this.subStep = subStep;
        }
    }

    private Gradient gradient;
    private Gradient lastGradient; 
    private DescentDirection descentDirection;
    private final List<ExecutionParametersSet> jobsNotDone;
    private final double gradientMinNorm;

    public GradientDescentParamManager(InputParametersSet params) {
        super(params);
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasJobsToExplore() {
        try {
            return lastGradient.getNorm() < gradientMinNorm;
        } catch (Gradient.NotAlreadyComputedGradient ex) {
            LOGGER.debug("We continue because the first graddient is not already compute");
            return true;
        }
    }

    @Override
    protected ExecutionParametersSet doGetNonBuildParameters() {
        if (!gradient.isAlreadyComputed()) {
            if (gradient.hasJobsToGive()) {
                return gradient.getParameterToCompute();
            } else {
                if (jobsNotDone.isEmpty()) {
                    throw new HyperParameterSearchError("The gradient is not compute, but thre are not more jobs for him");
                } else {
                    return jobsNotDone.remove(0);
                }
            }
        } else {
            // We are know computing a new step
            // There are not so many steps non computed, we can look for a new one
            if (jobsNotDone.size() < 3) {
                return descentDirection.getNextTry();
            } else {
                return jobsNotDone.get(0);
            }
        }
    }

    @Override
    public int getJobsTodo() {
        // We do not know who much jobs we have
        return -1;
    }

    @Override
    public void canNotBeDoneNow(ExecutionParametersSet params) {
        // TODO : implemente cette méthode
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyAsNotValidParameter(ExecutionParametersSet params) {
        // TODO : implemente cette méthode
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateObserver(PROCESSRESULT obj) {
        throw new UnsupportedOperationException();
    }

}
