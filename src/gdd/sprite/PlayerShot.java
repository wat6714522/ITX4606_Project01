package gdd.sprite;

import static gdd.Global.*;
import gdd.ClippingAnimation;
import javax.swing.ImageIcon;

public class PlayerShot extends Sprite {

    private static final int H_SPACE = 20;
    private static final int V_SPACE = 1;
    private static final int PAUSE_AT_FRAME = 2; 

    private ClippingAnimation shotAnimation; 
    private boolean hasHitTarget = false;
    private boolean animationPaused = false; 
    private boolean animationComplete = false;
    private boolean animationStarted = false; 

    public PlayerShot(int x, int y) {
        initShot(x, y);
        initAnimation();
    }

    private void initShot(int x, int y) {

        var ii = new ImageIcon(IMG_PLAYER_SHOT);

        // Scale the image to use the global scaling factor
        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
                ii.getIconHeight() * SCALE_FACTOR, 
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);

        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }

    private void initAnimation(){
        shotAnimation = createAnimation(ANIM_PLAYER01_SHOT, 64, 64, 6, 6, 3, false); 
    }

    @Override
    public void act() {
        System.out.println("PlayerShot.act() - shotAnimation null: " + (shotAnimation == null) + ", animationStarted: " + animationStarted + ", animationComplete: " + animationComplete);
        if(shotAnimation != null && animationStarted && !animationComplete){
            System.out.println("PlayerShot act() - frame: " + shotAnimation.getCurrentFrame() + ", paused: " + animationPaused + ", hitTarget: " + hasHitTarget + ", isPlaying: " + shotAnimation.isPlaying());
            if(!animationPaused){
                shotAnimation.update();

                if(shotAnimation.getCurrentFrame() >= PAUSE_AT_FRAME && !hasHitTarget){
                    System.out.println("PlayerShot - pausing animation at frame " + shotAnimation.getCurrentFrame());
                    shotAnimation.pause();
                    animationPaused = true;
                }
            }else if(hasHitTarget){
                System.out.println("PlayerShot - hit target, continuing animation");
                shotAnimation.play();
                shotAnimation.updateOnce();
                animationPaused = false;

                if(shotAnimation.isFinished()){
                    System.out.println("PlayerShot - animation finished, shot dying");
                    animationComplete = true; 
                    this.die();
                }
            }
            updateImageFromAnimation(shotAnimation, 64, 64, IMG_PLAYER01);
        }
        // Move the shot down the screen
        this.y += 2; // Move down by 2 pixels each frame

        // Check if the shot is out of bounds
        if (this.y > BOARD_HEIGHT) {
            this.die(); // Remove the shot if it goes off-screen
        }
    }

    public void onHitTarget(){
        System.out.println("PlayerShot.onHitTarget() called - setting hasHitTarget to true");
        hasHitTarget = true;
    }

    public boolean isAnimationComplete(){
        return animationComplete;
    }

    public boolean hasHitTarget(){
        return hasHitTarget;
    }

    public boolean isAnimationStarted(){
        return animationStarted;
    }

    public void startShotAnimation(){
        System.out.println("PlayerShot.startShotAnimation() called");
        if(shotAnimation != null && !animationStarted){
            shotAnimation.reset(); 
            shotAnimation.play();
            animationStarted = true; 
            System.out.println("PlayerShot animation started - isPlaying: " + shotAnimation.isPlaying());
        } else {
            System.out.println("PlayerShot animation NOT started - shotAnimation null: " + (shotAnimation == null) + ", already started: " + animationStarted);
        }
    }
}
