//package Main;
//
//import javax.swing.*;
//
//public class PauseMenu extends JPanel{
//    private JButton button;
//    private JPanel panel;
//
//    private final int originalTileSize = 32;
//    private final int scale = 2;
//    public final int tileSize = originalTileSize * scale;
//
//    // Screen size based on tile size
//    private final int maxScreenCol = 16;
//    private final int maxScreenRow = 12;
//
//    private final int screenWidth = tileSize * maxScreenCol;
//    private final int screenHeight = tileSize * maxScreenRow;
//
//    // Button sizes
//    private final int buttonWidth = 144;
//    private final int buttonHeight = 72;
//    private JButton button2;
//}

package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PauseMenu extends JPanel {
    // Tile and screen settings
    private final int originalTileSize = 32;
    private final int scale = 2;
    public final int tileSize = originalTileSize * scale;

    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    // Button settings
    private final int buttonWidth = 144;
    private final int buttonHeight = 72;

    private Image bgImage;

    private JButton btnHome; // Pause menu buttons
    private ArrayList<ImageIcon[]> buttonFrames; // Stores button animation frames
    private int frameIndex = 0; // Tracks the current animation frame
    private Timer animationTimer; // Timer for frame animation

    public PauseMenu() {
        // Set panel size and layout
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setLayout(null); // Absolute positioning

        bgImage = new ImageIcon("pause_ui/buttons/pausebg.png").getImage();

        // Initialize button frame arrays
        buttonFrames = new ArrayList<>();
        //buttonFrames.add(loadButtonFrames("home")); // Load frames for button1
        //buttonFrames.add(loadButtonFrames("button2")); // Load frames for button2

        // Create animated buttons
        //btnHome = createAnimatedButton(buttonFrames.get(0));
        //button2 = createAnimatedButton(buttonFrames.get(1));

        // Set button positions
        //btnHome.setBounds(100, 200, buttonWidth, buttonHeight);
        //button2.setBounds(100, 300, buttonWidth, buttonHeight);

        // Add buttons to panel
        //add(btnHome);
        //add(button2);

        // Start animation loop
        //startAnimation();
    }

    /**
     * Creates a JButton with animated frames.
     * @param frames Array of ImageIcons representing button animation frames.
     * @return A JButton with the first frame set as its icon.
     */
    private JButton createAnimatedButton(ImageIcon[] frames) {
        JButton button = new JButton(frames[0]); // Set first frame as default
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Loads 5 animation frames for a button.
     * @param buttonName The base name of the button image files.
     * @return An array of ImageIcons containing button frames.
     */
    private ImageIcon[] loadButtonFrames(String buttonName) {
        ImageIcon[] frames = new ImageIcon[5]; // Array to hold 5 frames
        for (int i = 0; i < 5; i++) {
            frames[i] = new ImageIcon("pause_ui/buttons/home" + buttonName + "0" + i + ".png"); // Adjust path as needed
        }
        return frames;
    }

    //Starts a timer to cycle through button frames for animation.
    private void startAnimation() {
        animationTimer = new Timer(100, new ActionListener() { // Change frame every 100ms
            @Override
            public void actionPerformed(ActionEvent e) {
                frameIndex = (frameIndex + 1) % 5; // Loop through 5 frames
                btnHome.setIcon(buttonFrames.get(0)[frameIndex]); // Update button1 frame
                //button2.setIcon(buttonFrames.get(1)[frameIndex]); // Update button2 frame
            }
        });
        animationTimer.start(); // Start the animation loop
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background image
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

