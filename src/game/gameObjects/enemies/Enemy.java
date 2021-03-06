package game.gameObjects.enemies;

import java.awt.Color;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;
import game.WaveManager;
import game.scenes.GameScene;
import game.scenes.Path;

public abstract class Enemy extends CollisionGameObject
{
    public double speed;
    
    protected int maxHealth;
    protected int health;
    
    protected Sprite sprite;
    
    protected Path path;
    protected int pathIndex;
    protected Vector nextPoint;
    protected double lastPointDistance;
    
    protected WaveManager waveManager;
    
    protected boolean goToPathStart;
    
    protected static final Color HEALTH_COLOR_A = new Color(23, 196, 23);
    protected static final Color HEALTH_COLOR_B = new Color(196, 23, 23);
    protected static final Color HEALTH_COLOR_C = new Color(0, 0, 0);
    protected static final int HEALTHBAR_WIDTH = 50;
    protected static final int HEALTHBAR_HEIGHT = 5;
    protected static final int HEALTHBAR_DISTANCE = 5;
    
    public Enemy(boolean goToPathStart)
    {
        super(2);
        
        this.goToPathStart = goToPathStart;
        
        /*// TODO: this belongs in the subclasses
        sprite = new Sprite("enemies/test_enemy");
        speed = 20;
        maxHealth = 30;
        health = 30;
        size = sprite.size.clone();*/
    }
    
    public boolean isFurtherThan(Enemy b)
    {
        if(pathIndex > b.pathIndex) return true;
        if(pathIndex == b.pathIndex && lastPointDistance > b.lastPointDistance) return true;
        return false;
    }
    
    public void damage(int amount)
    {
        health -= amount;
        if(health <= 0)
        {
            ((GameScene) scene).money += maxHealth;
            onKill();
            destroy();
        }
    }
    
    /**
     * onKill is called before an Enemy is destroyed because it was killed.
     */
    protected void onKill() { }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        waveManager.removeEnemy(this);
    }
    
    @Override
    public void onSceneChange()
    {
        super.onSceneChange();
        if(scene instanceof GameScene)
        {
            path = ((GameScene)scene).map.path;
            waveManager = ((GameScene)scene).waveManager;
        }
    }
    
    @Override
    public void start()
    {
        waveManager.addEnemy(this);
        
        if(goToPathStart)
        {
            position = path.points[0].clone();
            pathIndex = 1;
            nextPoint = path.points[pathIndex];
            sprite.rotation = nextPoint.sub(position).angle();
        }
    }
    
    @Override
    public void update(double deltaTime)
    {
        if(position.distance(nextPoint) <= speed * deltaTime)
        {
            // move to the next path point if it is very close
            position = nextPoint.clone();
            lastPointDistance = 0;
            pathIndex++;
            if(pathIndex >= path.points.length)
            {
                onPathEnd();
                destroy();
                return;
            }
            nextPoint = path.points[pathIndex];
            sprite.rotation = nextPoint.sub(position).angle();
            // TODO: maybe handle overshooting a point by moving past it to the next one in the same tick
        }
        else
        {
            // move in the direction of the next path point otherwise
            Vector move = nextPoint.sub(position).normalized().mul(speed * deltaTime);
            position = position.add(move);
            lastPointDistance += move.magnitude();
        }
    }
    
    protected void onPathEnd()
    {
        ((GameScene) scene).damage(health);
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        sprite.position = position;
        if(layer.is("enemys"))
        {
            layer.renderSpriteCentered(sprite);
            
            // draw health bar
            int healthbarProgress = (int) ((double) health / (double) maxHealth * HEALTHBAR_WIDTH);
            int x = (int) (position.x - HEALTHBAR_WIDTH / 2);
            int y = (int) (position.y - sprite.size.y / 2 - HEALTHBAR_HEIGHT - HEALTHBAR_DISTANCE);
            layer.graphics().setColor(HEALTH_COLOR_A);
            layer.graphics().fillRect(x, y, healthbarProgress, HEALTHBAR_HEIGHT);
            layer.graphics().setColor(HEALTH_COLOR_B);
            layer.graphics().fillRect(x + healthbarProgress, y, HEALTHBAR_WIDTH - healthbarProgress, HEALTHBAR_HEIGHT);
            layer.graphics().setColor(HEALTH_COLOR_C);
            layer.graphics().drawRect(x - 1, y - 1, HEALTHBAR_WIDTH + 1, HEALTHBAR_HEIGHT + 1);
            // TODO: maybe show <health> / <maxhelath> as text
        }
    }
}
