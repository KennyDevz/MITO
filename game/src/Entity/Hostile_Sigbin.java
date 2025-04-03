package Entity;

import Main.GamePanel;
import object.Obj_Boots;
import object.Obj_Dropped_Coin;
import object.Obj_Dropped_Sword;
import object.Obj_Key;

import java.util.Random;

public class Hostile_Sigbin extends Entity{

    public Hostile_Sigbin(GamePanel gp){
        super(gp);

        type = 2;
        name = "Sigbin";
        direction ="down";
        speed = 1;
        maxLife = 7;
        life = maxLife;

        solidArea.x = 20;
        solidArea.y = 24;
        solidArea.width = 25;
        solidArea.height = 35;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage(){
        int i = 5;
        up1 = setup("/indi_sprites/Hostile/Sigbin/Sigbin_walk_up_1",gp.tileSize ,gp.tileSize );
        up2 = setup("/indi_sprites/Hostile/Sigbin/Sigbin_back",gp.tileSize ,gp.tileSize );
        up3 = setup("/indi_sprites/Hostile/Sigbin/Sigbin_walk_up_2",gp.tileSize ,gp.tileSize );
        down1 = setup("/indi_sprites/Hostile/Sigbin/Sigbin_walk_down_1",gp.tileSize ,gp.tileSize );
        down2 = setup("/indi_sprites/Hostile/Sigbin/Sigbin_front",gp.tileSize ,gp.tileSize );
        down3 = setup("/indi_sprites/Hostile/Sigbin/Sigbin_walk_down_2",gp.tileSize ,gp.tileSize );
        left1 = setup("/indi_sprites/Hostile/Sigbin/Sigbin_walk_left_1",gp.tileSize ,gp.tileSize );
        left2 = setup("/indi_sprites/Hostile/Sigbin/Sigbin_left",gp.tileSize ,gp.tileSize );
        left3 = setup("/indi_sprites/Hostile/Sigbin/Sigbin_walk_left_2",gp.tileSize ,gp.tileSize );
        right1 = setup("/indi_sprites/Hostile/Sigbin/Sigbin_walk_right_1",gp.tileSize ,gp.tileSize );
        right2 = setup("/indi_sprites/Hostile/Sigbin/Sigbin_right",gp.tileSize ,gp.tileSize );
        right3 = setup("/indi_sprites/Hostile/Sigbin/Sigbin_walk_right_2",gp.tileSize ,gp.tileSize );
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
    public void damageReaction(){
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    public void checkDrop(){
        int i = new Random().nextInt(100)+1;
        if(i < 50){
            dropItem(new Obj_Dropped_Coin(gp));
            System.out.println("Dropped coin");
        } else if(i >= 50 && i < 75){
            dropItem(new Obj_Key(gp));
            System.out.println("Dropped Key");
        }else if(i >= 75 && i < 100){
            dropItem(new Obj_Dropped_Sword(gp));
            System.out.println("Dropped sword");
        }
    }
}
