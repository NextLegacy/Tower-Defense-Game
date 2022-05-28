package engine.scene;

import java.util.ArrayList;

import engine.scene.Collisions.LayerCollision;
import engine.window.RenderLayer;

public class Scene extends Activateable
{
    public final static Scene EMPTY_SCENE = new Scene(0, new LayerCollision[] { });
    private ArrayList<GameObject> gameObjects;
    
    public final Collisions collisions;
    
    private static final int FRAME_DELAY = 50;
    
    public Scene(int collisionLayerCount, LayerCollision[] layerCollisions)
    {
        deactivate();
        
        gameObjects = new ArrayList<GameObject>();
        
        // setup collisions
        collisions = new Collisions(collisionLayerCount, layerCollisions);
    }
    
    public void init() {}
    
    public void addObject(GameObject o)
    {
        gameObjects.add(o);
        o.setScene(this);
        
        if(isActive()) 
            o.start();
    }
    
    public void removeObject(GameObject o)
    {
        gameObjects.remove(o);
        o.setScene(null);
    }
    
    public void start()
    {
        activate();
    }
    
    public void update(double deltaTime)
    {
        for(GameObject gameObject : gameObjects)
        {
            gameObject.update();
        }
        
        // collisions
        collisions.collisionsUpdate();
    }

    public void render(RenderLayer layer, double deltaTime)
    {
        for(GameObject gameObject : gameObjects)
        {
            gameObject.update();
        }
        
        collisions.collisionsUpdate();
    }
    
    @Override
    public void onActivate() 
    {
        for(GameObject gameObject : gameObjects)
        {
            gameObject.start();
        }
    }

    @Override
    public void onDeactivate() 
    {
        for(GameObject gameObject : gameObjects)
        {
            //gameObject.end(); //TODO: macht das sinn?
        }    
    }

    public void destroy()
    {
        deactivate();

        for(GameObject gameObject : gameObjects)
            gameObject.destroy();
    }
}