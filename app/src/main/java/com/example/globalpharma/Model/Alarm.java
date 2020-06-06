package com.example.globalpharma.Model;

public class Alarm {
    private String startingDate;
    private String endingDate;
    private long repeatTime;
    private long triggerMillis;

    public Alarm(String startingDate, String endingDate, long repeatTime, long triggerMillis) {
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.repeatTime = repeatTime;
        this.triggerMillis = triggerMillis;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public long getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(long repeatTime) {
        this.repeatTime = repeatTime;
    }

    public long getTriggerMillis() {
        return triggerMillis;
    }

    public void setTriggerMillis(long triggerMillis) {
        this.triggerMillis = triggerMillis;
    }
}
