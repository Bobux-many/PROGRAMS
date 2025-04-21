import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Bbx extends JPanel implements ActionListener {
    private Image background;
    private Image bobux;
    private Timer timer;
    private java.util.List<FallingBobux> bobuxList = new ArrayList<>();

    public Bbx() {
        // Загружаем изображения с расширением .jpg
        background = new ImageIcon(getClass().getResource("DAD.jpg")).getImage();
        bobux = new ImageIcon(getClass().getResource("BOBUX.jpg")).getImage();

        // Создаем падающие объекты
        for (int i = 0; i < 30; i++) {
            bobuxList.add(new FallingBobux());
        }

        timer = new Timer(30, this); // Обновление каждые 30 мс
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Отрисовка фона
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        // Отрисовка падающих элементов
        for (FallingBobux b : bobuxList) {
            g.drawImage(bobux, (int) b.x, (int) b.y, 40, 40, null);
            b.update(getHeight());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bobux Dropper");
        Bbx panel = new Bbx();
        frame.setContentPane(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Внутренний класс одного падающего объекта
    static class FallingBobux {
        float x, y, speedY;

        public FallingBobux() {
            x = (float) (Math.random() * 800);
            y = (float) (Math.random() * -600);
            speedY = 1 + (float) (Math.random() * 3);
        }

        void update(int height) {
            y += speedY;
            if (y > height) {
                y = -40;
                x = (float) (Math.random() * 800);
            }
        }
    }
}
