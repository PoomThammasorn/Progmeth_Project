package testcase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import basecard.Card;
import card.StealCard;
import player.Player;

public class CardTest {

	@Test
	public void test() {
		Card card = new StealCard();
		Player p1 = new Player("p1", "white");
		p1.setBalance(0);
		Player p2 = new Player("p2", "blue");
		p1.setBalance(0);
		p1.useCardWithObj(card, p2);
		assertEquals(0, p1.getBalance());
		assertEquals(0, ((StealCard) card).getStolenMoney());
	}

}
