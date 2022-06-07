package game.gameObjects.projectile.projectiles;

import java.util.Stack;

import engine.math.Vector;
import engine.utils.Sprite;
import engine.utils.Lambda.Action2;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.projectile.Projectile;

public class PiercingProjectile extends OneWayProjectile
{
    private int pierce;

    Stack<Enemy> enemiesHit = new Stack<Enemy>();

    public PiercingProjectile(Sprite sprite, Vector position, Vector direction, double speed, int pierce, Action2<Projectile, Enemy> onHit)
    {
        super(sprite, position, direction, speed, onHit);

        this.pierce = pierce;
    }

    @Override
    public void update(double deltaTime) 
    {
        super.update(deltaTime);
        
        if (pierce <= 0) 
            destroy();
    }

    @Override
    public void onHitEnemy(Enemy enemy) 
    {
        if (enemiesHit.contains(enemy))
            return;
        
        enemiesHit.push(enemy);

        pierce--;
        
        super.onHitEnemy(enemy);
    }
}
