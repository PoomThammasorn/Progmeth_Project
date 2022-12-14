package base;

import player.Player;

public class SpecialLocation extends Location {

	public SpecialLocation(String name, int amountOfPlayer, int dicevalue) {
		super(name, amountOfPlayer, dicevalue);
	}

	public int sendReward(Player p) {
		int reward = super.sendReward(p);
		p.setBalance(p.getBalance() + reward);
		return reward;

	}

}