package test;
import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Images;
import engine.window.RenderLayer;

public class TestGameObject extends CollisionGameObject
{
    
    public TestGameObject()
    {
        super(0);
        size = new Vector(20, 20);
        
        sprite = Images.getImage("box_20x20");
    }
    
    @Override
    public void update(double deltaTime)
    {
        position.x += 150 * deltaTime;

        if(position.x > 700) 
            engine.deactivate();
    }
    
    @Override
    public void onCollisionEnter()
    {
        sprite = Images.getImage("box_20x20_active");
    }
    
    @Override
    public void onCollisionExit()
    {
        sprite = Images.getImage("box_20x20");
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.name() == "Test") renderSprite(layer, sprite);
    }
    
}