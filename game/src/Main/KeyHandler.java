package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean up,down,left,right,space;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // Returns the integer KeyCode of the key being pressed

        if (code == KeyEvent.VK_W) { // If 'W' key is pressed
            System.out.println("Key pressed: W");
            up = true;
        }
        if (code == KeyEvent.VK_S) { // If 'S' key is pressed
            System.out.println("Key pressed: S");
            down = true;
        }
        if (code == KeyEvent.VK_A) { // If 'A' key is pressed
            System.out.println("Key pressed: A");
            left = true;
        }
        if (code == KeyEvent.VK_D) { // If 'D' key is pressed
            System.out.println("Key pressed: D");
            right = true;
        }
        if (code == KeyEvent.VK_SPACE) { // If Spacebar is pressed
            System.out.println("Key pressed: SPACE");
            space = true;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); //returns the integer Keycode/number of the key being pressed (ex: 8 - backspace 9 - tab)
        if(code == KeyEvent.VK_W){//if code is equal to 'W' keycode
            up = false;
        }
        if(code == KeyEvent.VK_S){//if code is equal to 'S' keycode
            down = false;
        }
        if(code == KeyEvent.VK_A){//if code is equal to 'A' keycode
            left = false;
        }
        if(code == KeyEvent.VK_D){//if code is equal to 'D' keycode
            right = false;
        }
    }
}
