package object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Boots extends  SuperObject{
    GamePanel gp;
    public Obj_Boots(GamePanel gp){
        this.gp = gp;
        setName("Boots");
        try{
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/boots.png")));
            uTool.scaleImage(getImage(), gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
