package game.gameObjects.projectile.projectiles;

import engine.math.Vector;
import engine.utils.Sprite;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.projectile.Projectile;

public class OneWayProjectile extends Projectile
{
    private Vector direction;

    private int counter = 0;

    public OneWayProjectile(Sprite sprite, Vector position, Vector direction) 
    {
        super(sprite, position);
        this.direction = direction;
    }

    @Override
    public void onHitEnemy(Enemy enemy) 
    {
        if (counter > 0) return;

        enemy.damage(1);
        counter++;
    }

    @Override
    public void update(double deltaTime)
    {
        if (counter > 0) 
        {
            destroy();
            return;
        }

        velocity = velocity.add(direction.mul(deltaTime+2));
        super.update(deltaTime);
    }
}
