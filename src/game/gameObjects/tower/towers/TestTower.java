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
    public TestTower(Vector position)
    {
        super(position);
        this.sprite = new Sprite("stein");
        this.range = 150;
        this.fireRate = 2;
    }

    @Override
    protected void fire() 
    {
        Enemy target = getFurthestEnemy(position);

        if (target == null)
            return;

        lookAt(target.position);

        scene.addObject(new HomingProjectile(new Sprite("stein").setSize(new Vector(30, 30)), position, this::getFurthestEnemy, 5, (projectile, enemey) -> 
        {
            enemey.damage(1);
            projectile.destroy();
        }));
    }

    @Override
    protected UpgradeManager createUpgradeManager() 
    {
        return new UpgradeManager(
            new UpgradePath(UpgradePathType.ONE_BY_ONE, 
                new Upgrade(
                    "faster", 
                    Sprite.ohno(), 
                    "does fire faster", 
                    10, 
                    (upgrade) -> fireRate = 1,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "much faster", 
                    Sprite.ohno(), 
                    "does fire way faster", 
                    100, 
                    (upgrade) -> fireRate = 0.1,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "lightspeed",
                    Sprite.ohno(), 
                    "most firerate in game", 
                    1000, 
                    (upgrade) -> fireRate = 0.01,
                    Upgrade.NO_EFFECT
                )
            ),
            new UpgradePath(UpgradePathType.ONLY_ONE,
                new Upgrade(
                    "more range", 
                    Sprite.ohno(), 
                    "does hava more range", 
                    10, 
                    (upgrade) -> range = 10,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "much more range", 
                    Sprite.ohno(), 
                    "does hava way more range", 
                    100, 
                    (upgrade) -> range = 100,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "ultravision",
                    Sprite.ohno(), 
                    "ultra range", 
                    1000, 
                    (upgrade) -> range = 1000,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "mega ultravision",
                    Sprite.ohno(), 
                    "most range in game", 
                    10000, 
                    (upgrade) -> range = 1000,
                    Upgrade.NO_EFFECT
                )
            ),
            new UpgradePath(UpgradePathType.FIRST_AND_ONLY_ONE,
                new Upgrade(
                    "bullet", 
                    Sprite.ohno(), 
                    "does fire bullets", 
                    10, 
                    Upgrade.NO_EFFECT,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "much more bullets", 
                    Sprite.ohno(), 
                    "does shoot way\nmore bullets at once", 
                    100, 
                    Upgrade.NO_EFFECT,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "ulta bullets",
                    Sprite.ohno(), 
                    "most damage in game", 
                    1000, 
                    Upgrade.NO_EFFECT,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "ulta bullets",
                    Sprite.ohno(), 
                    "most damage in game", 
                    1000, 
                    Upgrade.NO_EFFECT,
                    Upgrade.NO_EFFECT
                )
            )
        );
    }
}
