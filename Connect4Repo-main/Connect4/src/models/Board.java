package models;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	public static final int MAX_COLS = 7;
	public static final int MAX_ROWS = 6;
	private Piece[][] pieces = new Piece[MAX_ROWS][MAX_COLS];
	public Color pieceColor = Color.Empty;
	
	public void resetBoard() {
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				pieces[i][j] = new Piece(Color.Empty);
			}
		}
	}
	
	public Piece[][] getPieces() {
		return pieces;
	}
	
	public void setPiece(Color pieceColor) {
		this.pieceColor = pieceColor;
	}

	public Board() {
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				pieces[i][j] = new Piece(Color.Empty);
			}
		}
	}
	
	//Gets available cells
	public List<Integer> getAvailableCells() {
		List<Integer> availableCells = new ArrayList<>();
//		piece = new Piece(Color.Empty);
		
		for (int i = 0; i < MAX_COLS; i++) {
//			for (int j = 0; j < pieces[i].length; j++) {
				if (pieces[0][i].color == Color.Empty) {
					availableCells.add(i);
				}
//			}
		}
//		System.out.println("Available Cells: " + availableCells + " ");
		
		return availableCells;
	}

	public int placePiece(int col, Piece p) {
		int row = 0;
		if (pieces[0][col].color != Color.Empty) {
			throw new IllegalArgumentException("No open spaces in column " + col);
		}
		
		for(int r = MAX_ROWS - 1; r >= 0; r--) {
			if(pieces[r][col].color == Color.Empty) {
				pieces[r][col] = p;
				row = r;
				break;
			}
		}
		return row;
	}
	
	public String toString() {
		String board = " 1  2  3  4  5  6  7\n";
		
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				board += "[" + pieces[i][j].toString() + "]";
			}
			board += "\n";
		}
		
		return board;
		
	}

}
