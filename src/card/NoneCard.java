package card;

import basecard.Card;

public class NoneCard extends Card {

	public NoneCard() {
		super("noneCard", "Nothing, just a card.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Hmmmm..." + getKeyword();
	}

}
