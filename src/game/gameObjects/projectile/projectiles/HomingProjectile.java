package game.gameObjects.projectile.projectiles;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import engine.utils.Lambda.Action2;
import engine.utils.Lambda.Func2;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.projectile.Projectile;

public class HomingProjectile extends Projectile
{
    private CollisionGameObject target;
    private Func2<CollisionGameObject, Vector, Double> getTarget;

    private double speed;
    private Vector direction;
    private double range;

    public HomingProjectile(Sprite sprite, double range, Vector position, CollisionGameObject target, double speed, Action2<Projectile, Enemy> onHit)
    {
        super(sprite, position, onHit);
        this.target = target;
        this.speed = speed;
        this.range = range;
        this.direction = position.directionTo(target.position);
    }

    public HomingProjectile(Sprite sprite, double range, Vector position, Vector startDirection, Func2<CollisionGameObject, Vector, Double> getTarget, double speed, Action2<Projectile, Enemy> onHit)
    {
        super(sprite, position, onHit);
        this.getTarget = getTarget;
        this.range = range;
        this.speed = speed;

        this.direction = startDirection;
    }


    @Override
    public void update(double deltaTime) 
    {
        if (getTarget != null)
            target = getTarget.invoke(position, range);

        if (target != null && target.isActive())
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
