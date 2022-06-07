package game.gameObjects.tower;

import engine.utils.Sprite;
import engine.utils.Lambda.Func0;
import game.scenes.GameScene;

public class TowerPlaceable<T extends Tower>
{
    public final Sprite towerSprite;
    public final Sprite towerInMenuSprite;

    public final String name;
    public final String description;
    public final double cost;

    private Func0<T> towerFactory;

    public TowerPlaceable(String name, String description, double cost, Sprite sprite, Func0<T> towerFactory)
    {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.towerSprite = sprite;
        this.towerInMenuSprite = towerSprite.deriveSprite().setSize(GameScene.PLACEABLE_TOWER_IN_MENU_SIZE);
        this.towerFactory = towerFactory;
    }

    public TowerPlaceable(String name, String description, double cost, String imageName, Func0<T> towerFactory)
    {
        this(name, description, cost, new Sprite(imageName).deriveSprite().setSize(GameScene.PLACEABLE_TOWER_IN_MENU_SIZE), towerFactory);
    }

    public T createTower() { return towerFactory.invoke(); }
}
