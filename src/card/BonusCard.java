package card;

import basecard.Card;
import basecard.Givable;
import player.Player;

public class BonusCard extends Card implements Givable {

	public BonusCard(String name, String keyword) {
		super("Bonus Card", "Congratulations!!, you got 10,000$ for free.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void give(Player p) {
		// TODO Auto-generated method stub
		p.setBalance(p.getBalance() + 10000);

	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "It's The " + getName() + "!! " + getKeyword();
	}

}
