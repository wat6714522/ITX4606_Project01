package gdd.sprite;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.List;
import java.util.Random;
import static gdd.Global.*;

public class Boss extends Enemy {
    
    private int health;
    private final int maxHealth = 30;
    private boolean isFlashing = false;
    private int flashTimer = 0;
    
    // Shooting related variables
    private static final int shootCooldown = 30; // 30 frames = 0.5 seconds at 60 FPS
    private static final int shotChance = 15; // 15% chance to shoot each frame when cooldown is 0
    private int shotCoolDown = 0;
    private Random random = new Random();
    private Player player;
    private List<EnemyShot> enemyShots;

    public Boss(int x, int y) {
        super(x, y);
        this.health = maxHealth;
        initBoss();
    }

    private void initBoss() {
        ImageIcon ii = new ImageIcon(IMG_BOSS01);
        // Scale the boss image to be smaller
        var scaledImage = ii.getImage().getScaledInstance(
            (int)(ii.getIconWidth() * SCALE_FACTOR * 0.75), // Make boss 0.75x smaller (half of original 1.5x)
            (int)(ii.getIconHeight() * SCALE_FACTOR * 0.75),
            java.awt.Image.SCALE_SMOOTH
        );
        setImage(scaledImage);
        setX(getX() - getImage().getWidth(null) / 2);
    }

    @Override
    public void act() {
        // Simple horizontal back and forth movement
        if (x >= BOARD_WIDTH - getImage().getWidth(null) || x <= 0) {
            dx = -dx;
        }
        x += dx;
        
        // Handle flash effect when hit
        if (isFlashing) {
            flashTimer--;
            if (flashTimer <= 0) {
                isFlashing = false;
            }
        }
        
        // Handle shooting
        if (shotCoolDown > 0) {
            shotCoolDown--;
        } else {
            if (player != null && player.isVisible()) {
                if (random.nextInt(100) < shotChance) {
                    shoot();
                    shotCoolDown = shootCooldown; // Reset cooldown
                }
            }
        }
    }
    
    public void takeDamage(int damage) {
        health -= damage;
        isFlashing = true;
        flashTimer = 10; // Flash for 10 frames
        
        System.out.println("Boss hit! Health: " + health + "/" + maxHealth);
        
        if (health <= 0) {
            setDying(true);
            System.out.println("Boss defeated!");
        }
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public boolean isFlashing() {
        return isFlashing;
    }

    public void setInitialMovement(int dx) {
        this.dx = dx;
    }
    
    // Setter methods for player and enemy shots
    public void setTarget(Player player) {
        this.player = player;
    }
    
    public void setEnemyShots(List<EnemyShot> enemyShots) {
        this.enemyShots = enemyShots;
    }
    
    // Shooting method
    private void shoot() {
        if (enemyShots == null) {
            return;
        }
        
        // Boss shoots from multiple positions (3 shots spread across its width)
        int bossWidth = getImage().getWidth(null);
        int bossHeight = getImage().getHeight(null);
        
        // Left shot
        int leftShotX = this.x + (bossWidth / 4);
        int leftShotY = this.y + bossHeight;
        EnemyShot leftShot = new EnemyShot(leftShotX, leftShotY);
        enemyShots.add(leftShot);
        
        // Center shot
        int centerShotX = this.x + (bossWidth / 2);
        int centerShotY = this.y + bossHeight;
        EnemyShot centerShot = new EnemyShot(centerShotX, centerShotY);
        enemyShots.add(centerShot);
        
        // Right shot
        int rightShotX = this.x + (3 * bossWidth / 4);
        int rightShotY = this.y + bossHeight;
        EnemyShot rightShot = new EnemyShot(rightShotX, rightShotY);
        enemyShots.add(rightShot);
        
        System.out.println("Boss fired 3 shots!");
    }
}
