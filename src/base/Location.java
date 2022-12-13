package base;

import java.util.ArrayList;
import java.util.Collections;

import comparator.SortByBanknoteValue;
import player.Player;

public class Location {
	private ArrayList<Banknote> fund = new ArrayList<>();
	private ArrayList<Integer> diceInLocation = new ArrayList<>(); 
	private String name;
	private CasinoBudget budget = new CasinoBudget();
	private int amountOfPlayer;
	private int diceValue;

	public Location(String name, int amountOfPlayer, int diceValue) {
		this.setDiceValue(diceValue);
		this.setName(name);
		this.setAmountOfPlayer(amountOfPlayer);
		setFund(new ArrayList<Banknote>());
		setDiceInLocation(new ArrayList<Integer>());
		for (int i = 0; i < amountOfPlayer; i++) {
			diceInLocation.add(0);
		}
		while (fundValue(fund) < 50000) {
			this.updateFund();
		}

	}

	public int fundValue(ArrayList<Banknote> fund) {
		int value = 0;
		for (Banknote b : fund) {
			value += b.getBanknoteValue() * b.getAmount();
		}
		return value;
	}

	public int addDice(Player p, int amount) {
		if (p.getPlayerColour().equals("white")) {
			amount += this.getDiceInLocation().get(0);
			this.getDiceInLocation().set(0, amount);
		} else if (p.getPlayerColour().equals("blue")) {
			amount += this.getDiceInLocation().get(1);
			this.getDiceInLocation().set(1, amount);
		} else if (p.getPlayerColour().equals("red")) {
			amount += this.getDiceInLocation().get(2);
			this.getDiceInLocation().set(2, amount);
		} else if (p.getPlayerColour().equals("green")) {
			amount += this.getDiceInLocation().get(3);
			this.getDiceInLocation().set(3, amount);
		} else if (p.getPlayerColour().equals("yellow")) {
			amount += this.getDiceInLocation().get(4);
			this.getDiceInLocation().set(4, amount);
		}
		return amount;
	}

	public boolean haveSameElement(int amount,int index) {
		boolean nothavesame = true;
		for (int i = 0; i < this.getDiceInLocation().size(); i++) {
			if (this.getDiceInLocation().get(i) == amount && i != index) {
				nothavesame = false;
				this.getDiceInLocation().set(i, 0);
			}
		}
		return nothavesame;
	}

	public int sendReward(Player p) {
		int reward = this.getFund().get(0).getBanknoteValue();
		this.getFund().get(0).setAmount(this.getFund().get(0).getAmount() - 1);
		if (this.getFund().get(0).getAmount() <= 0) {
			ArrayList<Banknote> newfund = new ArrayList<Banknote>();
			for (Banknote b : this.getFund()) {
				if (b.getAmount() > 0) {
					newfund.add(b);
				}
			}
			this.setFund(newfund);
			Collections.sort(this.getFund(), new SortByBanknoteValue());
		}
		p.setBalance(p.getBalance() + reward);
		return reward;
	}

	public void updateFund() {
		boolean found = false;
		Banknote topbank = budget.getBanknoteTypeList().get(0);
		if (fund.size() == 0) {
			fund.add(new Banknote(topbank.getBanknoteValue()));
		} else {
			for (Banknote bank : fund) {
				if (bank.equals(topbank)) {
					bank.setAmount(bank.getAmount() + 1);
					found = true;
					break;
				}
			}
			if(!found) {
				fund.add(new Banknote(topbank.getBanknoteValue()));
			}
		}
		Collections.sort(fund, new SortByBanknoteValue());
	}
	
	public void resetDiceInLocation() {
		for (int i = 0; i < getDiceInLocation().size(); i++) {
			this.diceInLocation.set(i, 0);
		}
	}

	public int getAmountOfPlayer() {
		return amountOfPlayer;
	}

	public void setAmountOfPlayer(int amountOfPlayer) {
		if (this.amountOfPlayer < 2) {
			this.amountOfPlayer = 2;
			return;
		} else if (this.amountOfPlayer > 5) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name.equals("")) {
			this.name = "Location";
			return;
		}
		this.name = name;
	}

	public int getDiceValue() {
		return diceValue;
	}

	public void setDiceValue(int diceValue) {
		this.diceValue = diceValue;
	}

}