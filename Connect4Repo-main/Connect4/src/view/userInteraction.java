package view;

import lib.ConsoleIO;
import models.Board;
import models.Player;

public class userInteraction {
	
	private Board board;

	public String getPlayerName(int playerNum) {
		String name = ConsoleIO.promptForInput("Player " + playerNum + ", what is your name? ", false);
		if (name.trim().isEmpty() || name == "") {
			name = "Player";
		}
		return name;
	}

	public int promptForDropColumn(Player turn) {
		boolean isInvalid = false;
		int column = 0;

		do {
			try {
				column = turn.takeTurn();
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

	public int promptCompDropColumn(Player turn, int col) {
		int column = 0;
		
		boolean isInvalid = false;

		do {
			
			System.out.println(turn.getName() + ", is choosing a column!");
			column = col;
			
		} while (isInvalid);

		return column;
	}

	
	public void errorColumnFull(int col) {
		ConsoleIO.displayMessage("Sorry, col " + (col + 1) + " is full. Please try again.");
	}

	public int displayMainMenu() {
		String[] options = { "Human vs. Human", "Human vs. Comp", "Comp vs. Comp", "Human vs. Human (GUI" };
		int menuSelection = ConsoleIO.promptForMenuSelection(options, true);
		return menuSelection;

	}

}
