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
package io.tessilab.oss.hypop.mains;

import io.tessilab.oss.hypop.execution.ExecutionConfig;
import io.tessilab.oss.hypop.execution.ExecutionRun;
import io.tessilab.oss.hypop.execution.progress.progressBar.PBarJobsToDoMonitoring;
import io.tessilab.oss.hypop.execution.stopCondition.NoMoreStopCondition;
import io.tessilab.oss.hypop.extinterface.CombatMonstersInterface;
import io.tessilab.oss.hypop.parameters.managers.GridParametersManager;
import io.tessilab.oss.hypop.results.analyzer.NBetterResultsOutPrint;
import io.tessilab.oss.hypop.results.saver.ElasticSearchResultSaver;
import io.tessilab.oss.openutils.elasticsearch.ElasticSearchBaseData;
import io.tessilab.oss.openutils.io.LoggerPrintStream;
import io.tessilab.oss.openutils.locker.ElasticSearchDAO;
import io.tessilab.oss.openutils.progressbar.ProgressBarStyle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A first example of what an hypop main is. 
 * @author Andres BEL ALONSO
 */
public class FirstProblemaMain {
    
    private static final Logger LOGGER = LogManager.getLogger(ElasticSearchDAO.class);


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String esType = "locks";
        String resultType = "result";
        ElasticSearchBaseData locksData = new ElasticSearchBaseData("monster", "192.168.1.107", 9300, "elasticsearch");  
        ElasticSearchBaseData saverData = new ElasticSearchBaseData("monster","192.168.1.107", 9300, "elasticsearch");
        ExecutionConfig config = new ExecutionConfig();
        
        config.setLockerDao(new ElasticSearchDAO(esType, locksData));
        config.setParametersManagerConfig(new GridParametersManager.Config(3));
        config.setProcessInterface(new CombatMonstersInterface.Config());
        config.setStopConditionConfig(new NoMoreStopCondition.Config());
        config.setResultAnalyzerConfig(new NBetterResultsOutPrint.Config(10,new LoggerPrintStream(NBetterResultsOutPrint.class, Level.INFO)));
//        config.setExecutionProgressConfig(new ShutUpMonitoring.Config());
        config.setExecutionProgressConfig(new PBarJobsToDoMonitoring.Config(ProgressBarStyle.UNICODE_BLOCK,new LoggerPrintStream(PBarJobsToDoMonitoring.class, Level.DEBUG)));
        config.setResultsSaverConfig(new ElasticSearchResultSaver.Config(saverData,resultType));
        ExecutionRun run = new ExecutionRun(config);
        run.run();
    }
    
}
