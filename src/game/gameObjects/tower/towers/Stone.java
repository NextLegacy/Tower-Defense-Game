package game.gameObjects.tower.towers;

import engine.math.Vector;
import engine.utils.Sprite;
import engine.utils.SpriteSheet;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.projectile.projectiles.HomingProjectile;
import game.gameObjects.tower.Tower;
import game.gameObjects.tower.upgrades.Upgrade;
import game.gameObjects.tower.upgrades.UpgradeManager;
import game.gameObjects.tower.upgrades.UpgradePath;
import game.gameObjects.tower.upgrades.UpgradePathType;

public class Stone extends Tower
{
    public int damage = 1;
    public static Sprite SPRITE = new Sprite("towers/stein_default");
    public static final Vector SIZE = new Vector(60, 34).mul(1.25);

    public static final SpriteSheet UPGRADE_SHEET = new SpriteSheet("upgrades/shapebraker", new Vector(30, 30));
    
    public Stone(Vector position)
    {
        super(position, SIZE);
        this.sprite = SPRITE.deriveSprite().setSize(SIZE);
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

        scene.addObject(new HomingProjectile(new Sprite("towers/stein_default").setSize(SIZE.mul(0.5)), range, position, position.directionTo(target.position), this::getFurthestEnemy, 5, (projectile, enemey) -> 
        {
            enemey.damage(damage);
            projectile.destroy();
        }));
    }

    @Override
    protected UpgradeManager createUpgradeManager() 
    {
        return new UpgradeManager
        (
            UPGRADE_SHEET,
            new UpgradePath(UpgradePathType.ONE_BY_ONE, 
                new Upgrade(
                    "schneller", 
                    "Wirft schneller.", 
                    10, 
                    (upgrade) -> fireRate = 1,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "noch schneller", 
                    "Wirft noch schnerller", 
                    100, 
                    (upgrade) -> fireRate = 0.2,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "viel schneller",
                    "", 
                    500, 
                    (upgrade) -> fireRate = 0.05,
                    Upgrade.NO_EFFECT
                )
            ),
            new UpgradePath(UpgradePathType.ONLY_ONE,
                new Upgrade(
                    "mehr Reichweite", 
                    "Hat mehr Reichweite.", 
                    150, 
                    (upgrade) -> range = 200,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "noch mehr Reichweite", 
                    "Die Reichweite wird weiter erhöht.", 
                    400, 
                    (upgrade) -> range = 400,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "Ultrasicht",
                    "Sehr hohe Reichweite.", 
                    800, 
                    (upgrade) -> range = 600,
                    Upgrade.NO_EFFECT
                )
            ),
            new UpgradePath(UpgradePathType.FIRST_AND_ONLY_ONE,
                new Upgrade(
                    "mehr Schaden", 
                    "Macht meht Schaden.", 
                    10, 
                    (upgrade) -> damage = 2,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "noch mehr Schaden", 
                    "does shoot way\nmore bullets at once", 
                    400, 
                    (upgrade) -> damage = 4,
                    Upgrade.NO_EFFECT
                ),
                new Upgrade(
                    "Supersteine",
                    "Der höchste Schaden im Spiel.", 
                    700, 
                    (upgrade) -> damage = 7,
                    Upgrade.NO_EFFECT
                )
            )
        );
    }
}
