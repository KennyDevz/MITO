package Entity;

import Main.GamePanel;
import Main.UtilityTool;
import object.Obj_Dropped_Coin;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Entity{
    GamePanel gp;
    public int worldX, worldY;
    public String name;
    public BufferedImage up1,up2,up3,down1,down2,down3,left1,left2,left3,right1,right2,right3,dead;
    public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,
    attackLeft1,attackLeft2,attackRight1,attackRight2;
    public String direction;
    public int type; //0 - player, 1 - npc, 2 - monster
    public int spriteNum = 1;


    public Rectangle solidArea = new Rectangle(0, 0, 48 ,48);
    public Rectangle attackArea = new Rectangle(0,0,0, 0);
    public Rectangle attackHitbox = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public  boolean invincible   = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    private String dialogues[] = new String[20];
    private int dialogueIndex= 0;

    //Counters
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int spriteCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;

    //Character Attributes
    private int level;
    public int maxLife;
    public int life;
    public int speed;
    public int strength;
    public int dexterity;
    public int attack;
    private int defense;
    public int exp;
    public int nextLevelExp;
    public int coins = 0;
    public Entity currentWeapon;
    public Entity currentShield;

    //Item Attributes
    public int attackValue;
    public int defenseValue;


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
    public void damageReaction(){

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
                gp.playSE(6);
                gp.player.life -= 25;
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
        if (spriteCounter > 12) {
            spriteNum++;
            if (spriteNum > 3) { // Cycle back to 1
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
                    if(spriteNum == 3) {image = up3;}
                    break;
                case "down":
                    if(spriteNum == 1) {image = down1;}
                    if(spriteNum == 2) {image = down2;}
                    if(spriteNum == 3) {image = down3;}
                    break;
                case "right":
                    if(spriteNum == 1) {image = right1;}
                    if(spriteNum == 2) {image = right2;}
                    if(spriteNum == 3) {image = right3;}
                    break;
                case "left":
                    if(spriteNum == 1) {image = left1;}
                    if(spriteNum == 2) {image = left2;}
                    if(spriteNum == 3) {image = left3;}
                    break;
            }
            //Hostile HealthBar
            if(type == 2 && hpBarOn){
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1,screenY-16,gp.tileSize + 2, 12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX,screenY-15,(int)hpBarValue, 10);
                hpBarCounter++;

                if(hpBarCounter > 600){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            //Damage effect when player is damaged.(changes the opacity)
            if(invincible){
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2,0.4F);
            }

            if(dying){
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            //Reset effect 1F = normal opacity
            changeAlpha(g2,1F);

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

    public void speak(){}

    public void checkDrop(){
    }


    public void dropItem(SuperObject droppedItem){
        for(int i = 0; i < gp.obj.length; i++){
            if(gp.obj[i] == null){
                gp.obj[i] = droppedItem;
                gp.obj[i].setWorldX(getWorldX());
                gp.obj[i].setWorldY(getWorldY());
                break;
            }
        }
    }

    //setters and getters
    public int getWorldX() { return worldX; }
    public void setWorldX(int worldX) { this.worldX = worldX; }

    public int getWorldY() { return worldY; }
    public void setWorldY(int worldY) { this.worldY = worldY; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BufferedImage getUp1() { return up1; }
    public void setUp1(BufferedImage up1) { this.up1 = up1; }

    public BufferedImage getUp2() { return up2; }
    public void setUp2(BufferedImage up2) { this.up2 = up2; }

    public BufferedImage getUp3() { return up3; }
    public void setUp3(BufferedImage up3) { this.up3 = up3; }

    public BufferedImage getDown1() { return down1; }
    public void setDown1(BufferedImage down1) { this.down1 = down1; }

    public BufferedImage getDown2() { return down2; }
    public void setDown2(BufferedImage down2) { this.down2 = down2; }

    public BufferedImage getDown3() { return down3; }
    public void setDown3(BufferedImage down3) { this.down3 = down3; }

    public BufferedImage getLeft1() { return left1; }
    public void setLeft1(BufferedImage left1) { this.left1 = left1; }

    public BufferedImage getLeft2() { return left2; }
    public void setLeft2(BufferedImage left2) { this.left2 = left2; }

    public BufferedImage getLeft3() { return left3; }
    public void setLeft3(BufferedImage left3) { this.left3 = left3; }

    public BufferedImage getRight1() { return right1; }
    public void setRight1(BufferedImage right1) { this.right1 = right1; }

    public BufferedImage getRight2() { return right2; }
    public void setRight2(BufferedImage right2) { this.right2 = right2; }

    public BufferedImage getRight3() { return right3; }
    public void setRight3(BufferedImage right3) { this.right3 = right3; }

    public BufferedImage getAttackUp1() { return attackUp1; }
    public void setAttackUp1(BufferedImage attackUp1) { this.attackUp1 = attackUp1; }

    public BufferedImage getAttackUp2() { return attackUp2; }
    public void setAttackUp2(BufferedImage attackUp2) { this.attackUp2 = attackUp2; }

    public BufferedImage getAttackDown1() { return attackDown1; }
    public void setAttackDown1(BufferedImage attackDown1) { this.attackDown1 = attackDown1; }

    public BufferedImage getAttackDown2() { return attackDown2; }
    public void setAttackDown2(BufferedImage attackDown2) { this.attackDown2 = attackDown2; }

    public BufferedImage getAttackLeft1() { return attackLeft1; }
    public void setAttackLeft1(BufferedImage attackLeft1) { this.attackLeft1 = attackLeft1; }

    public BufferedImage getAttackLeft2() { return attackLeft2; }
    public void setAttackLeft2(BufferedImage attackLeft2) { this.attackLeft2 = attackLeft2; }

    public BufferedImage getAttackRight1() { return attackRight1; }
    public void setAttackRight1(BufferedImage attackRight1) { this.attackRight1 = attackRight1; }

    public BufferedImage getAttackRight2() { return attackRight2; }
    public void setAttackRight2(BufferedImage attackRight2) { this.attackRight2 = attackRight2; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    public int getType() { return type; }
    public void setType(int type) { this.type = type; }

    public int getSpriteNum() { return spriteNum; }
    public void setSpriteNum(int spriteNum) { this.spriteNum = spriteNum; }

    public void resetSolidArea() {
        this.solidArea.x = solidAreaDefaultX;
        this.solidArea.y = solidAreaDefaultY;
    }

    public Rectangle getSolidArea() { return solidArea; }
    public void setSolidArea(Rectangle solidArea) { this.solidArea = solidArea; }

    public Rectangle getAttackArea() { return attackArea; }
    public void setAttackArea(Rectangle attackArea) { this.attackArea = attackArea; }

    public int getSolidAreaX() { return solidArea.x; }
    public void setSolidAreaX(int x) { solidArea.x = x; }

    public int getSolidAreaY() { return solidArea.y; }
    public void setSolidAreaY(int y) { solidArea.y = y; }

    public int getSolidAreaWidth() { return solidArea.width; }
    public void setSolidAreaWidth(int width) { solidArea.width = width; }

    public int getSolidAreaHeight() { return solidArea.height; }
    public void setSolidAreaHeight(int height) { solidArea.height = height; }

    public int getSolidAreaDefaultX() { return solidAreaDefaultX; }
    public void setSolidAreaDefaultX(int solidAreaDefaultX) { this.solidAreaDefaultX = solidAreaDefaultX; }

    public int getSolidAreaDefaultY() { return solidAreaDefaultY; }
    public void setSolidAreaDefaultY(int solidAreaDefaultY) { this.solidAreaDefaultY = solidAreaDefaultY; }

    public boolean isCollisionOn() { return collisionOn; }
    public void setCollisionOn(boolean collisionOn) { this.collisionOn = collisionOn; }

    public boolean isInvincible() { return invincible; }
    public void setInvincible(boolean invincible) { this.invincible = invincible; }

    public boolean isAttacking() { return attacking; }
    public void setAttacking(boolean attacking) { this.attacking = attacking; }

    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }

    public boolean isDying() { return dying; }
    public void setDying(boolean dying) { this.dying = dying; }

    public boolean isHpBarOn() { return hpBarOn; }
    public void setHpBarOn(boolean hpBarOn) { this.hpBarOn = hpBarOn; }

    public int getActionLockCounter() { return actionLockCounter; }
    public void setActionLockCounter(int actionLockCounter) { this.actionLockCounter = actionLockCounter; }

    public int getInvincibleCounter() { return invincibleCounter; }
    public void setInvincibleCounter(int invincibleCounter) { this.invincibleCounter = invincibleCounter; }

    public int getSpriteCounter() { return spriteCounter; }
    public void setSpriteCounter(int spriteCounter) { this.spriteCounter = spriteCounter; }

    public int getDyingCounter() { return dyingCounter; }
    public void setDyingCounter(int dyingCounter) { this.dyingCounter = dyingCounter; }

    public int getHpBarCounter() { return hpBarCounter; }
    public void setHpBarCounter(int hpBarCounter) { this.hpBarCounter = hpBarCounter; }

    public int getMaxLife() { return maxLife; }
    public void setMaxLife(int maxLife) { this.maxLife = maxLife; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getLife() { return life; }
    public void setLife(int life) { this.life = life; }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

    public int getStrength() { return strength; }
    public void setStrength(int strength) { this.strength = strength; }

    public int getDexterity() { return dexterity; }
    public void setDexterity(int dexterity) { this.dexterity = dexterity; }

    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }

    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }

    public int getExp() { return exp; }
    public void setExp(int exp) { this.exp = exp; }

    public int getNextLevelExp() { return nextLevelExp; }
    public void setNextLevelExp(int nextLevelExp) { this.nextLevelExp = nextLevelExp; }

    public int getCoin() { return coins; }
    public void setCoin(int coins) { this.coins = coins; }

    public int getAttackValue() { return attackValue; }
    public void setAttackValue(int attackValue) { this.attackValue = attackValue; }

    public int getDefenseValue() { return defenseValue; }
    public void setDefenseValue(int defenseValue) { this.defenseValue = defenseValue; }

    public String getDialogues(int dialogueIndex) {
        return dialogues[dialogueIndex];
    }

    public void setDialogues(String[] dialogues) { this.dialogues = dialogues; }

    public int getDialogueIndex() { return dialogueIndex; }

    public void setDialogueIndex(int dialogueIndex) { this.dialogueIndex = dialogueIndex; }

}
