package models;

import lib.ConsoleIO;

public class Player {
	
	protected String name;
	private Color color;
	
	public Player() {}
	
	public Player(String name, Color color) {
		this.setName(name);
		this.setColor(color);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public int takeTurn() {
		int column = 0;
		column = ConsoleIO.promptForInt(getName() + ", from 1-7 which column do you want? ", 1, Board.MAX_COLS) - 1;
		return column;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
