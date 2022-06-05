package game.gameObjects.tower;

import java.awt.Color;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.window.RenderLayer;
import game.gameObjects.tower.towers.TestTower;

public class TowerMenu extends GameObject
{
    private final Vector menuPosition = new Vector(1000, 0);
    private final Vector menuSize = new Vector(280, 720);

    private final Vector towerSize = new Vector(60, 60);

    public Tower selectedTower;

    public TowerPlaceable<?>[] placeableTowers;

    public TowerMenu()
    {
        this.placeableTowers = new TowerPlaceable[]
        {
            new TowerPlaceable<TestTower>("testTower", TestTower::new),
            new TowerPlaceable<TestTower>("testTower", TestTower::new),
            new TowerPlaceable<TestTower>("testTower", TestTower::new),
            new TowerPlaceable<TestTower>("testTower", TestTower::new),
            new TowerPlaceable<TestTower>("testTower", TestTower::new),

        };

        setupTowers();
    }
    
    private void setupTowers()
    {
        int x = 0;
        int y = 0;
        for (int i = 0; i < placeableTowers.length; i++)
        {
            placeableTowers[i].sprite().size = towerSize;

            placeableTowers[i].sprite().position = new Vector
            (
                menuPosition.x + 12.5 + (5 + towerSize.x) * x, menuPosition.y + 20 + y * (placeableTowers[i].sprite().image().getHeight())
            );

            x++;
            if (x > 3)
            {
                x = 0;
                y++;
            }
        }
    }   

    @Override
    public void update(double deltaTime) 
    {
        
    }

    @Override
    public void render(RenderLayer layer, double deltaTime) 
    {
        if (layer.isNot("background")) return;

        layer.graphics().setColor(new Color(0x4aa1fa));

        renderSelectionMenu(layer);
        renderUpgradeMenu(layer);
    }

    public void renderSelectionMenu(RenderLayer layer) 
    {
        layer.graphics().setColor(new Color(0x4aa1fa));
        layer.fillRect(menuPosition, menuSize);

        for (int i = 0; i < placeableTowers.length; i++)
        {
            TowerPlaceable<?> placeableTower = placeableTowers[i];
            layer.renderSprite(placeableTower.sprite());
        }
    }

    public Vector upgradeSize = new Vector(90, 90);
    public Vector upgradeMenuBegin = new Vector(1010, 310);
    public Vector upgradeMenuEnd = new Vector(260, 400);

    public void renderUpgradeMenu(RenderLayer layer) 
    {
        layer.graphics().setColor(new Color(0x8a8a8a));
        layer.fillRect(upgradeMenuBegin, upgradeMenuEnd);
    }
}
