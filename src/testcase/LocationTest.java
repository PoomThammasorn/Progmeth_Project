package testcase;

import static org.junit.Assert.*;

import org.junit.Test;

import base.Location;

public class LocationTest {

	@Test
	public void test() {
		Location l = new Location("",4,1);
		assertEquals("Location",l.getName());
		assertEquals(0,l.fundValue(l.getFund()));
		l.updateFund();
		assertEquals(0,l.fundValue(l.getFund()));
	}

}
