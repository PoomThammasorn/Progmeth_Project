package base;

import player.Player;

public class SpecialLocation extends Location {

	public SpecialLocation(int amountOfPlayer) {
		super(amountOfPlayer);
	}

	public int sendReward(Player p) {
		int reward = super.sendReward(p);
		p.setBalance(p.getBalance() + (2 * reward));
		return reward;
	}

}