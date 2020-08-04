package com.java.assemblyline.controllers;

import com.java.assemblyline.models.ProcessSchedule;
import com.java.assemblyline.models.ProductionSchedule;
import org.w3c.dom.ls.LSOutput;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductionScheduleController {
    public static void optimizeSteps(String input) {
        final LocalTime morningStarHour = LocalTime.of(9, 0);
        final LocalTime lunchStartHour = LocalTime.of(12, 0);
        final LocalTime afternoonStartHour = LocalTime.of(13, 0);
        final LocalTime gymnasticsSoonerStartHour = LocalTime.of(16, 0);
        final LocalTime gymnasticsLatestStartHour = LocalTime.of(17, 0);
        final LocalTime morningPeriod = lunchStartHour.minusHours(morningStarHour.getHour());
        final LocalTime lunchPeriod = afternoonStartHour.minusHours(lunchStartHour.getHour());;
        final LocalTime afternoonMinimumPeriod = gymnasticsSoonerStartHour.minusHours(afternoonStartHour.getHour());;
        final LocalTime afternoonMaximumPeriod = gymnasticsLatestStartHour.minusHours(afternoonStartHour.getHour());;
        final LocalTime dailyMinimumProductionPeriod = morningPeriod.plusHours(afternoonMinimumPeriod.getHour());;
        final LocalTime dailyMaximumProductionPeriod = morningPeriod.plusHours(afternoonMaximumPeriod.getHour());;
        LocalTime morningActualTime = morningStarHour;
        LocalTime afternoonActualTime = afternoonStartHour;

        ArrayList<ProductionSchedule> morningProduction = new ArrayList<>();
        ArrayList<ProductionSchedule> afternoonProduction = new ArrayList<>();

        List<ProcessSchedule> process = new ArrayList<>();
        process.add(new ProcessSchedule(60, "Cutting of steel sheets 60min"));
        process.add(new ProcessSchedule(30, "Austenpera (Heat treatment) 30min"));
        process.add(new ProcessSchedule(45, "Tempering sub-zero (Heat treatment) 45min"));
        process.add(new ProcessSchedule(60, "Safety sensor assembly 60min"));
        process.add(new ProcessSchedule(45, "Pieces washing 45min"));
        process.add(new ProcessSchedule(30, "Axis calibration 30min"));
        process.add(new ProcessSchedule(45, "Steel bearing assembly 45min"));
        process.add(new ProcessSchedule(5, "Assembly line cooling - maintenance"));
        process.add(new ProcessSchedule(45, "Nitriding process 45min"));
        process.add(new ProcessSchedule(60, "Injection subsystem assembly 60min"));
        process.add(new ProcessSchedule(30, "Compliance check 30min"));
        process.add(new ProcessSchedule(60, "Navigation subsystem assembly 60min"));
        process.add(new ProcessSchedule(60, "Torque converter subsystem calibration 60min"));
        process.add(new ProcessSchedule(30, "Left stabilizer bar alignment 30min"));
        process.add(new ProcessSchedule(45, "Setup of lock and control device 45min"));
        process.add(new ProcessSchedule(30, "Right stabilizer bar alignment 30min"));
        process.add(new ProcessSchedule(45, "Seal installation 45min"));
        process.add(new ProcessSchedule(30, "Application of decals 30min"));
        process.add(new ProcessSchedule(30, "Monitoring subsystem assembly 30min"));



        List<String> processSteps = new ArrayList<>(getSteps(input));
        ArrayList<Integer> processStepsTime = getTime(processSteps);
        final double processMinutes = sumProductionTime(processStepsTime);
        final int numberOfAssemblyLines = ((int) Math.ceil(processMinutes /
                (dailyMaximumProductionPeriod.getHour() *60)));

        for (int i = 1; i <= numberOfAssemblyLines; i++) {
            System.out.println("\nLinha de montagem: " + i);

            for (int j = 0; j < process.size(); j++) {
                if (morningActualTime.plusMinutes(process.get(j).getTime()).isBefore(lunchStartHour)) {
                    morningProduction.add(new ProductionSchedule(morningActualTime, process.get(j).getDescription()));
                    morningActualTime = morningActualTime.plusMinutes(process.get(j).getTime());
                } else if (afternoonActualTime.plusMinutes(process.get(j).getTime()).isBefore(gymnasticsLatestStartHour)) {
                    afternoonProduction.add(new ProductionSchedule(afternoonActualTime, process.get(j).getDescription()));
                    afternoonActualTime = afternoonActualTime.plusMinutes(process.get(j).getTime());
                }
            }

            afternoonProduction.add(new ProductionSchedule(afternoonActualTime, "Gymnastics"));

            for (ProductionSchedule step : morningProduction) {
                System.out.println(step.getTime() + " " + step.getDescription());
                process.removeIf(processStep -> processStep.getDescription().contains(step.getDescription()));
            }
            System.out.println(lunchStartHour + " Almoço");
            for (ProductionSchedule step : afternoonProduction) {
                System.out.println(step.getTime() + " " + step.getDescription());
                process.removeIf(processStep -> processStep.getDescription().contains(step.getDescription()));
            }



            morningActualTime = morningStarHour;
            afternoonActualTime = afternoonStartHour;
            morningProduction.clear();
            afternoonProduction.clear();
        }
    }

    private static List<String> getSteps(String input) {
        String[] processSteps = input.split("\n");
        return Arrays.asList(processSteps);
    }

    private static ArrayList<Integer> getTime(List<String> processSteps) {
        ArrayList<Integer> processStepsTime = new ArrayList<>();
        for (String step : processSteps) {
            if (step.contains("maintenance")) {
                processStepsTime.add(5);
            } else {
                processStepsTime.add(Integer.valueOf(step.replaceAll("\\D+","")));
            }
        }
        return processStepsTime;
    }

    private static double sumProductionTime(ArrayList<Integer> processStepsTime) {
        double totalProductionTime = 0.0;

        for (int step : processStepsTime) {
            totalProductionTime += step;
        }
        return totalProductionTime;
    }
}
