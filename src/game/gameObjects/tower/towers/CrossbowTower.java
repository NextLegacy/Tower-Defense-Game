package game.gameObjects.tower.towers;

import engine.math.Vector;
import engine.utils.Sprite;
import engine.utils.SpriteSheet;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.projectile.projectiles.PiercingProjectile;
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
        super(position);

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

        gameScene.addObject(new PiercingProjectile(BULLET, position, direction, 900, piercing, (projectile, enemy) ->
        {
            enemy.damage(damage);
        }));
    }

    @Override
    protected UpgradeManager createUpgradeManager() 
    {
        return new UpgradeManager
        (
            new UpgradePath(UpgradePathType.ONE_BY_ONE, 
                new Upgrade
                (
                    "mehr Schaden", 
                    UPGRADE_SHEET.getSprite(2, 0),
                    "Macht 3 Schaden.", 
                    250, 
                    (upgrade) -> damage = 3, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "noch mehr Schaden", 
                    UPGRADE_SHEET.getSprite(2, 1),
                    "Macht 4 Schaden.",
                    500, 
                    (upgrade) -> damage = 4, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "viel mehr Schaden", 
                    UPGRADE_SHEET.getSprite(2, 2),
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
                    UPGRADE_SHEET.getSprite(2, 0),
                    "Es können 3 Gegner getroffen werden.", 
                    250, 
                    (upgrade) -> piercing = 3, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "mehr Durchschlagskaft", 
                    UPGRADE_SHEET.getSprite(2, 1),
                    "Es können 5 Gegner getroffen werden.",
                    500, 
                    (upgrade) -> piercing = 5, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "noch mehr Durchschlagskraft", 
                    UPGRADE_SHEET.getSprite(2, 2),
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
                    UPGRADE_SHEET.getSprite(2, 0),
                    "Hat mehr Reichweite", 
                    250, 
                    (upgrade) -> range = 175, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "noch mehr Reichweite", 
                    UPGRADE_SHEET.getSprite(2, 1),
                    "Noch viel mehr Reichweite.",
                    500, 
                    (upgrade) -> range = 200, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "Ultra Reichweite", 
                    UPGRADE_SHEET.getSprite(2, 2),
                    "Sehr hohe Reichweite.",
                    1680, 
                    (upgrade) -> range = 500, 
                    Upgrade.NO_EFFECT
                )
            )
       );
    }
}