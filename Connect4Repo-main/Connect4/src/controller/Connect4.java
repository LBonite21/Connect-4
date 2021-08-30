package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import lib.ConsoleIO;
import models.Board;
import models.Color;
import models.Computer;
import models.Piece;
import models.Player;
import models.Tree;
import view.userInteraction;

public class Connect4 {

	private userInteraction userInteraction = new userInteraction();
	private Board board = new Board();
	private Player player1 = new Player();
	private Player player2 = new Player();
	private Player currentPlayer;
//	private Computer computer;
	private boolean squareMode = false;
	private boolean gameOver = false;
	private Random rng = new Random();

	public void run() {


		do {
			System.out.println("WELCOME TO CONNECT 4!\n----------------------\n");
			int menuSelection = userInteraction.displayMainMenu();
			if (menuSelection == 1) {
				humanVsHuman();
			} else if (menuSelection == 2) {
				humanVsComp();
			} else if (menuSelection == 3) {
				compVsComp();
			}

			gameOver = menuSelection == 0 ? true
					: !ConsoleIO.promptForBool("Would you like to play again? Yes or No: ", "Yes", "No");
			System.out.println();
		} while (!gameOver);

	}

	private void AskForSquareMode() {
		String prompt = "In Square Mode, instead of creating a straight line, the goal is to create a 2x2 square with your color.\nChange to Square Mode? (yes/no) ";

		squareMode = ConsoleIO.promptForBool(prompt, "yes", "no");
	}

	private void humanVsHuman() {
		board.resetBoard();
		init();

		do {
			System.out.println("\n" + board);
			System.out.println();
			System.out.println("Next player is " + getNextPlayer() + "!\n");
			takeTurn();
			switchTurn();
		} while (!gameOver);

	}

	private void humanVsComp() {
		board.resetBoard();

		AskForSquareMode();

		rng = new Random();
		int goingFirst = rng.nextInt(2);

		if (goingFirst == 0) {
			System.out.print("Player goes first!\n\n");
			player1 = new Player(userInteraction.getPlayerName(1), Color.Red);
			player2 = new Computer("Comp1", Color.Yellow);
			currentPlayer = player1;
		} else {
			System.out.print("Computer goes first!\n\n");
			player1 = new Player(userInteraction.getPlayerName(1), Color.Yellow);
			player2 = new Computer("Comp1", Color.Red);
			currentPlayer = player2;
		}
		do {
			System.out.println("\n" + board);
			System.out.println();
			if (currentPlayer == player2) {
				System.out.println("Next player is " + getNextPlayer() + "!\n");
				compTakeTurn();
			} else {
				System.out.println("Next player is " + getNextPlayer() + "!\n");
				takeTurn();

			}
			switchTurn();
		} while (!gameOver);
	}

	private void compVsComp() {
		board.resetBoard();

		AskForSquareMode();

		rng = new Random();
		int goingFirst = rng.nextInt(2);

		if (goingFirst == 0) {
			System.out.print("Comp1 goes first!\n");
			player1 = new Computer("Comp1", Color.Red);
			player2 = new Computer("Comp2", Color.Yellow);
			currentPlayer = player1;
			System.out.print("\n" + player1.getName() + " is " + player1.getColor() + "!\n" + player2.getName() + " is "
					+ player2.getColor() + "!\n\n");
		} else {
			System.out.print("Comp2 goes first!\n");
			player1 = new Computer("Comp1", Color.Yellow);
			player2 = new Computer("Comp2", Color.Red);
			currentPlayer = player2;
			System.out.print("\n" + player1.getName() + " is " + player1.getColor() + "!\n" + player2.getName() + " is "
					+ player2.getColor() + "!\n\n");
		}
		do {
			board.getAvailableCells();
			System.out.println("\n" + board);
			System.out.println();
			System.out.println("Next player is " + getNextPlayer() + "!\n");
			compTakeTurn();
			switchTurn();
		} while (!gameOver);

	}

	private void init() {
		AskForSquareMode();

		rng = new Random();
		int goingFirst = rng.nextInt(2);

		if (goingFirst == 0) {
			System.out.print("Player 1 goes first!\n\n");
			player1 = new Player(userInteraction.getPlayerName(1), Color.Red);
			player2 = new Player(userInteraction.getPlayerName(2), Color.Yellow);
			currentPlayer = player1;

		} else {
			System.out.print("Player 2 goes first!\n\n");
			player1 = new Player(userInteraction.getPlayerName(2), Color.Yellow);
			player2 = new Player(userInteraction.getPlayerName(1), Color.Red);
			currentPlayer = player1;
		}
	}

	private void takeTurn() {
		boolean placed = false;
		int col = -1;
		int row = 0;
		while (!placed) {
			col = userInteraction.promptForDropColumn(currentPlayer);
			try {
				row = board.placePiece(col, new Piece(currentPlayer.getColor()));
				placed = true;
			} catch (IllegalArgumentException ex) {
				userInteraction.errorColumnFull(col);
			}
		}
		checkWin(row, col, currentPlayer.getColor());
	}

	private void compTakeTurn() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean placed = false;
		int col = -1;
		int row = 0;

