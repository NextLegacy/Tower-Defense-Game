package game.gameObjects.tower;

import java.awt.Color;

import engine.math.Vector;
import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;
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

    public Tower()
    {
        super(1);
        this.sprite = new Sprite("");
        this.upgradeManager = createUpgradeManager();

        this.upgradeManager.setupSprites();
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