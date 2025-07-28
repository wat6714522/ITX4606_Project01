package gdd.powerup;

import static gdd.Global.*;
import gdd.sprite.Player; 
import javax.swing.ImageIcon; 

public class PlayerArmor extends PowerUp {
    public PlayerArmor(int x, int y){
        super(x, y);
        initPlayerArmor();
    }

    public void initPlayerArmor(){
        ImageIcon ii = new ImageIcon(IMG_ARMOR);
        var scaledImage = ii.getImage().getScaledInstance(
                ii.getIconWidth(), 
                ii.getIconHeight(),
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    @Override
    public void upgrade(Player player) {
        player.activateArmor(300);
    }

    @Override 
    public void act(){
        this.y +=2 ; 
    }
}

