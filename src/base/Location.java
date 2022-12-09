package base;

import java.util.ArrayList;
import java.util.Collections;

public class Location {
	private ArrayList<Banknote> fund;
	private ArrayList<Integer> diceInLocation;
	private int amountOfPlayer;
	
	public Location(int amountOfPlayer) {
		this.setAmountOfPlayer(amountOfPlayer);
		setFund(new ArrayList<Banknote>());
		setDiceInLocation(new ArrayList<Integer>());
		for(int i=0;i<this.getAmountOfPlayer();i++) {
			diceInLocation.add(0);
		}
		/*while( fundValue(fund) < 40000) {
			fund f = กองการ์ดบนสุด
			for(Banknote b:fund) {
				if(b.equals(f)) {
					b.setAmount(b.getAmount()+1);
					break;
				}
			}
			fund.add(new Banknote(f.getBanknoteValue()));
			Collections.sort(fund,new SortByBanknoteValue()); Cheack sort ว่าถูกไหม
		}*/

	}

	public int fundValue(ArrayList<Banknote> fund){
		int value = 0;
		for(Banknote b:fund) {
			value += b.getBanknoteValue()*b.getAmount();
		}
		return value;
	}
	
	public int sendReward(){
		int reward = fund.get(0).getBanknoteValue();
		fund.get(0).setAmount(fund.get(0).getAmount()-1);
		if(fund.get(0).getAmount() <= 0) {
			ArrayList<Banknote> newfund = new ArrayList<Banknote>() ;
			for(Banknote b: fund) {
				if(b.getAmount() > 0) {
					newfund.add(b);
				}
			}
			// ปรับจำนวนเงินของ player
			this.setFund(newfund);
			//Collections.sort(fund,new SortByBanknoteValue()); Cheack sort ว่าถูกไหม
		}
		return reward;
	}
	
	public int getAmountOfPlayer() {
		return amountOfPlayer;
	}

	public void setAmountOfPlayer(int amountOfPlayer) {
		if(this.amountOfPlayer < 2)
		{
			this.amountOfPlayer = 2;
			return;
		}
		else if (this.amountOfPlayer > 5)
		{
			this.amountOfPlayer = 5;
		}
		this.amountOfPlayer = amountOfPlayer;
	}

	public ArrayList<Banknote> getFund() {
		return fund;
	}

	public void setFund(ArrayList<Banknote> fund) {
		this.fund = fund;
	}

	public ArrayList<Integer> getDiceInLocation() {
		return diceInLocation;
	}

	public void setDiceInLocation(ArrayList<Integer> diceInLocation) {
		this.diceInLocation = diceInLocation;
	}
	
	
}
