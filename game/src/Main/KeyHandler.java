package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean up,down,left,right,space, interact;
    private GamePanel gp;
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // Returns the integer KeyCode of the key being pressed
        if (gp.gameState == gp.playState){
            if (code == KeyEvent.VK_W) { // If 'W' key is pressed

                up = true;
            }
            if (code == KeyEvent.VK_S) { // If 'S' key is pressed

                down = true;
            }
            if (code == KeyEvent.VK_A) { // If 'A' key is pressed

                left = true;
            }
            if (code == KeyEvent.VK_D) { // If 'D' key is pressed

                right = true;
            }
            if (code == KeyEvent.VK_SPACE) { // If Spacebar is pressed

                space = true;
            }
            if(code == KeyEvent.VK_E){

                interact = true;
            }
            if (code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.pauseState;
            }
        } else if(gp.gameState == gp.pauseState){
            if (code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.playState;
            }
        } else if(gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_E){

                interact = false;
                gp.gameState = gp.playState;
            }
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

    //setters and getters
    public boolean isUp() { return up; }
    public void setUp(boolean up) { this.up = up; }

    public boolean isDown() { return down; }
    public void setDown(boolean down) { this.down = down; }

    public boolean isLeft() { return left; }
    public void setLeft(boolean left) { this.left = left; }

    public boolean isRight() { return right; }
    public void setRight(boolean right) { this.right = right; }

    public boolean isSpace() { return space; }
    public void setSpace(boolean space) { this.space = space; }

    public boolean isInteracting() { return interact; }
    public void setInteracting(boolean interact) { this.interact = interact; }
}
