package game.gameObjects.tower;

import java.awt.Color;
import java.awt.event.KeyEvent;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;
import game.gameObjects.tower.towers.TestTower;
import game.scenes.GameScene;

public class TowerMenu extends GameObject
{
    public Tower selectedTower;

    public TowerPlaceablePreview<?> placeableTowerPreview;

    public TowerPlaceable<?>[] placeableTowers;

    public GameScene gameScene;

    public TowerMenu()
    {
        this.placeableTowers = new TowerPlaceable[]
        {
            new TowerPlaceable<TestTower>("testTower", 2345, TestTower::new),
            new TowerPlaceable<TestTower>("testTower", 3245, TestTower::new),
            new TowerPlaceable<TestTower>("testTower", 13, TestTower::new),
            new TowerPlaceable<TestTower>("testTower", 234, TestTower::new),
            new TowerPlaceable<TestTower>("testTower", 2314, TestTower::new),
            new TowerPlaceable<TestTower>("testTower", 213, TestTower::new),
            new TowerPlaceable<TestTower>("testTower", 234, TestTower::new),
            new TowerPlaceable<TestTower>("testTower", 835, TestTower::new),
            new TowerPlaceable<TestTower>("testTower", 548, TestTower::new),
            new TowerPlaceable<TestTower>("testTower", 452, TestTower::new),
            new TowerPlaceable<TestTower>("testTower", 272, TestTower::new),
            new TowerPlaceable<TestTower>("testTower", 52725, TestTower::new),
        };

        setupTowers();
    }
    
    private void setupTowers()
    {
        int x = 0;
        int y = 0;
        for (int i = 0; i < placeableTowers.length; i++)
        {
            placeableTowers[i].towerInMenuSprite.position = new Vector
            (
                GameScene.MENU_AREA_START.x + 5 + x * (10 + GameScene.PLACEABLE_TOWER_IN_MENU_SIZE.x), 
                GameScene.MENU_AREA_START.y + 35 + y * (35 + GameScene.PLACEABLE_TOWER_IN_MENU_SIZE.y)
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
    public void start() 
    {
        this.gameScene = (GameScene) scene;
    }

    @Override
    public void update(double deltaTime) 
    {
        if (placeableTowerPreview == null || placeableTowerPreview.isNotActive())
        {
            for (TowerPlaceable<?> placeableTower : placeableTowers)
            {
                if (gameScene.money < placeableTower.cost)
                    continue;

                if (input.left().isClickedInBounds(placeableTower.towerInMenuSprite.position, GameScene.PLACEABLE_TOWER_IN_MENU_SIZE, 0.10))
                {
                    System.out.println("Tower selected");

                    placeableTowerPreview = new TowerPlaceablePreview<>(placeableTower);

                    gameScene.addObject(placeableTowerPreview);
                    
                    break;
                }
            }
        }
        else
        {
            if (input.key(KeyEvent.VK_ESCAPE).isDown())
            {
                placeableTowerPreview.destroy();

                return;
            }
        }
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
        layer.graphics().setColor(Color.gray);
        layer.fillRect(GameScene.MENU_AREA_START, GameScene.MENU_AREA_SIZE);

        for (int i = 0; i < placeableTowers.length; i++)
        {
            TowerPlaceable<?> placeableTower = placeableTowers[i];
            layer.renderSprite(placeableTower.towerInMenuSprite);

            layer.graphics().setFont(GameScene.MONEY_FONT.deriveFont(25f - (placeableTower.cost+"$").length()));

            if (gameScene.money >= placeableTower.cost)
            {
                layer.graphics().setColor(Color.GREEN);
            }
            else
            {
                layer.graphics().setColor(Color.RED);
            }
            
            layer.drawStringCentered((int)placeableTower.cost+"$", placeableTower.towerInMenuSprite.position.add(GameScene.PLACEABLE_TOWER_IN_MENU_SIZE.x / 2, GameScene.PLACEABLE_TOWER_IN_MENU_SIZE.y * 1.25));
        }
    }

    public void renderUpgradeMenu(RenderLayer layer) 
    {
        layer.graphics().setColor(new Color(0x8a8a8a));
        layer.fillRect(GameScene.UPGRADE_MENU_BEGIN, GameScene.UPGRADE_MENU_END);
    }
}
