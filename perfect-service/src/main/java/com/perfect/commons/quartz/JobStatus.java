package com.perfect.commons.quartz;

/**
 * Created on 2015-09-30.
 *
 * @author dolphineor
 */
public enum JobStatus {
    PAUSE(0), ACTIVE(1), DELETE(-1);

    private final int value;

    JobStatus(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
