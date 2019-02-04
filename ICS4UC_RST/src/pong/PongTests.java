package pong;

import static org.junit.Assert.*;

import org.junit.Test;

public class PongTests {

	Ball b1 = new Ball();
	
	@Test
	public void test() {
		//Tests random number generator (5 cases)
		double r = b1.rand(1, 2);
		assertTrue(r <= 2 && r >= 1);
		
		r = b1.rand(-1, 1);
		assertTrue(r <= 1 && r >= -1);
		
		r = b1.rand(0, 0);
		assertTrue(r == 0);
		
		r = b1.rand(0, 10);
		assertTrue(r <= 10 && r >= 0);
		
		r = b1.rand(-10, 0);
		assertTrue(r <= 0 && r >= -10);
	}
}
