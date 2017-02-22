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
package io.tessilab.oss.hypop.results.saver;

import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;
import io.tessilab.oss.hypop.results.ProcessResult;
import io.tessilab.oss.openutils.elasticsearch.ElasticSearchBaseData;
import io.tessilab.oss.openutils.elasticsearch.ElasticSearchHelper;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.get.GetResponse;

/**
 * The saver that saves it results on an ElasticSearch database <p>
 * https://www.elastic.co
 * @author Andres BEL ALONSO
 * @param <PROCESSRESULT>
 */
public class ElasticSearchResultSaver<PROCESSRESULT extends ProcessResult> implements ResultsSaver<PROCESSRESULT> {

    private static final Logger LOGGER = LogManager.getLogger(ElasticSearchResultSaver.class);

    public static class Config extends ResultsSaver.Config {

        private final ElasticSearchBaseData baseData;
        private final String ESType;

        public Config(ElasticSearchBaseData baseData, String ESType) {
            this.baseData = baseData;
            this.ESType = ESType;
        }

        @Override
        protected ResultsSaver build() {
            return new ElasticSearchResultSaver(baseData, ESType);
        }

    }

    private final ElasticSearchHelper helper;
    private final String eSType;

    public ElasticSearchResultSaver(ElasticSearchBaseData baseData, String eSType) {
        this.helper = new ElasticSearchHelper(baseData);
        this.eSType = eSType;
    }

    @Override
    public SaverAnswer getJobDone(ExecutionParametersSet params) {
        GetResponse response = helper.prepareGet(eSType, params.getDescriptionParameters(), 0);
        if (response.isExists() && !response.isSourceEmpty()) {
            LOGGER.trace("The entry has been found in the db. Getting it. Ir contains {}", response.getSourceAsString());
            return new SaverAnswer(response.getSourceAsMap(), params.getDescriptionParameters());
        } else {
            LOGGER.trace("The job {} is not in the db", params.getDescriptionParameters());
            return new SaverAnswer(params.getDescriptionParameters());
        }
    }

    @Override
    public void saveResult(ExecutionParametersSet param, PROCESSRESULT res) {
        Map<String, Object> json = res.getResultInDBFormat();
        helper.prepareIndex(eSType, param.getDescriptionParameters(), 0, json);
    }

}
