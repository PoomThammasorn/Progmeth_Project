package testcase;

import static org.junit.Assert.*;

import org.junit.Test;

import base.Location;
import player.Player;

public class LocationTest {

	@Test
	public void test() {
		Location l = new Location("", 2, 1);
		Player p1 = new Player("", "white");
		Player p2 = new Player("", "blue");
		assertEquals("Location", l.getName());
		// assertEquals(0,l.fundValue(l.getFund()));
		//l.updateFund();
		// assertEquals(1,l.getFund().size());
		// assertEquals(0,l.fundValue(l.getFund()));
		// assertEquals(0,p.getBalance());
		// assertEquals(0,l.getFund().get(0).getAmount()-1);
		l.sendReward(p1);
		//l.sendReward(p2);
		// assertEquals(0,l.getFund().get(0).getAmount());
		assertEquals(1,l.getFund().size());
		//assertEquals(30000, p2.getBalance());
		// assertEquals(0,l.fundValue(l.getFund()));
		//assertEquals(0, l.fundValue(l.getFund()));
		//assertEquals(0,l.getFund().get(0).getAmount());
		//assertEquals(0,p.getBalance());
		//assertEquals(0,l.fundValue(l.getFund()));
		//assertEquals(0,l.fundValue(l.getFund()));
		//assertEquals(0,l.getFund().get(0).getAmount());
		//assertEquals(0,p.getBalance());
		//assertEquals(0,l.fundValue(l.getFund()));
		//assertEquals(0,l.fundValue(l.getFund()));
	}

}
