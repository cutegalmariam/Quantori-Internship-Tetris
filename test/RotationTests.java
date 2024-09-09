import static org.junit.Assert.*;

import org.junit.Test;

public class RotationTests {

	@Test
	public void test() {
		var model = new TetrisModel(TetrisModel.DEFAULT_WIDTH, TetrisModel.DEFAULT_HEIGHT, TetrisModel.DEFAULT_COLORS_NUMBER);
		model.state.figure = FigureFactory.O();
		model.rotate();
		var rotatedO = new int[][] {
				{0, 1, 1, 0},
				{0, 1, 1, 0},
				{0, 0, 0, 0},
				{0, 0, 0, 0},
		};
		for (int i = 0; i < rotatedO.length; i++) {
			assertArrayEquals(model.state.figure[i], rotatedO[i]);
		}
	}

}
