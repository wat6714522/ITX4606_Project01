package gdd.sprite;

import static gdd.Global.*;
import java.util.List;
import java.util.Random; 
import javax.swing.ImageIcon;

public class MissileEnemy extends Enemy {

    private static final int DETECTION_RANGE = 150; 
    private static final int MISSILE_COOLDOWN = 120; 
    private static final int MISSILE_CHANCE = 3; // 1 in 3 chance to fire a missile

    private int missileCoolDown = 0; // Cooldown for missile firing
    private Random random = new Random();
    private Player player; // Reference to the player 
    private List<Missile> missiles; 

    public MissileEnemy(int x, int y) {
        super(x, y);
        initMissileEnemy(x, y);
    }

    private void initMissileEnemy(int x, int y) {
        this.x = x;
        this.y = y;

        // Set the image for the missile enemy
        var ii = new ImageIcon(IMG_PIRATE02);
        var scaledImage = ii.getImage().getScaledInstance(
            ii.getIconWidth() * SCALE_FACTOR,
            ii.getIconHeight() * SCALE_FACTOR,
            java.awt.Image.SCALE_SMOOTH
        );
        setImage(scaledImage);
    }

    @Override
    public void act() {
        this.y += 1; // Move downwards

        if(missileCoolDown > 0) {
            missileCoolDown--;
        }

        if(player != null && canSeePlayer() && missileCoolDown <= 0) {
            if(random.nextInt(MISSILE_CHANCE) == 0) { // Random chance to fire a missile
                fireMissile();
                missileCoolDown = MISSILE_COOLDOWN; // Reset cooldown
            }
        }
    }

    public void setTargetPlayer(Player player) {
        this.player = player;
    }

    private boolean canSeePlayer(){
        if(player == null || player.isVisible()){
            return false;
        }

        int playerX = player.getX();
        int playerY = player.getY();

        double distance = Math.sqrt(
            Math.pow(playerX -this.x ,2) + Math.pow(playerY - this.y, 2)
        );
        return distance <= DETECTION_RANGE && playerY > this.y; 
    }

    public void setMissilesList(List<Missile> missiles) {
        this.missiles = missiles;
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    private void fireMissile(){
        if(missiles == null) {
            return; // No missiles list set
        }

        int missileX = this.x + (getImage().getWidth(null) / 2);
        int missileY = this.y + getImage().getHeight(null);

        Missile missile = new Missile(missileX, missileY);
        missiles.add(missile); // Add the missile to the list
    }

    @Override
    public void die() {
        super.die(); // Call the parent die method
        // Additional logic for missile enemy death can be added here
    }   
    
}
