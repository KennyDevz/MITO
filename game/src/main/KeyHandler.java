package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean up,down,left,right;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); //returns the integer Keycode/number of the key being pressed (ex: 8 - backspace 9 - tab)
        if(code == KeyEvent.VK_W){//if code is equal to 'W' keycode
            System.out.println("Key pressed: W");
            up = true;
        }
        if(code == KeyEvent.VK_S){//if code is equal to 'S' keycode
            System.out.println("Key pressed: S");
            down = true;
        }
        if(code == KeyEvent.VK_A){//if code is equal to 'A' keycode
            System.out.println("Key pressed: A");
            left = true;
        }
        if(code == KeyEvent.VK_D){//if code is equal to 'D' keycode
            System.out.println("Key pressed: D");
            right = true;
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
