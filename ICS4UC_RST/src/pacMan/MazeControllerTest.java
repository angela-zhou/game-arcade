package pacMan;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

class MazeControllerTest {

	@Test
	void Clockwise() {
		// test for all four directions
		assertEquals(MazeController.DOWN , MazeController.rotateClockwise(MazeController.RIGHT));
		assertEquals(MazeController.RIGHT, MazeController.rotateClockwise(MazeController.UP));
		assertEquals(MazeController.UP   , MazeController.rotateClockwise(MazeController.LEFT));
		assertEquals(MazeController.LEFT , MazeController.rotateClockwise(MazeController.DOWN));
	}
	
	@Test
	void CounterClockwise() {
		// test for all four directions
		assertEquals(MazeController.UP   , MazeController.rotateCounterClockwise(MazeController.RIGHT));
		assertEquals(MazeController.LEFT , MazeController.rotateCounterClockwise(MazeController.UP));
		assertEquals(MazeController.DOWN , MazeController.rotateCounterClockwise(MazeController.LEFT));
		assertEquals(MazeController.RIGHT, MazeController.rotateCounterClockwise(MazeController.DOWN));
	}

}
