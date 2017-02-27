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
package io.tessilab.oss.hypop.exceptions;

/**
 * The class representing an error that should stop the current execution of the
 * hyperparameter search. Usually it is because the pb has an uncoheren configuration.
 * @author Andres BEL ALONSO
 */
public class HyperParameterSearchError extends RuntimeException{
    
    /**
     *
     * @param ex : The throwable that caused this exception
     */
    public HyperParameterSearchError(Throwable ex) {
        super(ex);
    }
    
    /**
     *
     * @param msg : The cause that throw this exeception
     */
    public HyperParameterSearchError(String msg) {
        super(msg);
    }

    /**
     *
     */
    public HyperParameterSearchError() {
    }
    
    
}
