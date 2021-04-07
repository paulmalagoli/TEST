package kd;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputGatherer {
	static Scanner reader = new Scanner(System.in);

	public static String gatherString(String prompt) {

		String message = null;
		boolean done = false;
		while (!done) {
			System.out.println(" " + prompt);

			try {
				message = reader.nextLine();
				if (message.equals("N") || message.equals("S") || message.equals("E") || message.equals("W")
						|| message.equals("U"))
					done = true;
				else
					System.out.print("Invalid input, try again.");
			} catch (InputMismatchException e) {
				System.out.print("Invalid input, try again.");
			}

		}

		return message;
	}

	public static int gatherInt(String prompt) {
		int message = 0;
		boolean done = false;
		while (!done) {
			System.out.println(" " + prompt);
			try {
				message = reader.nextInt();
				done = true;
			} catch (InputMismatchException e) {
				System.out.print("Invalid input, try again.");
			}
			reader.nextLine();
		}

		return message;
	}
}
