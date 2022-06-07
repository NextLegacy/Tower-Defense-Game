package game.gameObjects.tower.towers;

import java.util.ArrayList;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.scene.GameObject;
import engine.utils.Sprite;
import engine.utils.SpriteSheet;
import engine.utils.Lambda.Action2;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.projectile.Projectile;
import game.gameObjects.projectile.projectiles.HomingProjectile;
import game.gameObjects.projectile.projectiles.OneWayProjectile;
import game.gameObjects.tower.Tower;
import game.gameObjects.tower.upgrades.Upgrade;
import game.gameObjects.tower.upgrades.UpgradeManager;
import game.gameObjects.tower.upgrades.UpgradePath;
import game.gameObjects.tower.upgrades.UpgradePathType;

public class ShapeBraker extends Tower 
{
    public static final Vector SIZE = new Vector(95, 95);

    public static final Sprite SPRITE = new Sprite("/towers/shapebraker_default").setSize(SIZE);

    public static final Sprite BULLET = new Sprite("/projectiles/ball");

    public static final SpriteSheet UPGRADE_SHEET = new SpriteSheet("upgrades/shapebraker", new Vector(30, 30));
    public static final SpriteSheet TOWE_SHEET = new SpriteSheet("towers/shapebraker_upgraded", new Vector(50, 50));

    public final Vector DEFAULT_BULLET_SIZE = new Vector(10, 10);
    public final double DEFAULT_FIRE_RATE = 0.8;
    public final double DEFAULT_RANGE = 175;

    public Vector bulletSize = DEFAULT_BULLET_SIZE;

    public int damage = 1;

    public boolean threeBullets;
    public boolean rotateTowardsEnemey;
    public boolean homing;

    public final Vector LEFT;
    public final Vector RIGHT;
    public final Vector UP;
    public final Vector DOWN;

    public ShapeBraker(Vector position)
    {
        super(position);
        range = DEFAULT_RANGE;
        fireRate = DEFAULT_FIRE_RATE;
        this.sprite = SPRITE.deriveSprite(); 

        LEFT = this.position.sub(SIZE.x/2, 0);
        RIGHT = this.position.add(SIZE.x/2, 0);
        UP = this.position.sub(0, SIZE.x/2);
        DOWN = this.position.add(0, SIZE.x/2);
    }

    private int x = 0;
    private int y = -1;
    
    @Override
    public void update(double deltaTime) 
    {
        super.update(deltaTime);

        int x = upgradeManager.UPGRADE_PATHS[0].currentUpgradeIndex + 1;
        int y = upgradeManager.UPGRADE_PATHS[1].currentUpgradeIndex;
        
        if (this.x == x && this.y == y)
            return;

        if (x != -1 || y != -1) 
        { 
            if (x == -1) x = 0;
            else if (y == -1) y = 0;

            this.x = x;
            this.y = y;

            this.sprite = TOWE_SHEET.getSprite(x, y).setSize(ShapeBraker.SIZE).setPosition(position).setRotation(this.sprite.rotation);
        }
    }

    @Override
    protected void fire() 
    {
        if (getFurthestEnemy(position) == null)
            return;

        if (homing)
            shootHomingBullets();
        else
            shootNormalBullets();
    }

