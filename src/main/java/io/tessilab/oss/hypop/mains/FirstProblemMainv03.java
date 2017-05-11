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
import io.tessilab.oss.hypop.execution.progress.CompositeMonitoring;
import io.tessilab.oss.hypop.execution.progress.ExecutionProgress;
import io.tessilab.oss.hypop.execution.progress.progressBar.PBarJobsToDoMonitoring;
import io.tessilab.oss.hypop.execution.progress.progressBar.PBarQualityMonitoring;
import io.tessilab.oss.hypop.execution.stopCondition.CompositeStopCondition;
import io.tessilab.oss.hypop.execution.stopCondition.JobsToDoStopCondition;
import io.tessilab.oss.hypop.execution.stopCondition.MaxScoreStopCondition;
import io.tessilab.oss.hypop.execution.stopCondition.StopCondition;
import io.tessilab.oss.hypop.extinterface.CombatMonsterInterfacev03;
import io.tessilab.oss.hypop.extinterface.CombatMonstersInterface;
import io.tessilab.oss.hypop.parameters.control.AllConditionsMustBeTrue;
import io.tessilab.oss.hypop.parameters.managers.RandomSearchParamManager;
import io.tessilab.oss.hypop.results.analyzer.NBetterResultsOutPrint;
import io.tessilab.oss.hypop.results.saver.ElasticSearchResultSaver;
import io.tessilab.oss.openutils.elasticsearch.ElasticSearchBaseData;
import io.tessilab.oss.openutils.io.LoggerPrintStream;
import io.tessilab.oss.openutils.locker.ElasticSearchDAO;
import io.tessilab.oss.openutils.progressbar.ProgressBarStyle;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.Level;

/**
 * An second example of what a hypop main looks like. 
 * @author Andres BEL ALONSO
 */
public class FirstProblemMainv03 {
    
    public static double OBJECTIVE_SCORE = 2000;
    public static int MAXIMAL_NUMBER_EXECUTIONS = 100000;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String esType = "locks";
        String resultType = "result";
        ElasticSearchBaseData locksData = new ElasticSearchBaseData("monster", "192.168.1.107", 9300, "elasticsearch");  
        ElasticSearchBaseData saverData = new ElasticSearchBaseData("monster","192.168.1.107", 9300, "elasticsearch");
        ExecutionConfig<Double,CombatMonstersInterface.MonsterExecutionResult> config = new ExecutionConfig<>();
        
        config.setLockerDao(new ElasticSearchDAO(esType, locksData));
        config.setParametersManagerConfig(new RandomSearchParamManager.Config<>(60));
        config.setProcessInterface(new CombatMonsterInterfacev03.Config());
        JobsToDoStopCondition.Config<Double,CombatMonstersInterface.MonsterExecutionResult> jobsToDoCondition =
                new JobsToDoStopCondition.Config<>(MAXIMAL_NUMBER_EXECUTIONS);
        StopCondition.Config<Double,CombatMonstersInterface.MonsterExecutionResult> scoreStopCondition =
                new MaxScoreStopCondition.Config<>(OBJECTIVE_SCORE); 
        List<StopCondition.Config<?,?>> stopConditions = Arrays.asList(jobsToDoCondition,scoreStopCondition);
        config.setStopConditionConfig(new CompositeStopCondition.Config(stopConditions));
        config.setResultAnalyzerConfig(new NBetterResultsOutPrint.Config(10,new LoggerPrintStream(NBetterResultsOutPrint.class, Level.INFO)));
        
        List<ExecutionProgress.Config> monitoringList = Arrays.asList(new PBarJobsToDoMonitoring.Config<>(ProgressBarStyle.UNICODE_BLOCK,new LoggerPrintStream(PBarJobsToDoMonitoring.class, Level.DEBUG)),
                new PBarQualityMonitoring.Config<>(ProgressBarStyle.UNICODE_BLOCK, new LoggerPrintStream(PBarQualityMonitoring.class, Level.DEBUG), (int) OBJECTIVE_SCORE));
        config.setExecutionProgressConfig(new CompositeMonitoring.Config(monitoringList));
        config.setResultsSaverConfig(new ElasticSearchResultSaver.Config(saverData,resultType));
        config.setExecParamsFiltersConfig(new AllConditionsMustBeTrue.Config());
        ExecutionRun run = new ExecutionRun(config);
        run.run();
    }
}
