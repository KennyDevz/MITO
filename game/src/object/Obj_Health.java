package object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Health extends SuperObject {
    GamePanel gp;

    public Obj_Health(GamePanel gp){
        this.gp = gp;

        name = "HealthBar";
        try{
            // Load empty bar (background)
            image = ImageIO.read(getClass().getResourceAsStream("/indi_sprites/gui/healthbar.png"));
            // Load filled health bar (foreground)
            image2 = ImageIO.read(getClass().getResourceAsStream("/indi_sprites/gui/healthbar_fill.png"));

            // Scale images to fit UI
            image = uTool.scaleImage(image, gp.tileSize * 5, gp.tileSize / 2);
            image2 = uTool.scaleImage(image2, gp.tileSize * 5, gp.tileSize / 2);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}


