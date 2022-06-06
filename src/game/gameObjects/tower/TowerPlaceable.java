package game.gameObjects.tower;

import engine.utils.Sprite;
import engine.utils.Lambda.Func0;
import game.scenes.GameScene;

public class TowerPlaceable<T extends Tower>
{
    public final Sprite towerSprite;
    public final Sprite towerInMenuSprite;

    public final double cost;

    private Func0<T> towerFactory;

    public TowerPlaceable(Sprite sprite, double cost, Func0<T> towerFactory)
    {
        this.towerSprite = sprite;
        this.towerInMenuSprite = towerSprite.deriveSprite().setSize(GameScene.PLACEABLE_TOWER_IN_MENU_SIZE);
        this.cost = cost;
        this.towerFactory = towerFactory;
    }

    public TowerPlaceable(String imageName, double cost, Func0<T> towerFactory)
    {
        this.towerSprite = new Sprite("/towers/" + imageName + ".png");
        this.towerInMenuSprite = towerSprite.deriveSprite().setSize(GameScene.PLACEABLE_TOWER_IN_MENU_SIZE);
        this.cost = cost;
        this.towerFactory = towerFactory;
    }

    public T createTower() { return towerFactory.invoke(); }
}
