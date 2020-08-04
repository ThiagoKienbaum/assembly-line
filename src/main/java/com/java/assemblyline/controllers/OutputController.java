package com.java.assemblyline.controllers;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class OutputController {
    public static void writeOutput(String output) {
        try {
            final Path outputPath = Paths.get("./././././output.txt");
            Files.write(outputPath, Collections.singleton(output), StandardCharsets.UTF_8);

        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
