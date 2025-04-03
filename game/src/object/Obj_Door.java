package object;

import Main.GamePanel;
import com.sun.management.GarbageCollectionNotificationInfo;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Door extends SuperObject{
    GamePanel gp;
    public Obj_Door(GamePanel gp){
        this.gp = gp;
        setName("Door");
        try{
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/door.png")));
            uTool.scaleImage(getImage(), gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        setCollision(true);
    }
}
