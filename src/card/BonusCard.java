package card;

import basecard.Card;
import basecard.Givable;
import player.Player;

public class BonusCard extends Card implements Givable {

	public BonusCard() {
		// TODO Auto-generated constructor stub
		super("bonusCard", "Congratulations!!, you got 10,000$ for free.");
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
