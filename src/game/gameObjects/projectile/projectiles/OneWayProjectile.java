package game.gameObjects.projectile.projectiles;

import engine.math.Vector;
import engine.utils.Sprite;
import engine.utils.Lambda.Action2;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.projectile.Projectile;

public class OneWayProjectile extends Projectile
{
    private Vector direction;

    private double speed;

    public OneWayProjectile(Sprite sprite, Vector position, Vector direction, double speed, Action2<Projectile, Enemy> onHit) 
    {
        super(sprite, position, onHit);

        rotation = direction.angle();

        this.direction = direction;
        this.speed = speed;
    }

    @Override
    public void update(double deltaTime)
    {
        velocity = direction.mul(deltaTime*speed);
        super.update(deltaTime);
    }
}
