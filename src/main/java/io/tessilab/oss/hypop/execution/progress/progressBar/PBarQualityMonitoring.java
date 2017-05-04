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
package io.tessilab.oss.hypop.execution.progress.progressBar;

import io.tessilab.oss.hypop.execution.progress.ExecutionProgress;
import io.tessilab.oss.hypop.execution.stopCondition.StopCondition;
import io.tessilab.oss.hypop.parameters.managers.ParametersManager;
import io.tessilab.oss.hypop.results.ProcessResult;
import io.tessilab.oss.openutils.progressbar.ProgressBar;
import io.tessilab.oss.openutils.progressbar.ProgressBarStyle;
import java.io.PrintStream;

/**
 * A progress bar depending on the quality of the result. A threhold is fixed, 
 * and after every execution the bar is updated.
 * 
 * @author Andres BEL ALONSO
 * @param <SCORE> The score of an excution
 * @param <PROCESSRESULT> The class containing all the informations about one execution
 */
public class PBarQualityMonitoring<SCORE extends Comparable<SCORE>, PROCESSRESULT extends ProcessResult<SCORE>>
        implements ExecutionProgress<SCORE,PROCESSRESULT> {

    public static class Config<SCORE extends Comparable<SCORE>, PROCESSRESULT extends ProcessResult<SCORE>>
            extends ExecutionProgress.Config<SCORE,PROCESSRESULT> {

        private final ProgressBarStyle style;
        private final PrintStream printStream;
        private final long targetScore;

        public Config(ProgressBarStyle style, PrintStream printStream, long targetScore) {
            this.style = style;
            this.printStream = printStream;
            this.targetScore = targetScore;
        }
        
        

        @Override
        protected ExecutionProgress<SCORE,PROCESSRESULT> build() {
            return new PBarQualityMonitoring<>(style, printStream, targetScore);
        }

    }

    private ProgressBar progressBar;
    private final ProgressBarStyle style;
    private final PrintStream printStream;
    private final long targetScore;
    private long bestScoreFound = 0;

    public PBarQualityMonitoring(ProgressBarStyle style, PrintStream printStream, long targetScore) {
        this.style = style;
        this.printStream = printStream;
        this.targetScore = targetScore;
    }

    @Override
    public void showProgress() {
        progressBar.refreshBar();
    }

    @Override
    public void init(ParametersManager<SCORE,PROCESSRESULT> paramManager,
            StopCondition<SCORE,PROCESSRESULT> stopCondition) {
        progressBar = new ProgressBar(style, printStream, targetScore, "Quality progress bar");
    }

    @Override
    public void updateObserver(PROCESSRESULT obj) {
        long score = obj.getLongScoreRepresentation();
        if (score > bestScoreFound) {
            progressBar.stepBy((bestScoreFound - score) * -1);
            bestScoreFound = score;
        }
    }

}
