package com.java.assemblyline.controllers;

import com.java.assemblyline.models.ProcessSchedule;
import com.java.assemblyline.models.ProductionSchedule;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ProductionScheduleController {
    public static String optimizeSteps(List<ProcessSchedule> processSteps) {
        String output = "";

        final LocalTime morningStartHour = LocalTime.of(9, 0);
        final LocalTime lunchStartHour = LocalTime.of(12, 0);
        final LocalTime afternoonStartHour = LocalTime.of(13, 0);
        final LocalTime gymnasticsStartHour = LocalTime.of(17, 0);
        final LocalTime morningPeriod = lunchStartHour.minusHours(morningStartHour.getHour());
        final LocalTime afternoonPeriod = gymnasticsStartHour.minusHours(afternoonStartHour.getHour());;
        final LocalTime dailyProductionPeriod = morningPeriod.plusHours(afternoonPeriod.getHour());;
        LocalTime morningActualTime = morningStartHour;
        LocalTime afternoonActualTime = afternoonStartHour;

        ArrayList<ProductionSchedule> morningProduction = new ArrayList<>();
        ArrayList<ProductionSchedule> afternoonProduction = new ArrayList<>();

        final double processTotalMinutes = sumProductionTime(processSteps);
        final int numberOfAssemblyLines = ((int) Math.ceil(processTotalMinutes / (dailyProductionPeriod.getHour() *60)));

        for (int i = 1; i <= numberOfAssemblyLines; i++) {
            output += i == 1 ? "Assembly line: " + i : "\n\nAssembly line: " + i;

            for (ProcessSchedule step : processSteps) {
                if (morningActualTime.plusMinutes(step.getTime()).isBefore(lunchStartHour)) {
                    morningProduction.add(new ProductionSchedule(morningActualTime, step.getDescription()));
                    morningActualTime = morningActualTime.plusMinutes(step.getTime());
                } else if (afternoonActualTime.plusMinutes(step.getTime()).isBefore(gymnasticsStartHour)) {
                    afternoonProduction.add(new ProductionSchedule(afternoonActualTime, step.getDescription()));
                    afternoonActualTime = afternoonActualTime.plusMinutes(step.getTime());
                }
            }

            afternoonProduction.add(new ProductionSchedule(afternoonActualTime, "Gymnastics"));

            for (ProductionSchedule step : morningProduction) {
                output += ("\n" + step.getTime() + " " + step.getDescription());
                processSteps.removeIf(processStep -> processStep.getDescription().contains(step.getDescription()));
            }

            output += ("\n" + lunchStartHour + " Lunch");

            for (ProductionSchedule step : afternoonProduction) {
                output += ("\n" + step.getTime() + " " + step.getDescription());
                processSteps.removeIf(processStep -> processStep.getDescription().contains(step.getDescription()));
            }

            morningActualTime = morningStartHour;
            afternoonActualTime = afternoonStartHour;
            morningProduction.clear();
            afternoonProduction.clear();
        }
        return output;
    }

    private static double sumProductionTime(List<ProcessSchedule> processSteps) {
        double totalProductionTime = 0.0;

        for (ProcessSchedule step : processSteps) {
            totalProductionTime += step.getTime();
        }
        return totalProductionTime;
    }
}
