package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

public class Enemy extends Sprite {

    // private Bomb bomb;

    public Enemy(int x, int y) {

        initEnemy(x, y);
    }

    private void initEnemy(int x, int y) {

        this.x = x;
        this.y = y;

        // bomb = new Bomb(x, y);

        var ii = new ImageIcon(IMG_PIRATE01);

        // Scale the image to use the global scaling factor
        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
                ii.getIconHeight() * SCALE_FACTOR,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    @Override 
    public void act(){
        this.y += 1;
    }
}
