package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;
import gdd.ClippingAnimation;   

public class Missile extends Sprite {
    private static final int MISSILE_SPEED = 5;
    private static final int EXPLOSION_DAMAGE_RADIUS = 30; 

    private boolean destroyed; 
    private boolean exploding; 
    private boolean explodsionStarted; 

    private ClippingAnimation flyingAnimation; 
    private ClippingAnimation explosionAnimation; 


    public Missile(int x, int y) {
        initMissile(x, y);
        initAnimation();
    }

    private void initMissile(int x, int y) {
        this.x = x;
        this.y = y;

        var ii = new ImageIcon(IMG_MISSILE01);
        var scaledImage = ii.getImage().getScaledInstance(
            ii.getIconWidth() * SCALE_FACTOR,
            ii.getIconHeight() * SCALE_FACTOR,
            java.awt.Image.SCALE_SMOOTH
        );
        setImage(scaledImage);
    }

    private void initAnimation() {
        flyingAnimation = createAnimation(ANIM_MISSILE01_FLYING, 128, 128, 10, 10, 3, true);
        explosionAnimation = createAnimation(ANIM_MISSILE01_EXPLOSION, 128,128,  9, 9, 3, false);
    }

    @Override
    public void act() {
        if(exploding){
            explosionAnimation.updateOnce();
            updateImageFromAnimation(explosionAnimation, 128, 128, IMG_MISSILE01);

            if(explosionAnimation.isFinished()){
                this.die(); // Remove the missile after explosion animation finishes
            }
        }else{
            flyingAnimation.update();
            updateImageFromAnimation(flyingAnimation, 128, 128, IMG_MISSILE01);
            
            this.y += MISSILE_SPEED;
            if(this.y>BOARD_HEIGHT){
                this.die(); // Remove the missile if it goes off-screen
            }
        }
    }

    public int getSpeed() {
        return MISSILE_SPEED;
    } 

    public int getExplosionDamageRadius() {
        return EXPLOSION_DAMAGE_RADIUS;
    }  

    public boolean isExplosive() {
        return true; // Indicates that this missile can cause an explosion
    }   

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean isExploding() {
        return exploding;
    }

    public boolean hasExplosionStarted() {
        return explodsionStarted;
    }

    public void explode(){
        if(!exploding && !explodsionStarted) {
            exploding = true;
            explodsionStarted = true;

            flyingAnimation.pause();
            explosionAnimation.playOnce();

            this.x -= 16;
            this.y -= 16;
        }
    }

}
