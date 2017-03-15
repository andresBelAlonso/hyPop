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

import io.tessilab.oss.hypop.execution.ExecutionConfig;
import io.tessilab.oss.hypop.execution.ExecutionRun;
import io.tessilab.oss.hypop.execution.progress.ShutUpMonitoring;
import io.tessilab.oss.hypop.execution.stopCondition.MaxScoreStopCondition;
import io.tessilab.oss.hypop.parameters.control.AllConditionsMustBeTrue;
import io.tessilab.oss.hypop.parameters.managers.GridParametersManager;
import io.tessilab.oss.hypop.results.analyzer.NBetterResultsOutPrint;
import io.tessilab.oss.hypop.results.saver.ElasticSearchResultSaver;
import io.tessilab.oss.openutils.elasticsearch.ElasticSearchBaseData;
import io.tessilab.oss.openutils.locker.ElasticSearchDAO;

/**
 *
 * @author Andres BEL ALONSO
 */
public class HelloWorldMain {
    
    public static void main(String[] args) {
        // In this example will will use a database in the localhost, port 9300 
        // (the ElasticSearch default configuration)
        
        // The different variables
        String esType = "locks";
        String resultType = "result";
        ElasticSearchBaseData locksData = new ElasticSearchBaseData("helloworld", "127.0.0.1", 9300, "elasticsearch");  
        ElasticSearchBaseData saverData = new ElasticSearchBaseData("helloworld","127.0.0.1", 9300, "elasticsearch");
        
        // The configuration of the execution
        ExecutionConfig config = new ExecutionConfig();
        
        //Here we set the support to lock the jobs, in this case ElasticSearch
        config.setLockerDao(new ElasticSearchDAO(esType, locksData));
        
        //We set the algorithm that will be use to select the parameters to execute
        config.setParametersManagerConfig(new GridParametersManager.Config(0)); // The parameter does not make sense in this context
        
        // We set the interface with hello world (the one we just writted before) 
        // to allow to the execution to run the hello world
        config.setProcessInterface(new HelloWorldInterface.Config());
        
        // Here we indicate to the execution that we want to stop the execution when
        // we reach the max score. Here the max score is 1.0, because as we have
        // implemented in the process result, this will only happen when we will
        // find the target hello world message
        config.setStopConditionConfig(new MaxScoreStopCondition.Config<Double>(1.0));
        
        // We indicate that after the execution we want to print the 10 best results
        // on the standard output
        config.setResultAnalyzerConfig(new NBetterResultsOutPrint.Config(10,System.out));
        
        // Here we simply did not add any filter to the parameters gived by the parameter manager. 
        config.setExecParamsFiltersConfig(new AllConditionsMustBeTrue.Config());
        
        //Here we indicate that we do not want any kind of monitoring
        config.setExecutionProgressConfig(new ShutUpMonitoring.Config());
        
        //Here we indicate that we are going to save the produced results in ElasticSearch
        config.setResultsSaverConfig(new ElasticSearchResultSaver.Config(saverData,resultType));
        ExecutionRun run = new ExecutionRun(config);
        // You can also execute this in a threadpool
        run.run();
    }
}
