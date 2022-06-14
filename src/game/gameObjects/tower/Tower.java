package game.gameObjects.tower;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.scene.GameObject;
import engine.utils.Sprite;
import engine.utils.SpriteSheet;
import engine.window.RenderLayer;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.tower.upgrades.UpgradeManager;
import game.scenes.GameScene;

public abstract class Tower extends CollisionGameObject
{
    protected final UpgradeManager upgradeManager;

    public boolean selected = false;

    /**
     * 000 100 200 300
     * 010 110 210 310
     * 020 120 220 320
     * 030 130 230 330
     * 
     * 001 101 201 301
     * 011 111 211 311
     * 021 121 221 321
     * 031 131 231 331
     * 
     * 002 102 202 302
     * 012 112 212 312
     * 022 122 222 322
     * 032 132 232 332
     * 
     * 003 103 203 303
     * 013 113 213 313
     * 023 123 223 323
     * 033 133 233 333
     */
    protected SpriteSheet spriteSheet;

    protected Sprite sprite;

    protected double range;
    protected double fireRate;

    protected GameScene gameScene;

    private double time;

    protected double rotation;

    public Tower(Vector position, Vector size)
    {
        super(1);

        this.size = size;

        this.position = position;

        this.rotation = 0;

        this.sprite = new Sprite("");
        this.upgradeManager = createUpgradeManager();

        this.upgradeManager.setupUpgrades();
    }

    public Enemy getFurthestEnemy(Vector position)
    {
        return getFurthestEnemy(position, range);
    }

    public Enemy getFurthestEnemy(Vector position, double range)
    {
        ArrayList<CollisionGameObject> gameObjects = collisions.objectsInCircle(2, position.x, position.y, range);

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

        return target;
    }

    @Override
    public void start() 
    {
        this.gameScene = (GameScene) scene;
    }
    
    protected void fire() { }

    protected abstract UpgradeManager createUpgradeManager();

    @Override
    public void update(double deltaTime)
    {
        sprite.position = position;

        time += deltaTime;

        if (time >= fireRate)
        {
            time = 0;
            fire();
        }

        if (input.left().isClickedInBounds(sprite.position.sub(sprite.size.div(2)), sprite.size))
        {
            gameScene.towerMenu.setSelectedTower(this);
        }

        if (selected && input.key(KeyEvent.VK_DELETE).isDown())
        {
            destroy();
            gameScene.towerMenu.setSelectedTower(null);
        }
    }

    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if (layer.isNot("towers"))
            return;

        layer.graphics().setColor(new Color(20, 20, 20, 50));
        
        if(selected)
            layer.graphics().fillOval((int)(position.x-range), (int)(position.y-range), (int)range*2, (int)range*2);    

        if (spriteSheet != null)
            sprite = spriteSheet.getSprite
            (
                upgradeManager.UPGRADE_PATHS[0].currentUpgradeIndex, 
                upgradeManager.UPGRADE_PATHS[1].currentUpgradeIndex + upgradeManager.UPGRADE_PATHS[2].currentUpgradeIndex * 4
            ).setPosition(position).setSize(size).setRotation(rotation);

        layer.renderSpriteCentered(sprite);
    }

    public void lookAt(Vector target)
    {
        rotation = target.sub(position).angle();
    }
}