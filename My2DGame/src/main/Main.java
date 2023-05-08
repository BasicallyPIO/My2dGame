package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2d Racer");

        // creating an object of the GamePanel class, which passes the height and width of our window
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // causes this window to be sized to fit preferred size and layouts of its subcomponents
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}