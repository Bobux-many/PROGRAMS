package g1ame;

import javax.swing.*;
import java.awt.*;

/**
 * @author Микушина Надежда
 * @author admin
 */
public class Game extends JFrame {
    private static Game game;  // Singleton instance
    private final GameField gameField;  // Game panel for rendering

    public Game() {
        // Initialize game window settings
        setTitle("Simple Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(200, 50);
        setSize(900, 900);
        setResizable(false);

        // Initialize game field (drawing panel)
        gameField = new GameField();
        add(gameField);

        // Make window visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Start the game
        SwingUtilities.invokeLater(() -> {
            game = new Game();
        });
    }

    // Custom rendering method
    public static void onRepaint(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillOval(10, 10, 220, 100);  // Draw a red oval
    }

    // Inner class for the game drawing panel
    public static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);  // Clear the panel
            onRepaint(g);  // Call custom drawing method
        }
    }
}