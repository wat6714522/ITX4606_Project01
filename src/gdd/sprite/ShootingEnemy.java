package gdd.sprite;

import static gdd.Global.*;

import java.util.List; 
import java.util.Random; 
import javax.swing.ImageIcon; 


public class ShootingEnemy extends Enemy {
    private static final int detectionRange = 100; 
    private static final int shootCooldown = 10; 
    private static final int shotChance = 5; // 5% chance to shoot

    private int shotCoolDown = 0;
    private Random random = new Random();

    private Player player;
    private List<EnemyShot> enemyShots;

    public ShootingEnemy(int x, int y){
        super(x, y);
        initShootingEnemy(x, y);
    }

    private void initShootingEnemy(int x, int y){
        this.x = x; 
        this.y = y;

        var ii = new ImageIcon(IMG_PIRATE01); 
        var scaledImage = ii.getImage().getScaledInstance(
            ii.getIconWidth() * SCALE_FACTOR,
            ii.getIconHeight() * SCALE_FACTOR,
            java.awt.Image.SCALE_SMOOTH
        );
        setImage(scaledImage);
    }

    @Override 
    public void act() {
        this.y += 1;

        if(shotCoolDown > 0) {
            shotCoolDown--;
        } else {
            if (player != null && canSeePlayer()) {
                if (random.nextInt(100) < shotChance) {
                    shoot();
                    shotCoolDown = shootCooldown; // Reset cooldown
                }
            }
        }
    }

    public void setTarget(Player player) {
        this.player = player;
    }

    private boolean canSeePlayer(){
        if (player == null || !player.isVisible()) {
            return false;
        }

        int playerX = player.getX();
        int playerY = player.getY();   

        double distance = Math.sqrt(
            Math.pow(playerX - this.x, 2) + Math.pow(playerY - this.y, 2)
        ); 
        return distance <= detectionRange && playerY > this.y;
    }

    public void setEnemyShots(List<EnemyShot> enemyShots) {
        this.enemyShots = enemyShots;
    }   

    public List<EnemyShot> getEnemyShots() {
        return enemyShots;
    }   

    private void shoot(){
        if(enemyShots == null){
            return; 
        }

        int shotX = this.x + (getImage().getWidth(null) / 2);
        int shotY = this.y + (getImage().getHeight(null) / 2);

        EnemyShot shot = new EnemyShot(shotX, shotY);
        enemyShots.add(shot);
        }

    @Override
    public void die() {
        // Handle death logic for ShootingEnemy
        super.die();
        // Additional logic can be added here if needed
    }

}
