package game.gameObjects.projectile.projectiles;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.projectile.Projectile;

public class HomingProjectile extends Projectile
{
    private CollisionGameObject target;
    private double speed;
    Vector direction;

    public HomingProjectile(Sprite sprite, CollisionGameObject target, Vector position, double speed)
    {
        super(sprite, position);
        this.target = target;
        this.speed = speed;
        this.direction = Vector.one();
    }


    @Override
    public final void update(double deltaTime) 
    {
        if (target.isActive())
            direction = target.position.sub(position).normalized();

        velocity = velocity.add(direction.mul(deltaTime * speed));
        super.update(deltaTime);
    }

    public void onHitEnemy(Enemy enemy) 
    {
        enemy.damage(1);
        destroy();
    }
}
