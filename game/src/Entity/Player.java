package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;

import Main.KeyHandler;
import Main.GamePanel;
import Main.UtilityTool;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    int standCounter = 0;

    public Player(GamePanel gp, KeyHandler kh){
        super(gp);
        this.gp = gp;
        this.keyH = kh;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        solidArea = new Rectangle();
        solidArea.x = 20;
        solidArea.y = 24;
        solidArea.width = 25;
        solidArea.height = 35;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 36;
        attackArea.height = 36;
        setDefaultvalues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void getPlayerImage(){
        up1 = setup("/indi_sprites/Entity/player/andres_walk_back1",gp.tileSize ,gp.tileSize);
        up2 = setup("/indi_sprites/Entity/player/andres_walk_back2",gp.tileSize,gp.tileSize);
        down1 = setup("/indi_sprites/Entity/player/andres_walk_front1",gp.tileSize,gp.tileSize);
        down2 = setup("/indi_sprites/Entity/player/andres_walk_front2",gp.tileSize,gp.tileSize);
        left1= setup("/indi_sprites/Entity/player/andres_walk_left1",gp.tileSize,gp.tileSize);
        left2 = setup("/indi_sprites/Entity/player/andres_walk_left2",gp.tileSize,gp.tileSize);
        right1 = setup("/indi_sprites/Entity/player/andres_walk_right1",gp.tileSize,gp.tileSize);
        right2 = setup("/indi_sprites/Entity/player/andres_walk_right2",gp.tileSize,gp.tileSize);
    }
    public void getPlayerAttackImage() {
        attackUp1 = setup("/indi_sprites/Entity/player/swing_back1",gp.tileSize,gp.tileSize*2);
        attackUp2 = setup("/indi_sprites/Entity/player/swing_back2",gp.tileSize,gp.tileSize*2);
        attackDown1 = setup("/indi_sprites/Entity/player/swing_front1",gp.tileSize,gp.tileSize*2);
        attackDown2 = setup("/indi_sprites/Entity/player/swing_front2",gp.tileSize,gp.tileSize*2);
        attackLeft1= setup("/indi_sprites/Entity/player/swing_left1",gp.tileSize*2,gp.tileSize);
        attackLeft2 = setup("/indi_sprites/Entity/player/swing_left2",gp.tileSize*2,gp.tileSize);
        attackRight1 = setup("/indi_sprites/Entity/player/swing_right1",gp.tileSize*2,gp.tileSize);
        attackRight2 = setup("/indi_sprites/Entity/player/swing_right2",gp.tileSize*2,gp.tileSize);
    }

    public void setDefaultvalues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 5;
        direction = "down";

        //Player status
        maxLife = 6;
        life = maxLife;
    }

    public void update(){

        if(attacking == true){
            attacking();
        } else if(keyH.up == true || keyH.down == true || keyH.left == true || keyH.right == true || keyH.space == true){
            if(keyH.up){ //listens to player controls or inputs or pressed keys
                direction = "up";
            }
            else if(keyH.down){ //listens to player controls or inputs or pressed keys
                direction = "down";
            }
            else if(keyH.left){ //listens to player controls or inputs or pressed keys
                direction = "left";
            }
            else if(keyH.right){ //listens to player controls or inputs or pressed keys
                direction = "right";
            }

            //Check tile collision
            collisionOn = false;
            gp.coliCheck.checkTile(this);

            //Check object collision
            int objIndex = gp.coliCheck.checkObject(this, true);
            pickUpObject(objIndex);

            //Check npc collision
            int npcIndex = gp.coliCheck.checkEntity(this,gp.npc);
            interactNPC(npcIndex);

            //Check hostile collision
            int hostileIndex = gp.coliCheck.checkEntity(this,gp.hostile);
            contactHostile(hostileIndex);

            //Check Event
            gp.eHander.checkEvent();
            if(collisionOn == false && keyH.space == false){
                switch (direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            gp.keyH.space = false;

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
        }else{
            standCounter++;
            if(standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void attacking(){
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;
            //Save current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player's worldX/Y for the attackArea
            switch (direction){
                case "up":
                    worldY -= attackArea.height; break;
                case "down":
                    worldX += attackArea.height; break;
                case "left":
                    worldX -= attackArea.width; break;
                case "right":
                    worldX += attackArea.width; break;
            }
            //Attack area becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //Check hostile collisiom with the updated worldX, worldY and solidArea
            int hostileIndex = gp.coliCheck.checkEntity(this, gp.hostile);
            damageHostile(hostileIndex);

            //After checking collision, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;



        }
        if(spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i){

        if(i != 999){
            String objectName = gp.obj[i].name;

            switch (objectName){
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You picked up a key!");
                    break;
                case "Door":
                    if(hasKey > 0) {
                        gp.obj[i] = null;
                        gp.ui.showMessage("You opened the door!");
                        hasKey--;
                    }else{
                        gp.ui.showMessage("You need a key!");
                    }

                    break;
                case "Boots":
                    speed += 2;
                    gp.ui.showMessage("You picked up boots!");
                    gp.obj[i] = null;
                    break;
                case "Chest":
                    if(hasKey > 0) {
                        gp.obj[i] = null;
                        gp.ui.showMessage("You discoverd a chest!");
                        hasKey--;
                    }else{
                        gp.ui.showMessage("You need a key!");
                    }
            }
        }
    }

    public void interactNPC(int i){
        if(gp.keyH.space == true){
            if(i != 999){
                System.out.println("You have collided with an NPC!");
            } else {
                attacking = true;
            }
        }

    }
    public void contactHostile(int i){
        if(i != 999){
            if(invincible == false){
                life -= 1;
                invincible = true;
            }
        }
    }
    public void damageHostile(int i) {
        if(i != 999){
            if(gp.hostile[i].invincible == false){
                gp.hostile[i].life -= 1;
                gp.hostile[i].invincible = true;

                if(gp.hostile[i].life <= 0){
                    gp.hostile[i].dying = true;
                }
            }
        }
    }

    public void paintComponent(Graphics2D g2){
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction){
            case "up":
                if(attacking == false){
                    if(spriteNum == 1) {image = up1;}
                    if(spriteNum == 2) {image = up2;}
                }
                if(attacking == true){
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1) {image = attackUp1;}
                    if(spriteNum == 2) {image = attackUp2;}
                }
                break;
            case "down":
                if(attacking == false){
                    if(spriteNum == 1) {image = down1;}
                    if(spriteNum == 2) {image = down2;}
                }
                if(attacking == true){
                    if(spriteNum == 1) {image = attackDown1;}
                    if(spriteNum == 2) {image = attackDown2;}
                }
                break;
            case "right":
                if(attacking == false){
                    if(spriteNum == 1) {image = right1;}
                    if(spriteNum == 2) {image = right2;}
                }
                if(attacking == true){
                    if(spriteNum == 1) {image = attackRight1;}
                    if(spriteNum == 2) {image = attackRight2;}
                }
                break;
            case "left":
                if(attacking == false){
                    if(spriteNum == 1) {image = left1;}
                    if(spriteNum == 2) {image = left2;}
                }
                if(attacking == true){
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1) {image = attackLeft1;}
                    if(spriteNum == 2) {image = attackLeft2;}
                }
                break;

        }
        //Damage effect when player is damaged.(changes the opacity)
        if(invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //Reset effect 1f = normal opacity
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


//        checking collision rectangle
        g2.setColor(Color.red);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y,solidArea.width,solidArea.height);

//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible: " + invincibleCounter, 10, 400);
    }
}
