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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.tessilab.oss.hypop.parameters.ParameterName;

/**
 *
 * @author Andres BEL ALONSO
 * @param <T> : the type of element in the interval
 * @param <PARAMTYPE> : the type of interval
 */
public abstract class Interval<T extends Comparable,PARAMTYPE extends Interval> extends InputParameter<PARAMTYPE> {

    public static class EmptyInterval extends Exception {

        EmptyInterval() {
            super("An empty interval has been created");
        }
        
    }
    private static final Logger LOGGER = LogManager.getLogger(Interval.class);

    protected final T lowerBorder;
    protected final T higherBorder;
    protected final boolean includeLower;
    protected final boolean includeHigher;

    public Interval(T lowerBorder, T higherBorder,ParameterName paramName,boolean includeLower, boolean includeHigher) throws EmptyInterval {
        super(paramName);
        this.includeHigher = includeHigher;
        this.includeLower = includeLower;
        if (lowerBorder.compareTo(higherBorder) < 0) {
            this.lowerBorder = lowerBorder;
            this.higherBorder = higherBorder;
        } else if (lowerBorder.compareTo(higherBorder) == 0 && includeHigher && includeLower) {
            LOGGER.debug("The interval borders are equal");
            this.higherBorder = lowerBorder;
            this.lowerBorder = lowerBorder;
        } else {
            LOGGER.warn("Empty interval has been created");
            throw new EmptyInterval();
        }
    }
    
    /**
     * 
     * @param val The value to test if it is or not in this interval
     * @return true if val belongs to the interval
     */
    public boolean isInInterval(T val) {
        if(this.lowerBorder.compareTo(val) == 0 ) {
            return includeLower;
        } 
        if(higherBorder.compareTo(val) == 0) {
            return includeHigher;
        }
        return lowerBorder.compareTo(val) < 0 && higherBorder.compareTo(val) > 0;
    }
    
    public boolean isIncludedInterval(PARAMTYPE param) {
        return isInInterval(this.lowerBorder) && isInInterval(this.higherBorder);
    }
}
