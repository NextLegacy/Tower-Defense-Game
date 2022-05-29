package engine.scene;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.Engine;
import engine.math.Vector;
import engine.window.InputListener;
import engine.window.RenderLayer;
import engine.window.Window;

public abstract class GameObject extends Activateable
{
    public Vector position;
    public Vector size;
    public double rotation;

    protected BufferedImage sprite;

    //private Vector rotation;
    
    protected Scene scene;
    
    protected Engine engine;
    protected Window window;
    protected InputListener input;

    public GameObject()
    {
        position = Vector.zero();
        rotation = 0.0d;
        size = Vector.zero();
    }
    
    public void setScene(final Engine engine, Scene scene)
    {
        this.scene = scene;
        this.engine = engine;
        this.window = engine.getWindow();
        this.input = engine.getInputListener();
    }
    
    public void destroy()
    {
        deactivate();
        onDestroy();
        scene.removeObject(this);
    }
    
    public void start() { }
    
    public void update(double deltaTime) { }
    
    public void onDestroy() { }

    public void render(RenderLayer layer, double deltaTime) { }

    public void renderSprite(RenderLayer layer, BufferedImage sprite)
    {
        if (sprite == null) 
            return;

        Graphics2D g2dSprite = sprite.createGraphics();
        
        g2dSprite.rotate(this.rotation);
            
        layer.graphics().drawImage(sprite, (int) (position.x - sprite.getWidth() / 2), (int) (position.y - sprite.getHeight() / 2), null); // TODO@El_Tigere: ist halfSize bei den collisions sinvoll? wenn ja sollte man das auch hier machen

        g2dSprite.rotate(-this.rotation);
    }
}