package com.java.assemblyline.models;

import java.time.LocalTime;

public class ProductionSchedule {
    private final LocalTime time;
    private final String description;

    public ProductionSchedule(LocalTime time, String description) {
        this.time = time;
        this.description = description;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }
}
