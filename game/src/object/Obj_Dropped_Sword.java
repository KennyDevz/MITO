package object;

import Main.GamePanel;

import javax.swing.*;

public class Obj_Dropped_Sword extends SuperObject {
    private GamePanel gp;
    private ImageIcon swordGIF;

    public Obj_Dropped_Sword(GamePanel gp) {
        this.gp = gp;
        setName("Dropped_Sword");
        swordGIF = new ImageIcon("assets/objects/drop_silver_sword_1.gif");
        setImageIcon(swordGIF);
    }
}
