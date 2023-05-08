package entity;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MovingCircle extends JPanel {
    private int x = 0;
    private int y = 150;
    private int diameter = 50;

    public MovingCircle() {
        Thread animationThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    x -= 5;
                    if (x < -diameter) {
                        x = getWidth();
                    }
                    repaint();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        animationThread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.RED);
        g2.fillOval(x, y, diameter, diameter);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Moving Circle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setContentPane(new MovingCircle());
        frame.setVisible(true);
    }

    public void update() {
    }
}

