package object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Chest extends SuperObject{
    GamePanel gp;
    public Obj_Chest(GamePanel gp){
        this.gp = gp;
        setName("Chest");
        try{
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/chest.png")));
            uTool.scaleImage(getImage(), gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
