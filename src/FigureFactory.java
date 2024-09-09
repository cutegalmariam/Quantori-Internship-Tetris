import java.util.Random;

public class FigureFactory {

	static Random random = new Random();

	private static MoveAction[] moveActions = new MoveAction[] {
			new MoveAction() { public int[][] move() { return O(); } },
			new MoveAction() { public int[][] move() { return J(); } },
			new MoveAction() { public int[][] move() { return T(); } },
			new MoveAction() { public int[][] move() { return I(); } },
			new MoveAction() { public int[][] move() { return S(); } },
			new MoveAction() { public int[][] move() { return L(); } },
			new MoveAction() { public int[][] move() { return Z(); } },
	};

	public static int[][] createNextFigure() {
		int index = random.nextInt(moveActions.length);
		return moveActions[index].move();
	}


	static int[][] O() {
		return new int[][] {
				{0, 1, 1, 0},
				{0, 1, 1, 0},
				{0, 0, 0, 0},
				{0, 0, 0, 0},
		};
	}

	static int[][] J() {
		return new int[][] {
				{0, 0, 2, 0},
				{0, 0, 2, 0},
				{0, 2, 2, 0},
				{0, 0, 0, 0},
		};
	}

	static int[][] T() {
		return new int[][] {
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{0, 3, 0, 0},
				{3, 3, 3, 0},
		};
	}

	static int[][] I() {
		return new int[][] {
				{0, 0, 0, 0},
				{4, 4, 4, 4},
				{0, 0, 0, 0},
				{0, 0, 0, 0},
		};
	}

	static int[][] S() {
		return new int[][] {
				{0, 0, 0, 0},
				{0, 5, 5, 0},
				{5, 5, 0, 0},
				{0, 0, 0, 0},
		};
	}

	static int[][] L() {
		return new int[][] {
				{0, 6, 0, 0},
				{0, 6, 0, 0},
				{0, 6, 6, 0},
				{0, 0, 0, 0},
		};
	}

	static int[][] Z() {
		return new int[][] {
				{0, 0, 0, 0},
				{7, 7, 0, 0},
				{0, 7, 7, 0},
				{0, 0, 0, 0},
		};
	}


}