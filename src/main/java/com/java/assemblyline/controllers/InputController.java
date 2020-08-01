package com.java.assemblyline.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputController {
    public static String readInput() {
        try {
            Path inputPath = Paths.get("./././././input.txt");
            String input = new String(Files.readAllBytes(inputPath));

            return input;

        } catch (Exception error) {
            return String.valueOf(error);
        }
    }
}
