package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

public class EnemyShot extends Sprite {

    private static final int H_SPACE = 20;
    private static final int V_SPACE = 1;

    public EnemyShot(int x , int y){
        super();
        initEnemyShot(x, y);
    }

    private void initEnemyShot(int x, int y) {
        this.x = x; 
        this.y = y; 
        var ii = new ImageIcon(IMG_EnemySHOT);

        // Scale the image to use the global scaling factor
        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
                ii.getIconHeight() * SCALE_FACTOR,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);

        setX(x + H_SPACE);
        setY(y + V_SPACE);
    }

    @Override 
    public void act() {
        // Move the shot down the screen
        this.y -= 2; // Move down by 2 pixels each frame

        // Check if the shot is out of bounds
        if (this.y > BOARD_HEIGHT) {
            this.die(); // Remove the shot if it goes off-screen
        }
    }
}