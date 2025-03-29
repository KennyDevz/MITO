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

        attackArea.width = 48;
        attackArea.height = 48;
        setDefaultvalues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void getPlayerImage() {
        up1 = setup("/indi_sprites/Entity/player/andres_walk_back1", gp.tileSize, gp.tileSize);
        up2 = setup("/indi_sprites/Entity/player/andres_walk_back2", gp.tileSize, gp.tileSize);
        down1 = setup("/indi_sprites/Entity/player/andres_walk_front1", gp.tileSize, gp.tileSize);
        down2 = setup("/indi_sprites/Entity/player/andres_walk_front2", gp.tileSize, gp.tileSize);
        left1 = setup("/indi_sprites/Entity/player/andres_walk_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/indi_sprites/Entity/player/andres_walk_left2", gp.tileSize, gp.tileSize);
        right1 = setup("/indi_sprites/Entity/player/andres_walk_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/indi_sprites/Entity/player/andres_walk_right2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {
        attackUp1 = setup("/indi_sprites/Entity/player/swing_back1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/indi_sprites/Entity/player/swing_back2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/indi_sprites/Entity/player/swing_front1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/indi_sprites/Entity/player/swing_front2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/indi_sprites/Entity/player/swing_left1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/indi_sprites/Entity/player/swing_left2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/indi_sprites/Entity/player/swing_right1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/indi_sprites/Entity/player/swing_right2", gp.tileSize*2, gp.tileSize);
    }

    public void setDefaultvalues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 5;
        direction = "down";
        //Player status
        maxLife = 16;
        life = maxLife;
    }

    public void update() {
        if (attacking == true) {
            attacking();
        } else if (keyH.up == true || keyH.down == true || keyH.left == true || keyH.right == true || keyH.space == true) {
            if (keyH.up) { //listens to player controls or inputs or pressed keys
                direction = "up";
            } else if (keyH.down) { //listens to player controls or inputs or pressed keys
                direction = "down";
            } else if (keyH.left) { //listens to player controls or inputs or pressed keys
                direction = "left";
            } else if (keyH.right) { //listens to player controls or inputs or pressed keys
                direction = "right";
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
            gp.eHander.checkEvent();
            if (collisionOn == false && keyH.space == false) {
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
            gp.keyH.space = false;

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            standCounter++;
            if (standCounter == 20) {
                spriteNum = 1;
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


            //Check hostile collisiom with the updated worldX, worldY and solidArea
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
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You picked up a key!");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        gp.ui.showMessage("You opened the door!");
                        hasKey--;
                    } else {
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Boots":
                    speed += 5;
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
            }
        }
    }

    public void interactNPC(int i) {
        if (gp.keyH.space == true) {
            if (i != 999) {
                System.out.println("You have collided with an NPC!");
            } else {
                attacking = true;
            }
        }

    }

    public void contactHostile(int i) {
        if (i != 999) {
            if (invincible == false) {
                life -= 2;
                invincible = true;
            }
        }
    }

    public void damageHostile(int i) {
        if (i != 999) {
            if (gp.hostile[i].invincible == false) {
                gp.hostile[i].life -= 1;
                gp.hostile[i].invincible = true;
                gp.hostile[i].damageReaction();

                if (gp.hostile[i].life <= 0) {
                    gp.hostile[i].dying = true;
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

        switch (direction) {
            case "up":
                if (!attacking) {
                    image = (spriteNum == 1) ? up1 : up2;
                } else {
                    tempScreenY = screenY - gp.tileSize;
                    image = (spriteNum == 1) ? attackUp1 : attackUp2;
                    targetWidth = gp.tileSize;
                    targetHeight = gp.tileSize * 2;
                }
                break;
            case "down":
                if (!attacking) {
                    image = (spriteNum == 1) ? down1 : down2;
                } else {
                    image = (spriteNum == 1) ? attackDown1 : attackDown2;
                    targetWidth = gp.tileSize;
                    targetHeight = gp.tileSize * 2;
                }
                break;
            case "left":
                if (!attacking) {
                    image = (spriteNum == 1) ? left1 : left2;
                } else {
                    tempScreenX = screenX - gp.tileSize;
                    image = (spriteNum == 1) ? attackLeft1 : attackLeft2;
                    targetWidth = gp.tileSize * 2;
                    targetHeight = gp.tileSize;
                }
                break;
            case "right":
                if (!attacking) {
                    image = (spriteNum == 1) ? right1 : right2;
                } else {
                    image = (spriteNum == 1) ? attackRight1 : attackRight2;
                    targetWidth = gp.tileSize * 2;
                    targetHeight = gp.tileSize;
                }
                break;
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
        g2.setColor(Color.red);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

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
            g2.drawRect(atkX, atkY, attackArea.width, attackArea.height);
        }
    }

}