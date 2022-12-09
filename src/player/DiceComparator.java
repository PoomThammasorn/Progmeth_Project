package player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import base.Banknote;
import base.Dice;
import base.SortByBanknoteValue;

public class DiceComparator implements Comparator<Dice> {

	@Override
	public int compare(Dice d1, Dice d2) {
		// TODO Auto-generated method stub
		return d1.getPoint() - d2.getPoint();
	}
}