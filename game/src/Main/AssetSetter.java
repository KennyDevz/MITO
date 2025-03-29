package Main;

import Entity.Hostile_Slime;
import Entity.NPC_OldMan;
import object.Obj_Boots;
import object.Obj_Chest;
import object.Obj_Door;
import object.Obj_Key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new Obj_Key(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new Obj_Key(gp);
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        gp.obj[2] = new Obj_Key(gp);
        gp.obj[2].worldX = 37 * gp.tileSize;
        gp.obj[2].worldY = 7 * gp.tileSize;

        gp.obj[3] = new Obj_Key(gp);
        gp.obj[3].worldX = 35 * gp.tileSize;
        gp.obj[3].worldY = 7 * gp.tileSize;

        gp.obj[4] = new Obj_Door(gp);
        gp.obj[4].worldX = 10 * gp.tileSize;
        gp.obj[4].worldY = 11 * gp.tileSize;

        gp.obj[5] = new Obj_Door(gp);
        gp.obj[5].worldX = 8 * gp.tileSize;
        gp.obj[5].worldY = 28 * gp.tileSize;

        gp.obj[6] = new Obj_Door(gp);
        gp.obj[6].worldX = 12 * gp.tileSize;
        gp.obj[6].worldY = 22 * gp.tileSize;

        gp.obj[7] = new Obj_Chest(gp);
        gp.obj[7].worldX = 10 * gp.tileSize;
        gp.obj[7].worldY = 7 * gp.tileSize;

        gp.obj[8] = new Obj_Boots(gp);
        gp.obj[8].worldX = 25 * gp.tileSize;
        gp.obj[8].worldY = 23 * gp.tileSize;
    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);

        //Set where NPC spawns
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;

    }
    public void setHostile(){
        gp.hostile[0] = new Hostile_Slime(gp);
        //Set where Hostile spawns
        gp.hostile[0].worldX = gp.tileSize*23;
        gp.hostile[0].worldY = gp.tileSize*36;

        gp.hostile[1] = new Hostile_Slime(gp);
        gp.hostile[1].worldX = gp.tileSize*23;
        gp.hostile[1].worldY = gp.tileSize*37;

    }
}
