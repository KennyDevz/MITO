package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Main.KeyHandler;
import Main.GamePanel;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler kh){
        this.gp = gp;
        this.keyH = kh;

        screenX = (gp.screenWidth/2) - (gp.tileSize/2);
        screenY = (gp.screenHeight/2) - (gp.tileSize/2);

        setDefaultvalues();
        getPlayerImage();
    }

    public void getPlayerImage(){
        try {
            up0 = ImageIO.read(getClass().getResourceAsStream("/player/rizal_up0.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/rizal_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/rizal_up2.png"));
            down0 = ImageIO.read(getClass().getResourceAsStream("/player/rizal_down0.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/rizal_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/rizal_down2.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/player/rizal_left0.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/rizal_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/rizal_left2.png"));
            right0 = ImageIO.read(getClass().getResourceAsStream("/player/rizal_right0.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/rizal_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/rizal_right2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setDefaultvalues(){
        worldX = gp.tileSize * 1;
        worldY = gp.tileSize * 4;
        speed = 3;
        direction = "down";
    }

    public void update(){
        if(keyH.up == true || keyH.down == true || keyH.left == true || keyH.right == true){
            if(keyH.up){ //listens to player controls or inputs or pressed keys
                direction = "up";
                worldY -= speed;
            }
            else if(keyH.down){ //listens to player controls or inputs or pressed keys
                direction = "down";
                worldY += speed;
            }
            else if(keyH.right){ //listens to player controls or inputs or pressed keys
                direction = "right";
                worldX += speed;
            }
            else if(keyH.left){ //listens to player controls or inputs or pressed keys
                direction = "left";
                worldX -= speed;
            }

            spriteCounter++;
            if(spriteCounter > 10){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 3;
                }

                else if(spriteNum == 3){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    public void paintComponent(Graphics2D g2){
        BufferedImage image = null;

        switch (direction){
            case "up":
                if(spriteNum == 1) {image = up0;}
                if(spriteNum == 2) {image = up1;}
                if(spriteNum == 3) {image = up2;}
                break;
            case "down":
                if(spriteNum == 1) {image = down0;}
                if(spriteNum == 2) {image = down1;}
                if(spriteNum == 3) {image = down2;}
                break;
            case "right":
                if(spriteNum == 1) {image = right0;}
                if(spriteNum == 2) {image = right1;}
                if(spriteNum == 3) {image = right2;}
                break;
            case "left":
                if(spriteNum == 1) {image = left0;}
                if(spriteNum == 2) {image = left1;}
                if(spriteNum == 3) {image = left2;}
                break;

        }
        g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize, null);
    }
}
