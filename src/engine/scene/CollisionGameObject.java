package engine.scene;

import java.util.ArrayList;

import engine.math.Vector;

public abstract class CollisionGameObject extends GameObject
{
    public Vector position;
    public Vector size;
    public boolean colliding; // updated after update method so don't use there
    
    protected Collisions collisions;
    
    public final int collisionLayer;
    
    public CollisionGameObject(int collisionLayer)
    {
        super();
        position = Vector.zero();
        size = Vector.zero();
        this.collisionLayer = collisionLayer;
        colliding = false;
    }
    
    @Override
    public void onSceneChange() 
    {
        if(scene == null) return;
        
        collisions = scene.collisions;
        collisions.addObject(this, collisionLayer);
    }
    
    public void onCollision(ArrayList<CollisionGameObject> collisionObjects) { }
    
    public void onCollisionEnter() { } // onCollisionEnter / onCollisionExit is used for stard / end of a collision with any object(s) from the other collision layer
    
    public void onCollisionExit() { }
}