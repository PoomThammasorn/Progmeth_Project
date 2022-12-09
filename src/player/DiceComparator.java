package player;

import java.util.Comparator;
import base.Dice;

public class DiceComparator implements Comparator<Dice> {

	@Override
	public int compare(Dice d1, Dice d2) {
		// TODO Auto-generated method stub
		return d1.getPoint() - d2.getPoint();
	}

}
