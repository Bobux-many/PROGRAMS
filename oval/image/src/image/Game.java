import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class BombCatchGame extends JFrame {
    private static BombCatchGame gameInstance;
    private static long lastFrameTime;
    
    // Game assets
    private static Image background;  // kiev.jpeg
    private static Image bomb;        // bomba.jpeg
    private static Image explosion;   // vzriv.jpeg
    
    // Game state
    private static float bombX = 200;
    private static float bombY = -100;
    private static float fallSpeed = 200;
    private static int score = 0;

    public static void main(String[] args) throws IOException {
        // Load images
        background = ImageIO.read(BombCatchGame.class.getResourceAsStream("kiev.jpeg"));
        bomb = ImageIO.read(BombCatchGame.class.getResourceAsStream("bomba.jpeg"));
        explosion = ImageIO.read(BombCatchGame.class.getResourceAsStream("vzriv.jpeg"));
        
        initializeGame();
    }

    private static void initializeGame() {
        gameInstance = new BombCatchGame();
        gameInstance.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameInstance.setLocation(200, 50);
        gameInstance.setSize(1200, 800);
        gameInstance.setResizable(false);
        gameInstance.setTitle("Score: 0");
        
        lastFrameTime = System.nanoTime();
        
        GameField gameField = new GameField();
        setupMouseListener(gameField);
        
        gameInstance.add(gameField);
        gameInstance.setVisible(true);
    }

    private static void setupMouseListener(JPanel gameField) {
        gameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int clickX = e.getX();
                int clickY = e.getY();
                
                float bombRight = bombX + bomb.getWidth(null);
                float bombBottom = bombY + bomb.getHeight(null);
                
                boolean isClickOnBomb = clickX >= bombX && clickX <= bombRight 
                                     && clickY >= bombY && clickY <= bombBottom;
                
                if (isClickOnBomb) {
                    resetBombPosition();
                    increaseDifficulty();
                    updateScore();
                }
            }
        });
    }

    private static void resetBombPosition() {
        bombY = -100;
        bombX = (int) (Math.random() * (gameInstance.getWidth() - bomb.getWidth(null)));
    }

    private static void increaseDifficulty() {
        fallSpeed += 10;
    }

    private static void updateScore() {
        score++;
        gameInstance.setTitle("Score: " + score);
    }

    private static void gameUpdate(Graphics g) {
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
        lastFrameTime = currentTime;
        
        // Update bomb position
        bombY += fallSpeed * deltaTime;
        
        // Draw game elements
        g.drawImage(background, 0, 0, null);
        g.drawImage(bomb, (int) bombX, (int) bombY, null);
        
        // Game over if bomb reaches the bottom
        if (bombY > gameInstance.getHeight()) {
            g.drawImage(explosion, 210, 150, null);
        }
    }

    private static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            gameUpdate(g);
            repaint();
        }
    }
}