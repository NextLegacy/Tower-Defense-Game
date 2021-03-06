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
    private AffineTransform centeredTransform;

    private Sprite()
    {
        position = Vector.zero();
        rotation = 0;
        size = Vector.zero();

        ratio = Vector.one();

        lastPosition = Vector.zero();
        lastRotation = 0;
        lastSize = Vector.zero();

        transform = new AffineTransform();
        centeredTransform = new AffineTransform();
    }

    public Sprite(BufferedImage image)
    {
        this();

        setImage(image);

        size = new Vector(image.getWidth(), image.getHeight());
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

    public void setImage(BufferedImage image)
    {
        this.image = image;
    }

    public BufferedImage image() { return image; }

    public Sprite deriveSprite()
    {
        Sprite sprite = new Sprite(image);

        sprite.position = position.clone();
        sprite.rotation = rotation;
        sprite.size = size.clone();

        return sprite;
    }

    public Sprite setPosition(Vector position) { this.position = position; return this; }

    public Sprite setRotation(double rotation) { this.rotation = rotation; return this;}

    public Sprite setSize(Vector size) { this.size = size; return this; }

    public AffineTransform transform()
    {
        updateTransform(transform, position);

        return transform;
    }

    public AffineTransform centeredTransform()
    {
        updateTransform(centeredTransform, position.sub(size.div(2)));

        return centeredTransform;
    }

    private void updateTransform(AffineTransform transform, Vector position)
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

    public static Sprite ohno() { return new Sprite("ohno"); }
}
