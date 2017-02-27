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
 *
 * @author Andres BEL ALONSO
 */
public class HyperParameterSearchRuntimeException extends RuntimeException{

    /**
     *
     */
    public HyperParameterSearchRuntimeException() {
    }

    /**
     *
     * @param message : A message with the cause of this exeception
     */
    public HyperParameterSearchRuntimeException(String message) {
        super(message);
    }

    /**
     *
     * @param cause : The trowable that creates this exception
     */
    public HyperParameterSearchRuntimeException(Throwable cause) {
        super(cause);
    }
    
    
}
