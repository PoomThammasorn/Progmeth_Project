package comparator;

import java.util.*;

import base.Banknote;

public class SortByBanknoteValue implements Comparator<Banknote>{

	@Override
	public int compare(Banknote b1, Banknote b2) {
		// TODO Auto-generated method stub
		return b2.getBanknoteValue() - b1.getBanknoteValue();
	}
}
