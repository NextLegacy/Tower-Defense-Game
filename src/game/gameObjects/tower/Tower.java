package game.gameObjects.tower;

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

    protected boolean rotateTowardsEnemy = false;

    protected double range;
    protected double fireRate;

    private double time;

    public Tower()
    {
        super(1);
        this.upgradeManager = createUpgradeManager();
    }

    protected void fire() { }
    protected abstract UpgradeManager createUpgradeManager();

    @Override
    public final void update(double deltaTime)
    {
        time += deltaTime;

        if (time >= fireRate)
        {
            time = 0;
            fire();
        }
    }

    @Override
    public void render(RenderLayer layer, double deltaTime) 
    {
        if (layer.isNot("towers"))
            return;

        if (selected)
            layer.graphics().fillArc((int)position.x, (int)position.y, (int)range, (int)range, 0, 360);

        layer.renderSpriteCentered(sprite);
    }

    public GameScene gameScene() { return (GameScene) scene; }
    public UpgradeManager upgradeManager() { return upgradeManager; }
}