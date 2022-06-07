package game.gameObjects.projectile.projectiles;

import engine.math.Vector;
import engine.utils.Sprite;
import game.gameObjects.projectile.Projectile;

public class OneWayProjectile extends Projectile
{
    private Vector direction;

    private double speed;

    public OneWayProjectile(Sprite sprite, Vector position, Vector direction, double speed) 
    {
        super(sprite, position);

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
