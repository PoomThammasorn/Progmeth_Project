package testcase;

import static org.junit.Assert.*;

import org.junit.Test;

import base.Location;
import player.Player;

public class LocationTest {

	@Test
	public void test() {
		Location l = new Location("", 4, 1);
		Player p = new Player("", "white");
		assertEquals("Location", l.getName());
		// assertEquals(0,l.fundValue(l.getFund()));
		l.updateFund();
		// assertEquals(1,l.getFund().size());
		// assertEquals(0,l.fundValue(l.getFund()));
		// assertEquals(0,p.getBalance());
		// assertEquals(0,l.getFund().get(0).getAmount()-1);
		l.sendReward(p);
<<<<<<< HEAD
		// assertEquals(0,l.getFund().get(0).getAmount());
		assertEquals(0, p.getBalance());
		// assertEquals(0,l.fundValue(l.getFund()));
		assertEquals(0, l.fundValue(l.getFund()));
||||||| e7d6ddf
		//assertEquals(0,l.getFund().get(0).getAmount());
		assertEquals(0,p.getBalance());
<<<<<<< HEAD
		//assertEquals(0,l.fundValue(l.getFund()));
=======
		assertEquals(0,l.fundValue(l.getFund()));
>>>>>>> c16fef7a8b9b35696063374f90ceb06de1f47cba
=======
		//assertEquals(0,l.getFund().get(0).getAmount());
		assertEquals(0,p.getBalance());
		//assertEquals(0,l.fundValue(l.getFund()));

		assertEquals(0,l.fundValue(l.getFund()));
>>>>>>> 0ab15580b64d66c8553cc4b0a281e662ec46e08e
	}

}
