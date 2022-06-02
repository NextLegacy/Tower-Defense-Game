package test;

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
        
        this.sprite = new Sprite("stein");
        this.sprite.size = this.size;
    }
    
    @Override
    public void update(double deltaTime) 
    {
        sprite.rotation += deltaTime*2;
    }

    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.name() == "Test") 
            layer.renderSprite(this.position, sprite);
    }
    
}
