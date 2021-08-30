package models;

public class Piece {
	
	public Color color;

	public Piece(Color c) {
		color = c;
	}

	@Override
	public String toString() {
		String result = " ";
		if (color != Color.Empty) {
			if (color == Color.Red) {
				result = "R";
			} else {
				result = "Y";
			}

//			result = (color == Color.Red ? "R" : "Y");
//			result = "" + color.toString().charAt(0);
			
			
		}
		
		return result;
	}
	

}
