package object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Health extends SuperObject {
    GamePanel gp;

    public Obj_Health(GamePanel gp){
        this.gp = gp;

        setName("HealthBar");
        try{
            // Load empty bar (background)
            setImage(ImageIO.read(getClass().getResourceAsStream("/indi_sprites/gui/healthbar.png")));
            // Load filled health bar (foreground)
            setImage2(ImageIO.read(getClass().getResourceAsStream("/indi_sprites/gui/healthbar_fill.png")));

            // Scale images to fit UI
            setImage(uTool.scaleImage(getImage(), gp.tileSize * 5, gp.tileSize / 2));
            setImage2(uTool.scaleImage(getImage2(), gp.tileSize * 5, gp.tileSize / 2));
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}


