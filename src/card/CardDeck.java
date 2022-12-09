package card;

import java.util.ArrayList;
import java.util.Collections;

import basecard.Card;

public class CardDeck {
	private ArrayList<Card> deck;

	public CardDeck() {
		deck = new ArrayList<Card>();
		// add card
		deck.add(new BuffCard("", 0, 0));
		deck.add(new DebuffCard("", 0, 0));
	}

	public Card giveTopCard() {
		if (deck.size() != 0) {
			Collections.shuffle(deck);
			Card topcard = deck.get(0);
			deck.remove(0);
			return topcard;
		}
		return null;
	}
}
