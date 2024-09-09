import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class TetrisModel implements GameEventsListener {

	public static final int DEFAULT_HEIGHT = 20;
	public static final int DEFAULT_WIDTH = 10;
	public static final int DEFAULT_COLORS_NUMBER = 7;

	public static int LEVEL = 0;
	public static int TOTAL_ROWS_CLEARED = 0;
	public static int CURRENT_ROWS_CLEARED = 0;

	public static int SPEED = 1000;

	public static int SCORE = 0;

	public static final int INITIAL_SCORE = 0;
	public static final int INITIAL_LEVEL = 1;
	public int[][] field;
	public int[][] figure;
	public Pair position;

	int maxColors;
	public TetrisState state = new TetrisState();
	final List<ModelListener> listeners = new ArrayList<>();

	public void addListener(ModelListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ModelListener listener) {
		listeners.remove(listener);
	}

	public TetrisModel(int width, int height, int maxColors) {
		this.state.width = width;
		this.state.height = height;
		this.maxColors = maxColors;
		state.field = new int[height][width];
		initFigure();
	}

	public void initFigure() {
		state.figure = FigureFactory.createNextFigure();
		state.position = new Pair(state.width / 2 - 2, 0);
	}

	public void resetGame(int width, int height, int colorsNumber) {
		this.field = new int[height][width];
//		this.figure = new int[4][4]; // Example size
		this.position = new Pair(0, 0); // Reset position
		SCORE = INITIAL_SCORE;
		LEVEL = INITIAL_LEVEL;
		initFigure();
		// Any other game state initialization
	}
	public Pair size() {
		return new Pair(state.width, state.height);
	}

	@Override
	public void slideDown() {
		var newPosition = new Pair(state.position.x(), state.position.y() + 1);
		if (isNewFiguresPositionValid(newPosition)) {
			state.position = newPosition;
			notifyListeners();
		} else {
			pasteFigure();
			initFigure();
			notifyListeners();
			if (!isNewFiguresPositionValid(state.position)) {
				gameOver();
			}
		}
	}

	private void notifyListeners() {
		listeners.forEach(listener -> listener.onChange(this));
	}

	@Override
	public void gameOver() {
		System.out.println("Game Over");
		// Notify the listeners about game over
		listeners.forEach(listener -> listener.onGameOver());
	}

	@Override
	public void moveLeft() {
		var newPosition = new Pair(state.position.x() - 1, state.position.y());
		if (isNewFiguresPositionValid(newPosition)) {
			state.position = newPosition;
			notifyListeners();
		}
	}

	@Override
	public void moveRight() {
		var newPosition = new Pair(state.position.x() + 1, state.position.y());
		if (isNewFiguresPositionValid(newPosition)) {
			state.position = newPosition;
			notifyListeners();
		}
	}

	@Override
	public void rotate() {
		int n = state.figure.length;
		int[][] rotatedFigure = new int[n][n];

		// Rotate the figure 90 degrees clockwise
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				rotatedFigure[j][n - 1 - i] = state.figure[i][j];
			}
		}

		// Check if the rotation is valid (within bounds and no collisions)
		if (isValidPosition(rotatedFigure, state.position)) {
			state.figure = rotatedFigure; // Apply the rotation if valid
			notifyListeners();
		}
	}

	private boolean isValidPosition(int[][] figure, Pair position) {
		int[][] field = state.field;

		for (int row = 0; row < figure.length; row++) {
			for (int col = 0; col < figure[row].length; col++) {
				if (figure[row][col] != 0) {
					int x = position.x() + col;
					int y = position.y() + row;

					if (x < 0 || x >= field[0].length || y < 0 || y >= field.length) {
						return false;
					}

					if (field[y][x] != 0) {
						return false;
					}
				}
			}
		}

		return true;
	}

	@Override
	public void drop() {
		var newPosition = new Pair(state.position.x(), state.position.y() + 1);
		if (isNewFiguresPositionValid(newPosition)) {
			state.position = newPosition;
			notifyListeners();
		}

	}

	@Override
	public void fullRowRemoval() {
		for (int i = 0; i < state.field.length; i++) {
			boolean isFull = true;
			for (int j = 0; j < state.field[i].length; j++) {
				if (state.field[i][j] == 0){
					isFull = false;
					break;
				}
			}
			if(isFull){
				for (int k = i; k > 0; k--) {
					for (int j = 0; j < state.field[k].length; j++) {
						state.field[k][j] = state.field[k-1][j];
					}
				}
				for (int j = 0; j < state.field[0].length; j++) {
					state.field[0][j] = 0;
				}
				i--;
				TOTAL_ROWS_CLEARED += 1;
				CURRENT_ROWS_CLEARED += 1;
				int currentLevel = checkForLevel();
				System.out.println(LEVEL);
				System.out.println(SCORE);
				assessScore(CURRENT_ROWS_CLEARED, currentLevel);
				CURRENT_ROWS_CLEARED = 0;
				System.out.println(SCORE);
			}
		}
		notifyListeners();
	}

	public void assessScore(int CURRENT_ROWS_CLEARED, int currentLevel){
		switch (CURRENT_ROWS_CLEARED){
			case 1:
				SCORE += 40 * (currentLevel + 1);
				break;
			case 2:
				SCORE += 100 * (currentLevel + 1);
				break;
			case 3:
				SCORE += 300 * (currentLevel + 1);
				break;
			case 4:
				SCORE += 1200 * (currentLevel + 1);
				break;
			default:
				System.out.println("Score is unchanged");
		}

		adjustSpeed();
	}

	public int checkForLevel() {
		if(TOTAL_ROWS_CLEARED == 2){
			LEVEL += 1;
			TOTAL_ROWS_CLEARED = 0;

		}
		return LEVEL;
	}

	public void adjustSpeed(){
		int newSpeed;

		if (SCORE >= 5000) {
			newSpeed = 100; // Maximum speed
		} else if (SCORE >= 2000) {
			newSpeed = 200;
		} else if (SCORE >= 1000) {
			newSpeed = 300;
		} else if (SCORE >= 500) {
			newSpeed = 500;
		} else {
			newSpeed = SPEED; // Initial speed
		}

		if (newSpeed != SPEED) {
			SPEED = newSpeed;
		}
		System.out.println("speed: "+ newSpeed);
	}
	public boolean isNewFiguresPositionValid(Pair newPosition) {
		AtomicBoolean result = new AtomicBoolean(true);

		walkThroughAllFigureCells(newPosition, (absPos, relPos) -> {
			if (result.get()) {
				result.set(checkAbsPos(absPos));
			}
		});

		return result.get();
	}


	private void walkThroughAllFigureCells(Pair newPosition, BiConsumer<Pair, Pair> payload) {
		for (int row = 0; row < state.figure.length; row++) {
			for (int col = 0; col < state.figure[row].length; col++) {
				if (state.figure[row][col] == 0)
					continue;
				int absCol = newPosition.x() + col;
				int absRow = newPosition.y() + row;
				payload.accept(new Pair(absCol, absRow), new Pair(col, row));
			}
		}
	}

	private boolean checkAbsPos(Pair absPos) {
		var absCol = absPos.x();
		var absRow = absPos.y();
		if (isColumnPositionOutOfBoundaries(absCol))
			return false;
		if (isRowPositionOutOfBoundaries(absRow))
			return false;
		if (state.field[absRow][absCol] != 0)
			return false;
		return true;
	}


	private boolean isRowPositionOutOfBoundaries(int absRow) {
		return absRow < 0 || absRow >= state.height;
	}

	private boolean isColumnPositionOutOfBoundaries(int absCol) {
		return absCol < 0 || absCol >= state.width;
	}

	public void pasteFigure() {
		walkThroughAllFigureCells(state.position, (absPos, relPos) -> {
			state.field[absPos.y()][absPos.x()] = state.figure[relPos.y()][relPos.x()];
		});
	}

}
