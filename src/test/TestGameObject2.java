package test;
import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Images;

public class TestGameObject2 extends CollisionGameObject
{
    
    public TestGameObject2() {
        super(1);
        setSize(new Vector(200, 200));
        
        sprite = Images.getImage("box_50x50");
        renderLayer = "Test";
    }
    
}
