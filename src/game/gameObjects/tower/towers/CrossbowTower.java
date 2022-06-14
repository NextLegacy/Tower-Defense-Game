package game.gameObjects.tower.towers;

import engine.math.Vector;
import engine.utils.Sprite;
import engine.utils.SpriteSheet;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.projectile.projectiles.PiercingOneWayProjectile;
import game.gameObjects.tower.Tower;
import game.gameObjects.tower.upgrades.Upgrade;
import game.gameObjects.tower.upgrades.UpgradeManager;
import game.gameObjects.tower.upgrades.UpgradePath;
import game.gameObjects.tower.upgrades.UpgradePathType;

public class CrossbowTower extends Tower
{
    public final static Vector SIZE = new Vector(95, 95);
    
    public static final Sprite SPRITE = new Sprite("/towers/crossbow_default").setSize(SIZE);

    public static final Sprite BULLET = new Sprite("/projectiles/arrow").setSize(new Vector(29, 12).mul(2));

    public static final SpriteSheet UPGRADE_SHEET = new SpriteSheet("upgrades/shapebraker", new Vector(30, 30));

    public int damage = 1;
    public int piercing = 1;

    public CrossbowTower(Vector position)
    {
        super(position, SIZE);

        sprite = SPRITE.deriveSprite();

        range = 140;
        fireRate = 0.3;
    }

    @Override
    protected void fire() 
    {
        Enemy target = getFurthestEnemy(position);

        if (target == null) 
            return;

        lookAt(target.position);

        Vector direction = target.position.sub(position).normalized();

        gameScene.addObject(new PiercingOneWayProjectile(BULLET, position, direction, 900, piercing, (projectile, enemy) ->
        {
            enemy.damage(damage);
        }));
    }

    @Override
    protected UpgradeManager createUpgradeManager() 
    {
        return new UpgradeManager
        (
            UPGRADE_SHEET,
            new UpgradePath(UpgradePathType.ONE_BY_ONE, 
                new Upgrade
                (
                    "mehr Schaden",
                    "Macht 3 Schaden.", 
                    250, 
                    (upgrade) -> damage = 3, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "noch mehr Schaden",
                    "Macht 4 Schaden.",
                    500, 
                    (upgrade) -> damage = 4, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "viel mehr Schaden",
                    "Macht 5 Schaden.",
                    680, 
                    (upgrade) -> damage = 5, 
                    Upgrade.NO_EFFECT
                )
            ),
            new UpgradePath(UpgradePathType.ONE_BY_ONE, 
                new Upgrade
                (
                    "Durchschlagskaft",
                    "Es können 3 Gegner getroffen werden.", 
                    250, 
                    (upgrade) -> piercing = 3, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "mehr Durchschlagskaft",
                    "Es können 5 Gegner getroffen werden.",
                    500, 
                    (upgrade) -> piercing = 5, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "noch mehr Durchschlagskraft",
                    "Es können 7 Gegner getroffen werden.",
                    680, 
                    (upgrade) -> piercing = 7, 
                    Upgrade.NO_EFFECT
                )
            ),
            new UpgradePath(UpgradePathType.ONE_BY_ONE, 
                new Upgrade
                (
                    "mehr Reichweite",
                    "Hat mehr Reichweite", 
                    250, 
                    (upgrade) -> range = 175, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "noch mehr Reichweite",
                    "Noch viel mehr Reichweite.",
                    500, 
                    (upgrade) -> range = 200, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "Ultra Reichweite",
                    "Sehr hohe Reichweite.",
                    1680, 
                    (upgrade) -> range = 500, 
                    Upgrade.NO_EFFECT
                )
            )
       );
    }
}
