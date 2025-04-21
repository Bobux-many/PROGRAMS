import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;

/* @author Микушина Надежда */
public class Bbx extends JFrame {
    private static Bbx bobux_game;
    private static long last_frame_time;
    private static Image background;
    private static Image falling_bobux;
    private static Image end_image;

    private static float bobux_x = 200;
    private static float bobux_y = -100;
    private static float bobux_speed = 200;
    private static int score = 0;

    public static void main(String[] args) throws IOException {
        // Загружаем изображения
        background = ImageIO.read(Bbx.class.getResourceAsStream("DAD.jpg"));
        falling_bobux = ImageIO.read(Bbx.class.getResourceAsStream("BOBUX.jpg"));
        end_image = ImageIO.read(Bbx.class.getResourceAsStream("end.jpg"));

        // Создание окна
        bobux_game = new Bbx();
        bobux_game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        bobux_game.setLocation(200, 100);
        bobux_game.setSize(1280, 720);
        bobux_game.setResizable(false);

        last_frame_time = System.nanoTime();

        GameField game_field = new GameField();

        // Обработка нажатий мыши
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                float bobux_right = bobux_x + falling_bobux.getWidth(null);
                float bobux_bottom = bobux_y + falling_bobux.getHeight(null);
                boolean is_hit = x >= bobux_x && x <= bobux_right && y >= bobux_y && y <= bobux_bottom;

                if (is_hit) {
                    bobux_y = -100;
                    bobux_x = (int) (Math.random() * (game_field.getWidth() - falling_bobux.getWidth(null)));
                    bobux_speed += 10;
                    score++;
                    bobux_game.setTitle("Score: " + score);
                }
            }
        });

        bobux_game.add(game_field);
        bobux_game.setVisible(true);
    }

    // Перерисовка
    private static void onRepaint(Graphics g) {
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;

        bobux_y += bobux_speed * delta_time;

        g.drawImage(background, 0, 0, null);
        g.drawImage(falling_bobux, (int) bobux_x, (int) bobux_y, null);

        if (bobux_y > bobux_game.getHeight()) {
            g.drawImage(end_image, 200, 100, null);
        }
    }

    // Панель игры
    private static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}
