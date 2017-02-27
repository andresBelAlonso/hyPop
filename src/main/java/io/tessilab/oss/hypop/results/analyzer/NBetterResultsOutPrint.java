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

import io.tessilab.oss.hypop.results.ProcessResult;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * The class that prints the n better results of the research in the output
 * passed in argument
 *
 * @author Andres BEL ALONSO
 */
public class NBetterResultsOutPrint implements ResultAnalyzer {



    public static class Config extends ResultAnalyzer.Config {

        private final int nbResults;
        private final PrintStream printStream;

        public Config(int nbResults, PrintStream printStream) {
            this.nbResults = nbResults;
            this.printStream = printStream;

        }

        @Override
        protected ResultAnalyzer build() {
            return new NBetterResultsOutPrint(nbResults, printStream);
        }

    }

    /**
     * Constraint : 1) The map is limited to n elements
     *              2) If there is an entry on the map, there is a value on the list
     */
    private final TreeMap<Comparable, List<ProcessResult>> map;
    private final int n;
    private final PrintStream printStream;

    public NBetterResultsOutPrint(int n, PrintStream printStream) {
        this.n = n;
        map = new TreeMap();
        this.printStream = printStream;
    }

    @Override
    public void analyseResults() {
        int totalSize = this.size();
        int i = totalSize > n?n:totalSize;
        for (List<ProcessResult> resList : map.values()) {
            for (ProcessResult res : resList) {
                printStream.println("Result ranked #" + String.valueOf(i) + " SCORE : " + res.getResultScore().toString() + " PARAMETERS : " + res.getJobId());
                i--;
            }
        }
    }

    @Override
    public void updateObserver(ProcessResult newEntry) {
        if (this.size() < n) {
            //there is place to add
            addElementToMap(newEntry);
        } else {
            if (newEntry.getResultScore().compareTo(map.firstKey()) > 0) {
                //the new entry is better, we add it
                removeOneEntry(map.firstKey());
                addElementToMap(newEntry);
            }
        }
    }

    /**
     * @return the number of saved results currently in this map
     */
    public int getCachedElements() {
        int res = 0;
        for (List<ProcessResult> entry : this.map.values()) {
            res += entry.size();
        }
        return res;
    }
    
    private void addElementToMap(ProcessResult entry) {
        // simply we add an element ASUMING that there is enought place
        Comparable score = entry.getResultScore();
        if(this.map.containsKey(score)) {
            map.get(score).add(entry);
        } else {
            List<ProcessResult> list = new LinkedList<>();
            list.add(entry);
            map.put(score,list);
        }
    }
    
    private void removeOneEntry(Comparable key) {
        //Removes an entry from the key list
        // If an entry of the map would have a list, it is removed
        List<ProcessResult> list = this.map.get(key);
        list.remove(list.get(0));
        if(list.isEmpty()) {
            map.remove(key);
        }
    }
    
    /**
     * The number of scores saved.
     * @return The number of results allready saved
     */
    public int size() {
        int counter = 0;
        for(List<ProcessResult> list : this.map.values()) {
            counter += list.size();
        }
        return counter;
    }

}
