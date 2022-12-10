package card;

import java.util.ArrayList;
import java.util.Collections;

import basecard.Card;
import player.Player;

public class CardDeck {
	private ArrayList<Card> deck;
	
	public CardDeck() {
		deck.add(new StealCard("",""));
		deck.add(new StealCard("",""));
		deck.add(new TaxCard("",""));
		deck.add(new BonusCard("",""));
	}
	
	public Card giveTopCardTo(Player p) {
		Collections.shuffle(deck);
		return deck.get(0);
	}
}
