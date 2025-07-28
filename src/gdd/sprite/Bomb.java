package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

import gdd.ClippingAnimation;

public class Bomb extends Sprite {

    private static final int BOMB_SPEED = 3;
    private static final int EXPLOSION_RADIUS = 50; // Radius of the explosion damage

    private boolean destroyed;
    private boolean exploding; 
    private boolean explodsionStarted; 

    private ClippingAnimation idleAnimation; 
    private ClippingAnimation explosionAnimation; 

    public Bomb(int x, int y) {
        initBomb(x, y);
        initAnimation();
    }

    private void initBomb(int x, int y) {

        setDestroyed(false);

        this.x = x;
        this.y = y;

        var ii = new ImageIcon(IMG_BOMB01);
        var scaledImage = ii.getImage().getScaledInstance(
            ii.getIconWidth() * SCALE_FACTOR,
            ii.getIconHeight() * SCALE_FACTOR,
            java.awt.Image.SCALE_SMOOTH
        );

        setImage(scaledImage);
    }

    private void initAnimation(){
        idleAnimation = createAnimation(ANIM_BOMB01_IDLE, 128, 128, 10, 10, 3, true); 

        explosionAnimation = createAnimation(ANIM_BOMB01_EXPLOSION, 128, 128, 9, 9, 1, false); 
    }

    public void setDestroyed(boolean destroyed) {

        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {

        return destroyed;
    }

    @Override
    public void act() {
        if(exploding){
            explosionAnimation.updateOnce();
            updateImageFromAnimation(explosionAnimation, 128, 128, IMG_BOMB01);

            if(explosionAnimation.isFinished()){
                this.die();
            }
        }else{
            idleAnimation.update();
            updateImageFromAnimation(idleAnimation, 128, 128, IMG_BOMB01);

            // Move the bomb downwards
            this.y += BOMB_SPEED;
            // Check if the bomb is out of bounds
            if (this.y > BOARD_HEIGHT) {
                this.die(); // Remove the bomb if it goes off-screen
            }
        }
    }
    
        
    public int getSpeed() {
        return BOMB_SPEED;
    }
    
    public int getExplosionRadius() {
        return EXPLOSION_RADIUS;
    }
    
    public boolean hasExploded() {
        return destroyed;
    }
    
    // Bombs are dangerous area-effect weapons
    public boolean isAreaDamage() {
        return true;
    }
    
    // Check if bomb should explode when it hits something
    public boolean shouldExplodeOnContact() {
        return true;
    }

    public void explode(){
        if(!exploding){
            exploding = true; 
            
            idleAnimation.pause(); 
            explosionAnimation.play();

            this.x -= 16; 
            this.y -= 16; 
        }
    }

    public boolean isExploding(){
        return exploding;
    }
}
