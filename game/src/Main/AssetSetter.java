package Main;

import Entity.*;
import object.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

        // Map 0 objects
        if(gp.tileM.map.equals("/maps/worldMap1.txt")) {

        }
        // Map 1 objects
        else if(gp.tileM.map.equals("/maps/worldMap2.txt")) {

        }
        else if(gp.tileM.map.equals("/maps/worldMap3.txt")) {

        }
    }

    public void setNPC(){
        //type1 = OldMan, type2 = PilosopoTasyo
        if(gp.npc != null) {
            for(int i = 0; i < gp.npc.length; i++) {
                gp.npc[i] = null;
            }
        }
        if(gp.tileM.map.equals("/maps/worldMap1.txt")) {
            spawnNPC(0,17,6,2);
        }
        else if(gp.tileM.map.equals("/maps/worldMap2.txt")) {
            spawnNPC(0,36,35,1);
        }
        else if(gp.tileM.map.equals("/maps/worldMap3.txt")) {
//            spawnNPC(0,13,2,1);
        }
    }

    public void setHostile(){
        //type1 = Dwende, type2 = Sigbin, type3 = Tikbalang
        if(gp.hostile != null) {
            for(int i = 0; i < gp.hostile.length; i++) {
                gp.hostile[i] = null;
            }
        }
        if(gp.tileM.map.equals("/maps/worldMap1.txt")) {
            spawnHostile(0,38,8,1);
            spawnHostile(1,38,9,1);
            spawnHostile(2,38,10,1);
            spawnHostile(3,36,17,1);
            spawnHostile(4,38,18,1);

            spawnHostile(5,28,33,2);
            spawnHostile(6,28,34,2);
            spawnHostile(7,35,36,2);
            spawnHostile(8,37,30,2);
            spawnHostile(9,36,41,2);

        }
        else if(gp.tileM.map.equals("/maps/worldMap2.txt")) {
            spawnHostile(0,37,35,3);
            spawnHostile(1,35,33,3);
            spawnHostile(2,34,37,3);
            spawnHostile(3,33,42,3);
            spawnHostile(4,31,34,3);

            spawnHostile(5,15,33,4);
            spawnHostile(6,12,38,4);
            spawnHostile(7,17,31,4);
            spawnHostile(8,19,36,4);
            spawnHostile(9,15,31,4);

        }
        else if(gp.tileM.map.equals("/maps/worldMap3.txt")) {

        }
    }

    public void spawnHostile(int i, int x, int y, int type){
        if(type == 1){
            gp.hostile[i] = new Hostile_Dwende(gp);
            gp.hostile[i].worldX = gp.tileSize * x;
            gp.hostile[i].worldY = gp.tileSize* y;
        } else if(type ==2){
            gp.hostile[i] = new Hostile_Sigbin(gp);
            gp.hostile[i].worldX = gp.tileSize * x;
            gp.hostile[i].worldY = gp.tileSize* y;
        } else if(type == 3){
            gp.hostile[i] = new Hostile_Tikbalang(gp);
            gp.hostile[i].worldX = gp.tileSize * x;
            gp.hostile[i].worldY = gp.tileSize* y;
        } else if(type == 4){
            gp.hostile[i] = new Hostile_Wakwak(gp);
            gp.hostile[i].worldX = gp.tileSize * x;
            gp.hostile[i].worldY = gp.tileSize* y;
        }

    }
    public void spawnNPC(int i, int x, int y, int type){
        if(type == 1){
            gp.npc[i] = new NPC_OldMan(gp);
            gp.npc[i].worldX = gp.tileSize * x;
            gp.npc[i].worldY = gp.tileSize* y;
        } else if(type ==2){
            gp.npc[i] = new NPC_PilosopoTasyo(gp);
            gp.npc[i].worldX = gp.tileSize * x;
            gp.npc[i].worldY = gp.tileSize* y;
        }

    }

    public void spawnObject(int i, int x, int y, int type){


    }
}