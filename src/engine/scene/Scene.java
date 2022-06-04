package engine.scene;

import java.util.ArrayList;

import engine.Engine;
import engine.scene.Collisions.LayerCollision;
import engine.window.RenderLayer;

public class Scene extends Activateable
{
    protected ArrayList<GameObject> gameObjects;
    
    protected final Collisions collisions;
    
    protected Engine engine;

    public Scene(int collisionLayerCount, LayerCollision[] layerCollisions)
    {
        deactivate();
        
        gameObjects = new ArrayList<>();
        
        // setup collisions
        collisions = new Collisions(collisionLayerCount, layerCollisions);
    }
    
    public void init() { }
    
    public final void setEngine(Engine engine) { this.engine = engine; }
    
    public final void addObject(GameObject gameObject)
    {
        gameObjects.add(gameObject);
        gameObject.setScene(this.engine, this);
        gameObject.activate();
        
        if(isActive()) 
            gameObject.start();
    }
    
    public final void removeObject(GameObject gameObject)
    {
        gameObjects.remove(gameObject);
        gameObject.setScene(this.engine, null);
    }
    
    public void start()
    {
        activate();

        /*for(GameObject gameObject : gameObjects)
        {
            if(gameObject.isActive())
                gameObject.start();
        }*/ // TODO: fix ConcurrentModificationException
        
        for(int i = 0; i < gameObjects.size(); i++)
        {
            if(gameObjects.get(i).isActive())
                gameObjects.get(i).start();
        }
    }
    
    public void update(double deltaTime)
    {
        /*for(GameObject gameObject : gameObjects)
        {
            if(gameObject.isActive())
                gameObject.update(deltaTime);
        }*/ // TODO: fix ConcurrentModificationException
        
        for(int i = 0; i < gameObjects.size(); i++)
        {
            if(gameObjects.get(i).isActive())
                gameObjects.get(i).update(deltaTime);
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