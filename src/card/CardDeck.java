package card;

import java.util.ArrayList;
import java.util.Collections;

import basecard.Card;
import player.Player;

public class CardDeck {
	private ArrayList<Card> deck = new ArrayList<>();

	public CardDeck() {
		deck.add(new StealCard());
		deck.add(new StealCard());
		deck.add(new StealCard());
		deck.add(new StealCard());
		deck.add(new TaxCard());
		deck.add(new TaxCard());
		deck.add(new TaxCard());
		deck.add(new BonusCard());
		deck.add(new BonusCard());
		deck.add(new NoneCard());
	}

	public Card giveTopCardTo() {
		Collections.shuffle(deck);
		return deck.get(0);
	}
}
