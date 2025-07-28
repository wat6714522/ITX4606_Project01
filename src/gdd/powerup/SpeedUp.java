package gdd.powerup;

import static gdd.Global.*;
import javax.swing.ImageIcon;
import gdd.sprite.Player;

public class SpeedUp extends PowerUp {

    public SpeedUp(int x, int y){
        super(x, y); 
        initSpeedUp();


    }

    public void initSpeedUp(){

        ImageIcon ii = new ImageIcon(IMG_SPEEDUP);
        var scaleImage = ii.getImage().getScaledInstance(ii.getIconWidth(), 
                ii.getIconHeight(), 
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaleImage);
    }

    @Override
    public void upgrade(Player player) {
        player.setSpeed(player.getSpeed() + 1); 
        this.die(); 
    }

    @Override
    public void act() {
        this.y +=2; 
    }

    
} 
    

