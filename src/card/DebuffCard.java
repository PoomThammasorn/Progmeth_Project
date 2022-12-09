package card;

import basecard.Card;
import basecard.Givable;
import player.Player;

public class DebuffCard extends Card implements Givable{

	public DebuffCard(String name, int effect, int amount) {
		super(name, effect, amount);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void give(Player act, Player obj) {
		// TODO Auto-generated method stub
		act.setBalance(act.getBalance()-this.getEffect());
		obj.setBalance(obj.getBalance()+this.getEffect());
	}

}
