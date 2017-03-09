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
package io.tessilab.oss.hypop.helloworld;

import java.io.PrintStream;

/**
 * The hello world example. This class will display : 
 * <p> 
 *  "Hellow world. This is HyPop"
 * <p> 
 * Or another things depending on the parameters passed to the constructor
 * @author Andres BEL ALONSO
 */
public class HelloWorld {
    
    private final String target;
    private final String source;
    private final PrintStream output;
    
    /**
     * 
     * @param target The target of the hellow. In "Hellow world. This is HyPop" 
     * this should be equal to "world"
     * @param source The element that says hello. In "Hellow world. This is HyPop" 
     * this should be equal to "hello"
     * @param printStream  The channel to print the message. If we want to print 
     * on the standard output, this parameter should be System.out
     */
    public HelloWorld(String target, String source, PrintStream printStream) {
        this.output = printStream;
        this.target = target;
        this.source = source;
    }
    
    /**
     * Prints the parametrized message into the specified output
     */
    public void printHelloWorld() {
        output.print("Hello " + target + ". This is " + source + ".");
    }
    
    
}
