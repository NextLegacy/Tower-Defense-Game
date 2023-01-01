package engine.scene;

import java.util.ArrayList;
import java.util.Stack;

import engine.math.Vector;

public class Collisions
{
    private final ArrayList<ArrayList<CollisionGameObject>> layers;
    private final LayerCollision[] layerCollisions;
    
    private Stack<CollisionGameObject> newObjects;
    private Stack<Integer> newObjectLayers;
    private Stack<CollisionGameObject> removedObjects;
    private Stack<Integer> removedObjectLayers;
    
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
        
        newObjects = new Stack<CollisionGameObject>();
        newObjectLayers = new Stack<Integer>();
        removedObjects = new Stack<CollisionGameObject>();
        removedObjectLayers = new Stack<Integer>();
        
        this.layerCollisions = layerCollisions;
    }
    
    public void addObject(CollisionGameObject o, int layer)
    {
        newObjects.push(o);
        newObjectLayers.push(layer);
    }
    
    public void removeObject(CollisionGameObject o, int layer)
    {
        removedObjects.push(o);
        removedObjectLayers.push(layer);
    }
    
    public void collisionsUpdate()
    {
        syncObjects();

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
                        if(objA != objB /*&& objA.isActive() && objB.isActive()*/ && collision(objA, objB))
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

    private void syncObjects()
    {
        // add and remove objects to / from the layers
        while(!newObjects.empty())
        {
            layers.get(newObjectLayers.pop()).add(newObjects.pop());
        }
        while(!removedObjects.empty())
        {
            layers.get(removedObjectLayers.pop()).remove(removedObjects.pop());
        }
    }
    
    public static boolean collision(CollisionGameObject a, CollisionGameObject b)
    {
        if(a == b) return false;
        
        if(!(a instanceof DiagonalCollisionGameObject) && !(b instanceof DiagonalCollisionGameObject))
            return boxCollision(a, b);
        else if((a instanceof DiagonalCollisionGameObject) && !(b instanceof DiagonalCollisionGameObject))
            return diagonalBoxCollision((DiagonalCollisionGameObject) a, b);
        else if(!(a instanceof DiagonalCollisionGameObject) && (b instanceof DiagonalCollisionGameObject))
            return diagonalBoxCollision((DiagonalCollisionGameObject) b, a);
        else
            return false; // TODO: collisions of two diagonal objects (not important for now)
    }
    
    public static boolean diagonalBoxCollision(DiagonalCollisionGameObject a, CollisionGameObject b)
    {
        if(a.horizontal)
        {
            return boxCollision(a, b)
                && a.startPos.x - Math.abs(a.slope * b.size.y / 2) + a.slope * (b.position.y - a.startPos.y) - b.position.x - b.size.x / 2 <= 0
                && b.position.x - b.size.x / 2  - Math.abs(a.slope * b.size.y / 2) + a.slope * (a.startPos.y - b.position.y) - a.height - a.startPos.x <= 0;
        }
        else
        {
            return boxCollision(a, b)
                && a.startPos.y - Math.abs(a.slope * b.size.x / 2) + a.slope * (b.position.x - a.startPos.x) - b.position.y - b.size.y / 2 <= 0
                && b.position.y - b.size.y / 2  - Math.abs(a.slope * b.size.x / 2) + a.slope * (a.startPos.x - b.position.x) - a.height - a.startPos.y <= 0;
        }
    }
    
    public static boolean boxCollision(CollisionGameObject a, CollisionGameObject b)
    {
        return b.position.x + b.size.x / 2 > a.position.x - a.size.x / 2
            && b.position.x - b.size.x / 2 < a.position.x + a.size.x / 2
            && b.position.y + b.size.y / 2 > a.position.y - a.size.y / 2
            && b.position.y - b.size.y / 2 < a.position.y + a.size.y / 2;
    }
    
    public static boolean inCenteredBox(CollisionGameObject a, int x, int y, int width, int height)
    {
        return a.position.x < x + width / 2 && a.position.x > x - width / 2
            && a.position.y < y + height / 2 && a.position.y > y - height / 2;
    }
    
    public ArrayList<CollisionGameObject> objectsInCircle(int collisionLayer, Vector center, double radius)
    {
        return objectsInCircle(collisionLayer, center.x, center.y, radius);
    }
    
    public ArrayList<CollisionGameObject> objectsInCircle(int collisionLayer, double x, double y, double radius)
    {
        ArrayList<CollisionGameObject> ret = new ArrayList<CollisionGameObject>();
        for(CollisionGameObject obj : layers.get(collisionLayer))
        {
            if(obj.isActive() && inCircle(obj, x, y, radius)) ret.add(obj);
        }
        return ret;
    }
    
    public static boolean inCircle(CollisionGameObject a, double x, double y, double radius)
    {
        return Math.sqrt((a.position.x - x) * (a.position.x - x) + (a.position.y - y) * (a.position.y - y)) < radius;
    }
    
    public void setLayerCollisionActive(int layerCollision, boolean active)
    {
        layerCollisions[layerCollision].active = active;
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
