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
    
    private Vector lastPosition;
    private double lastRotation;
    private Vector lastSize;

    private AffineTransform transform;

    public Sprite()
    {
        position = Vector.zero();
        rotation = 0;
        size = Vector.zero();

        ratio = Vector.one();

        lastPosition = Vector.zero();
        lastRotation = 0;
        lastSize = Vector.zero();

        transform = new AffineTransform();
    }

    public Sprite(String name)
    {
        this();

        setImage(name);

        size = new Vector(image.getWidth(), image.getHeight());
    }

    public void setImage(String name)
    {
        image = Images.getImage(name);
    }

    public BufferedImage image() { return image; }

    public AffineTransform transform()
    {
        updateTransform();

        return transform;
    }

    private void updateTransform()
    {
        if (position == lastPosition && rotation == lastRotation && size.equals(lastSize))
            return;

        lastPosition = position;
        lastRotation = rotation;
        lastSize = size;

        ratio.setX(size.x / image.getWidth())
             .setY(size.y / image.getHeight());

        transform.setToTranslation(position.x, position.y);

        transform.rotate(rotation, size.x / 2, size.y / 2);
        
        transform.scale(ratio.x, ratio.y);
    }
}
