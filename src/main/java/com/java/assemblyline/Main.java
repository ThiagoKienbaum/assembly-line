package com.java.assemblyline;

import com.java.assemblyline.controllers.FormatInputController;
import com.java.assemblyline.controllers.InputController;
import com.java.assemblyline.controllers.OutputController;
import com.java.assemblyline.controllers.ProductionScheduleController;
import com.java.assemblyline.models.ProcessSchedule;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		final String input = InputController.readInput();

		List<ProcessSchedule> processSteps = FormatInputController.formatInput(input);

		String output = ProductionScheduleController.optimizeSteps(processSteps);

		OutputController.writeOutput(output);
	}
}
