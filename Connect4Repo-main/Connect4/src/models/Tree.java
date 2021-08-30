package models;

import java.util.ArrayList;

public class Tree { //This is the mimimax algorithm

	public int value;
	Board board = new Board();
	Computer computer;
	private ArrayList<Integer> bestMoves;
//	Board prev = null;
	int depth;
	static int maxDepth = 4;

	public Tree(int depth, Computer computer) {

		this.bestMoves = new ArrayList<Integer>();
		this.depth = depth;
		this.computer = computer;
		this.value = getValue();

		if (depth < maxDepth && this.value < 100 && this.value > -100) {

			board.getAvailableCells();

			for (int i = 0; i < board.getAvailableCells().size(); i++) {
				board.placePiece(board.getAvailableCells().get(i), new Piece(computer.getColor()));
				Tree child = new Tree(depth + 1, computer);
				board.setPiece(Color.Empty);
				

				if (i == 0) {
					bestMoves.add(board.getAvailableCells().get(i));
					value = child.value;
				} else if (depth % 2 == 0) {
					if (value < child.value) {
						bestMoves.clear();
						bestMoves.add(board.getAvailableCells().get(i));
						this.value = child.value;
					} else if (value == child.value) {
						bestMoves.add(board.getAvailableCells().get(i));
					}
				} else if (depth % 2 == 1) {
					if (value > child.value) {
						bestMoves.clear();
						bestMoves.add(board.getAvailableCells().get(i));
						this.value = child.value;
					} else if (value == child.value) {
						bestMoves.add(board.getAvailableCells().get(i));
					}
				}
			}

		} else {
			this.value = getValue();
		}

	}

	public int getX() {
		int random = (int) (Math.random() * 100) % bestMoves.size();
		return bestMoves.get(random);
	}

	//Gets the value of each move
	private int getValue() {

		int value = 0;
		for (int j = 0; j < Board.MAX_ROWS; j++) {
			for (int i = 0; i < Board.MAX_COLS; i++) {
				if (board.getPieces()[j][i].color != Color.Empty) {
					if (computer.getName() == "Comp1") {
						value += possibleConnections(i, j) * (maxDepth - this.depth);
					} else {
						value -= possibleConnections(i, j) * (maxDepth - this.depth);
					}
				}
			}
		}

		return value;
	}

	private int possibleConnections(int i, int j) {
		int value = 0;
		value += lineOfFour(i, j, -1, -1);
		value += lineOfFour(i, j, -1, 0);
		value += lineOfFour(i, j, -1, 1);
		value += lineOfFour(i, j, 0, -1);
		value += lineOfFour(i, j, 0, 1);
		value += lineOfFour(i, j, 1, -1);
		value += lineOfFour(i, j, 1, 0);
		value += lineOfFour(i, j, 1, 1);

		return value;
	}

	private int lineOfFour(int x, int y, int i, int j) {

		int value = 1;
		Color pieceColor = board.getPieces()[x][y].color;

		for (int k = 0; k < 4; k++) {
			if (x + i * k < 0 || y + j * k < 0 || x + i * k >= board.getPieces().length
					|| y + j * k >= board.getPieces()[0].length) {
				return 0;
			}
			if (board.getPieces()[x + i * k][y + j * k].color == pieceColor) {
				value++;
			} else if (board.getPieces()[x + i * k][y + j * k].color != Color.Empty) {
				return 0;
			} else {
				for (int l = y + j * k; l >= 0; l--) {
					if (board.getPieces()[x + i * k][l].color == Color.Empty) {
						value--;
					}
				}
			}
		}

		if (value == 4)
			return 100;
		if (value < 0)
			return 0;
		return value;
	}

}
