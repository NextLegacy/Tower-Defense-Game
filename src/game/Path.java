package game;

import java.awt.Color;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;

public class Path extends GameObject // debug
{
    public Vector[] points;
    
    private Sprite sprite; // debug
    
    public Path(Vector... points)
    {
        this.points = points;
        
        sprite = new Sprite("marker"); // debug
    }
    
    // TODO: remove debug stuff

    @Override
    public void update(double deltaTime) 
    {
        sprite.rotation += deltaTime * 5;
    }

    // debug
    @Override
    public void render(RenderLayer layer, double deltaTime) 
    {
        if(layer.name() == "Test")
        {
            for (var i = 0; i < points.length; i++)
            {
                var point = points[i];
                var nextPoint = points[(i + 1) % points.length];

                layer.graphics().setColor(Color.green);
                layer.drawLine(point, nextPoint);
            }

            for(Vector p : points)
            {
                sprite.position = p.sub(sprite.size.div(2));
                layer.renderSprite(sprite);
            }
        }
    }
}