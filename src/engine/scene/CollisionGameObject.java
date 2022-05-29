package engine.scene;

import java.util.ArrayList;

import engine.Engine;
import engine.math.Vector;

public abstract class CollisionGameObject extends GameObject
{
    
    public Vector size;
    public boolean colliding; // updated after update method so don't use there
    
    protected Collisions collisions;
    
    public final int collisionLayer;
    
    public CollisionGameObject(int collisionLayer)
    {
        super();
        size = new Vector(0, 0);
        this.collisionLayer = collisionLayer;
        colliding = false;
    }
    
    @Override
    public void setScene(final Engine engine, Scene scene) {
        super.setScene(engine, scene);
        if(scene != null)
        {
            collisions = scene.collisions;
            collisions.addObject(this, collisionLayer);
        }
    }
    
    public void onCollision(ArrayList<CollisionGameObject> collisionObjects) {}
    
    public void onCollisionEnter() {} // onCollisionEnter / onCollisionExit is used for stard / end of a collision with any object(s) from the other collision layer
    
    public void onCollisionExit() {}
    
}