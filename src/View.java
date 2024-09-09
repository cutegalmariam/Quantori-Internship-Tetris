import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View {
	private Graphics2D graphics;
	private boolean gameOver = false; // Track if the game is over
	private JPanel gameOverPanel;

	static final int BOX_SIZE = 30;
	static final int ORIGIN = 50;
	static final int OFFSET_X = 20;
	static final int OFFSET_Y = 30;

	public void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public JPanel getGameOverPanel() {
		if (gameOverPanel == null) {
			gameOverPanel = new JPanel();
			gameOverPanel.setLayout(new BorderLayout());

			JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
			gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50));
			gameOverPanel.add(gameOverLabel, BorderLayout.CENTER);

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(1, 1, 100, 100)); // Vertical layout with spacing


			JButton quitButton = new JButton("Quit");

			quitButton.setFont(new Font("Arial", Font.BOLD, 30));

			quitButton.setPreferredSize(new Dimension(200, 50));


			buttonPanel.add(quitButton);

			// Add button panel to game over panel
			gameOverPanel.add(buttonPanel, BorderLayout.CENTER);



			quitButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0); // Close the application
				}
			});
		}
		return gameOverPanel;
	}


	public void draw(TetrisModel model) {
		if (graphics == null) return;

		if (gameOver) {
			drawGameOverScreen();
		} else {
			// Draw the game board
			drawBoard(model);

			// Draw the current figure
			drawFigure(model);
		}
	}

	private void drawBoard(TetrisModel model) {
		int[][] field = model.state.field;
		for (int row = 0; row < field.length; row++) {
			for (int col = 0; col < field[row].length; col++) {
				int colorIndex = field[row][col];
				if (colorIndex != 0) {
					graphics.setColor(Tetris.COLORS[colorIndex]);
				} else {
					graphics.setColor(Color.WHITE); // Background color for empty cells
				}
				graphics.fillRect(OFFSET_X + col * BOX_SIZE, OFFSET_Y + row * BOX_SIZE, BOX_SIZE, BOX_SIZE);
				graphics.setColor(Color.BLACK); // Draw grid lines
				graphics.drawRect(OFFSET_X + col * BOX_SIZE, OFFSET_Y + row * BOX_SIZE, BOX_SIZE, BOX_SIZE);
			}
		}
	}

	private void drawFigure(TetrisModel model) {
		int[][] figure = model.state.figure;
		Pair position = model.state.position;
		for (int row = 0; row < figure.length; row++) {
			for (int col = 0; col < figure[row].length; col++) {
				if (figure[row][col] != 0) {
					graphics.setColor(Tetris.COLORS[figure[row][col]]);
					graphics.fillRect(OFFSET_X + (position.x() + col) * BOX_SIZE, OFFSET_Y + (position.y() + row) * BOX_SIZE, BOX_SIZE, BOX_SIZE);
				}
			}
		}
	}

	private void drawGameOverScreen() {
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, graphics.getClipBounds().width, graphics.getClipBounds().height);

		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Arial", Font.BOLD, 50));
		String message = "GAME OVER";
		int messageWidth = graphics.getFontMetrics().stringWidth(message);
		int messageHeight = graphics.getFontMetrics().getAscent();
		graphics.drawString(message, (graphics.getClipBounds().width - messageWidth) / 2, (graphics.getClipBounds().height + messageHeight) / 2);
	}
}
