package game.gameObjects.tower;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.scene.GameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.tower.upgrades.UpgradeManager;
import game.scenes.GameScene;

public abstract class Tower extends CollisionGameObject
{
    protected final UpgradeManager upgradeManager;

    public boolean selected = false;

    protected Sprite sprite;

    protected double range;
    protected double fireRate;

    protected GameScene gameScene;

    private double time;

    public Tower(Vector position)
    {
        super(1);

        this.position = position;

        this.sprite = new Sprite("");
        this.upgradeManager = createUpgradeManager();

        this.upgradeManager.setupUpgrade();
    }

    public Enemy getFurthestEnemy(Vector position)
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
        size = sprite.size;
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
        if (layer.isNot("debug"))
            return;

        layer.graphics().setColor(new Color(20, 20, 20, 50));
        
        if(selected)
            layer.graphics().fillOval((int)(position.x-range), (int)(position.y-range), (int)range*2, (int)range*2);    

        layer.renderSpriteCentered(sprite);
    }

    public void lookAt(Vector target)
    {
        sprite.rotation = target.sub(position).angle();
    }
}