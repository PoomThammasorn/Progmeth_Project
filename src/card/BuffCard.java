package card;

import basecard.*;
import player.Player;

public class BuffCard extends Card implements Stealable{

	public BuffCard(String name, int effect, int amount) {
		super(name, effect, amount);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void steal(Player act,Player obj) {
		// TODO Auto-generated method stub
		act.setBalance(act.getBalance()+this.getEffect());
		obj.setBalance(obj.getBalance()-this.getEffect());
	}

}