		while (!placed) {
			col = userInteraction.promptCompDropColumn(currentPlayer, minimax());
			System.out.println("Press ENTER to continue");
			try {
				reader.readLine();
				row = board.placePiece(col, new Piece(currentPlayer.getColor()));
				placed = true;
			} catch (IllegalArgumentException | IOException ex) {
				userInteraction.errorColumnFull(col);
			}
		}
		checkWin(row, col, currentPlayer.getColor());
	}

	private void switchTurn() {
		currentPlayer = (currentPlayer == player1 ? player2 : player1);
	}

	public int minimax() {

		int col = 0;

		if (currentPlayer.getName() == "Comp1" || currentPlayer.getName() == "Comp2") {

			Tree tree = new Tree(0, new Computer(currentPlayer.getName(), currentPlayer.getColor()));
			col = tree.getX();
		}

		return col;
	}

	public Player getNextPlayer() {
		Player nextPlayer = null;

		nextPlayer = (currentPlayer == player1 ? player2 : player1);

		return nextPlayer;
	}

	private boolean checkWin(int row, int col, Color color) {
		if (squareMode) {
			// Square Check
			gameOver = SquareWinCheck(row, col, color) || gameOver;
		} else {
			// HorizontalCheck
			gameOver = HorizontalWinCheck(row, col, color);
			// VerticalCheck
			gameOver = VerticalWinCheck(row, col, color) || gameOver;
			// Diagonal Check
			gameOver = DiagonalWinCheck(row, col, color) || gameOver;
		}

		return gameOver;
	}

	private boolean HorizontalWinCheck(int row, int col, Color color) {
		int win = 1;

		// Horizontal Right Win Condition
		for (int i = col + 1; i < Board.MAX_COLS; i++) {
			if (board.getPieces()[row][i].color == color) {
				win += 1;
			} else {
				break;
			}
		}

		// Horizontal Left Win Condition
		for (int i = col - 1; i >= 0; i--) {
			if (board.getPieces()[row][i].color == color) {
				win += 1;
			} else {
				break;
			}
		}

		if (win == 4) {
			System.out.println(board);
			System.out.println(currentPlayer.getName() + " won the game!!!!");
			gameOver = true;
		}
		return gameOver;
	}

	private boolean VerticalWinCheck(int row, int col, Color color) {
		int win = 0;

		for (int i = row; i < Board.MAX_ROWS; i++) {
			if (board.getPieces()[i][col].color == color) {
				win += 1;
			} else {
				break;
			}
		}

		if (win == 4) {
			System.out.println(board);
			System.out.println(currentPlayer.getName() + " won the game!!!!");
			gameOver = true;
		}
		return gameOver;
	}

	private boolean DiagonalWinCheck(int row, int col, Color color) {
		int win = 1;
		int j = col + 1;

		// Up-Right
		for (int i = row - 1; i >= 0; i--) {
			if (j > Board.MAX_COLS - 1) {
				break;
			}

			if (board.getPieces()[i][j].color == color) {
				win += 1;
				j++;
			} else {
				break;
			}
		}

		// Down-Left
		j = col - 1;
		for (int i = row + 1; i < Board.MAX_ROWS; i++) {
			if (j < 0) {
				break;
			}

			if (board.getPieces()[i][j].color == color) {
				win += 1;
				j--;
			} else {
				break;
			}
		}

		if (win == 4) {
			System.out.println(board);
			System.out.println(currentPlayer.getName() + " won the game!!!!");
			gameOver = true;
		}

		// Up-Left
		win = 1;
		j = col - 1;
		for (int i = row - 1; i >= 0; i--) {
			if (j < 0) {
				break;
			}

			if (board.getPieces()[i][j].color == color) {
				win += 1;
				j--;
			} else {
				break;
			}
		}

		// Down-Right
		j = col + 1;
		for (int i = row + 1; i < Board.MAX_ROWS; i++) {
			if (j > Board.MAX_COLS - 1) {
				break;
			}

			if (board.getPieces()[i][j].color == color) {
				win += 1;
				j++;
			} else {
				break;
			}
		}

		if (win == 4) {
			System.out.println(board);
			System.out.println(currentPlayer.getName() + " won the game!!!!");
			gameOver = true;
		}
		return gameOver;
	}

	private boolean SquareWinCheck(int row, int col, Color color) {
		// There is no top left / top right because those scenarios should be impossible
		boolean result = false;
		boolean topRow = false;
		boolean bottomRow = false;

		// Checks the "bottom left" potential square
		if (row < Board.MAX_ROWS - 1 && col > 0 && !result) {
			topRow = board.getPieces()[row + 1][col - 1].color == color
					&& board.getPieces()[row + 1][col].color == color;
			bottomRow = board.getPieces()[row][col - 1].color == color && board.getPieces()[row][col].color == color;
			result = topRow && bottomRow;
		}

		// Checks the "bottom right" potential square
		if (row < Board.MAX_ROWS - 1 && col < Board.MAX_COLS - 1 && !result) {
			topRow = board.getPieces()[row + 1][col].color == color
					&& board.getPieces()[row + 1][col + 1].color == color;
			bottomRow = board.getPieces()[row][col].color == color && board.getPieces()[row][col + 1].color == color;
			result = topRow && bottomRow;
		}

		if (result) {
			System.out.println(board);
			System.out.println(currentPlayer.getName() + " won the game!!!!");
		}

		return result;
	}
}
