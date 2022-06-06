package game.gameObjects.projectile.projectiles;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.projectile.Projectile;

public class HomingProjectile extends Projectile
{
    private CollisionGameObject target;
    
    public HomingProjectile(Sprite sprite, Vector position, CollisionGameObject target)
    {
        super(sprite, position);
        this.target = target;
    }

    @Override
    public final void update(double deltaTime) 
    {
        velocity = target.position.sub(position).normalized().mul(deltaTime*150);
        super.update(deltaTime);
    }

    public void onHitEnemy(Enemy enemy) 
    {
        enemy.damage(1);
        destroy();
    }
}
