package card;

import basecard.Card;
import basecard.Givable;
import basecard.Stealable;
import player.Player;

public class StealCard extends Card implements Stealable, Givable {

	private int stolenMoney;

	public StealCard(String name, String keyword) {
		super("Stealing Card", "Steal $20,000 from player's money to another player.");
		setStolenMoney(20000);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void steal(Player p) {
		// TODO Auto-generated method stub
		int balanceBeforeSteal = p.getBalance();
		p.setBalance(p.getBalance() - stolenMoney);
		setStolenMoney(balanceBeforeSteal - p.getBalance());

	}

	@Override
	public void give(Player p) {
		// TODO Auto-generated method stub
		p.setBalance(p.getBalance() + getStolenMoney());

	}

	public int getStolenMoney() {
		return stolenMoney;
	}

	public void setStolenMoney(int stolenMoney) {
		this.stolenMoney = stolenMoney;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "OMG!! It's the " + getName() + ". " + getKeyword();
	}

}
