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
package io.tessilab.oss.hypop.execution.stopCondition;

import io.tessilab.oss.hypop.results.ProcessResult;
import java.util.ArrayList;
import java.util.List;

/**
 * pplies the composite design pattern ( https://en.wikipedia.org/wiki/Composite_pattern )
 * to allow to have many {@link io.tessilab.oss.hypop.execution.stopCondition.StopCondition}
 * in the execution. 
 * @author Andres BEL ALONSO
 */
public class CompositeStopCondition extends StopCondition{
    
    public static class Config extends StopCondition.Config {
        
        private final List<StopCondition.Config> configList;

        public Config(List<StopCondition.Config> configList) {
            this.configList = configList;
        }

        @Override
        protected StopCondition build() {
            return new CompositeStopCondition(configList);
        }
        
    }
    
    private final List<StopCondition> stopConditions;
    
    public CompositeStopCondition(List<StopCondition.Config> configList) {
        stopConditions = new ArrayList<>();
        configList.stream().map(e -> e.synchroBuild())
                .forEach(e -> {stopConditions.add(e);});
    }

    @Override
    public boolean continueWork() {
        // Continue only if all the conditions want to continue
        for(StopCondition curStopCondition : stopConditions) {
            if(!curStopCondition.continueWork()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getJobsToDo() {
        //The lowest job to do that are not -1
        int jobsToDo = Integer.MAX_VALUE;
        for(StopCondition curStopCondition : stopConditions) {
            int nbJobsToDo = curStopCondition.getJobsToDo();
            if(nbJobsToDo != -1 && nbJobsToDo<jobsToDo) {
                jobsToDo = nbJobsToDo;
            }
        }
        return jobsToDo==Integer.MAX_VALUE?-1:jobsToDo;
    }

    @Override
    public void updateObserver(ProcessResult obj) {
        stopConditions.stream().forEach(e -> e.updateObserver(obj));
    }
    
}
