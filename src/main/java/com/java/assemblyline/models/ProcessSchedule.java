package com.java.assemblyline.models;

public class ProcessSchedule {
    private int time;
    private String description;

    public ProcessSchedule(int time, String description) {
        this.time = time;
        this.description = description;
    }

    public int getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }
}
