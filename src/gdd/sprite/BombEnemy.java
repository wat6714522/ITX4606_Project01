package gdd.sprite;

import static gdd.Global.*;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    private void rotateTowardsPlayer() {
        if (player == null || !player.isVisible()) {
            return;
        }
        
        // Calculate angle to player
        int playerX = player.getX();
        int playerY = player.getY();
        int centerX = this.x + (getImage().getWidth(null) / 2);
        int centerY = this.y + (getImage().getHeight(null) / 2);
        
        double angle = Math.atan2(playerY - centerY, playerX - centerX);
        
        // Rotate the image
        var ii = new ImageIcon(IMG_PIRATE02);
        var originalImage = ii.getImage().getScaledInstance(
            ii.getIconWidth() * SCALE_FACTOR,
            ii.getIconHeight() * SCALE_FACTOR,
            java.awt.Image.SCALE_SMOOTH
        );
        
        Image rotatedImage = rotateImage(originalImage, angle);
        setImage(rotatedImage);
    }
    
    private Image rotateImage(Image image, double angle) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        
        // If image dimensions are not available, use default size
        if (width <= 0 || height <= 0) {
            width = 128;  // Default to our standard sprite size
            height = 128;
        }
        
        BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();
        
        // Set rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        // Rotate around the center of the image
        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, width / 2.0, height / 2.0);
        g2d.setTransform(transform);
        
        // Draw the image
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        
        return rotatedImage;
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
