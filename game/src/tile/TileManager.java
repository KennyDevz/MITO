package tile;

import Main.GamePanel;
import Main.UI;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;
    int tileWidth = 32;
    int tileHeight = 32;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[16];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/worldMap3.txt");
    }


    public void getTileImage() {

        int y = 0;

        int k = 0;
        for (int i = 0; i < tile.length / 4; i++) {
            for (int j = 0; j < 4; j++) {
                boolean wall = false;
                if (k == 0 || k == 1|| k == 2 || k == 10 || k == 11)
                    wall = true;
                setup(k, y, i, j, wall);
                k++;
            }
            y++;
        }


    }

    public void setup(int index, int y, int i, int j, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try{
            BufferedImage tileSheet = ImageIO.read(getClass().getResourceAsStream("/indi_sprites/world/ruins.png"));
            tile[index] = new Tile();
            tile[index].image = tileSheet.getSubimage(tileWidth * j, tileHeight * y, tileWidth, tileHeight);
            tile[index].image = uTool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision = collision;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filepath){
        try {
            InputStream is = getClass().getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
