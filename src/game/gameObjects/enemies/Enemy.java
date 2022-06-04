package game.gameObjects.enemies;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;
import game.scenes.GameSceneNicht;
import game.scenes.Path;

public class Enemy extends CollisionGameObject
{
    public double speed;
    
    protected Sprite sprite;
    
    protected Path path;
    protected int pathIndex;
    protected Vector nextPoint;
    
    public Enemy()
    {
        super(1);
        
        // TODO: this belongs in the subclasses
        sprite = new Sprite("enemies/test_enemy");
        speed = 200;
    }
    
    @Override
    public void onSceneChange() {
        if(scene instanceof GameSceneNicht) path = ((GameSceneNicht)scene).map.path;
    }
    
    @Override
    public void start() {
        position = path.points[0].clone();
        pathIndex = 1;
        nextPoint = path.points[pathIndex];
    }
    
    @Override
    public void update(double deltaTime)
    {
        if(position.distance(nextPoint) <= speed * deltaTime)
        {
            position = nextPoint.clone();
            pathIndex++;
            if(pathIndex >= path.points.length)
            {
                destroy();
                return;
                // TODO: decrease health
            }
            nextPoint = path.points[pathIndex];
            // TODO: maybe handle overshooting a point by moving past it to the next one in the same tick
        }
        else
        {
            Vector move = nextPoint.sub(position).normalized().mul(speed * deltaTime);
            sprite.rotation = move.normalized().angle();
            position = position.add(move);
        }
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime) {
        sprite.position = position.sub(sprite.size.div(2));
        if(layer.name() == "Test2")
            layer.renderSprite(sprite);
    }
}
