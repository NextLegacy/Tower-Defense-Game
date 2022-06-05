package game.gameObjects.enemies;

import java.awt.Color;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;
import game.scenes.GameScene;
import game.scenes.Path;

public class Enemy extends CollisionGameObject
{
    public double speed;
    
    protected int maxHealth;
    protected int health;
    
    protected Sprite sprite;
    
    protected Path path;
    protected int pathIndex;
    protected Vector nextPoint;
    
    protected static final Color HEALTH_COLOR_A = new Color(23, 196, 23);
    protected static final Color HEALTH_COLOR_B = new Color(196, 23, 23);
    protected static final int HEALTHBAR_WIDTH = 20;
    protected static final int HEALTHBAR_HEIGHT = 5;
    protected static final int HEALTHBAR_DISTANCE = 5;
    
    public Enemy()
    {
        super(1);
        
        // TODO: this belongs in the subclasses
        sprite = new Sprite("enemies/test_enemy");
        speed = 200;
        maxHealth = 20;
        health = 20;
    }
    
    public void damage(int ammount)
    {
        health -= ammount;
        if(health <= 0)
        {
            onKill();
            destroy();
        }
    }
    
    protected void onKill() { }
    
    @Override
    public void onSceneChange() {
        if(scene instanceof GameScene) path = ((GameScene)scene).map.path;
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
                onPathEnd();
                destroy();
                return;
            }
            nextPoint = path.points[pathIndex];
            // TODO: maybe handle overshooting a point by moving past it to the next one in the same tick
            damage(5); // debug
        }
        else
        {
            Vector move = nextPoint.sub(position).normalized().mul(speed * deltaTime);
            sprite.rotation = move.normalized().angle();
            position = position.add(move);
        }
    }
    
    protected void onPathEnd()
    {
        // TODO: decrease scene health or add scene damage method
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime) {
        sprite.position = position.sub(sprite.size.div(2));
        if(layer.name() == "test2")
        {
            layer.renderSprite(sprite);
            double healthbarProgress = (double) health / (double) maxHealth * HEALTHBAR_HEIGHT;
            layer.graphics().setColor(HEALTH_COLOR_A);
            layer.graphics().fillRect((int) (position.x - HEALTHBAR_WIDTH / 2), (int) (position.y - sprite.size.y / 2 - HEALTHBAR_HEIGHT - HEALTHBAR_DISTANCE), (int) healthbarProgress, HEALTHBAR_HEIGHT);
            layer.graphics().setColor(HEALTH_COLOR_B);
            //layer.graphics().fillRect((int) (position.x - HEALTHBAR_WIDTH / 2 + healthbarProgress), (int) (position.y - sprite.size.y / 2 - HEALTHBAR_HEIGHT - HEALTHBAR_DISTANCE), (int) (HEALTHBAR_WIDTH - healthbarProgress), HEALTHBAR_HEIGHT);
        }
    }
}
