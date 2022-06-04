package test;

import java.awt.event.KeyEvent;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;

public class TestGameObject extends CollisionGameObject
{
    public Sprite sprite;
    
    public TestGameObject()
    {
        super(0);
        size = new Vector(20, 20);
        
        sprite = new Sprite("stein");
        sprite.size = new Vector(20, 20);
    }
    
    @Override
    public void update(double deltaTime)
    {
        //System.out.println(deltaTime);

        sprite.rotation += deltaTime * 5;

        if(position.x <= 700)
            position.x += 150 * deltaTime;
        
        if(input.left().isClickedInBounds(position, size, 1_000_000_000) || input.key(KeyEvent.VK_X).isDown() || input.key(KeyEvent.VK_C).downTime() > 2_000_000_000)
            engine.deactivate();
            
    }
    
    @Override
    public void onCollisionEnter()
    {
        sprite.setImage("box_20x20_active");
    }
    
    @Override
    public void onCollisionExit()
    {
        sprite.setImage("stein");
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.name() == "test") 
            layer.renderSprite(sprite);
    }
    
}