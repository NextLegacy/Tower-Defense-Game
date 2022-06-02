package test;

import java.awt.image.BufferedImage;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;

public class TestGameObject2 extends CollisionGameObject
{
    public Sprite sprite;
    
    public TestGameObject2()
    {
        super(1);
        size = new Vector(200, 200);
        
        this.sprite = new Sprite("box_200x200_");
        this.sprite.size = this.size;
    }
    
    @Override
    public void update(double deltaTime) 
    {
        sprite.rotation += deltaTime*10;
    }

    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.name() == "Test") 
            layer.renderSprite(sprite);
    }
    
}
