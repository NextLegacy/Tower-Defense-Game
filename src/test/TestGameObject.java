package test;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Images;
import engine.window.RenderLayer;

public class TestGameObject extends CollisionGameObject
{

    private BufferedImage sprite;
    
    public TestGameObject()
    {
        super(0);
        size = new Vector(20, 20);
        
        sprite = Images.getImage("box_20x20_");
    }
    
    @Override
    public void update(double deltaTime)
    {
        rotation += deltaTime *10;
        if(position.x <= 700)
            position.x += 150 * deltaTime;
        
        if(input.left().isClickedInBounds(position, size, 1_000_000_000) || input.key(KeyEvent.VK_X).isDown() || input.key(KeyEvent.VK_C).downTime() > 2_000_000_000)
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
        sprite = Images.getImage("box_20x20_");
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.name() == "Test") 
            layer.renderSprite(layer, sprite, position, rotation);
    }
    
}