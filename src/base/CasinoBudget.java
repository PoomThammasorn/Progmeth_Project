package base;

import java.util.ArrayList;
import java.util.Collections;

public class CasinoBudget {
	private ArrayList<Banknote> banknoteTypeList = new ArrayList<>();

	public CasinoBudget() {
		banknoteTypeList.add(new Banknote(10000));
		banknoteTypeList.add(new Banknote(20000));
		banknoteTypeList.add(new Banknote(30000));
		banknoteTypeList.add(new Banknote(40000));
		banknoteTypeList.add(new Banknote(50000));
		banknoteTypeList.add(new Banknote(60000));
		banknoteTypeList.add(new Banknote(70000));
		banknoteTypeList.add(new Banknote(80000));

	}

	public ArrayList<Banknote> getBanknoteTypeList() {
		Collections.shuffle(banknoteTypeList);
		return banknoteTypeList;
	}

}