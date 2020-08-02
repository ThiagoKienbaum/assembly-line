package com.java.assemblyline;

import com.java.assemblyline.controllers.FormatInputController;
import com.java.assemblyline.controllers.InputController;
import com.java.assemblyline.controllers.OutputController;

public class Main {
	public static void main(String[] args) {
		String input = InputController.readInput();


		var retorno = FormatInputController.formatInput(input);
//		System.out.printf(">> %s%n", retorno);
//		System.out.println(input);
		OutputController.writeOutput(input);

	}
}
