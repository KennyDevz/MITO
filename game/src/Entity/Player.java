package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;

import Main.KeyHandler;
import Main.GamePanel;
import Main.UtilityTool;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    int standCounter = 0;

    public Player(GamePanel gp, KeyHandler kh) {
        super(gp);
        this.gp = gp;
        this.keyH = kh;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidArea = new Rectangle();
        solidArea.x = 20;
        solidArea.y = 24;
        solidArea.width = 25;
        solidArea.height = 35;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 38;
        attackArea.height = 48;
        setDefaultvalues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void getPlayerImage() {
        up1 = setup("/indi_sprites/Entity/player/andres/andres_walk_back1", gp.tileSize, gp.tileSize);
        up2 = setup("/indi_sprites/Entity/player/andres/andres_back", gp.tileSize, gp.tileSize);
        up3 = setup("/indi_sprites/Entity/player/andres/andres_walk_back2", gp.tileSize, gp.tileSize);
        down1 = setup("/indi_sprites/Entity/player/andres/andres_walk_front1", gp.tileSize, gp.tileSize);
        down2 = setup("/indi_sprites/Entity/player/andres/andres_front", gp.tileSize, gp.tileSize);
        down3 = setup("/indi_sprites/Entity/player/andres/andres_walk_front2", gp.tileSize, gp.tileSize);
        left1 = setup("/indi_sprites/Entity/player/andres/andres_walk_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/indi_sprites/Entity/player/andres/andres_left", gp.tileSize, gp.tileSize);
        left3 = setup("/indi_sprites/Entity/player/andres/andres_walk_left2", gp.tileSize, gp.tileSize);
        right1 = setup("/indi_sprites/Entity/player/andres/andres_walk_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/indi_sprites/Entity/player/andres/andres_right", gp.tileSize, gp.tileSize);
        right3 = setup("/indi_sprites/Entity/player/andres/andres_walk_right2", gp.tileSize, gp.tileSize);
        dead = setup("/indi_sprites/Entity/player/andres/dead", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {
        attackUp1 = setup("/indi_sprites/Entity/player/andres/swing_back1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/indi_sprites/Entity/player/andres/swing_back2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/indi_sprites/Entity/player/andres/swing_front1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/indi_sprites/Entity/player/andres/swing_front2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/indi_sprites/Entity/player/andres/swing_left1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/indi_sprites/Entity/player/andres/swing_left2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/indi_sprites/Entity/player/andres/swing_right1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/indi_sprites/Entity/player/andres/swing_right2", gp.tileSize*2, gp.tileSize);
    }

    public void setDefaultvalues() {
        worldX = gp.tileSize * 13;
        worldY = gp.tileSize * 21;
        speed = 8;
        direction = "down";
        //Player status
        maxLife = 200;
        life = maxLife;
    }

    public void update() {
        if (isAttacking()) {
            attacking();
        } else if (keyH.isUp() || keyH.isDown() || keyH.isLeft() || keyH.isRight() || keyH.isSpace()) {
            if (keyH.isUp()) { //listens to player controls or inputs or pressed keys
                setDirection("up");
            } else if (keyH.isDown()) { //listens to player controls or inputs or pressed keys
                setDirection("down");
            } else if (keyH.isLeft()) { //listens to player controls or inputs or pressed keys
                setDirection("left");
            } else if (keyH.isRight()) { //listens to player controls or inputs or pressed keys
                setDirection("right");
            }
            if(keyH.isSpace()){
                gp.playSE(7);
            }


            //Check tile collision
            collisionOn = false;
            gp.coliCheck.checkTile(this);

            //Check object collision
            int objIndex = gp.coliCheck.checkObject(this, true);
            pickUpObject(objIndex);

            //Check npc collision
            int npcIndex = gp.coliCheck.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //Check hostile collision
            int hostileIndex = gp.coliCheck.checkEntity(this, gp.hostile);
            contactHostile(hostileIndex);

            //Check Event
            gp.eHandler.checkEvent();
            if (!collisionOn && !keyH.isSpace()) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            System.out.println(worldX / gp.tileSize + " " + worldY / gp.tileSize);
            gp.keyH.setSpace(false);

            spriteCounter++;
            if (spriteCounter > 12) { // Reduce delay between frames
                spriteNum++;
                if (spriteNum > 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            standCounter++;
            if (standCounter == 60) {
                spriteNum = 2;
                standCounter = 0;
            }
        }
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;
            //Save current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player's worldX/Y for the attackArea
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            //Attack area becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;


            //Check hostile collision with the updated worldX, worldY and solidArea
            int hostileIndex = gp.coliCheck.checkEntity(this, gp.hostile);
            damageHostile(hostileIndex);

            //After checking collision, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {

        if (i != 999) {
            String objectName = gp.obj[i].getName();

            switch (objectName) {
                case "Key":
                    hasKey++;
                    gp.playSE(1);
                    gp.obj[i] = null;
                    gp.ui.showMessage("You picked up a key!");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.playSE(3);
                        gp.obj[i] = null;
                        gp.ui.showMessage("You opened the door!");
                        hasKey--;
                    } else {
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Boots":
                    speed += 5;
                    gp.playSE(2);
                    gp.ui.showMessage("You picked up boots!");
                    gp.obj[i] = null;
                    break;
                case "Chest":
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        gp.ui.showMessage("You discoverd a chest!");
                        hasKey--;
                    } else {
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Dropped_Coin":
                    gp.obj[i] = null;
                    gp.playSE(2);
                    gp.ui.showMessage("You picked up coin!");
                    setCoin(getCoin()+1);
                    break;
                case "Dropped_Sword":
                    gp.playSE(2);
                    gp.ui.showMessage("You picked up sword!");
                    gp.obj[i] = null;
                    break;
            }
        }
    }

    public void interactNPC(int i) {
        if (gp.keyH.isSpace() == true) {
            if (i != 999) {
                System.out.println("You have collided with an NPC!");
            } else {
                attacking = true;
            }
        }
        if (i != 999) {
            if (gp.keyH.isInteracting()) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();}
        }
        gp.keyH.setInteracting(false);

    }

    public void contactHostile(int i) {
        if (i != 999) {
            if (invincible == false && gp.hostile[i].dying == false) {
                gp.playSE(6);
                life -= 25;
                invincible = true;
            }
        }
    }

    public void damageHostile(int i) {
        if (i != 999) {
            if (gp.hostile[i].invincible == false) {
                gp.playSE(5);
                gp.hostile[i].life -= 3;
                gp.hostile[i].invincible = true;
                gp.hostile[i].damageReaction();

                if (gp.hostile[i].life <= 0) {
                    gp.hostile[i].dying = true;
                    gp.hostile[i].checkDrop();
                }
            }
        }
    }

    public void paintComponent(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        int targetWidth = gp.tileSize;
        int targetHeight = gp.tileSize;  // default for normal sprites

        if(!alive) image = dead;

        else{
            switch (direction) {
                case "up":
                    if (!attacking) {
                        image = (spriteNum == 1 ? up1 : spriteNum == 2 ? up2 : up3);
                    } else {
                        tempScreenY = screenY - gp.tileSize;
                        image = (spriteNum == 1) ? attackUp1 : attackUp2;
                        targetWidth = gp.tileSize;
                        targetHeight = gp.tileSize * 2;
                    }
                    break;
                case "down":
                    if (!attacking) {
                        image = (spriteNum == 1 ? down1 : spriteNum == 2 ? down2 : down3);
                    } else {
                        image = (spriteNum == 1) ? attackDown1 : attackDown2;
                        targetWidth = gp.tileSize;
                        targetHeight = gp.tileSize * 2;
                    }
                    break;
                case "left":
                    if (!attacking) {
                        image = (spriteNum == 1 ? left1 : spriteNum == 2 ? left2 : left3);
                    } else {
                        tempScreenX = screenX - gp.tileSize;
                        image = (spriteNum == 1) ? attackLeft1 : attackLeft2;
                        targetWidth = gp.tileSize * 2;
                        targetHeight = gp.tileSize;
                    }
                    break;
                case "right":
                    if (!attacking) {
                        image = (spriteNum == 1 ? right1 : spriteNum == 2 ? right2 : right3);
                    } else {
                        image = (spriteNum == 1) ? attackRight1 : attackRight2;
                        targetWidth = gp.tileSize * 2;
                        targetHeight = gp.tileSize;
                    }
                    break;
            }
        }

        // Damage effect when player is invincible.
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        // Calculate offsets to center the scaled image in the target area.
        int offsetX = (targetWidth - image.getWidth()) / 2;
        int offsetY = (targetHeight - image.getHeight()) / 2;
        g2.drawImage(image, tempScreenX + offsetX, tempScreenY + offsetY, null);

        // Reset opacity.
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Draw collision rectangles for debugging.
//        g2.setColor(Color.red);
//        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

        if (attacking) {
            int atkX = screenX + solidArea.x;
            int atkY = screenY + solidArea.y;
            switch (direction) {
                case "up":
                    atkY = screenY - attackArea.height;
                    break;
                case "down":
                    atkY = screenY + gp.tileSize;
                    break;
                case "left":
                    atkX = screenX - attackArea.width;
                    break;
                case "right":
                    atkX = screenX + gp.tileSize;
                    break;
            }
//            g2.drawRect(atkX, atkY, attackArea.width, attackArea.height);
        }
    }

    //setters and getters
    public int getScreenX() { return screenX; }

    public int getScreenY() { return screenY; }

    public int getHasKey() { return hasKey; }
    public void setHasKey(int hasKey) { this.hasKey = hasKey; }

    public int getStandCounter() { return standCounter; }
    public void setStandCounter(int standCounter) { this.standCounter = standCounter; }

    @Override
    public int getAttack() {
        super.setAttack(getStrength() * currentWeapon.getAttackValue());
        return super.getAttack();
    }

    @Override
    public int getDefense() {
        super.setDefense(getDexterity() * currentShield.getDefenseValue());
        return super.getDefense();
    }

}