    private void shootNormalBullets()
    {
        if (threeBullets)
        {
            shootNormalBullet(LEFT.sub(0, 10), Vector.left());
            shootNormalBullet(LEFT, Vector.left());
            shootNormalBullet(LEFT.add(0, 10), Vector.left());

            shootNormalBullet(RIGHT.sub(0, 10), Vector.right());
            shootNormalBullet(RIGHT, Vector.right());
            shootNormalBullet(RIGHT.add(0, 10), Vector.right());

            shootNormalBullet(UP.sub(10, 0), Vector.up());
            shootNormalBullet(UP, Vector.up());
            shootNormalBullet(UP.add(10, 0), Vector.up());

            shootNormalBullet(DOWN.sub(10, 0), Vector.down());
            shootNormalBullet(DOWN, Vector.down());
            shootNormalBullet(DOWN.add(10, 0), Vector.down());
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
            shootHomingBullet(LEFT.sub(bulletSize.div(2)));
            shootHomingBullet(LEFT);
            shootHomingBullet(LEFT.add(bulletSize.div(2)));

            shootHomingBullet(RIGHT.sub(bulletSize.div(2)));
            shootHomingBullet(RIGHT);
            shootHomingBullet(RIGHT.add(bulletSize.div(2)));

            shootHomingBullet(UP.sub(bulletSize.div(2)));
            shootHomingBullet(UP);
            shootHomingBullet(UP.add(bulletSize.div(2)));

            shootHomingBullet(DOWN.sub(bulletSize.div(2)));
            shootHomingBullet(DOWN);
            shootHomingBullet(DOWN.add(bulletSize.div(2)));
        }
        else
        {
            shootHomingBullet(LEFT);
            shootHomingBullet(RIGHT);
            shootHomingBullet(UP);
            shootHomingBullet(DOWN);
        }
    }

    private void shootNormalBullet(Vector position, Vector direction)
    {
        gameScene.addObject(new OneWayProjectile(BULLET.setSize(bulletSize), position, direction, 400, this::onHit));
    }

    private void shootHomingBullet(Vector position)
    {
        gameScene.addObject(new HomingProjectile(BULLET.setSize(bulletSize), position, this::getFurthestEnemy, 14, this::onHit));
    }

    private void onHit(Projectile projectile, Enemy enemy)
    {
        enemy.damage(damage);
        projectile.destroy();
    }

    @Override
    protected UpgradeManager createUpgradeManager() 
    {
        return new UpgradeManager
        (
            new UpgradePath(UpgradePathType.ONLY_ONE,
                new Upgrade
                (
                    "More FireRate", 
                    UPGRADE_SHEET.getSprite(0, 1), 
                    "Does Fire super fasst", 
                    350, 
                    (upgrade) -> fireRate = 0.4, 
                    (upgrade) -> fireRate = DEFAULT_FIRE_RATE
                ),
                new Upgrade
                (
                    "Homing Bullets", 
                    UPGRADE_SHEET.getSprite(0, 2), 
                    "Bullets will home in on the enemy", 
                    1500, 
                    (upgrade) -> homing = true, 
                    (upgrade) -> homing = false
                )
            ),
            new UpgradePath(UpgradePathType.ONLY_ONE, 
                new Upgrade
                (
                    "More Range", 
                    UPGRADE_SHEET.getSprite(1, 0), 
                    "Range is increased", 
                    50, 
                    (upgrade) -> range = 225,
                    (upgrade) -> range = DEFAULT_RANGE
                ),
                new Upgrade
                (
                    "Bigger Bullets", 
                    UPGRADE_SHEET.getSprite(1, 1),
                    "Bullets are bigger", 
                    300,
                    (upgrade) -> bulletSize = new Vector(20, 20),
                    (upgrade) -> bulletSize = DEFAULT_BULLET_SIZE
                ),
                new Upgrade
                (
                    "Three Bullets", 
                    UPGRADE_SHEET.getSprite(1, 2),
                    "Three bullets are shot", 
                    600, 
                    (upgrade) -> threeBullets = true, 
                    (upgrade) -> threeBullets = false
                )
            ),
            new UpgradePath(UpgradePathType.ONE_BY_ONE, 
                new Upgrade
                (
                    "More Damage", 
                    UPGRADE_SHEET.getSprite(2, 0),
                    "Does make 3 damage", 
                    250, 
                    (upgrade) -> damage = 3, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "Way more Damage", 
                    UPGRADE_SHEET.getSprite(2, 1),
                    "Does make 4 damage",
                    500, 
                    (upgrade) -> damage = 4, 
                    Upgrade.NO_EFFECT
                ),
                new Upgrade
                (
                    "Even more Damage", 
                    UPGRADE_SHEET.getSprite(2, 2),
                    "Does make 5 damage",
                    680, 
                    (upgrade) -> damage = 5, 
                    Upgrade.NO_EFFECT
                )
            )
        ); 
    }
}
