package com.perfect.service.keywordgroup;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveAction;

/**
 * Created by vbzer_000 on 14-7-22.
 */
@Deprecated
public class Task extends RecursiveAction {
    private final Map<String, String> keyGroupMap;
    private final Map<String, Set<String>> matchKeySet;
    private int start;
    private int end;
    private final int MAX_SIZE = 100;

    public Task(Map<String, String> keyGroupMap, Map<String, Set<String>> matchKeySet, int start, int end) {
        this.keyGroupMap = keyGroupMap;
        this.matchKeySet = matchKeySet;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start > MAX_SIZE) {
            Task task1 = new Task(keyGroupMap, matchKeySet, start, start + (end - start) / 2);
            Task task2 = new Task(keyGroupMap, matchKeySet, start + (end - start) / 2 + 1, end);
            invokeAll(task1, task2);
        } else {
            int idx = 0;
            for (Map.Entry<String, String> entry1 : keyGroupMap.entrySet()) {
                if (idx < start) {
                    idx++;
                    continue;
                }
                if (end < idx) {
                    break;
                }
                // 候选关键词
                String key = entry1.getKey();
                for (Map.Entry<String, Set<String>> entrySet : matchKeySet.entrySet()) {
                    for (String toMatch : entrySet.getValue()) {
                        if (key.contains(toMatch)) {
                            entry1.setValue(entry1.getValue() + "," + entrySet.getKey());
                            break;
                        }
                    }
                }
                idx++;
            }
        }
    }
}