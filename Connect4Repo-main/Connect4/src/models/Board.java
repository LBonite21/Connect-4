package models;

public class Board {
	
	public static final int MAX_COLS = 7;
	public static final int MAX_ROWS = 6;
	private Piece[][] pieces = new Piece[MAX_ROWS][MAX_COLS];
	
	
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

	public Board() {
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				pieces[i][j] = new Piece(Color.Empty);
			}
		}
	}

	public void placePiece(int col, Piece p) {
		
		if (pieces[0][col].color != Color.Empty) {
			throw new IllegalArgumentException("No open spaces in column " + col);
		}
		
		for(int r = MAX_ROWS - 1; r >= 0; r--) {
			if(pieces[r][col].color == Color.Empty) {
				pieces[r][col] = p;
				return;
			}
		}
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
