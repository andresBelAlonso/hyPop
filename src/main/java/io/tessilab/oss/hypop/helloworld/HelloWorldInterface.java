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

import io.tessilab.oss.hypop.extinterface.ProcessInterface;
import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;
import io.tessilab.oss.hypop.parameters.input.InputParametersSet;
import io.tessilab.oss.hypop.parameters.input.NominativeInputParameter;
import io.tessilab.oss.hypop.results.ProcessResult;
import io.tessilab.oss.hypop.results.saver.SaverAnswer;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Andres BEL ALONSO
 */
public class HelloWorldInterface implements ProcessInterface<Double,HelloWorldResult>{
   
    // The config class. This class simply instanciated the HelloWorldInterface, 
    // and it is used by the main to create it
    public static class Config extends ProcessInterface.Config<Double,HelloWorldResult> {

        @Override
        protected HelloWorldInterface build() {
            return new HelloWorldInterface();
        }
        
    }
    
    // The class to easily retrieve the Hello World message        
    public static class MyPrintStream extends PrintStream {
        
        private String lastPrintedString;
        
        public MyPrintStream() {
            super(System.out);
        }

        @Override
        public void println(String x) {
            // first we print
            super.println(x);
            this.lastPrintedString = x;
        }

        public String getLastPrintedString() {
            return lastPrintedString;
        }
        
        
    }

    @Override
    public HelloWorldResult computeIt(ExecutionParametersSet params) {
        // We retrieve the parameters that will be use during this execution
        String target = (String) params.getExecParameter(new ParameterName("target")).getValue();
        String source = (String) params.getExecParameter(new ParameterName("source")).getValue();
        HelloWorld helloWorld;
        // We instanciate hello world with the specified values
        MyPrintStream printStream = new MyPrintStream();
        helloWorld = new HelloWorld(target, source, printStream); // Here we use our printStream to retrieve the result
        //We execute hello world
        helloWorld.printHelloWorld();
        // We retrieve the printed result
        String msg = printStream.getLastPrintedString();
        // We build the builded process result
        HelloWorldResult res = new HelloWorldResult(msg, params);
        return res;
    }
    

    @Override
    public InputParametersSet createInputParameters() {
        // In this method we create the parameters one by one
        Map<String,String> sourceValues = new HashMap<>();
        sourceValues.put("HyPop", "HyPop"); // The target value
        // Other different values to make the problem more interesting
        sourceValues.put("Jean", "Jean");
        sourceValues.put("your father", "your father");
        sourceValues.put("Fred", "Fred");
        sourceValues.put("Natasha", "Natasha");
        // This paremeter is a nominative one because 
        NominativeInputParameter<String> sourceParameter = new NominativeInputParameter<>(new ParameterName("source"), sourceValues);
        
        //The second parameter
        // We do exactly the same than in the first parameter
        Map<String,String> targetValues = new HashMap<>();
        targetValues.put("World", "World");
        targetValues.put("chicken","chicken");
        targetValues.put("cow", "cow");
        targetValues.put("Tom", "Tom");
        targetValues.put("Milda", "Milda");
        NominativeInputParameter<String> targetParameter = new NominativeInputParameter<>(new ParameterName("target"),targetValues);
        
        //We create and we add the parameters to the output object
        InputParametersSet parameterSet = new InputParametersSet();
        parameterSet.addParameter(targetParameter);
        parameterSet.addParameter(sourceParameter);
        return parameterSet;
    }

    @Override
    public HelloWorldResult createResult(SaverAnswer jobDone) {
        // We just build a new process result with
        return new HelloWorldResult(jobDone);
    }
    
}
