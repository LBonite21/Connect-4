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
import view.userInteraction;

public class Connect4 {

	private userInteraction userInteraction = new userInteraction();
	private Board board = new Board();
	private Player player1 = new Player();
	private Player player2 = new Player();
	private Player currentPlayer;
	private boolean gameOver = false;
	private Random rng = new Random();

	public void run() {

		System.out.println("WELCOME TO CONNECT 4!\n----------------------\n");

		do {
			int menuSelection = userInteraction.displayMainMenu();
			if (menuSelection == 1) {
				humanVsHuman();
			} else if (menuSelection == 2) {
				humanVsComp();
			} else if (menuSelection == 3) {
				compVsComp();
			} else if (menuSelection == 0) {
				continue;
			}
			
			gameOver = !ConsoleIO.promptForBool("Would you like to play again? Yes or No: ", "Yes", "No");
		} while (!gameOver);

	}

	private void humanVsHuman() {
		board.resetBoard();
		init();

		do {
			System.out.println("\n" + board);
			System.out.println();
			takeTurn();
			switchTurn();
		} while (!gameOver);

	}

	private void humanVsComp() {
		board.resetBoard();
		rng = new Random();
		int goingFirst = rng.nextInt(2);

		if (goingFirst == 0) {
			System.out.print("Player goes first!\n\n");
			player1 = new Player(userInteraction.getPlayerName(1), Color.Red);
			player2 = new Computer(userInteraction.getCompName(), Color.Yellow);
			currentPlayer = player1;
		} else {
			System.out.print("Computer goes first!\n\n");
			player2 = new Computer(userInteraction.getCompName(), Color.Red);
			player1 = new Player(userInteraction.getPlayerName(1), Color.Yellow);
			currentPlayer = player2;
		}
		do {
			System.out.println("\n" + board);
			System.out.println();
			if (currentPlayer == player2) {
				compTakeTurn();
			} else {
				takeTurn();
			}
			switchTurn();
		} while (!gameOver);
	}

	private void compVsComp() {
		board.resetBoard();
		rng = new Random();
		int goingFirst = rng.nextInt(2);

		if (goingFirst == 0) {
			System.out.print("Player goes first!\n\n");
			player1 = new Computer(userInteraction.getCompName(), Color.Red);
			player2 = new Computer(userInteraction.getCompName(), Color.Yellow);
			currentPlayer = player1;
		} else {
			System.out.print("Computer goes first!\n\n");
			player1 = new Computer(userInteraction.getCompName(), Color.Red);
			player2 = new Computer(userInteraction.getCompName(), Color.Yellow);
			currentPlayer = player1;
		}
		do {
			System.out.println("\n" + board);
			System.out.println();
			compTakeTurn();
			switchTurn();
		} while (!gameOver);

	}

	private void init() {
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
			col = userInteraction.promptCompDropColumn(currentPlayer);
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

	private boolean checkWin(int row, int col, Color color) {
		//HorizontalCheck
		gameOver = HorizontalWinCheck(row, col, color);
	    // VerticalCheck
		gameOver = VerticalWinCheck(row, col, color);
		//Diagonal Check
		gameOver = DiagonalWinCheck(row, col, color);
		
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
			System.out.println(color.toString() + " won the game!!!!");
			gameOver = true;
		}
		return gameOver;
	}
	
	private boolean VerticalWinCheck(int row, int col, Color color) {
		int win = 0;
		
		for(int i = row; i < Board.MAX_ROWS; i++) {
			if (board.getPieces()[i][col].color == color) {
				win += 1;
			} else {
				break;
			}
		}
		
		if (win == 4) {
			System.out.println(board);
			System.out.println(color.toString() + " won the game!!!!");
			gameOver = true;
		}
		return gameOver;
	}
	
	private boolean DiagonalWinCheck(int row, int col, Color color) {
		int win = 1;
		int j = col + 1;
		
		// Up-Right
		for(int i = row - 1; i >= 0; i--) {
			if(j > Board.MAX_COLS - 1) {
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
		for(int i = row + 1; i < Board.MAX_ROWS; i++) {
			if(j < 0) {
				break;
			}
			
			if (board.getPieces()[i][j].color == color) {
				win += 1;
				j--;
			} else {
				break;
			}
		}

		// Up-Left
		win = 1;
		j = col - 1;
		for(int i = row - 1; i >= 0; i--) {
			if(j < 0) {
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
		for(int i = row + 1; i < Board.MAX_ROWS; i++) {
			if(j > Board.MAX_COLS - 1) {
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
			System.out.println(color.toString() + " won the game!!!!");
			gameOver = true;
		}
		return gameOver;
	}
}
