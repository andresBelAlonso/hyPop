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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A stop condition that occurs when a target score has been reached. 
 * @author Andres BEL ALONSO
 * @param <T> : The type of element who will be compare
 */
public class MaxScoreStopCondition<T extends Comparable> extends StopCondition{
    
    private static final Logger LOGGER = LogManager.getLogger(MaxScoreStopCondition.class);

    
    public static class Config<T extends Comparable> extends StopCondition.Config {
        
        private final T objectiveScore;

        public Config(T objectiveScore) {
            this.objectiveScore = objectiveScore;
        }  

        @Override
        protected StopCondition build() {
            return new MaxScoreStopCondition<>(objectiveScore);
        }

    } 
    
    private T bestScoreFound = null;
    private final T objectiveScore;

    public MaxScoreStopCondition(T objectiveScore) {
        this.objectiveScore = objectiveScore;
    }

    @Override
    public boolean continueWork() {
        if(bestScoreFound == null || objectiveScore.compareTo(bestScoreFound) >0) {
            return true;
        } else {
            LOGGER.debug("The max score has been reached. Stoping the work");
            return false;
        }
    }

    @Override
    public int getJobsToDo() {
        return -1;
    }

    @Override
    public void updateObserver(ProcessResult obj) {
        if(bestScoreFound == null || obj.getResultScore().compareTo(bestScoreFound)>0) {
            this.bestScoreFound = (T) obj.getResultScore();
        }
    }
    
}
