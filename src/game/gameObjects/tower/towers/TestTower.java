package game.gameObjects.tower.towers;

import java.util.ArrayList;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.scene.GameObject;
import engine.utils.Sprite;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.projectile.projectiles.HomingProjectile;
import game.gameObjects.tower.Tower;
import game.gameObjects.tower.upgrades.Upgrade;
import game.gameObjects.tower.upgrades.UpgradeManager;
import game.gameObjects.tower.upgrades.UpgradePath;
import game.gameObjects.tower.upgrades.UpgradePathType;

public class TestTower extends Tower
{
    public TestTower()
    {
        super();
        this.sprite = new Sprite("stein");
        this.range = 150;
        this.fireRate = 0.05;
    }

    @Override
    protected void fire() 
    {
        ArrayList<CollisionGameObject> gameObjects = collisions.objectsInCircle(2, (int)position.x, (int)position.y, (int)range);

        Enemy target = null;

        for (GameObject gameObject : gameObjects)
        {
            if (gameObject.isNotActive())
                continue;
                
            if (gameObject instanceof Enemy)
            {
                Enemy enemy = (Enemy)gameObject;

                if (target == null)
                    target = enemy;
                else if (enemy.isFurtherThan(target))
                    target = enemy;
            }
        }
        if (target != null)
        {
            lookAt(target.position);
            scene.addObject(new HomingProjectile(new Sprite("stein").setSize(new Vector(30, 30)), target, position, 5));
        } 
    }

    @Override
    protected UpgradeManager createUpgradeManager() 
    {
        return new UpgradeManager(
            new UpgradePath(UpgradePathType.ONE_BY_ONE, 
                new Upgrade(
                    "faster", 
                    "faster", 
                    "does fire faster", 
                    10, 
                    (upgrade) -> fireRate = 10,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "much faster", 
                    "much_faster", 
                    "does fire way faster", 
                    100, 
                    (upgrade) -> fireRate = 100,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "lightspeed",
                    "lightspeed", 
                    "most firerate in game", 
                    1000, 
                    (upgrade) -> fireRate = 1000,
                    Upgrade.NO_EFFECT
                )
            ),
            new UpgradePath(UpgradePathType.ONLY_ONE,
                new Upgrade(
                    "more range", 
                    "range", 
                    "does hava more range", 
                    10, 
                    (upgrade) -> range = 10,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "much more range", 
                    "much_range", 
                    "does hava way more range", 
                    100, 
                    (upgrade) -> range = 100,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "ultravision",
                    "ultravision", 
                    "ultra range", 
                    1000, 
                    (upgrade) -> range = 1000,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "mega ultravision",
                    "mega_ultravision", 
                    "most range in game", 
                    10000, 
                    (upgrade) -> range = 1000,
                    Upgrade.NO_EFFECT
                )
            ),
            new UpgradePath(UpgradePathType.FIRST_AND_ONLY_ONE,
                new Upgrade(
                    "bullet", 
                    "bullet", 
                    "does fire bullets", 
                    10, 
                    Upgrade.NO_EFFECT,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "much more bullets", 
                    "much_bullets", 
                    "does shoot way\nmore bullets at once", 
                    100, 
                    Upgrade.NO_EFFECT,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "ulta bullets",
                    "ultra_bullets", 
                    "most damage in game", 
                    1000, 
                    Upgrade.NO_EFFECT,
                    Upgrade.NO_EFFECT
                )
            )
        );
    }
}
