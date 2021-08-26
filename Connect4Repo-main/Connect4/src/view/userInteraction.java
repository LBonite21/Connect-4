package view;

import java.util.Random;

import lib.ConsoleIO;
import models.Board;
import models.Player;

public class userInteraction {

	public String getPlayerName(int playerNum) {
		String name = ConsoleIO.promptForInput("Player " + playerNum + ", what is your name? ", false);
		if (name.trim().isEmpty() || name == "") {
			name = "Player";
		}
		return name;
	}

	public String getCompName() {
		String name = ConsoleIO.promptForInput("Computer , what is your name? ", false);
		if (name.trim().isEmpty() || name == "") {
			name = "Computer";
		}
		return name;
	}

	public int promptForDropColumn(Player turn) {
		boolean isInvalid = false;
		int column = 0;
		String prompt = turn.getName() + ", from 1-7 which column do you want? "; 

		do {
			try {
				column = ConsoleIO.promptForInt(prompt, 1,
						Board.MAX_COLS) - 1;
				isInvalid = false;
				if (column < 0 || column > 7) {
					System.out.println("Invalid input.");
					isInvalid = true;
				}
			} catch (NumberFormatException e) {
				System.out.println("Not a number! Try again.");
				isInvalid = true;
			}

		} while (isInvalid);

		return column;
	}

	public int promptCompDropColumn(Player turn) {
		boolean isInvalid = false;
		int column = 0;
		Random rng = new Random();

		do {

			System.out.print(turn.getName() + ", from 1-7 which column do you want? ");
			column = rng.nextInt(Board.MAX_COLS - 1);
			System.out.println(column + 1);

		} while (isInvalid);

		return column;
	}

	public void errorColumnFull(int col) {
		ConsoleIO.displayMessage("Sorry, col " + (col + 1) + " is full. Please try again.");
	}

	public int displayMainMenu() {
		String[] options = { "Human vs. Human", "Human vs. Comp", "Comp vs. Comp" };
		int menuSelection = ConsoleIO.promptForMenuSelection(options, true);
		return menuSelection;

	}

}
