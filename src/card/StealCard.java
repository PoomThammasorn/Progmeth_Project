package card;

import basecard.Card;
import basecard.Givable;
import basecard.Stealable;
import player.Player;

public class StealCard extends Card implements Stealable, Givable {

	private int stolenMoney;

	public StealCard() {
		// TODO Auto-generated constructor stub
		super("stealCard", "You can steal $20,000 from the richest players!!");
		setStolenMoney(20000);
	}

	@Override
	public void steal(Player p) {
		// TODO Auto-generated method stub
		if (p.getBalance() < getStolenMoney()) {
			setStolenMoney(p.getBalance());
		}
		p.setBalance(p.getBalance() - stolenMoney);
		System.out.println(stolenMoney);
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
