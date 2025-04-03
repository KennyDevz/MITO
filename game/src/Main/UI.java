package Main;

import object.Obj_Health;
import object.Obj_Key;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UI {
    GamePanel gp;
    Font arial_40;
    Graphics2D g2;
    BufferedImage keyImage;
    BufferedImage healthFull, healthBlank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    PauseMenu pmenu = new PauseMenu();
    public String currentDialogue = "";
    BufferedImage bgDialogue;
    Font Draxel;

    public UI(GamePanel gp){
        this.gp = gp;
        this.pmenu.setVisible(false);
        gp.add(pmenu);
        arial_40 = new Font("Arial", Font.PLAIN, 40);

        //CREATE HUD OBJECT
        SuperObject health = new Obj_Health(gp);
        healthBlank = health.getImage();
        healthFull = health.getImage2();
        try {
            bgDialogue = ImageIO.read(getClass().getResource("/pause_ui.buttons/pausebg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Draxel = Font.createFont(Font.TRUETYPE_FONT, new File("/fonts/Draxel.ttf"))
                    .deriveFont(Font.PLAIN, 45f); // Change size if needed
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Draxel);
        } catch (Exception e) {
            e.printStackTrace();
            Draxel = new Font("Draxel", Font.PLAIN, 25);
        }
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        if(gp.gameState == gp.playState){
            pmenu.setVisible(false);
        }
        if(gp.gameState == gp.pauseState){
//            pmenu.setVisible(true);
            drawPauseScreen();
        }
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
        if(gp.gameState == gp.deadState){
            drawDeadScreen();
        }

        drawPlayerLife();
        //message
        if(messageOn){
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message,gp.tileSize/2,gp.tileSize*5);

            messageCounter++;

            if(messageCounter > 120){
                messageCounter = 0;
                messageOn = false;
            }
        }
    }

    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int fullBarWidth = gp.tileSize * 5; // Health container width
        int barHeight = gp.tileSize + 5;    // Health container height

        int healthBarOffsetX = 64;
        int healthBarOffsetY = 24;
        int adjustedBarWidth = fullBarWidth - healthBarOffsetX - 28;
        int adjustedBarHeight = barHeight - (healthBarOffsetY * 2);

        // Calculate HP percentage
        double hpPercent = (double) gp.player.life / gp.player.maxLife;

        // Determine health bar color based on percentage
        Color hpColor = (hpPercent <= 0.25) ? Color.RED :
                (hpPercent <= 0.50) ? Color.YELLOW :
                        (hpPercent <= 0.75) ? new Color(173, 255, 47) : // Yellowish Green
                                Color.GREEN;

        // Calculate the width of the current health bar
        int healthWidth = (int) (adjustedBarWidth * hpPercent);
        healthWidth = Math.max(0, healthWidth);

        // Draw the filled health bar inside the container
        g2.setColor(hpColor);
        g2.fillRect(x + healthBarOffsetX, y + healthBarOffsetY, healthWidth, adjustedBarHeight);

        // Draw the blank health container (background with character)
        g2.drawImage(healthBlank, x, y, fullBarWidth, barHeight, null);

        // Display numeric HP value
        g2.setColor(Color.white);
        g2.drawString(gp.player.life + "/" + gp.player.maxLife, x + fullBarWidth + 10, y + barHeight);
    }



    public void drawDialogueScreen(){
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 5;
        drawSubWindow(g2, x, y, width, height);
        g2.setFont(Draxel);
        g2.setColor(new Color(67, 40, 24));
        x += gp.tileSize;
        y += gp.tileSize + 50;
        if(currentDialogue != null && !currentDialogue.isEmpty()){
            for(String line: currentDialogue.split("\n")){
                x = getXforCenteredText(line);
                g2.drawString(line, x, y);
                y += 40;
            }
        }
    }

    public void drawSubWindow(Graphics2D g2, int x, int y, int width, int height){
        if (bgDialogue != null) {
            g2.drawImage(bgDialogue, x, y, width, height, null);
        } else {
            //in case bgDialogue is null
            g2.setColor(new Color(0,0,0));
            g2.fillRoundRect(x, y, width, height, 35, 35);
        }

    }

    public void drawDeadScreen(){
        String text = "YOU DIED!";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);

    }

    public void drawPauseScreen(){
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
//        pmenu.setVisible(true);
    }

    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }










}
