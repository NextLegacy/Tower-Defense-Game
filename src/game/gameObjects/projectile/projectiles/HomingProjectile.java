package game.gameObjects.projectile.projectiles;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import engine.utils.Lambda.Action2;
import engine.utils.Lambda.Func0;
import engine.utils.Lambda.Func1;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.projectile.Projectile;

public class HomingProjectile extends Projectile
{
    private CollisionGameObject target;
    private Func1<CollisionGameObject, Vector> getTarget;

    private double speed;
    Vector direction;

    public HomingProjectile(Sprite sprite, Vector position, CollisionGameObject target, double speed, Action2<Projectile, Enemy> onHit)
    {
        super(sprite, position, onHit);
        this.target = target;
        this.speed = speed;
        this.direction = Vector.one();
    }

    public HomingProjectile(Sprite sprite, Vector position, Func1<CollisionGameObject, Vector> getTarget, double speed, Action2<Projectile, Enemy> onHit)
    {
        super(sprite, position, onHit);
        this.getTarget = getTarget;
        this.speed = speed;
        this.direction = Vector.fromAngle(position.angle());
    }


    @Override
    public void update(double deltaTime) 
    {
        if (getTarget != null)
            target = getTarget.invoke(position);

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
