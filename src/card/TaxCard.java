package card;

import basecard.Card;
import basecard.Stealable;
import player.Player;

public class TaxCard extends Card implements Stealable {

	public TaxCard(String name, String keyword) {
		super("Tax Card", "Meet The Revenue Department!! Must pay 10% tax.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void steal(Player p) {
		// TODO Auto-generated method stub
		p.setBalance((int) (p.getBalance() * 0.90));
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Bad luck!! It's the " + getName() + ". " + getKeyword();
	}

}
