package survivor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Collidable {
    
    Position currentPosition;

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }
    BufferedImage image;
    
    public Collidable(Position initialPos , BufferedImage image) {
        currentPosition = initialPos;
        this.image = image;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }
    
    public int getWidth() {
        return this.image.getWidth();
    }
    
    public int getHeight() {
        return this.image.getHeight();
    }
    
    public void draw(Graphics g) {
        g.drawImage(this.image,currentPosition.getX(),currentPosition.getY(),null);
    }
    
    public boolean isCollided(Collidable other) {
        Collidable left,right,top,bottom;
        if (other.getCurrentPosition().getX() > this.currentPosition.getX()) {
            left = this;
            right = other;
        } else {
            left = other;
            right = this;
        }
        
        if (other.getCurrentPosition().getY() > this.currentPosition.getY()) {
            top = this;
            bottom = other;
        } else {
            top = other;
            bottom = this;
        }
        
        boolean isCollidingX = (left.getCurrentPosition().getX() + left.getWidth() > right.getCurrentPosition().getX());
        boolean isCollidingY = (top.getCurrentPosition().getY() + top.getHeight() > bottom.getCurrentPosition().getY());
        
        return (isCollidingX && isCollidingY);
    }
}
