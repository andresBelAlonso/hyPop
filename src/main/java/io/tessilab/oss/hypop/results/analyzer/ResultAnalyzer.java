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
package io.tessilab.oss.hypop.results.analyzer;

import io.tessilab.oss.hypop.execution.BlockConfiguration;
import io.tessilab.oss.hypop.results.ProcessResult;
import io.tessilab.oss.openutils.designpatterns.observer.ParametrizedObserver;

/**
 * The class that must analyse the results of an hyperparameter search
 * @author Andres BEL ALONSO
 */
public interface ResultAnalyzer extends ParametrizedObserver<ProcessResult>{
    
    public static abstract class Config extends BlockConfiguration<ResultAnalyzer> {
        
    }
    
    public void analyseResults();
    
}
