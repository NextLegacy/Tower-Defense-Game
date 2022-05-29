package test;
import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Images;
import engine.window.RenderLayer;

public class TestGameObject2 extends CollisionGameObject
{
    
    public TestGameObject2()
    {
        super(1);
        size = new Vector(200, 200);
        
        sprite = Images.getImage("box_50x50");
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.name() == "Test") renderSprite(layer, sprite);
    }
    
}
