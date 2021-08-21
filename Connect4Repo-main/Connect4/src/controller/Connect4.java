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
//			checkWin(0, currentPlayer.takeTurn(), currentPlayer.getColor());
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
//			checkWin(0, currentPlayer.takeTurn(), currentPlayer.getColor());
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
//			checkWin(0, currentPlayer.takeTurn(), currentPlayer.getColor());
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

		while (!placed) {
			col = userInteraction.promptForDropColumn(currentPlayer);
			try {
				board.placePiece(col, new Piece(currentPlayer.getColor()));
				placed = true;
			} catch (IllegalArgumentException ex) {
				userInteraction.errorColumnFull(col);
			}
		}
//		checkWin(0, col, currentPlayer.getColor());
	}

	private void compTakeTurn() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean placed = false;
		int col = -1;

		while (!placed) {
			col = userInteraction.promptCompDropColumn(currentPlayer);
			System.out.println("Press ENTER to continue");
			try {
				reader.readLine();
				board.placePiece(col, new Piece(currentPlayer.getColor()));
				placed = true;
			} catch (IllegalArgumentException | IOException ex) {
				userInteraction.errorColumnFull(col);
			}
		}
//		checkWin(0, col, currentPlayer.getColor());
	}

	private void switchTurn() {
		currentPlayer = (currentPlayer == player1 ? player2 : player1);

	}

	private boolean checkWin(int row, int col, Color color) {
//		Piece[][] pieces = new Piece[Board.MAX_ROWS][Board.MAX_COLS];

		int win = 1;

		// horizontal right
		for (int i = col + 1; i < col + 3; i++) {
			if (i >= col + 3) {
				continue;
			}
			if (board.getPieces()[row][i].color == color) {
				win += 1;
			}
		}

		// horizontal left
		for (int i = col - 1; i < col - 3; i++) {
			if (i >= col - 3) {
				continue;
			}
			if (board.getPieces()[row][i].color == color) {
				win += 1;
			}
		}

		if (win == 4) {
			System.out.println(board);
			System.out.println("You won the game!!!!");
			gameOver = true;
		}

//		int win = 0;
//
//		// horizontalCheck
//
//		for (int j = 0; j < Board.MAX_COLS - 3; j++) {
//			for (int i = 0; i < Board.MAX_ROWS; i++) {
//
//				if (board.getPieces()[i][j] != null) {
//					if (board.getPieces()[i][j].color == currentPlayer.getColor()) {
//						win += 1;
//					}
//				}
//				if (board.getPieces()[i][j + 1] != null) {
//					if (board.getPieces()[i][j + 1].color == currentPlayer.getColor()) {
//						win += 1;
//					}
//					if (board.getPieces()[i][j + 2] != null) {
//						if (board.getPieces()[i][j + 2].color == currentPlayer.getColor()) {
//							win += 1;
//						}
//					}
//
//					if (board.getPieces()[i][j + 3] != null) {
//						if (board.getPieces()[i][j + 3].color == currentPlayer.getColor()) {
//							win += 1;
//						}
//					}
//				}
//
//			}
//		}
//	    // verticalCheck
//	    for (int i = 0; i<getWidth()-3 ; i++ ){
//	        for (int j = 0; j<this.getHeight(); j++){
//	            if (this.board[i][j] == currentPlayer && this.board[i+1][j] == currentPlayer && this.board[i+2][j] == currentPlayer && this.board[i+3][j] == currentPlayer){
//	                return true;
//	            }           
//	        }
//	    }
//	    // ascendingDiagonalCheck 
//	    for (int i=3; i<getWidth(); i++){
//	        for (int j=0; j<getHeight()-3; j++){
//	            if (this.board[i][j] == currentPlayer && this.board[i-1][j+1] == currentPlayer && this.board[i-2][j+2] == currentPlayer && this.board[i-3][j+3] == currentPlayer)
//	                return true;
//	        }
//	    }
//	    // descendingDiagonalCheck
//	    for (int i=3; i<getWidth(); i++){
//	        for (int j=3; j<getHeight(); j++){
//	            if (this.board[i][j] == currentPlayer && this.board[i-1][j-1] == currentPlayer && this.board[i-2][j-2] == currentPlayer && this.board[i-3][j-3] == currentPlayer)
//	                return true;
//	        }
//	    }
//	    return false;

		return gameOver;
	}
}
