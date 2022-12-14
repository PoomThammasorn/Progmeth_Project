package player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import base.Dice;
import basecard.Card;
import card.BonusCard;
import card.StealCard;
import card.TaxCard;
import comparator.DiceComparator;

public class Player {

	private String name;
	private String playerColour;
	private int balance;
	private ArrayList<Dice> diceInPlayer;

	public Player(String name, String colour) {
		// TODO Auto-generated constructor stub
		setName(name);
		setBalance(0);
		setPlayerColour(colour);
		diceInPlayer = new ArrayList<Dice>();
		for (int i = 0; i < 8; i++) {
			diceInPlayer.add(new Dice(colour));
		}
	}

	public void resetDice() {
		diceInPlayer.clear();
		for (int i = 0; i < 8; i++) {
			diceInPlayer.add(new Dice(playerColour));
		}
	}

	public void rollDice() {
		for (Dice dice : diceInPlayer) {
			dice.rolling();
		}
	}

	public void sortDiceInPlayer() {
		Collections.sort(diceInPlayer, new DiceComparator());
	}

	public int dropDice(int point) {
		int count = 0;
		Iterator<Dice> itr = diceInPlayer.iterator();
		while (itr.hasNext()) {
			Dice dice = itr.next();
			if (dice.getPoint() == point) {
				itr.remove();
				count += 1;
			}
		}
		return count;

	}

	public void useCardWithOutObj(Card c) {
		if (c instanceof BonusCard) {
			((BonusCard) c).give(this);
		}
		if (c instanceof TaxCard) {
			((TaxCard) c).steal(this);
		}
	}

	public void useCardWithObj(Card c, Player obj) {
		StealCard sc = (StealCard) c;
		sc.steal(obj);
		sc.give(this);
	}

	public String getName() {
		return name;
	}

	public int playerNumber() {
		if (playerColour == "white") {
			return 1;
		} else if (playerColour == "blue") {
			return 2;
		} else if (playerColour == "red") {
			return 3;
		} else if (playerColour == "green") {
			return 4;
		} else {
			return 5;
		}
	}

	public void setName(String name) {
		if (name.equals("")) {
			name = "player";
		}
		this.name = name;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		if (balance < 0) {
			balance = 0;
		}
		this.balance = balance;
	}

	public ArrayList<Dice> getDiceInPlayer() {
		return diceInPlayer;
	}

	public void setDiceInPlayer(ArrayList<Dice> diceInPlayer) {
		this.diceInPlayer = diceInPlayer;
	}

	public String getPlayerColour() {
		return playerColour;
	}

	public void setPlayerColour(String playerColour) {
		this.playerColour = playerColour;
	}

}
