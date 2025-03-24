import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {
    private final int originalTileSize = 64;
    private final int scale = 1;
    public final int tileSize = originalTileSize * scale;

    // Screen size based on tile size
    private final int maxScreenCol = 20;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    // Button sizes
    private final int buttonWidth = 200;
    private final int buttonHeight = 100;

    // Button Y positions
    private final int startY = 200;
    private final int optionsY = 340;
    private final int exitY = 480;

    private final int titleScale = 2;

    private Image newGameIcon;
    private Image testGameIcon;
    private Image backgroundImage;
    private Image menuScrollIcon;
    private   Image swordIcon;
    private Image titleIcon;
    private ImageIcon sceneGIF;
    private ImageIcon mitoGIF;

    public GamePanel() {
        try {
            // Load sprite sheets and images
            BufferedImage spriteSheet = ImageIO.read(new File("assets/pixil-frame-0.png"));
            BufferedImage spriteBackground = ImageIO.read(new File("assets/background.png"));
            BufferedImage titleSheet = ImageIO.read(new File("assets/MITO_2.png"));
            BufferedImage scrollPortraitImage = ImageIO.read(new File("assets/scroll_portrait_2.png"));

            // Load animated GIF
            sceneGIF = new ImageIcon(new ImageIcon("assets/scenery.gif").getImage().getScaledInstance(screenWidth+64, screenHeight+64, Image.SCALE_DEFAULT));
            mitoGIF = new ImageIcon(new ImageIcon("assets/MITO_title_anim_floating.gif").getImage());

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

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Set screen size
        this.setBackground(Color.white); // Set background color
        this.setDoubleBuffered(true); // Improve rendering performance

        // Add MouseListener for button click events
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int centerX = (screenWidth - buttonWidth) / 2;

                // Check if the Exit button is clicked
                if (mouseX >= centerX && mouseX <= centerX + buttonWidth &&
                        mouseY >= exitY && mouseY <= exitY + buttonHeight) {
                    System.exit(0); // Exit the application
                }
            }
        });

        //start music
        SoundHandler.RunMusic("assets/menu_bg_music_2.wav");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int centerX = (screenWidth - buttonWidth) / 2;
        int centerSword = (screenWidth - 12 * 16 * 2) / 2;
        int centerTitle = (screenWidth - 300) / 2;
        int centerScroll = (screenWidth - 15 * 15 * 3) / 2;
        int centerTitle2 = (screenWidth - 33*16)/2;


        g.drawImage(sceneGIF.getImage(),0,0,null);
        g.drawImage(menuScrollIcon, centerScroll, 90, null);
        g.drawImage(swordIcon, centerSword, 85, null);
        g.drawImage(mitoGIF.getImage(), centerTitle2, (screenHeight - 16*7)/2, null);


       /*
        // Draw button text
        g.setColor(Color.darkGray);
        g.setFont(new Font("Serif", Font.ITALIC, 40));
        g.drawString("Exit", centerX + 65, exitY + 70);

        // Draw rectangles around buttons to visualize dimensions
        g.setColor(Color.RED);
        g.drawRect(centerX, startY, buttonWidth, buttonHeight); // Start Button
        g.drawRect(centerX, optionsY, buttonWidth, buttonHeight); // Options Button
        g.drawRect(centerX, exitY, buttonWidth, buttonHeight); // Exit Button

        */
    }

    @Override
    public void run() {
        // Game loop logic will go here
    }

    // Proper method to scale BufferedImage
    private Image getScaledImage(BufferedImage srcImg, int width, int height) {
        return srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
