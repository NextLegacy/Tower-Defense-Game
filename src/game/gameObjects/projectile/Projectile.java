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

    public Vector position;
    public double rotation;

    public Projectile(Sprite sprite, Vector position)
    {
        super(0);
        this.sprite = sprite;
        this.position = position;
        this.velocity = new Vector(0, 0);

    }

    @Override
    public void update(double deltaTime)
    {
        this.position.add(velocity);

        if (this.position.isOutOfBounds(GameScene.GAME_AREA_END.mul(2).inverse(), GameScene.GAME_AREA_END.mul(2)))
            this.destroy();

        this.sprite.position = this.position;
        this.sprite.rotation = this.rotation;
    }

    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if (layer.isNot("debug")) 
            return;

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
            }
        }    
    }
}
