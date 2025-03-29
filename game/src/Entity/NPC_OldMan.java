package Entity;

import Main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{

    public NPC_OldMan (GamePanel gp){
        super(gp);
        name = "Old Man";
        direction = "down";
        speed = 1;
        solidArea.x = 20;
        solidArea.y = 24;
        solidArea.width = 25;
        solidArea.height = 35;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    public void getImage(){
        up1 = setup("/indi_sprites/NPC/oldman_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/indi_sprites/NPC/oldman_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/indi_sprites/NPC/oldman_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/indi_sprites/NPC/oldman_down_2",gp.tileSize,gp.tileSize);
        left1= setup("/indi_sprites/NPC/oldman_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/indi_sprites/NPC/oldman_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/indi_sprites/NPC/oldman_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/indi_sprites/NPC/oldman_right_2",gp.tileSize,gp.tileSize);
    }

    public void setAction(){
        actionLockCounter++;

        if(actionLockCounter == 100){
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if(i <= 25) {
                direction = "up";
            }
            if(i > 25 && i <= 50){
                direction = "down";
            }
            if(i > 50 && i <= 75){
                direction = "left";
            }
            if(i > 75 && i <= 100){
                direction = "right";
            }
            actionLockCounter = 0;
        }

    }
}
