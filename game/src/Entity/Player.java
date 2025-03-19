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

    public Player(GamePanel gp, KeyHandler kh){
        this.gp = gp;
        this.keyH = kh;
        setDefaultvalues();
        getPlayerImage();
    }

    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setDefaultvalues(){
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void update(){
        if(keyH.up == true || keyH.down == true || keyH.left == true || keyH.right == true){
            if(keyH.up){ //listens to player controls or inputs or pressed keys
                direction = "up";
                y -= speed;
            }
            else if(keyH.down){ //listens to player controls or inputs or pressed keys
                direction = "down";
                y += speed;
            }
            else if(keyH.right){ //listens to player controls or inputs or pressed keys
                direction = "right";
                x += speed;
            }
            else if(keyH.left){ //listens to player controls or inputs or pressed keys
                direction = "left";
                x -= speed;
            }

            spriteCounter++;
            if(spriteCounter > 10){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    public void paintComponent(Graphics2D g2){
        BufferedImage image = null;

        switch (direction){
            case "up": if(spriteNum == 1) {image = up1;}
                if(spriteNum == 2) {image = up2;}
                break;
            case "down": if(spriteNum == 1) {image = down1;}
                if(spriteNum == 2) {image = down2;}
                break;
            case "right": if(spriteNum == 1) {image = right1;}
                if(spriteNum == 2) {image = right2;}
                break;
            case "left": if(spriteNum == 1) {image = left1;}
                if(spriteNum == 2) {image = left2;}
                break;

        }
        g2.drawImage(image,x,y,gp.tileSize,gp.tileSize, null);
    }
}
