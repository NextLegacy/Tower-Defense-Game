package test;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;

public class TestMouseCollider extends CollisionGameObject
{
    public Sprite sprite;
    
    public TestMouseCollider()
    {
        super(1);
        
        sprite = new Sprite("ohno");
        size = new Vector(64, 64);
    }
    
    @Override
    public void start() {
        collisions.setLayerCollisionActive(0, true);
    }
    
    @Override
    public void update(double deltaTime)
    {
        position = input.mouse().position();
        sprite.position = position;
    }
    
    @Override
    public void onCollisionEnter() {
        sprite.setImage("invalid");
        sprite.position = position;
    }
    
    @Override
    public void onCollisionExit() {
        sprite = new Sprite("ohno");
        sprite.position = position;
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.is("test2"))
            layer.renderSpriteCentered(sprite);
    }
}
