package object;

import Main.GamePanel;
import Main.UtilityTool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    private BufferedImage image, image2;
    private ImageIcon imgGIF;
    private String name;
    private boolean collision = false;
    private int worldX, worldY;
    private Rectangle solidArea = new Rectangle(0,0,48,48);
    private int solidAreaDefaultX = 0;
    private int solidAreaDefaultY = 0;
    public UtilityTool uTool = new UtilityTool();

    public void draw(Graphics2D g2, GamePanel gp){

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            if (imgGIF != null) {
                // Draw animated GIF
                g2.drawImage(imgGIF.getImage(), screenX, screenY, gp.tileSize, gp.tileSize, null);
            } else if (image != null) {
                // Draw static image
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

        }
    }

    //setters and getters
    public BufferedImage getImage() { return image; }

    public void setImage(BufferedImage image) { this.image = image; }

    public ImageIcon getImageIcon() { return imgGIF; }

    public void setImageIcon(ImageIcon imgGIF) { this.imgGIF = imgGIF; }

    public BufferedImage getImage2() {return image2; }

    public void setImage2(BufferedImage image2) { this.image2 = image2; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public boolean isCollision() { return collision; }

    public void setCollision(boolean collision) { this.collision = collision; }

    public int getWorldX() { return worldX; }

    public void setWorldX(int worldX) { this.worldX = worldX; }

    public int getWorldY() { return worldY; }

    public void setWorldY(int worldY) { this.worldY = worldY; }

    public Rectangle getSolidArea() { return solidArea; }

    public int getSolidAreaX() { return solidArea.x; }
    public void setSolidAreaX(int x) { solidArea.x = x; }

    public int getSolidAreaY() { return solidArea.y; }
    public void setSolidAreaY(int y) { solidArea.y = y; }

    public void resetSolidArea() {
        this.solidArea.x = solidAreaDefaultX;
        this.solidArea.y = solidAreaDefaultY;
    }

    public void setSolidArea(Rectangle solidArea) { this.solidArea = solidArea; }

    public int getSolidAreaDefaultX() { return solidAreaDefaultX; }

    public void setSolidAreaDefaultX(int solidAreaDefaultX) { this.solidAreaDefaultX = solidAreaDefaultX; }

    public int getSolidAreaDefaultY() { return solidAreaDefaultY; }

    public void setSolidAreaDefaultY(int solidAreaDefaultY) { this.solidAreaDefaultY = solidAreaDefaultY; }

    public UtilityTool getuTool() { return uTool; }

    public void setuTool(UtilityTool uTool) { this.uTool = uTool; }

}
