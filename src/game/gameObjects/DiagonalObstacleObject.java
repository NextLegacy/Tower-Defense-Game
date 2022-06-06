package game.gameObjects;

import java.awt.Color;

import engine.math.Vector;
import engine.scene.DiagonalCollisionGameObject;
import engine.window.RenderLayer;

public class DiagonalObstacleObject extends DiagonalCollisionGameObject
{
    public DiagonalObstacleObject(Vector position, Vector size, boolean horizontal, Vector startPos, double slope, double height)
    {
        super(0);
        
        this.position = position;
        this.size = size;
        this.horizontal = horizontal;
        this.startPos = startPos;
        this.slope = slope;
        this.height = height;
    }
    
    public static DiagonalObstacleObject fromPoints(boolean horizontal, Vector pointA, Vector pointB, double height)
    {
        if(horizontal)
        {
            Vector size = pointB.sub(pointA).abs().add(height, 0);
            Vector corner = new Vector(Math.min(pointA.x, pointB.x), Math.min(pointA.y, pointB.y));
            return new DiagonalObstacleObject(corner.add(size.div(2)), size, horizontal, pointA.y <= pointB.y ? pointA : pointB, (pointB.x - pointA.x) / (pointB.y - pointA.y), height);
        }
        else
        {
            Vector size = pointB.sub(pointA).abs().add(0, height);
            Vector corner = new Vector(Math.min(pointA.x, pointB.x), Math.min(pointA.y, pointB.y));
            return new DiagonalObstacleObject(corner.add(size.div(2)), size, horizontal, pointA.x <= pointB.x ? pointA : pointB, (pointB.y - pointA.y) / (pointB.x - pointA.x), height);
        }
    }
    
    // debug
    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.is("debug"))
        {
            if(horizontal)
            {
                Vector end = startPos.add(slope * size.y, size.y);
                layer.graphics().setColor(new Color(0, 0, 255));
                layer.graphics().drawLine((int) startPos.x, (int) startPos.y, (int) end.x, (int) end.y);
                layer.graphics().drawLine((int) end.x, (int) end.y, (int) (end.x + height), (int) (end.y));
                layer.graphics().drawLine((int) (end.x + height), (int) (end.y), (int) (startPos.x + height), (int) (startPos.y));
                layer.graphics().drawLine((int) (startPos.x + height), (int) (startPos.y), (int) startPos.x, (int) startPos.y);
            }
            else
            {
                Vector end = startPos.add(size.x, slope * size.x);
                layer.graphics().setColor(new Color(0, 0, 255));
                layer.graphics().drawLine((int) startPos.x, (int) startPos.y, (int) end.x, (int) end.y);
                layer.graphics().drawLine((int) end.x, (int) end.y, (int) (end.x), (int) (end.y + height));
                layer.graphics().drawLine((int) (end.x), (int) (end.y + height), (int) (startPos.x), (int) (startPos.y + height));
                layer.graphics().drawLine((int) (startPos.x ), (int) (startPos.y + height), (int) startPos.x, (int) startPos.y);
            }
        }
    }
}
