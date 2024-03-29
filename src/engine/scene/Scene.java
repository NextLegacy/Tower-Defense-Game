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
        deactivate(); // The Scene must be activated by the game loop so it should start inactive.
        
        gameObjects = new ArrayList<GameObject>();
        removedObjects = new Stack<GameObject>();
        newObjects = new Stack<GameObject>();
        
        // setup collisions
        collisions = new Collisions(collisionLayerCount, layerCollisions);
    }
    
    /**
     * Init is called before start when a Scene is startet and is used for adding GameObjects to the Scene.
     */
    public void init() { }
    
    public final void setEngine(Engine engine) { this.engine = engine; }
    
    public final void addObject(GameObject g)
    {
        g.setScene(this.engine, this);
        g.activate();
        
        g.start();
        
        newObjects.push(g); // GameObjects should not be added during an update
    }
    
    public final void removeObject(GameObject g)
    {
        g.setScene(this.engine, null);
        
        removedObjects.push(g); // GameObjects should not be removed during an update
    }
    
    public void start()
    {
        activate();

        for(GameObject gameObject : gameObjects)
        {
            gameObject.activate();
            gameObject.start();
        }
    }
    
    public void update(double deltaTime)
    {
        syncGameObjects();

        // main GameObject update loop
        for(GameObject gameObject : gameObjects)
        {
            if(gameObject.isActive())
                gameObject.update(deltaTime);
        }
        
        syncGameObjects();
        
        // collisions
        collisions.collisionsUpdate();
        
        syncGameObjects();
    }
    
    /**
     * Removes or adds GameObjects to the GameObject list outside the main update loop.
     */
    private void syncGameObjects()
    {
        // add new game objects
        while(!newObjects.empty())
        {
            GameObject g = newObjects.pop();
            gameObjects.add(g);
        }
        
        // remove destroyed game objects
        while(!removedObjects.empty())
        {
            GameObject g = removedObjects.pop();
            gameObjects.remove(g);
        }
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
        
        syncGameObjects();
    }
}