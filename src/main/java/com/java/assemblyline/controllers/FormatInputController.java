package com.java.assemblyline.controllers;

import com.java.assemblyline.models.ProcessSchedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormatInputController {
    public static List<ProcessSchedule> formatInput(String input) {
        final List<String> inputSteps = new ArrayList<>(getSteps(input));
        List<ProcessSchedule> processSteps = new ArrayList<>();

        for (String step : inputSteps) {
            processSteps.add(new ProcessSchedule(getTime(step), step));
        }
        return processSteps;
    }

    private static List<String> getSteps(String input) {
        String[] processSteps = input.split("\n");
        return Arrays.asList(processSteps);
    }

    private static int getTime(String step) {
        if (step.contains("maintenance")) {
            return 5;
        } else {
            return Integer.valueOf(step.replaceAll("\\D+",""));
        }
    }
}
