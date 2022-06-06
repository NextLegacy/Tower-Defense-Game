package engine.scene;

import engine.math.Vector;

public class DiagonalCollisionGameObject extends CollisionGameObject
{
    public Vector startPos; // P
    public double slope; // m
    public double height; // h_P
    public boolean horizontal;
    
    public DiagonalCollisionGameObject(int collisionLayer)
    {
        super(collisionLayer);
        startPos = Vector.zero();
        slope = 0;
        height = 0;
        horizontal = false;
    }
}
