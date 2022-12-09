package card;

import player.Player;

public class BonusCard extends Card implements Givable {

	public BonusCard(String name, String keyword) {
		super("Bonus Card", "Congratulations!!, you got 100$ for free.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void give(Player p) {
		// TODO Auto-generated method stub
		p.setBalance(p.getBalance() + 100);

	}

}
