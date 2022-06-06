package game.gameObjects;

import java.awt.Color;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.window.RenderLayer;

public class ObstacleObject extends CollisionGameObject
{
    public ObstacleObject(Vector position, Vector size)
    {
        super(0);
        this.position = position;
        this.size = size;
    }
    
    /**
     * @param pointA top left corner
     * @param pointB bottom right corner
     * @return resulting Obstacle Object
     */
    public static ObstacleObject fromPoints(Vector pointA, Vector pointB)
    {
        Vector s = pointB.sub(pointA);
        return new ObstacleObject(pointA.add(s.div(2)), s);
    }
    
    // TODO: remove debug stuff
    
    // debug
    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.is("test"))
        {
            layer.graphics().setColor(new Color(0, 0, 255));
            layer.graphics().drawRect((int) (position.x - size.x / 2), (int) (position.y - size.y / 2), (int) size.x, (int) size.y);
        }
    }
}
