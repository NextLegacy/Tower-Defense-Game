package engine.scene;

import java.util.ArrayList;

public class Collisions
{
    
    private final ArrayList<ArrayList<CollisionGameObject>> layers;
    private final LayerCollision[] layerCollisions;
    
    /**
     * 
     * @param layerCount Number of layers.
     * @param collisions An array of layer-index-pairs. If an object from the first layer collides with objects of the second layer, the onCollision method of the first object is called.
     */
    public Collisions(int layerCount, LayerCollision[] layerCollisions)
    {
        layers = new ArrayList<ArrayList<CollisionGameObject>>();
        for(int i = 0; i < layerCount; i++)
        {
            layers.add(new ArrayList<CollisionGameObject>());
        }
        
        this.layerCollisions = layerCollisions;
    }
    
    public void addObject(CollisionGameObject o, int layer)
    {
        layers.get(layer).add(o);
    }
    
    public void collisionsUpdate()
    {
        for(int i = 0; i < layerCollisions.length; i++)
        {
            if(layerCollisions[i].active)
            {
                ArrayList<CollisionGameObject> layerA = layers.get(layerCollisions[i].a);
                ArrayList<CollisionGameObject> layerB = layers.get(layerCollisions[i].b);
                
                for(CollisionGameObject objA : layerA)
                {
                    // get all objects colliding with objA
                    ArrayList<CollisionGameObject> collisionObjects = new ArrayList<CollisionGameObject>();
                    for(CollisionGameObject objB : layerB)
                    {
                        if(objA != objB && boxCollision(objA, objB))
                        {
                            collisionObjects.add(objB);
                        }
                    }
                    
                    // call collision methods of objA
                    if(collisionObjects.size() > 0)
                    {
                        if(!objA.colliding)
                        {
                            objA.colliding = true;
                            objA.onCollisionEnter();
                        }
                        objA.onCollision(collisionObjects);
                    }
                    else
                    {
                        if(objA.colliding)
                        {
                            objA.colliding = false;
                            objA.onCollisionExit();
                        }
                    }
                }
            }
        }
    }
    
    public static boolean boxCollision(CollisionGameObject a, CollisionGameObject b)
    {
        return b.position.x + b.halfSize.x > a.position.x - a.halfSize.x
            && b.position.x - b.halfSize.x < a.position.x + a.halfSize.x
            && b.position.y + b.halfSize.y > a.position.y - a.halfSize.y
            && b.position.y - b.halfSize.y < a.position.y + a.halfSize.y;
    }
    
    public static class LayerCollision // der Name ist schlecht
    {
        
        public final int a;
        public final int b;
        public boolean active;
        
        public LayerCollision(int a, int b, boolean startActive)
        {
            this.a = a;
            this.b = b;
            active = startActive;
            
        }
        
    }
    
}
