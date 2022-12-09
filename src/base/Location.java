package base;

import java.util.ArrayList;
import java.util.Collections;

import comparator.SortByBanknoteValue;
import player.Player;

public class Location {
	private ArrayList<Banknote> fund;
	private ArrayList<Integer> diceInLocation;
	private CasinoBudget budget;
	private int amountOfPlayer;
	
	public Location(int amountOfPlayer) {
		this.setAmountOfPlayer(amountOfPlayer);
		setFund(new ArrayList<Banknote>());
		setDiceInLocation(new ArrayList<Integer>());
		for(int i=0;i<this.getAmountOfPlayer();i++) {
			diceInLocation.add(0);
		}
		while( fundValue(fund) < 50000) {
			Banknote topbank = budget.getBanknoteTypeList().get(0);
			for(Banknote bank:fund) {
				if(bank.equals(topbank)) {
					bank.setAmount(bank.getAmount()+1);
					break;
				}
			}
			fund.add(new Banknote(topbank.getBanknoteValue()));
			Collections.sort(fund,new SortByBanknoteValue());
		}

	}

	public int fundValue(ArrayList<Banknote> fund){
		int value = 0;
		for(Banknote b:fund) {
			value += b.getBanknoteValue()*b.getAmount();
		}
		return value;
	}
	public void addDice(int amount,String Color) {
		//???
	}
	public int sendReward(Player p){
		int reward = fund.get(0).getBanknoteValue();
		fund.get(0).setAmount(fund.get(0).getAmount()-1);
		if(fund.get(0).getAmount() <= 0) {
			ArrayList<Banknote> newfund = new ArrayList<Banknote>() ;
			for(Banknote b: fund) {
				if(b.getAmount() > 0) {
					newfund.add(b);
				}
			}
			p.setBalance(p.getBalance()+reward);
			this.setFund(newfund);
			Collections.sort(fund,new SortByBanknoteValue());
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
	
	// เหลือ dic จาก player เกบใน location
}