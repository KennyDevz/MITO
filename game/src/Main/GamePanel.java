package Main;

import Entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 64; //16x16 tile size for all components
    final int scale = 1; //for scaling tile size to adapt to resolution

    public final int tileSize = originalTileSize * scale; //tile size to use for the game using orig tile size and scale (64x64) pixel

    //Screen size how many columns and row according to tile size
    final int maxScreenCol = 16; //number of columns for the window GUI
    final int maxScreenRow = 12; //number of rows for the window GUI
    final int screenWidth = tileSize * maxScreenCol; //(768 pixels) width is equal to size of tile times the number of columns (used for setting size of window)
    final int screenHeight = tileSize * maxScreenRow; // (576 pixels) height is equal to size of tile times the number of rows (used for setting size of window)

    Thread gameThread; // serves as game time or game clock
    KeyHandler keyH = new KeyHandler();

    int PlayerX = 100;//sets player x coordinate
    int PlayerY = 100;//sets player y coordinate
    double PlayerSpeed = 3;//sets player speeed
    int FPS = 60; //sets Frames per second

    Player player = new Player(this,keyH);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // windows screen dimensions initialization
        this.setBackground(Color.BLACK); //sets window background color
        this.setDoubleBuffered(true); //set to true to improve rendering performance
        this.addKeyListener(keyH);//added to panel to listen for keys inputed, KeyH is an object from main.KeyHandler
        this.setFocusable(true);//focuses on this window panel
    }

    public void startGameThread(){
        gameThread = new Thread(this); //instantiation of thread gamethread. "this" meaning this class main.GamePanel is passed to thread constructor
        gameThread.start();// starts the thread gamethread "game time"
    }

    @Override
    public void run() {// from runnable interface. Automatically called when thread is started. also called (GAME LOOP) the core of our game

        double drawInterval = 1000000000/FPS;//gets duration or interval until next draw time
        double nextDrawTime = System.nanoTime() - drawInterval;


        while(gameThread != null) { //as long as gameThread exists, it repeats the process inside this loop
            //System.out.println("The game loop is started!"); // to check if thread is starting or game loop is started

            //update: used to update informations like player positions
            update();
            //draw or repaint from paintComponent(just follow this mandatory): used to display or draw the screen with updated information
            repaint();


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if(remainingTime < 0) remainingTime =0;
                Thread.sleep((long)remainingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            nextDrawTime +=drawInterval;
        }
    }

    public void update(){//x and y coordinate are from top left so x=0 y=0
        player.update();
    }

    @Override
    public void paintComponent(Graphics g){//graphics class used to draw objects on the screen
        super.paintComponent(g); //necessary whenever painComponent is created
        Graphics2D g2 = (Graphics2D)g; //typecasting Graphics g to Graphics2D class because it has more function than Graphics class

        player.paintComponent(g2);

        g2.dispose();//dispose after drawing
    }
}
