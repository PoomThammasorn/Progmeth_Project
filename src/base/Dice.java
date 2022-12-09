package base;

import java.util.Random;

public class Dice {
	
	private String colour;
	

	public Dice(String colour) {
		// TODO Auto-generated constructor stub
		setColour(colour);
	}
	
	public void rolling() {
		Random random = new Random();
		
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}
	
	

}
