package engine.scene;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.math.Vector;

@Deprecated
public interface SpriteRenderer
{
    public BufferedImage sprite();
    
    //public String layer();

    public Vector position();
    public double rotation();

    default void renderSprite(Graphics2D g2d)
    {
        BufferedImage sprite = sprite();
        if (sprite == null) return;
        
        Graphics2D g2dImage = sprite.createGraphics();

        g2dImage.rotate(this.rotation());
        
        g2d.drawImage(sprite, (int) (position().x - sprite.getWidth() / 2), (int) (position().y - sprite.getHeight() / 2), null);

        g2dImage.rotate(-this.rotation());
    }
}
