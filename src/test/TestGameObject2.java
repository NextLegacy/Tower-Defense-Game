package test;

import java.awt.image.BufferedImage;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Images;
import engine.window.RenderLayer;

public class TestGameObject2 extends CollisionGameObject
{
    
    private BufferedImage sprite;
    
    public TestGameObject2()
    {
        super(1);
        size = new Vector(200, 200);
        
        sprite = Images.getImage("box_50x50_23346", 500, 500);
    }
    
    @Override
    public void update(double deltaTime) 
    {
        this.rotation += deltaTime*10;
    }

    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.name() == "Test") 
            layer.renderSprite(layer, sprite, position, rotation);
    }
    
}
