package models;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModelTests {

	@Test
	public void test() {
		// Nothing
	}

	@Test
	// Tests to make sure that creating a new Board doesn't return null
	public void CreateBoard() {
		Board testBoard = new Board();
		assertTrue(testBoard != null);
	}
	
	@Test
	// Tests to make sure that creating a new Piece doesn't return null and that it creates with the intended color
	public void CreatePiece() {
		Piece testPiece = new Piece(Color.Red);
		
		assertTrue(testPiece != null);
		assertTrue(testPiece.color == Color.Red);
	}
	
	@Test
	// Test creating a new Board, placing a piece, and that the piece is in the right spot
	public void PlacePiece() {
		Board testBoard = new Board();
		Piece testPiece = new Piece(Color.Red);
		
		// Places the piece
		testBoard.placePiece(0, testPiece);
		
		Piece checkPiece = testBoard.getPieces()[Board.MAX_ROWS - 1][0];
		assertTrue(checkPiece != null);
		assertTrue(checkPiece.color == Color.Red);
	}
	
	@Test
	// Tests creating a player
	public void CreatePlayer() {
		Player testPlayer = new Player();
		assertTrue(testPlayer != null);
	}
	
	@Test
	// Tests filling the bottom row with alternating red and yellow
	public void FillBottomRow() {
		Board testBoard = new Board();

		boolean yellow = false;
		for (int i = 0; i < Board.MAX_COLS - 1; i++) {
			Color tempColor = yellow ? Color.Yellow : Color.Red;
			Piece tempPiece = new Piece(tempColor);
			
			testBoard.placePiece(i, tempPiece);
			yellow = !yellow;
		}
		
		yellow = false;
		for (int i = 0; i < Board.MAX_COLS - 1; i++) {
			Color tempColor = yellow ? Color.Yellow : Color.Red;
			Piece tempPiece = testBoard.getPieces()[Board.MAX_ROWS - 1][i];
			
			assertTrue(tempPiece.color == tempColor);
			yellow = !yellow;
		}
	}
	
	@Test
	// Tests filling the bottom row with alternating red and yellow
	public void FillBoard() {
		Board testBoard = new Board();
		int currentRow = Board.MAX_ROWS - 1;
		
		for (; currentRow >= 0; currentRow--) {
			boolean yellow = false;
			for (int i = 0; i < Board.MAX_COLS - 1; i++) {
				Color tempColor = yellow ? Color.Yellow : Color.Red;
				Piece tempPiece = new Piece(tempColor);
				
				testBoard.placePiece(i, tempPiece);
				yellow = !yellow;
			}
			
			yellow = false;
			for (int i = 0; i < Board.MAX_COLS - 1; i++) {
				Color tempColor = yellow ? Color.Yellow : Color.Red;
				Piece tempPiece = testBoard.getPieces()[currentRow][i];
				
				assertTrue(tempPiece.color == tempColor);
				yellow = !yellow;
			}
		}
		
	}
}

