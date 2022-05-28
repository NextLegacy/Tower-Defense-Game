package engine.scene;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import engine.scene.Collisions.LayerCollision;
import engine.window.RenderLayer;

public class Scene
{
    
    private ArrayList<GameObject> objects;
    private boolean started;
    
    public final Collisions collisions;
    
    private static final int FRAME_DELAY = 50;
    
    public Scene(int collisionLayerCount, LayerCollision[] layerCollisions)
    {
        started = false;
        
        objects = new ArrayList<GameObject>();
        
        // setup collisions
        collisions = new Collisions(collisionLayerCount, layerCollisions);
    }
    
    public void init() {}
    
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
    
    public void sceneStart()
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
                //sceneUpdate();
            }
        }, 0, FRAME_DELAY);
    }
    
    public void sceneUpdate(RenderLayer layer)
    {
        // update gameobjects
        for(GameObject o : objects)
        {
            o.update();
        }
        
        // collisions
        collisions.collisionsUpdate();
        
        // render scene
        for(GameObject o : objects)
        {
            o.render(layer);
        }
    }
    
    public void destroy()
    {
        started = false;
        for(GameObject o : objects) o.destroy();
    }
    
}