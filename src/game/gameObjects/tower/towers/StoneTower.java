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

public class StoneTower extends Tower 
{
    public static final Vector SIZE = new Vector(75, 75);

    public static final SpriteSheet TOWER_SPRITE_SHEET = new SpriteSheet("towers/stone", new Vector(64, 64));
    public static final SpriteSheet UPGRADE_SHEET = new SpriteSheet("upgrades/stone", new Vector(30, 30));

    public static final Sprite BULLET = TOWER_SPRITE_SHEET.getSprite(0, 0);

    public final int DEFAULT_DAMAGE = 5;

    public final int UPGRADED_DAMAGE_1 = 7;
    public final int UPGRADED_DAMAGE_2 = 10;
    public final int UPGRADED_DAMAGE_3 = 25;

    public final int DEFAULT_PIERCE = 1;
    public final int UPGRADED_PIERCE = 5;

    public final Vector DEFAULT_BULLET_SIZE = new Vector(25, 25);
    public final Vector UPGRADED_BULLET_SIZE = new Vector(45, 45);

    public final double DEFAULT_FIRE_RATE = 0.66;
    public final double UPGRADED_FIRE_RATE_1 = 0.44;
    public final double UPGRADED_FIRE_RATE_2 = 0.15;

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

    public StoneTower(Vector position)
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

        scene.addObject(new PiercingHomingProjectile(BULLET.setSize(bulletSize), range, position, position.directionTo(target.position), this::getFurthestEnemy, 15, pierce, (projectile, enemey) -> 
        {
            enemey.damage(damage);
        })
        {
            @Override
            public void update(double deltaTime) 
            {
                rotation+=deltaTime*40;
                super.update(deltaTime);
            }
        });
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
                    "Größere Steine",
                    "Die Stein Kugeln\nsind größer", 
                    350, 
                    (upgrade) -> bulletSize = UPGRADED_BULLET_SIZE, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "Schnellere Steine",
                    "Schießt Steine\nsuper schnell", 
                    350, 
                    (upgrade) -> fireRate = UPGRADED_FIRE_RATE_1, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "SUPER SCHNELLE STEINE",
                    "Wirft Steine\nsuper schnell.", 
                    1500, 
                    (upgrade) -> fireRate = UPGRADED_FIRE_RATE_2, 
                    Upgrade.NO_EFFECT
                )
            ),
            new UpgradePath(UpgradePathType.ONE_BY_ONE, 
                new Upgrade
                (
                    "mehr Reichweite",
                    "Erhöht die Reichweite.", 
                    50,
                    (upgrade) -> range = UPGRADED_RANGE_1,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "Viel mehr Reichweite",
                    "Erhöht Reichweite\nERNORM", 
                    300,
                    (upgrade) -> range = UPGRADED_RANGE_2,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "Scharfe Steine",
                    "Schüsse gehen\ndurch die Gegner", 
                    600,
                    (upgrade) -> pierce = UPGRADED_PIERCE, 
                    Upgrade.NO_EFFECT
                )
            ),
            new UpgradePath(UpgradePathType.ONE_BY_ONE, 
                new Upgrade
                (
                    "mehr Schaden",
                    "Kugeln machen 7 Schaden.", 
                    550,
                    (upgrade) -> damage = UPGRADED_DAMAGE_1, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "noch mehr Schaden",
                    "Kugeln machen 10 Schaden.",
                    2000,
                    (upgrade) -> damage = UPGRADED_DAMAGE_2, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "viel mehr Schaden",
                    "Kugeln machen 25 Schaden.",
                    7500,
                    (upgrade) -> damage = UPGRADED_DAMAGE_3, 
                    Upgrade.NO_EFFECT
                )
            )
        ); 
    }
}
