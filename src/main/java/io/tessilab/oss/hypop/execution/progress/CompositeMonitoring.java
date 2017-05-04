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
package io.tessilab.oss.hypop.execution.progress;

import io.tessilab.oss.hypop.execution.stopCondition.StopCondition;
import io.tessilab.oss.hypop.parameters.managers.ParametersManager;
import io.tessilab.oss.hypop.results.ProcessResult;
import java.util.ArrayList;
import java.util.List;

/**
 * Applies the composite design pattern ( https://en.wikipedia.org/wiki/Composite_pattern )
 * to allow to have different {@link io.tessilab.oss.hypop.execution.progress.ExecutionProgress}
 * during the execution. 
 * @author Andres BEL ALONSO
 * @param <SCORE> The score of an execution
 * @param <PROCESSRESULT> The class containing all the data about an execution
 */
public class CompositeMonitoring<SCORE extends Comparable<SCORE>,PROCESSRESULT extends ProcessResult<SCORE>>
        implements ExecutionProgress<SCORE,PROCESSRESULT>{

    public static class Config<SCORE extends Comparable<SCORE>,PROCESSRESULT extends ProcessResult<SCORE>>
            extends ExecutionProgress.Config<SCORE,PROCESSRESULT> {
        
        private final List<ExecutionProgress.Config<SCORE,PROCESSRESULT>> monitoringList;

        public Config(List<ExecutionProgress.Config<SCORE,PROCESSRESULT>> monitoringList) {
            this.monitoringList = monitoringList;
        }
        

        @Override
        protected ExecutionProgress<SCORE,PROCESSRESULT> build() {
            return new CompositeMonitoring<>(monitoringList); 
        }
        
    }
    
    private final List<ExecutionProgress<SCORE,PROCESSRESULT>> execProgressList;
    
    public CompositeMonitoring(List<ExecutionProgress.Config<SCORE,PROCESSRESULT>> configs) {
        execProgressList = new ArrayList<>();
        configs.stream().map(e -> {
            return e.synchroBuild();
        }).forEach(e -> {
            execProgressList.add(e);
        });
    }
            
    @Override
    public void showProgress() {
        execProgressList.forEach(ExecutionProgress::showProgress);
    }

    @Override
    public void init(ParametersManager<SCORE,PROCESSRESULT> paramManager, 
            StopCondition<SCORE,PROCESSRESULT> stopCondition) {
        execProgressList.stream().forEach(e -> e.init(paramManager, stopCondition));
    }

    @Override
    public void updateObserver(PROCESSRESULT obj) {
        execProgressList.stream().forEach(e -> e.updateObserver(obj));
    }
    
}
