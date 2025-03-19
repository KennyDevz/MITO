package Main;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame(); //creation of window GUI
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //for close function
        frame.setResizable(false); //makes screen size unchangeable
        GamePanel panel = new GamePanel(); //gamepanel object creation

        frame.setTitle("MITO: Adventure Quest"); //sets window title

        frame.add(panel); //adds gamepanel object to the window gui
        frame.pack(); //packs the window to fit the preferred size indicated in the gamepanel class
        frame.setLocationRelativeTo(null); //displays the window at the center of the screen
        frame.setVisible(true);// make window visible
        panel.startGameThread();// start game time or game clock

    }
}