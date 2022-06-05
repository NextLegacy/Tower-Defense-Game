package game.gameObjects.tower;

import engine.utils.Sprite;
import engine.utils.Lambda.Func0;

public class TowerPlaceable<T extends Tower>
{
    private final Sprite sprite;
    
    private Func0<T> towerFactory;

    public TowerPlaceable(String imageName, Func0<T> towerFactory)
    {
        this.sprite = new Sprite("/towers/" + imageName + ".png");
        this.towerFactory = towerFactory;
    }

    public Sprite sprite() { return sprite; }

    public T creater() { return towerFactory.invoke(); }
}
