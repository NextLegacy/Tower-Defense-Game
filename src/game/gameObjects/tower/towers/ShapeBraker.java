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

public class ShapeBraker extends Tower 
{
    public static final Vector SIZE = new Vector(85, 85);

    public static final Sprite BULLET = new Sprite("/projectiles/ball");

    public static final SpriteSheet UPGRADE_SHEET = new SpriteSheet("upgrades/shapebraker", new Vector(30, 30));

    public static final SpriteSheet TOWER_SPRITE_SHEET = new SpriteSheet("towers/shapebraker", new Vector(50, 50));

    public final int DEFAULT_DAMAGE = 3;

    public final int UPGRADED_DAMAGE_1 = 5;
    public final int UPGRADED_DAMAGE_2 = 6;
    public final int UPGRADED_DAMAGE_3 = 8;

    public final int DEFAULT_PIERCE = 1;
    public final int UPGRADED_PIERCE = 4;

    public final Vector DEFAULT_BULLET_SIZE = new Vector(17, 17);
    public final Vector UPGRADED_BULLET_SIZE = new Vector(25, 25);

    public final double DEFAULT_FIRE_RATE = 0.8;
    public final double UPGRADED_FIRE_RATE = 0.4;

    public final double DEFAULT_RANGE = 125;
    public final double UPGRADED_RANGE = 225;

    public Vector bulletSize = DEFAULT_BULLET_SIZE;
    public int pierce = DEFAULT_PIERCE;
    public int damage = DEFAULT_DAMAGE;

    public boolean threeBullets;
    public boolean homingBullets;

    public final Vector LEFT;
    public final Vector RIGHT;
    public final Vector UP;
    public final Vector DOWN;

    public ShapeBraker(Vector position)
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
        if (getFurthestEnemy(position) == null)
            return;

        if (homingBullets)
            shootHomingBullets();
        else
            shootNormalBullets();
    }

    private void shootNormalBullets()
    {
        if (threeBullets)
        {
            shootNormalBullet(LEFT.sub(0, bulletSize.y), Vector.left());
            shootNormalBullet(LEFT, Vector.left());
            shootNormalBullet(LEFT.add(0, bulletSize.y), Vector.left());

            shootNormalBullet(RIGHT.sub(0, bulletSize.y), Vector.right());
            shootNormalBullet(RIGHT, Vector.right());
            shootNormalBullet(RIGHT.add(0, bulletSize.y), Vector.right());

            shootNormalBullet(UP.sub(bulletSize.x, 0), Vector.up());
            shootNormalBullet(UP, Vector.up());
            shootNormalBullet(UP.add(bulletSize.x, 0), Vector.up());

            shootNormalBullet(DOWN.sub(bulletSize.x, 0), Vector.down());
            shootNormalBullet(DOWN, Vector.down());
            shootNormalBullet(DOWN.add(bulletSize.x, 0), Vector.down());
        }
        else
        {
            shootNormalBullet(LEFT, Vector.left());
            shootNormalBullet(RIGHT, Vector.right());
            shootNormalBullet(UP, Vector.up());
            shootNormalBullet(DOWN, Vector.down());
        }
    }

    private void shootHomingBullets()
    {
        if (threeBullets)
        {
            shootHomingBullet(LEFT.sub(0, bulletSize.y), Vector.left());
            shootHomingBullet(LEFT, Vector.left());
            shootHomingBullet(LEFT.add(0, bulletSize.y), Vector.left());

            shootHomingBullet(RIGHT.sub(0, bulletSize.y), Vector.right());
            shootHomingBullet(RIGHT, Vector.right());
            shootHomingBullet(RIGHT.add(0, bulletSize.y), Vector.right());

            shootHomingBullet(UP.sub(bulletSize.x, 0), Vector.up());
            shootHomingBullet(UP, Vector.up());
            shootHomingBullet(UP.add(bulletSize.x, 0), Vector.up());

            shootHomingBullet(DOWN.sub(bulletSize.x, 0), Vector.down());
            shootHomingBullet(DOWN, Vector.down());
            shootHomingBullet(DOWN.add(bulletSize.x, 0), Vector.down());
        }
        else
        {
            shootHomingBullet(LEFT, Vector.left());
            shootHomingBullet(RIGHT, Vector.right());
            shootHomingBullet(UP, Vector.up());
            shootHomingBullet(DOWN, Vector.down());
        }
    }

    private void shootNormalBullet(Vector position, Vector direction)
    {
        gameScene.addObject(new PiercingOneWayProjectile(BULLET.setSize(bulletSize), position, direction, 600, pierce, this::onHit));
    }

    private void shootHomingBullet(Vector position, Vector direction)
    {
        gameScene.addObject(new PiercingHomingProjectile(BULLET.setSize(bulletSize), range, position, direction, this::getFurthestEnemy, 24, pierce, this::onHit));
    }

    private void onHit(Projectile projectile, Enemy enemy)
    {
        enemy.damage(damage);
    }

    @Override
    protected UpgradeManager createUpgradeManager() 
    {
        return new UpgradeManager
        (
            UPGRADE_SHEET,
            new UpgradePath(UpgradePathType.ONLY_ONE,
                new Upgrade
                (
                    "Größere Kugeln",
                    "Die Kugeln sind größer", 
                    400, 
                    (upgrade) -> bulletSize = UPGRADED_BULLET_SIZE, 
                    (upgrade) -> bulletSize = DEFAULT_BULLET_SIZE
                ),
                new Upgrade
                (
                    "Mehr Kugeln",
                    "Schießt drei Kugeln", 
                    1250, 
                    (upgrade) -> threeBullets = true, 
                    (upgrade) -> threeBullets = false
                ),
                new Upgrade
                (
                    "verfolgende Schüsse",
                    "Projektile verfolgen Gegner.", 
                    1500, 
                    (upgrade) -> homingBullets = true, 
                    (upgrade) -> homingBullets = false
                )
            ),
            new UpgradePath(UpgradePathType.ONLY_ONE, 
                new Upgrade
                (
                    "mehr Reichweite",
                    "Erhöht die Reichweite.", 
                    225,
                    (upgrade) -> range = UPGRADED_RANGE,
                    (upgrade) -> range = DEFAULT_RANGE
                ),
                new Upgrade
                (
                    "schnelle Kugeln",
                    "Projektile sind Schneller.", 
                    565,
                    (upgrade) -> fireRate = UPGRADED_FIRE_RATE,
                    (upgrade) -> fireRate = DEFAULT_FIRE_RATE
                ),
                new Upgrade
                (
                    "Spitze Kugeln",
                    "Kugeln durchdringen Gegner.", 
                    740,
                    (upgrade) -> pierce = UPGRADED_PIERCE, 
                    (upgrade) -> pierce = DEFAULT_PIERCE
                )
            ),
            new UpgradePath(UpgradePathType.ONE_BY_ONE, 
                new Upgrade
                (
                    "mehr Schaden",
                    "Kugeln machen 5 Schaden.", 
                    250,
                    (upgrade) -> damage = UPGRADED_DAMAGE_1, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "noch mehr Schaden",
                    "Kugeln machen 6 Schaden.",
                    500,
                    (upgrade) -> damage = UPGRADED_DAMAGE_2, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "viel mehr Schaden",
                    "Kugeln machen 8 Schaden.",
                    680,
                    (upgrade) -> damage = UPGRADED_DAMAGE_3, 
                    Upgrade.NO_EFFECT
                )
            )
        ); 
    }
}
