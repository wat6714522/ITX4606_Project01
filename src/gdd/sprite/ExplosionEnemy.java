package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

public class ExplosionEnemy extends Enemy {

    private static final int UFO_SPEED = 2; // Speed of the UFO enemy

    public ExplosionEnemy(int x, int y) {
        super(x, y);
        initUFOEnemy(x, y);
    }

    private void initUFOEnemy(int x, int y) {

        this.x = x;
        this.y = y;

        var ii = new ImageIcon(IMG_UFO01);

        // Scale the image to use the global scaling factor
        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
                ii.getIconHeight() * SCALE_FACTOR,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    @Override
    public void act() {
    }

}
