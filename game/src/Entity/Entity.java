package Entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Entity{
    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public String name;
    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,
    attackLeft1,attackLeft2,attackRight1,attackRight2;
    public String direction;
    public int type; //0 - player, 1 - npc, 2 - monster
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48 ,48);
    public Rectangle attackArea = new Rectangle(0,0,0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public  boolean invincible   = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;


    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int spriteCounter = 0;
    int dyingCounter = 0;

    //Character Status
    public int maxLife;
    public int life;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public BufferedImage setup(String imagePath, int width, int height ) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
            image = uTool.scaleImage(image, width, height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void setAction(){

    }

    public void update(){
        setAction();
        collisionOn = false;
        gp.coliCheck.checkTile(this);
        gp.coliCheck.checkObject(this, false);
        gp.coliCheck.checkEntity(this,gp.npc);
        gp.coliCheck.checkEntity(this,gp.hostile);
        boolean contactPlayer = gp.coliCheck.checkPlayer(this);

        if(this.type == 2 && contactPlayer == true) {
            if(gp.player.invincible == false){
                //Give damage to player
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }
        if(collisionOn == false){
            switch (direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction){
                case "up":
                    if(spriteNum == 1) {image = up1;}
                    if(spriteNum == 2) {image = up2;}
                    break;
                case "down":
                    if(spriteNum == 1) {image = down1;}
                    if(spriteNum == 2) {image = down2;}
                    break;
                case "right":
                    if(spriteNum == 1) {image = right1;}
                    if(spriteNum == 2) {image = right2;}
                    break;
                case "left":
                    if(spriteNum == 1) {image = left1;}
                    if(spriteNum == 2) {image = left2;}
                    break;
            }
            //Hostile HealthBar
            if(type == 2){
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1,screenY-16,gp.tileSize + 2, 12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX,screenY-15,(int)hpBarValue, 10);
            }

            //Damage effect when player is damaged.(changes the opacity)
            if(invincible == true){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            if(dying == true){
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            //Reset effect 1f = normal opacity
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            g2.setColor(Color.red);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y,solidArea.width,solidArea.height);
        }
    }
    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        int i = 5;
        if(dyingCounter <= i) {changeAlpha(g2,0f);}
        if (dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2,1f);}
        if (dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2,0f);       }
        if (dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2,1f);}
        if (dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2,0f);}
        if (dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2,1f);}
        if (dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2,0f);}
        if (dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2,1f);}
        if(dyingCounter > i*8){
            dying = false;
            alive = false;
        }
    }
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
}
