package engine.scene;

import java.util.ArrayList;

import engine.math.Vector;

public abstract class CollisionGameObject extends GameObject
{
    
    public Vector size; // dont set size or halfSize manually; use setters
    public Vector halfSize;
    public boolean colliding; // updated after update method so don't use there
    
    protected Collisions collisions;
    
    public final int collisionLayer;
    
    public CollisionGameObject(int collisionLayer)
    {
        super();
        setSize(new Vector(0, 0));
        this.collisionLayer = collisionLayer;
        colliding = false;
    }
    
    //public Vector getSize() { return size; }
    public void setSize(Vector v) { size = v; halfSize = v.div(2); }
    //public Vector getHalfSize() { return halfSize; }
    public void setHalfSize(Vector v) { halfSize = v; halfSize = v.mul(2); }
    
    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
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