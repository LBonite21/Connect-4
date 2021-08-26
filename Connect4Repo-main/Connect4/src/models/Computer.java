package models;

import java.util.Random;

import lib.ConsoleIO;

public class Computer extends Player {

	private Random rng = new Random();

	public Computer(String playerName, Color color) {
		this.setName(playerName);
		this.setColor(color);
	}
	
	@Override
	public Color getColor() {
		return super.getColor();
	}
	
	@Override
	public void setColor(Color color) {
		super.setColor(color);
	}

	@Override
	public void setName(String name) {
		super.setName(name);
	}

	@Override
	public int takeTurn() {
		int compColumn = 0;
		compColumn = ConsoleIO.promptForInt(getName() + ", from 1-7 which column do you want? ", 1, Board.MAX_COLS) - 1;
		
		//Make AI think
		
		compColumn = rng.nextInt(6 + 1);
		return compColumn;
	}

}
