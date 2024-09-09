public class Controller implements ModelListener, GameEventsListener {
	
	private TetrisModel model;
	private View view;

	public Controller(TetrisModel model, View view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void onChange(TetrisModel model) {
		view.draw(model);
	}

	@Override
	public void onGameOver() {
		model.gameOver();
		view.draw(model);
	}

	@Override
	public void slideDown() {
		model.slideDown();
		view.draw(model);
	}

	@Override
	public void moveLeft() {
		model.moveLeft();
	}

	@Override
	public void moveRight() {
		model.moveRight();
	}

	@Override
	public void rotate() {
		model.rotate();
	}

	@Override
	public void drop() {
		model.drop();
	}

	@Override
	public void fullRowRemoval() {
		model.fullRowRemoval();
	}

	@Override
	public void gameOver(){
		model.gameOver();
	}

}
