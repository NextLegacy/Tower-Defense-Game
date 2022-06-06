package game.gameObjects.projectile;

import java.util.ArrayList;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;
import game.gameObjects.enemies.Enemy;
import game.scenes.GameScene;

public abstract class Projectile extends CollisionGameObject
{
    public Sprite sprite;

    public Vector velocity;

    public double rotation;

    public Projectile(Sprite sprite, Vector position)
    {
        super(3);
        this.sprite = sprite;
        this.position = position;

        this.size = sprite.size;

        velocity = new Vector(0, 0);
    }

    @Override
    public void update(double deltaTime)
    {
        position = position.add(velocity);
    }

    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if (layer.isNot("debug")) 
            return;

        sprite.position = position;
        sprite.rotation = rotation;

        layer.renderSpriteCentered(sprite);
    }

    public void onHitEnemy(Enemy enemy) { }

    @Override
    public void onCollision(ArrayList<CollisionGameObject> collisionObjects) 
    {
        for (CollisionGameObject collisionObject : collisionObjects)
        {
            if (collisionObject instanceof Enemy)
            {
                onHitEnemy((Enemy)collisionObject);
                
                if (isNotActive())
                    break;
            }
        }    
    }
}
