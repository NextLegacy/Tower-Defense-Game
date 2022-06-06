package game.gameObjects.tower.upgrades;

import engine.scene.Activateable;
import engine.utils.Sprite;
import engine.utils.Lambda.Action1;

public class Upgrade extends Activateable
{
    public final static  Action1<Upgrade> NO_EFFECT = (upgrade) -> { };
    
    private String name;
    private Sprite sprite;
    private String description;

    private double cost;

    private Action1<Upgrade> activate;
    private Action1<Upgrade> deactivate;

    public Upgrade(
        String name, String imageName, String description, double cost,
        Action1<Upgrade> activate, Action1<Upgrade> deactivate)
    {
        this.name = name;
        this.sprite = new Sprite("/upgrades/" + imageName + ".png");

        this.description = description;
        this.cost = cost;

        this.activate = activate;
        this.deactivate = deactivate;
    }

    public final String name() { return name; }
    public final String description() { return description; }
    public final  double cost() { return cost; }

    public final Sprite sprite() { return sprite; }

    @Override public final void onActivate() { cost = 0; activate.invoke(this); }
    @Override public final void onDeactivate() { deactivate.invoke(this); }
}
