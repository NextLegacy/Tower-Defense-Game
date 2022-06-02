package engine.utils;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import engine.math.Vector;

public class Sprite 
{
    private BufferedImage image;
    
    public Vector position;
    public double rotation;
    public Vector size;

    private Vector ratio;
    
    private double lastRotation;
    private Vector lastSize;

    private AffineTransform transform;

    public Sprite(String name)
    {
        image = Images.getImage(name);
        
        transform = new AffineTransform();

        position = new Vector(0, 0);
        rotation = 0;
        size = new Vector(image.getWidth(), image.getHeight());

        ratio = new Vector(1, 1);
    }

    public void setImage(String name)
    {
        image = Images.getImage(name);
        size = new Vector(image.getWidth(), image.getHeight());
    }

    public BufferedImage image() { return image; }

    public AffineTransform transform()
    {
        updateTransform();

        return transform;
    }

    private void updateTransform()
    {
        if (rotation == lastRotation && size.equals(lastSize))
            return;
        
        lastRotation = rotation;
        lastSize = size;
        
        //this.transform = AffineTransform.getTranslateInstance(position.x, position.y);

        ratio.setX(size.x / image.getWidth())
             .setY(size.y / image.getHeight());

        //transform.setToIdentity();
        transform.setToTranslation(position.x, position.y);
        transform.rotate(rotation, size.x / 2, size.y / 2);
        transform.scale(ratio.x, ratio.y);
    }
}
