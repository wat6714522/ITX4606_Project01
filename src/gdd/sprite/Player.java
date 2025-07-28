package gdd.sprite;

import static gdd.Global.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import gdd.ClippingAnimation;

public class Player extends Sprite {

    private static final int START_X = 270;
    private static final int START_Y = 540;
    private int width;
    private int currentSpeed = 2;

    private boolean multiShot = false;
    private boolean armored = false;

    private int multiShotFramesRemaining = 0;
    private int armorFramesRemaining = 0 ;

    private boolean isFlying = false;
    private boolean isExploding = false; 
    private boolean explosionStarted = false;  
    
    private ClippingAnimation flyingAnimation; 
    private ClippingAnimation deadAnimation; 

    public Player() {
        initPlayer();
        initAnimation();
    }

    private void initPlayer() {
        var ii = new ImageIcon(IMG_PLAYER01);

        // Scale the image to use the global scaling factor
        var scaledImage = ii.getImage().getScaledInstance(128 * SCALE_FACTOR,
                128 * SCALE_FACTOR,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);

        setX(START_X);
        setY(START_Y);
    }

    private void initAnimation() {
        flyingAnimation = createAnimation(ANIM_PLAYER01_FLYING, 128, 128, 10, 10, 10, true);
        // Use bomb explosion animation for player death
        deadAnimation = createAnimation(ANIM_PLAYER01_EXPLOSION, 128, 128, 9, 9, 10, false);
    }



    public int getSpeed() {
        return currentSpeed;
    }

    public int setSpeed(int speed) {
        if (speed < 1) {
            speed = 1; // Ensure speed is at least 1
        }
        this.currentSpeed = speed;
        return currentSpeed;
    }

    public boolean hasMultiShot(){
        return multiShot;
    }

    public void setMultiShot(boolean multiShot) {
        this.multiShot = multiShot;
    }

    public void activateMultiShot(int frames){
        this.multiShot = true; 
        this.multiShotFramesRemaining = frames;
    }

    public boolean isArmored(){
        return armored;
    }

    public void activateArmor(int frames){
        this.armored = true; 
        this.armorFramesRemaining = frames; 
    }

    public int getArmorFramesRemaining(){
        return armorFramesRemaining; 
    }

    public boolean isExploding() {
        return isExploding;
    }

    public boolean isFlying(){
        return isFlying; 
    }

    public boolean hasExplosionStarted() {
        return explosionStarted;
    }   

    public void explode(){
        if(!isExploding && !explosionStarted) {
            isExploding = true;
            explosionStarted = true;

            flyingAnimation.pause();
            deadAnimation.playOnce();

            this.x -= 16;
            this.y -= 16;
        }
    }

    @Override
    public void act() {
        if(isExploding){
            deadAnimation.updateOnce(); 
            updateImageFromAnimation(deadAnimation, 128, 128, IMG_PLAYER01);
            
            if(deadAnimation.isFinished()){
                this.die(); 
            }
            
        }else{
            flyingAnimation.update(); 
            updateImageFromAnimation(flyingAnimation, 128, 128, IMG_PLAYER01);

            if(multiShot && multiShotFramesRemaining > 0){
                multiShotFramesRemaining--; 
                if(multiShotFramesRemaining <= 0){
                    multiShot = false; 
                }
            }

            if(armored && armorFramesRemaining > 0){
                armorFramesRemaining--; 
                if(armorFramesRemaining <= 0){
                    armored = false;
                }
            }
            
            x += dx;

            if (x <= 2) {
                x = 2;
            }

            if (x >= BOARD_WIDTH - 2 * width) {
                x = BOARD_WIDTH - 2 * width;
            }
        }

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -currentSpeed;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = currentSpeed;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
}
