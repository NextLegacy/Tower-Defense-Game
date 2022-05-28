package engine.scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import engine.scene.Collisions.LayerCollision;
import engine.window.RenderLayer;
import engine.window.Window;

public class Scene
{
    
    private ArrayList<GameObject> objects;
    private boolean started;
    
    public final Window window;
    public final Collisions collisions;
    
    private static final int FRAME_DELAY = 50;
    
    private static final Color BG_COLOR = new Color(255, 255, 255);
    
    public Scene(Window window, int collisionLayerCount, LayerCollision[] layerCollisions)
    {
        started = false;
        
        objects = new ArrayList<GameObject>();
        
        this.window = window;
        
        // setup collisions
        collisions = new Collisions(collisionLayerCount, layerCollisions);
    }
    
    public void addObject(GameObject o)
    {
        objects.add(o);
        o.setScene(this);
        
        if(started) o.start();
    }
    
    public void removeObject(GameObject o)
    {
        objects.remove(o);
        o.setScene(null);
    }
    
    public void startScene()
    {
        started = true;
        
        // start game objects
        for(GameObject o : objects)
        {
            o.start();
        }
        
        // start updates
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                sceneUpdate();
            }
        }, 0, FRAME_DELAY);
    }
    
    public void sceneUpdate()
    {
        // update gameobjects
        for(GameObject o : objects)
        {
            o.update();
        }
        
        // collisions
        collisions.collisionsUpdate();
        
        // render scene
        window.render((RenderLayer layer) ->
        {
            Graphics2D graphics = layer.getGraphics();
            if(layer.name.equals("Background")) // TODO@El_Tigere: Hintergrund als GameObject hinzufügen (vielleicht)
            {
                graphics.setColor(BG_COLOR);
                graphics.fillRect(0, 0, window.getWidth(), window.getHeight());
                //System.out.println(window.getHeight());
            }
            else
            {
                graphics.fillRect(0, 0, window.getWidth(), window.getHeight()); // TODO@NextLegacy: sollte das notwendig sein (in der Scene die layers jeden Frame clearen)
            }
            for(GameObject o : objects)
            {
                o.render(layer);
            }
        });
    }
    
}