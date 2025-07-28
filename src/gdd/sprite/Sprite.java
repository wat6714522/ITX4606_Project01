package gdd.sprite;

import static gdd.Global.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.plaf.basic.BasicSliderUI.ScrollListener;
import gdd.ClippingAnimation;

abstract public class Sprite{

    protected boolean visible;
    protected Image image;
    protected boolean dying;
    protected int visibleFrames = 10;

    protected int x;
    protected int y;
    protected int dx;

    public Sprite() {
        visible = true;
    }

    abstract public void act();

    public boolean collidesWith(Sprite other) {
        if (other == null || !this.isVisible() || !other.isVisible()) {
            return false;
        }
        return this.getX() < other.getX() + other.getImage().getWidth(null)
                && this.getX() + this.getImage().getWidth(null) > other.getX()
                && this.getY() < other.getY() + other.getImage().getHeight(null)
                && this.getY() + this.getImage().getHeight(null) > other.getY();
    }

    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void visibleCountDown() {
        if (visibleFrames > 0) {
            visibleFrames--;
        } else {
            visible = false;
        }
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public boolean isDying() {
        return this.dying;
    }

    protected ClippingAnimation createAnimation(String spritePath, int frameWidth, int frameHeight, int framesPerRow, int totalFrames, int speed, boolean autoPlay){
        ClippingAnimation animation = new ClippingAnimation(spritePath, frameWidth, frameHeight, framesPerRow, totalFrames);
        animation.setAnimationSpeed(speed);

        if(autoPlay){
            animation.play();
        }

        return animation;
    }

    protected void updateImageFromAnimation(ClippingAnimation animation, int width, int height, String fallbackImagePath){
        var currentFrameImage = animation.getCurrentFrameImage();

        if(currentFrameImage != null){
            var scaledImage = currentFrameImage.getScaledInstance(width * SCALE_FACTOR, height * SCALE_FACTOR , java.awt.Image.SCALE_SMOOTH); 
            setImage(scaledImage);
        }else{
            var ii = new ImageIcon(fallbackImagePath); 
            var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR, ii.getIconHeight() * SCALE_FACTOR, java.awt.Image.SCALE_SMOOTH);
            setImage(scaledImage);
        }
    }
}
