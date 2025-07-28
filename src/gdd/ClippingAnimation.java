package gdd;

import java.awt.Image;
import javax.swing.ImageIcon; 
import java.awt.Rectangle; 

public class ClippingAnimation {
    private Image spriteSheet; 
    private Rectangle[] frameRects; 
    private int currentFrame; 
    private int frameCount; 
    private int animationSpeed; 
    private int animationCounter; 
    private boolean isPlaying; 


    public ClippingAnimation(String spriteSheetPath, int frameWidth, int frameHeight, 
                            int framePerRow, int totalFrames ){
        
        var ii = new ImageIcon(spriteSheetPath);
        this.spriteSheet = ii.getImage(); 

        this.frameCount = totalFrames;
        this.frameRects = new Rectangle[totalFrames]; 
        this.currentFrame = 0; 
        this.animationSpeed = 10;
        this.animationCounter = 0; 
        this.isPlaying = false; 
        
        for(int i = 0; i < totalFrames; i++){
            int row = i / framePerRow; 
            int col = i % framePerRow; 

            frameRects[i] = new Rectangle(
                col * frameWidth, 
                row * frameHeight, 
                frameWidth, 
                frameHeight
            ); 
        }
    }
    
    public void update(){
        if(!isPlaying || frameCount <= 1){
            return; 
        }

        animationCounter++; 

        if(animationCounter >= animationSpeed){
            animationCounter = 0; 
            currentFrame = (currentFrame + 1) % frameCount; 
        }
    }

    public void play(){
        isPlaying = true; 
    }

    public void pause(){
        isPlaying = false; 
    }

    public void reset(){
        currentFrame = 0; 
        animationCounter = 0; 
    }

    public void playOnce(){
        reset();
        isPlaying = true; 
    }

    public boolean isFinished(){
        return !isPlaying && currentFrame == frameCount - 1; 
    }

    public void setAnimationSpeed(int speed){
        this.animationSpeed = speed; 
    }

    public int getAnimationSpeed(){
        return this.animationSpeed; 
    }

    public Rectangle getCurrentFrameRect(){
        if(frameRects == null || currentFrame >= frameRects.length){
            return null; 
        }

        return frameRects[currentFrame];
    }

    public Image getCurrentFrameImage(){
        if(frameRects == null || currentFrame >= frameRects.length || spriteSheet == null){
            return null;
        }

        Rectangle rect = frameRects[currentFrame]; 
        java.awt.image.BufferedImage frameImage = new java.awt.image.BufferedImage(rect.width, rect.height, java.awt.image.BufferedImage.TYPE_INT_ARGB); 

        java.awt.Graphics2D g2d = frameImage.createGraphics(); 
        g2d.drawImage(spriteSheet, 0, 0, rect.width,rect.height,rect.x, rect.y, rect.x+rect.width, rect.y + rect.height, null); 
        g2d.dispose();

        return frameImage;
    }

    public int getCurrentFrame(){
        return currentFrame;
    }

    public Image getSpriteSheet(){
        return spriteSheet;
    }

    public boolean isPlaying(){
        return isPlaying; 
    }

    public void updateOnce(){
        if(!isPlaying || frameCount <= 1){
            return; 
        }

        animationCounter++;

        if(animationCounter >= animationSpeed){
            animationCounter = 0;
            currentFrame++;

            if(currentFrame >= frameCount){
                currentFrame = frameCount - 1; 
                isPlaying = false;
            }
        }
    }
}
