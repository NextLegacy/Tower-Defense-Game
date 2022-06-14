package game.gameObjects.tower;

import engine.math.Vector;
import engine.utils.Sprite;
import engine.utils.Lambda.Func1;
import game.scenes.GameScene;

public class TowerPlaceable<T extends Tower>
{
    public final Sprite towerSprite;
    public final Sprite towerInMenuSprite;

    public final String name;
    public final String description;
    public final double cost;

    private Func1<T, Vector> towerFactory;

    public TowerPlaceable(String name, String description, double cost, Sprite sprite, Func1<T, Vector> towerFactory)
    {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.towerSprite = sprite;
        this.towerInMenuSprite = towerSprite.deriveSprite().setSize(GameScene.PLACEABLE_TOWER_IN_MENU_SIZE);
        this.towerFactory = towerFactory;
    }

    public TowerPlaceable(String name, String description, double cost, String imageName, Func1<T, Vector> towerFactory)
    {
        this(name, description, cost, new Sprite(imageName).deriveSprite().setSize(GameScene.PLACEABLE_TOWER_IN_MENU_SIZE), towerFactory);
    }

    public T createTower(Vector position) { return towerFactory.invoke(position); }
}
