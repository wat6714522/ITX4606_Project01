package gdd.sprite;

import static gdd.Global.*;
import java.util.List; 
import java.util.Random;
import javax.swing.ImageIcon;

public class BombEnemy extends Enemy {
    private static final int BOMB_DROP_CHANCE = 200; 
    private static final int BOMB_DROP_RATE = 180; 

    private int lastDropFrame = -BOMB_DROP_RATE; 
    private Random random = new Random(); 
    private List<Bomb> bombs;  
    private int currentFrame = 0;
    private Player player;

    public BombEnemy(int x, int y){
        super(x, y);
        initBombEnemy(x, y);

    }

    private void initBombEnemy(int x, int y){
        this.x = x;
        this.y = y; 

        var ii = new ImageIcon(IMG_PIRATE02); 
        var scaledImage = ii.getImage().getScaledInstance(
            ii.getIconWidth() * SCALE_FACTOR,
            ii.getIconHeight() * SCALE_FACTOR,
            java.awt.Image.SCALE_SMOOTH
        );
        setImage(scaledImage);
    }

    @Override
    public void act(){
        currentFrame++;
        this.y += 1; // Move downwards like other enemies
        
        // Randomly drop bombs
        if (canDropBomb()) {
            if (random.nextInt(BOMB_DROP_CHANCE) == 0) {
                dropBomb();
                lastDropFrame = currentFrame;
            }
        }
    }
    
    public void setTarget(Player player) {
        this.player = player;
    }
    
    private boolean canDropBomb() {
        // Check if enough time has passed since last drop
        return bombs != null && 
               (currentFrame - lastDropFrame) >= BOMB_DROP_RATE &&
               this.y > 0 && this.y < BOARD_HEIGHT - 100; // Only drop when in visible area
    }
    
    public void setBombsList(List<Bomb> bombs) {
        this.bombs = bombs;
    }
    
    private void dropBomb() {
        if (bombs == null) {
            return; // Can't drop if no reference to scene bombs list
        }
        
        // Drop from the front tip of the ship (bottom center)
        int bombX = this.x + (getImage().getWidth(null) / 2);
        int bombY = this.y + getImage().getHeight(null); // Bottom of the ship (tip/front)
        
        // Create and add new bomb directly to Scene's list
        Bomb bomb = new Bomb(bombX, bombY);
        bombs.add(bomb);
    }
    
    @Override
    public void die() {
        super.die();
        // No need to clear bombs as they are managed by Scene1
    }
    
    // Bomb enemies are strategic units that stay back
    public boolean isStrategicUnit() {
        return true;
    }
    
    // Get current frame for timing calculations
    public int getCurrentFrame() {
        return currentFrame;
    }
    
    // Get time since last bomb drop
    public int getFramesSinceLastDrop() {
        return currentFrame - lastDropFrame;
    }
}
