package Main;

import object.Obj_Health;
import object.Obj_Key;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Font arial_40;
    Graphics2D g2;
    BufferedImage keyImage;
    BufferedImage healthFull, healthBlank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        Obj_Key key = new Obj_Key(gp);
        keyImage = key.image;

        //CREATE HUD OBJECT
        SuperObject health = new Obj_Health(gp);
        healthBlank = health.image;
        healthFull = health.image2;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize * 2 - 15, gp.tileSize, gp.tileSize, null);
        g2.drawString("X = " + gp.player.hasKey, gp.tileSize * 2 - 35, gp.tileSize * 3 - 25);

        drawPlayerLife();
        //message
        if(messageOn == true){
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message,gp.tileSize/2,gp.tileSize*5);

            messageCounter++;

            if(messageCounter > 120){
                messageCounter = 0;
                messageOn = false;
            }
        }
    }

    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;

        int barWidth = gp.tileSize * 5;  // Full health bar width
        int barHeight = gp.tileSize + 5; // Adjust height as needed

        // Draw the blank container (background)
        g2.drawImage(healthBlank, x, y, barWidth, barHeight, null);

        // Calculate the width of the health portion
        double healthPercentage = (double) gp.player.life / gp.player.maxLife;
        int currentBarWidth = (int) (barWidth * Math.max(healthPercentage, 0));

        // Ensure the health bar stays within bounds
        if (currentBarWidth > 0) {
            g2.setClip(x, y, currentBarWidth, barHeight); // Clip to show only current health
            g2.drawImage(healthFull, x, y, barWidth, barHeight, null);
            g2.setClip(null); // Reset clipping
        }
    }





}
