import javax.swing.*;
import java.awt.Graphics;
import java.awt.*;

public class TetrisPanel extends JPanel {
    private TetrisModel model;
    private View view;

    public TetrisPanel(TetrisModel model, View view) {
        this.model = model;
        this.view = view;
        setPreferredSize(new Dimension(500, 700));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        view.setGraphics(graphics);
        view.draw(model); // Delegate drawing to the View
    }

    public void setGameOver(boolean gameOver) {
        view.setGameOver(gameOver);
        repaint(); // Request a repaint to update the game panel
    }
}
