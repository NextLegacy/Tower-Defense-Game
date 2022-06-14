package game.gameObjects.tower.towers;

import engine.math.Vector;
import engine.utils.Sprite;
import engine.utils.SpriteSheet;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.projectile.Projectile;
import game.gameObjects.projectile.projectiles.PiercingHomingProjectile;
import game.gameObjects.projectile.projectiles.PiercingOneWayProjectile;
import game.gameObjects.tower.Tower;
import game.gameObjects.tower.upgrades.Upgrade;
import game.gameObjects.tower.upgrades.UpgradeManager;
import game.gameObjects.tower.upgrades.UpgradePath;
import game.gameObjects.tower.upgrades.UpgradePathType;

public class Crossbow extends Tower 
{
    public static final Vector SIZE = new Vector(32, 24).mul(3);

    public static final SpriteSheet TOWER_SPRITE_SHEET = new SpriteSheet("towers/crossbow", new Vector(32, 24));
    public static final SpriteSheet UPGRADE_SHEET = new SpriteSheet("upgrades/shapebraker", new Vector(30, 30));

    public static final Sprite BULLET = new Sprite("projectiles/arrow");

    public final int DEFAULT_DAMAGE = 3;

    public final int UPGRADED_DAMAGE_1 = 8;
    public final int UPGRADED_DAMAGE_2 = 12;
    public final int UPGRADED_DAMAGE_3 = 20;

    public final int DEFAULT_PIERCE = 5;
    public final int UPGRADED_PIERCE = 3;

    public final Vector DEFAULT_BULLET_SIZE = new Vector(25, 25);
    public final Vector UPGRADED_BULLET_SIZE = new Vector(35, 35);

    public final double DEFAULT_FIRE_RATE = 0.7777;
    public final double UPGRADED_FIRE_RATE_1 = 0.66;
    public final double UPGRADED_FIRE_RATE_2 = 0.33;

    public final double DEFAULT_RANGE = 150;
    public final double UPGRADED_RANGE_1 = 200;
    public final double UPGRADED_RANGE_2 = 275;

    public Vector bulletSize = DEFAULT_BULLET_SIZE;
    public int pierce = DEFAULT_PIERCE;
    public int damage = DEFAULT_DAMAGE;

    public boolean threeBullets;
    public boolean homingBullets;

    public final Vector LEFT;
    public final Vector RIGHT;
    public final Vector UP;
    public final Vector DOWN;

    public Crossbow(Vector position)
    {
        super(position, SIZE);
        range = DEFAULT_RANGE;
        fireRate = DEFAULT_FIRE_RATE;

        this.spriteSheet = TOWER_SPRITE_SHEET;

        LEFT = this.position.sub(SIZE.x/2, 0);
        RIGHT = this.position.add(SIZE.x/2, 0);
        UP = this.position.sub(0, SIZE.x/2);
        DOWN = this.position.add(0, SIZE.x/2);
    }
    
    @Override
    protected void fire() 
    {
        Enemy target = getFurthestEnemy(position);

        if (target == null) 
            return;

        lookAt(target.position);

        Vector direction = target.position.sub(position).normalized();

        gameScene.addObject(new PiercingOneWayProjectile(BULLET, position, direction, 900, pierce, (projectile, enemy) ->
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
                    "Macht 8 Schaden.", 
                    750, 
                    (upgrade) -> damage = UPGRADED_DAMAGE_1, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "noch mehr Schaden",
                    "Macht 12 Schaden.",
                    1625, 
                    (upgrade) -> damage = UPGRADED_DAMAGE_2, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "viel mehr Schaden",
                    "Macht 20 Schaden.",
                    4050, 
                    (upgrade) -> damage = UPGRADED_DAMAGE_3, 
                    Upgrade.NO_EFFECT
                )
            ),
            new UpgradePath(UpgradePathType.ONE_BY_ONE, 
                new Upgrade
                (
                    "Spitze Pfeile",
                    "Es können 5\nGegner getroffen werden.", 
                    250, 
                    (upgrade) -> pierce = 5, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "Besondere Pfeile",
                    "Es können 7\nGegner getroffen werden.",
                    500, 
                    (upgrade) -> pierce = 7, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "SUPER PFEILE",
                    "Es können 100\nGegner getroffen werden.",
                    4269, 
                    (upgrade) -> pierce = 100, 
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
