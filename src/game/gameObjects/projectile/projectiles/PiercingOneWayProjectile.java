package game.gameObjects.projectile.projectiles;

import java.util.ArrayList;
import java.util.Stack;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import engine.utils.Lambda.Action2;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.projectile.Projectile;

public class PiercingOneWayProjectile extends OneWayProjectile
{
    private int pierce;

    Stack<Enemy> enemiesHit = new Stack<Enemy>();

    public PiercingOneWayProjectile(Sprite sprite, Vector position, Vector direction, double speed, int pierce, Action2<Projectile, Enemy> onHit)
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

    @Override
    public void onCollision(ArrayList<CollisionGameObject> collisionObjects) 
    {
        Stack<Enemy> enemiesHitToRemove = new Stack<Enemy>();

        for (Enemy enemyHit : enemiesHit)
        {   
            if (collisionObjects.contains(enemyHit))
                continue;

            enemiesHitToRemove.push(enemyHit);
        }

        enemiesHit.removeAll(enemiesHitToRemove);

        super.onCollision(collisionObjects);
    }
}
