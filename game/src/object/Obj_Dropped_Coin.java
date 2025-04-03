package object;

import Main.GamePanel;

import javax.swing.*;

public class Obj_Dropped_Coin extends SuperObject {
    private GamePanel gp;
    private ImageIcon coinGIF;

    public Obj_Dropped_Coin(GamePanel gp) {
        this.gp = gp;
        setName("Dropped_Coin");
        coinGIF = new ImageIcon("assets/objects/drop_coin.gif");
        setImageIcon(coinGIF);
    }
}
