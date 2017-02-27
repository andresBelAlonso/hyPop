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
package io.tessilab.oss.hypop.execution;

/**
 * The class representing a block of configuration, that every important part of
 * the module should have an static class extending this class.
 * @author Andres BEL ALONSO
 * @param <T> : The class that will be build by this config
 */
public abstract class BlockConfiguration<T> {
    protected abstract T build();
    
    public synchronized T synchroBuild() {
        return this.build();
    }
}
