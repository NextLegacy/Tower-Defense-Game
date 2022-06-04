package game;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;

public class Path extends GameObject // debug
{
    public Vector[] points;
    
    private Sprite sprite; // debug
    
    public Path(Vector[] points)
    {
        this.points = points;
        
        sprite = new Sprite("marker"); // debug
    }
    
    // TODO: remove debug stuff
    
    // debug
    @Override
    public void render(RenderLayer layer, double deltaTime) {
        if(layer.name() == "Test")
        {
            for(Vector p : points)
            {
                sprite.position = p.sub(sprite.size.div(2));
                layer.renderSprite(sprite);
            }
        }
    }
}