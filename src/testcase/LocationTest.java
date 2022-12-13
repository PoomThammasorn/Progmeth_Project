package testcase;

import static org.junit.Assert.*;

import org.junit.Test;

import base.Location;
import player.Player;

public class LocationTest {

	@Test
	public void test() {
		Location l = new Location("",4,1);
		Player p = new Player("","white");
		assertEquals("Location",l.getName());
		//assertEquals(0,l.fundValue(l.getFund()));
		l.updateFund();
		//assertEquals(1,l.getFund().size());
		//assertEquals(0,l.fundValue(l.getFund()));
		//assertEquals(0,p.getBalance());
		//assertEquals(0,l.getFund().get(0).getAmount()-1);
		l.sendReward(p);
		//assertEquals(0,l.getFund().get(0).getAmount());
		assertEquals(0,p.getBalance());
		//assertEquals(0,l.fundValue(l.getFund()));
	}

}
