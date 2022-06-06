package game.gameObjects.projectile.projectiles;

import engine.math.Vector;
import engine.utils.Sprite;
import game.gameObjects.projectile.Projectile;

public class OneWayProjectile extends Projectile
{
    private Vector direction;

    public OneWayProjectile(Sprite sprite, Vector position, Vector direction) 
    {
        super(sprite, position);
        this.direction = direction;
    }

    @Override
    public void update(double deltaTime)
    {
        velocity.add(direction);
        super.update(deltaTime);
    }
}
