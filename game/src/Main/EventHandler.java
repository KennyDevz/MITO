package Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import Entity.Entity;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public void checkEvent() {
        if(gp.currentMap == 0) {
            // Teleport to World 2
            if((gp.player.worldX >= 13 * gp.tileSize && gp.player.worldX <= 14 * gp.tileSize) &&
                    gp.player.worldY == 28 * gp.tileSize) {
                teleportToWorld2();
            }

            // Other world 1 events...
            if(hit(27, 16, "right")) {
                damagePit();
            }
        }
        else if(gp.currentMap == 1) {
            // Teleport back to World 1
            if((gp.player.worldX >= 40 * gp.tileSize && gp.player.worldX <= 41 * gp.tileSize) &&
                    gp.player.worldY == 39 * gp.tileSize) {
                returnToWorld1();
            }
            //Teleport to World 3
            if((gp.player.worldX >= 43 * gp.tileSize && gp.player.worldX <= 44 * gp.tileSize) &&
                    gp.player.worldY == 12 * gp.tileSize) {
                teleportToWorld3();
            }
        }
        else if(gp.currentMap == 2) {
            // Teleport back to World 2
            if((gp.player.worldX >= 24 * gp.tileSize && gp.player.worldX <= 25 * gp.tileSize) &&
                    gp.player.worldY == 44 * gp.tileSize) {
                returnToWorld2();
            }
        }
    }

    public boolean hit(int eventCol, int eventRow, String reqDirection){
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect.x = eventCol*gp.tileSize + eventRect.x;
        eventRect.y = eventRow*gp.tileSize + eventRect.y;

        if(gp.player.solidArea.intersects(eventRect)){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;
        return hit;
    }

    public void damagePit() {
        gp.player.life -= 1;
    }

    public void healingPool(){
        gp.player.life = gp.player.maxLife;
    }


    private void teleportToWorld2() {
        gp.playSE(23); // Teleport sound
        gp.currentMap = 1;
        gp.tileM.loadMap("/maps/worldMap2.txt");

        // Reset hostile & npc array
        gp.hostile = new Entity[gp.maxHostile];
        gp.npc = new Entity[gp.maxNPC];

        // Set player position in world 2 (adjust these coordinates as needed)
        gp.player.worldX = gp.tileSize * 40 + gp.tileSize/2;
        gp.player.worldY = gp.tileSize * 38;

        // Reset and spawn new entities
        gp.aSetter.setObject();
        gp.aSetter.setNPC();
        gp.aSetter.setHostile();

    }

    private void returnToWorld1() {
        gp.playSE(23); // Teleport sound
        gp.currentMap = 0;
        gp.tileM.loadMap("/maps/worldMap1.txt");

        // Reset hostile & npc array
        gp.hostile = new Entity[gp.maxHostile];
        gp.npc = new Entity[gp.maxNPC];

        // Set player position in world 1
        gp.player.worldX = gp.tileSize * 13 + gp.tileSize/2;
        gp.player.worldY = gp.tileSize * 29;

        // Reset and spawn entities
        gp.aSetter.setObject();
        gp.aSetter.setNPC();
        gp.aSetter.setHostile();

    }

    private void teleportToWorld3() {
        gp.playSE(23); // Teleport sound
        gp.currentMap = 2;
        gp.tileM.loadMap("/maps/worldMap3.txt");

        // Reset hostile & npc array
        gp.hostile = new Entity[gp.maxHostile];
        gp.npc = new Entity[gp.maxNPC];

        // Set player position in world 3
        gp.player.worldX = gp.tileSize * 25;
        gp.player.worldY = gp.tileSize * 43;

        // Reset and spawn entities
        gp.aSetter.setObject();
        gp.aSetter.setNPC();
        gp.aSetter.setHostile();

    }

    private void returnToWorld2() {
        gp.playSE(23); // Teleport sound
        gp.currentMap = 1;
        gp.tileM.loadMap("/maps/worldMap2.txt");

        // Reset hostile & npc array
        gp.hostile = new Entity[gp.maxHostile];
        gp.npc = new Entity[gp.maxNPC];

        // Set player position back in world 2
        gp.player.worldX = gp.tileSize * 43 + gp.tileSize/2;
        gp.player.worldY = gp.tileSize * 13;

        // Reset and spawn entities
        gp.aSetter.setObject();
        gp.aSetter.setNPC();
        gp.aSetter.setHostile();

    }


}
