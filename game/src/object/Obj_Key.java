package object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Key extends SuperObject {
    GamePanel gp;

    public Obj_Key(GamePanel gp){
        this.gp = gp;
        name = "Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
