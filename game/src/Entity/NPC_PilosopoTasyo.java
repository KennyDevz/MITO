package Entity;

import Main.GamePanel;

import java.util.Random;

public class NPC_PilosopoTasyo extends Entity{
    private String[] dialogues = new String[20];

    public NPC_PilosopoTasyo(GamePanel gp){
        super(gp);
        setName("Old Man");
        setDirection("down");
        setSpeed(1);
        getSolidArea().x = 20;
        getSolidArea().y = 24;
        getSolidArea().width = 25;
        getSolidArea().height = 35;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);

        getImage();
        setDialogue();
    }

    public void getImage(){
        setUp1(setup("/indi_sprites/NPC/tasyo_up1", gp.tileSize/2, gp.tileSize));
        setUp2(setup("/indi_sprites/NPC/tasyo_up2", gp.tileSize/2, gp.tileSize));
        setDown1(setup("/indi_sprites/NPC/tasyo_down1", gp.tileSize/2, gp.tileSize));
        setDown2(setup("/indi_sprites/NPC/tasyo_down2", gp.tileSize/2, gp.tileSize));
        setLeft1(setup("/indi_sprites/NPC/tasyo_left1", gp.tileSize/2, gp.tileSize));
        setLeft2(setup("/indi_sprites/NPC/tasyo_left2", gp.tileSize/2, gp.tileSize));
        setRight1(setup("/indi_sprites/NPC/tasyo_right1", gp.tileSize/2, gp.tileSize));
        setRight2(setup("/indi_sprites/NPC/tasyo_right2", gp.tileSize/2, gp.tileSize));
    }

    public void setAction(){
        setActionLockCounter(getActionLockCounter() + 1);

        if(getActionLockCounter() == 100){
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if(i <= 25) {
                setDirection("up");
            }
            if(i > 25 && i <= 50){
                setDirection("down");
            }
            if(i > 50 && i <= 75){
                setDirection("left");
            }
            if(i > 75 && i <= 100){
                setDirection("right");
            }
            setActionLockCounter(0);
        }
    }

    public void setDialogue(){
        dialogues[0] = "Greetings, adventurer! You bear the name of our great hero,\nAndres Bonifacio.";
        dialogues[1] = "Dark forces roam these lands, Duwende and Sigbin\nthreaten our people.";
        dialogues[2] = "Legends say the Duwende trick the weak-minded, while the\nSigbin haunt travelers at dusk.";
        dialogues[3] = "Go now, hero! Bring light to the darkness.";
    }

    public void speak(){
        gp.ui.currentDialogue = dialogues[getDialogueIndex()];
        setDialogueIndex(getDialogueIndex() + 1);
        //loops when it reaches the last index
        if (gp.ui.currentDialogue == null || gp.ui.currentDialogue.isEmpty() || getDialogueIndex() >= dialogues.length) {
            gp.ui.currentDialogue = dialogues[0];
            setDialogueIndex(1);
        }


        switch (gp.player.getDirection()){
            case "up": setDirection("down"); break;
            case "down": setDirection("up"); break;
            case "left": setDirection("right"); break;
            case "right": setDirection("left"); break;
        }
    }
}
