package engine.scene;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.math.Vector;
import engine.window.RenderLayer;

public abstract class GameObject extends Activateable
{
    private Vector position;

    private double rotation;
    //private Vector rotation; //TODO: gibt es nicht eigentlich nur eine rotation?
    
    public BufferedImage sprite; // Das ist gut, ich hatte es falsch verstanden
    public String renderLayer;
    
    protected Scene scene; // controlled by the Scene class
    
    public GameObject()
    {
        position = Vector.zero();
        rotation = 0d; //new Vector(1, 0); //TODO: Vector.zero() ? 
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
    
    public void start() { }
    
    public void update() { }
    
    public void onDestroy() { }
    
    /*
     *TODO: Getter und Setter öfter nutzen; grund:
     *      - Getter und Setter sind gleich Schnell
     *      - Encapsulation ist ein wichtiger Aspekt
     *      - Gutes Design ist ein wichtiger Aspekt
     *      - Good Practice
     *      
     *      https://stackoverflow.com/a/18140897
     *      https://stackoverflow.com/a/24051675
     *  
     *      danke an:
     *      - Github Copilot
     *        zur tollen erstellung des Kommentars
     */

    public Vector position() { return this.position; }
    public double rotation() { return this.rotation; }

    public void render(RenderLayer layer)
    {
        if(sprite == null || layer.name() != renderLayer) 
            return;

        Graphics2D g2d = sprite.createGraphics();
        
        g2d.rotate(this.rotation());
            
        layer.graphics().drawImage(sprite, (int) (position.x - sprite.getWidth() / 2), (int) (position.y - sprite.getHeight() / 2), null); // TODO@El_Tigere: ist halfSize bei den collisions sinvoll? wenn ja sollte man das auch hier machen
    
        g2d.rotate(-this.rotation);
    }
}