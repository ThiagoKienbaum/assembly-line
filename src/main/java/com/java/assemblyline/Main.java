package com.java.assemblyline;

import com.java.assemblyline.controllers.InputController;
import com.java.assemblyline.controllers.ProductionScheduleController;

public class Main {
	public static void main(String[] args) {
		final String input = InputController.readInput();

//		final ArrayList<ProductionProcess> formattedInput = FormatInputController.formatInput(input);
//
		ProductionScheduleController.optimizeSteps(input);

//		OutputController.writeOutput(input);

	}
}
