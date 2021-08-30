package models;


public class Computer extends Player {

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

}
