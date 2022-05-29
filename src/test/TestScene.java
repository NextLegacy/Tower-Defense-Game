package test;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.scene.Scene;
import engine.scene.Collisions.LayerCollision;

public class TestScene extends Scene
{

    public TestScene(int collisionLayerCount, LayerCollision[] layerCollisions) {
        super(collisionLayerCount, layerCollisions);
    }
    
    @Override
    public void init() {
        GameObject go1 = new TestGameObject();
        GameObject go2 = new TestGameObject2();
        go1.position = new Vector(100, 100);
        go2.position = new Vector(350, 150);
        addObject(go1);
        addObject(go2);
    }
    
}
