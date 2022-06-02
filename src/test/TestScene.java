package test;

import engine.math.Vector;
import engine.scene.Collisions;
import engine.scene.Scene;
import engine.scene.Collisions.LayerCollision;

public class TestScene extends Scene
{

    public TestScene() {
        super(2, new LayerCollision[] {new Collisions.LayerCollision(0, 1, true)});
    }
    
    @Override
    public void init() {
        //GameObject go1 = new TestGameObject();
        TestGameObject2 go2 = new TestGameObject2();
        //go1.position = new Vector(100, 100);
        go2.position = new Vector(350, 150);
        go2.position = new Vector(350, 150);
        //addObject(go1);
        addObject(go2);
        
        for(int i = 0; i < 100; i++)
        {
            for(int j = 0; j < 100; j++)
            {
                TestGameObject g = new TestGameObject();
                g.position = new Vector(5 * j, 5 * i + 100);
                g.position = new Vector(5 * j, 5 * i + 100);
                addObject(g);
            }
        }
    }
    
    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
    }
    
}
