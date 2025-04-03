package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MenuPanel extends JPanel {
    private final int originalTileSize = 32;
    private final int scale = 2;
    public final int tileSize = originalTileSize * scale;

    // Screen size based on tile size
    private final int maxScreenCol = 20;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    // Button sizes
    private final int buttonWidth = 200;
    private final int buttonHeight = 84;

    // Button Y positions
    private final int startY = 240;
    private final int settingsY = 380;
    private final int exitY = 520;

    private final int titleScale = 2;

    private Image newGameIcon;
    private Image testGameIcon;
    private Image backgroundImage;
    private Image menuScrollIcon;
    private Image swordIcon;
    private Image titleIcon;
    private Image buttonBlank;
    private ImageIcon sceneGIF;
    private ImageIcon mitoGIF;
    private ImageIcon buttonGIF;

    //mouse hover
    private boolean hoverStart = false;
    private boolean hoverOptions = false;
    private boolean hoverExit = false;

    private JFrame parentFrame;

    public MenuPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setPreferredSize(new Dimension(screenWidth, screenHeight));

        try {
            // Load sprite sheets and images
            BufferedImage spriteSheet = ImageIO.read(new File("assets/menu/pixil-frame-0.png"));
            BufferedImage spriteBackground = ImageIO.read(new File("assets/menu/background.png"));
            BufferedImage titleSheet = ImageIO.read(new File("assets/menu/MITO_2.png"));
            BufferedImage scrollPortraitImage = ImageIO.read(new File("assets/menu/scroll_portrait_2.png"));
            BufferedImage buttonStatic = ImageIO.read(new File("assets/menu/Button_Blank.gif"));
            // Load animated GIF
            sceneGIF = new ImageIcon(new ImageIcon("assets/menu/scenery.gif").getImage().getScaledInstance(screenWidth+64, screenHeight+64, Image.SCALE_DEFAULT));
            mitoGIF = new ImageIcon(new ImageIcon("assets/menu/MITO_title_anim_floating.gif").getImage());
            buttonGIF = new ImageIcon(new ImageIcon("assets/menu/Button_Blank.gif").getImage().getScaledInstance(102*2,42*2,Image.SCALE_DEFAULT));
            // Extract sub-images from sprite sheets
            BufferedImage newGameImage = spriteSheet.getSubimage(0, 0, 49, 13);
            BufferedImage testGameImage = spriteSheet.getSubimage(0, 16, 49, 13);
            BufferedImage scrollImage = scrollPortraitImage.getSubimage(1 * 16, 1 * 16, 15 * 16, 15 * 16);
            BufferedImage titleImage = titleSheet.getSubimage(0, 0, 14 * 16, 7 * 16);
            BufferedImage swordImage = titleSheet.getSubimage(0, 11 * 16, 14 * 16, 5 * 16);


            // Scale images
            newGameIcon = getScaledImage(newGameImage, buttonWidth, buttonHeight);
            testGameIcon = getScaledImage(testGameImage, buttonWidth, buttonHeight);
            backgroundImage = getScaledImage(spriteBackground, screenWidth, screenHeight);
            menuScrollIcon = getScaledImage(scrollImage, 15 * 16 * 3, 15 * 16 * 3);
            titleIcon = getScaledImage(titleImage, 2 * 150, 1 * 150);
            swordIcon = getScaledImage(swordImage, 12 * 16 * 2, 5 * 16 * 2);
            buttonBlank = getScaledImage(buttonStatic,102*2,42*2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Mouse listeners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int centerX = (screenWidth - buttonWidth) / 2;

                if (hoverStart) {
                    switchToGameScreen();
                } else if (hoverExit) {
                    System.exit(0);
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int centerX = (screenWidth - buttonWidth) / 2;

                hoverStart = (mouseX >= centerX && mouseX <= centerX + buttonWidth &&
                        mouseY >= startY && mouseY <= startY + buttonHeight);
                hoverOptions = (mouseX >= centerX && mouseX <= centerX + buttonWidth &&
                        mouseY >= settingsY && mouseY <= settingsY + buttonHeight);
                hoverExit = (mouseX >= centerX && mouseX <= centerX + buttonWidth &&
                        mouseY >= exitY && mouseY <= exitY + buttonHeight);

                repaint();
            }
        });
    }

    private void switchToGameScreen() {
        parentFrame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel();
        parentFrame.add(gamePanel);
        parentFrame.revalidate();
        parentFrame.repaint();
        SoundHandler.StopMusic();

        gamePanel.setUpGame();
        gamePanel.startGameThread();
        gamePanel.requestFocusInWindow();
        gamePanel.setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int centerX = (screenWidth - buttonWidth) / 2;
        int centerSword = (screenWidth - 12 * 16 * 2) / 2;
        int centerTitle = (screenWidth - 300) / 2;
        int centerScroll = (screenWidth - 15 * 15 * 3) / 2;
        int centerTitle2 = (screenWidth - 33*16)/2;

        if (sceneGIF != null) {
            g.drawImage(sceneGIF.getImage(), 0, 0, screenWidth+40, screenHeight+40, this);
        }
//
//        g.drawImage(menuScrollIcon,centerScroll,100,null);
        g.drawImage(mitoGIF.getImage(), centerTitle2, 25, null);

        g.drawImage(buttonBlank,(screenWidth-102*2)/2,startY,null);
        g.drawImage(buttonBlank,(screenWidth-102*2)/2, settingsY,null);
        g.drawImage(buttonBlank,(screenWidth-102*2)/2,exitY,null);



        // Draw button text
        g.setColor(Color.darkGray);
        g.setFont(new Font("Serif", Font.ITALIC, 30));
        g.drawString("Play", centerX +75, startY + 50);
        g.drawString("Settings", centerX + 52, settingsY + 50);
        g.drawString("Exit", centerX + 75, exitY + 50);

        // Draw rectangles around buttons to visualize dimensions
//        g.setColor(Color.RED);
//        g.drawRect(centerX, startY, buttonWidth, buttonHeight); // Start Button
//        g.drawRect(centerX, settingsY, buttonWidth, buttonHeight); // Settings Button
//        g.drawRect(centerX, exitY, buttonWidth, buttonHeight); // Exit Button

        if (hoverStart) {
            g.drawImage(buttonGIF.getImage(), centerX, startY, null);
            g.setColor(Color.magenta);
            g.drawString("Play", centerX + 77, startY + 50);
        }
        if (hoverOptions) {
            g.drawImage(buttonGIF.getImage(), centerX, settingsY, null);
            g.setColor(Color.magenta);
            g.drawString("Settings", centerX + 54, settingsY + 50);
        }
        if (hoverExit) {
            g.drawImage(buttonGIF.getImage(), centerX, exitY, null);
            g.setColor(Color.red);
            g.drawString("Exit", centerX + 77, exitY + 50);
        }

    }

    private Image getScaledImage(BufferedImage srcImg, int width, int height) {
        return srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}