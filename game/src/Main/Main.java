package Main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("MITO: Adventure Quest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Start with menu
        MenuPanel menuPanel = new MenuPanel(frame);
        frame.add(menuPanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Play menu music
        SoundHandler.RunMusic("assets/menu/menu_bg_music_2.wav");
    }
}