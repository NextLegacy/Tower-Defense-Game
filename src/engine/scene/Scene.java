package engine.scene;

import java.util.ArrayList;
import java.util.Stack;

import engine.Engine;
import engine.scene.Collisions.LayerCollision;
import engine.window.RenderLayer;

public class Scene extends Activateable
{
    protected ArrayList<GameObject> gameObjects;
    
    protected final Collisions collisions;
    
    protected Engine engine;
    
    private Stack<GameObject> newObjects;
    private Stack<GameObject> removedObjects;
    
    public Scene(int collisionLayerCount, LayerCollision[] layerCollisions)
    {
        deactivate();
        
        gameObjects = new ArrayList<GameObject>();
        removedObjects = new Stack<GameObject>();
        newObjects = new Stack<GameObject>();
        
        // setup collisions
        collisions = new Collisions(collisionLayerCount, layerCollisions);
    }
    
    public void init() { }
    
    public final void setEngine(Engine engine) { this.engine = engine; }
    
    public final void addObject(GameObject gameObject)
    {
        newObjects.push(gameObject);
    }
    
    public final void removeObject(GameObject gameObject)
    {
        removedObjects.push(gameObject);
    }
    
    public void start()
    {
        activate();

        for(GameObject gameObject : gameObjects)
        {
            if(gameObject.isActive())
                gameObject.start();
        } // TODO: fix ConcurrentModificationException
        
        /*for(int i = 0; i < gameObjects.size(); i++)
        {
            if(gameObjects.get(i).isActive())
                gameObjects.get(i).start();
        }*/
    }
    
    public void update(double deltaTime)
    {
        for(GameObject gameObject : gameObjects)
        {
            if(gameObject.isActive())
                gameObject.update(deltaTime);
        } // TODO: fix ConcurrentModificationException
        
        /*for(int i = 0; i < gameObjects.size(); i++)
        {
            if(gameObjects.get(i).isActive())
                gameObjects.get(i).update(deltaTime);
        }*/
        
        // add new game objects
        while(!newObjects.empty())
        {
            GameObject g = newObjects.pop();
            
            gameObjects.add(g);
            g.setScene(this.engine, this);
            g.activate();
            
            if(isActive())
                g.start();
        }
        
        // remove destroyed game objects
        while(!removedObjects.empty())
        {
            GameObject g = removedObjects.pop();
            
            gameObjects.remove(g);
            g.setScene(this.engine, null);
        }
        
        // collisions
        collisions.collisionsUpdate();
    }

    public void render(RenderLayer layer, double deltaTime)
    {
        for(GameObject gameObject : gameObjects)
        {
            if (!gameObject.isActive())
                continue;

            gameObject.render(layer, deltaTime);

            layer.resetGraphics();
        }
    }
    
    public final void destroy()
    {
        deactivate();

        for(GameObject gameObject : gameObjects)
            gameObject.destroy();
    }
}