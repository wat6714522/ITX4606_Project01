package gdd.powerup;

import static gdd.Global.*; 
import gdd.sprite.Player;
import javax.swing.ImageIcon; 

public class MultiShot extends PowerUp {

    public MultiShot(int x, int y){
        super(x, y); 
        
    }

    private void initMultiShot(){
        ImageIcon ii = new ImageIcon(IMG_MULTISHOT); 
        var scaledImage = ii.getImage().getScaledInstance(
            ii.getIconWidth() * SCALE_FACTOR, 
            ii.getIconHeight() * SCALE_FACTOR, 
            java.awt.Image.SCALE_SMOOTH
        ); 
        setImage(scaledImage); 
    }

    @Override
    public void upgrade(Player player){
        player.activateMultiShot(600);
        this.die(); 
    }

    @Override
    public void act(){
        this.y += 1; 
    }
    
}
