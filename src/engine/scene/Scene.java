package engine.scene;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import engine.Engine;
import engine.scene.Collisions.LayerCollision;
import engine.window.RenderLayer;

public class Scene extends Activateable
{
    private ArrayList<GameObject> gameObjects;
    
    public final Collisions collisions;
    
    private Engine engine;

    public Scene(int collisionLayerCount, LayerCollision[] layerCollisions)
    {
        deactivate();
        
        gameObjects = new ArrayList<>();
        
        // setup collisions
        collisions = new Collisions(collisionLayerCount, layerCollisions);
    }
    
    public void init() { }
    
    public void addObject(GameObject gameObject)
    {
        gameObjects.add(gameObject);
        gameObject.setScene(this.engine, this);
        
        if(isActive()) 
            gameObject.start();
    }
    
    public void removeObject(GameObject gameObject)
    {
        gameObjects.remove(gameObject);
        gameObject.setScene(this.engine, null);
    }
    
    public void start()
    {
        activate();

        for(GameObject gameObject : gameObjects)
        {
            if (gameObject.isActive())
                gameObject.start();
        }
    }
    
    public void update(double deltaTime)
    {
        for(GameObject gameObject : gameObjects)
        {
            if (gameObject.isActive())
                gameObject.update(deltaTime);
        }
        
        // collisions
        collisions.collisionsUpdate();
    }

    public void render(RenderLayer layer, double deltaTime)
    {
        for(GameObject gameObject : gameObjects)
        {
            if (gameObject.isActive())
                gameObject.render(layer, deltaTime);

            layer.graphics().setColor(Color.BLACK);
        }
        
        collisions.collisionsUpdate();
    }
    
    public void destroy()
    {
        deactivate();

        for(GameObject gameObject : gameObjects)
            gameObject.destroy();
    }
}