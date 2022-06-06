package game.gameObjects.tower;

import engine.utils.Sprite;
import engine.utils.Lambda.Func0;

public class TowerPlaceable<T extends Tower>
{
    public final Sprite sprite;
    
    private Func0<T> towerFactory;

    public TowerPlaceable(Sprite sprite, Func0<T> towerFactory)
    {
        this.sprite = sprite;
        this.towerFactory = towerFactory;
    }

    public TowerPlaceable(String imageName, Func0<T> towerFactory)
    {
        this.sprite = new Sprite("/towers/" + imageName + ".png");
        this.towerFactory = towerFactory;
    }

    public Sprite sprite() { return sprite; }

    public T createTower() { return towerFactory.invoke(); }
}
