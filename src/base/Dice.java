package base;

import java.util.Random;

public class Dice {

	private String colour;
	private int point;

	public Dice(String colour) {
		// TODO Auto-generated constructor stub
		setColour(colour);
		setPoint(1);
	}

	public void rolling() {
		Random random = new Random();
		setPoint(random.nextInt(6) + 1);
		//return "dice" + getPoint();
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

}
