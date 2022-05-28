package test;
import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Images;

public class TestGameObject extends CollisionGameObject
{
    
    public TestGameObject() {
        super(0);
        setSize(new Vector(20, 20));
        
        sprite = Images.getImage("box_20x20");
        renderLayer = "Test";
    }
    
    @Override
    public void update() {
        position.x += 5;
        if(position.x > 700) destroy();
    }
    
    @Override
    public void onCollisionEnter() {
        sprite = Images.getImage("box_20x20_active");
    }
    
    @Override
    public void onCollisionExit() {
        sprite = Images.getImage("box_20x20");
    }
    
}