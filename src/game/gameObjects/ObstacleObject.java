package game.gameObjects;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;

public class ObstacleObject extends CollisionGameObject
{
    private Sprite sprite; // debug
    
    public ObstacleObject(Vector position, Vector size)
    {
        super(0);
        this.position = position;
        this.size = size;
        
        sprite = new Sprite("box_20x20"); // debug
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
        if(layer.name() == "test")
        {
            sprite.position = position;//.sub(size.div(2));
            sprite.size = size.clone();
            layer.renderSprite(sprite);
        }
    }
}
