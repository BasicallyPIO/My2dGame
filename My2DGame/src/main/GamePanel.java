package main;

import entity.MovingCircle;
import entity.Player;

import javax.swing.*;
import java.awt.*;

// extends = inherits and builds upon a parent class
//implements = implements an interface
public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile - default size of character/NPCs
    final int scale = 3; // used to scale up originalTileSize for modern monitors
    public final int tileSize = originalTileSize * scale; //48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12; //creating a 4:3 ratio for columns/rows
    final int screenWidth = tileSize * maxScreenCol; //768 pixels
    final int screenHeight = tileSize * maxScreenRow; //576 pixels
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    //once a thread is started, it continues until you tell it to stop. Useful when you want something
    //to run over and over and over
    Thread gameThread;
    Player player = new Player(this,keyH);

    // Set default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4; // how many pixels are we adjusting by with each key press

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //improves performance buffering
        this.addKeyListener(keyH); //method that adds keyH to the GamePanel
        this.setFocusable(true); //GamePanel can be "focused" to receive key input


    }
    //gameThread is being set to a new thread value passing this class as the Runnable task
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    //when the thread is called, it will automatically run the run method below
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; //draws screen 60 times per second
        double nextDrawTime = System.nanoTime() + drawInterval; //draw next frame

        while(gameThread != null)    {
            //System.out.println("The game loop is running");//can use this for testing

            update();
            //below method is used to call paintComponent
            repaint();

            try {
                //tells computer how long it has until it needs to draw again
                double remainingTime = nextDrawTime - System.nanoTime();
                //sleep takes a millisecond value, this is converting from nano to ms
                remainingTime = remainingTime/1000000;
                //if update and repaint took longer than drawInterval then no time is left and do not sleep
                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                //conserves memory when waiting to draw again
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void update() {

        player.update();

    }
    //paintComponent is a standard Jpanel method for drawing
    public void paintComponent(Graphics g) {
        //whenever paintComponent is used, this line is necessary
        //super means the parent class of this class (JPanel in this instance)
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g; //Graphics g is now converted to Graphics2D
        player.draw(g2);
        g2.dispose();
    }
}
