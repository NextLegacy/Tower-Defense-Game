package engine.scene;

import java.awt.image.BufferedImage;

import engine.math.Vector;
import engine.window.RenderLayer;

public abstract class GameObject
{
    
    public Vector position;
    public Vector rotation;
    
    public BufferedImage sprite; // TODO: das aendern
    public String renderLayer;
    
    protected Scene scene; // controlled by the Scene class
    
    public GameObject()
    {
        position = new Vector(0, 0);
        rotation = new Vector(1, 0);
    }
    
    public void setScene(Scene scene)
    {
        this.scene = scene;
    }
    
    public void destroy()
    {
        onDestroy();
        scene.removeObject(this);
    }
    
    public void start() {}
    
    public void update() {}
    
    public void onDestroy() {}
    
    public void render(RenderLayer layer)
    {
        if(sprite != null && layer.name.equals(renderLayer))
        {
            layer.getGraphics().drawImage(sprite, (int) (position.x - sprite.getWidth() / 2), (int) (position.y - sprite.getHeight() / 2), null); // TODO@El_Tigere: ist halfSize bei den collisions sinvoll? wenn ja sollte man das auch hier machen
        }
    }
    
}