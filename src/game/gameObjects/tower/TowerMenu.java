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

    public TowerPlaceable<?> placeAbleTower;
    public Sprite placeAbleTowerSprite;

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
            placeableTowers[i].sprite().size = GameScene.PLACEABLE_TOWER_SIZE;

            placeableTowers[i].sprite().position = new Vector
            (
                GameScene.MENU_AREA_START.x + 12.5 + (5 + GameScene.PLACEABLE_TOWER_SIZE.x) * x, GameScene.MENU_AREA_START.y + 20 + y * (placeableTowers[i].sprite().image().getHeight())
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
        if (placeAbleTower == null)
        {
            for (TowerPlaceable<?> placeableTower : placeableTowers)
            {
                if (input.left().isClickedInBounds(placeableTower.sprite().position, GameScene.PLACEABLE_TOWER_SIZE, 0.10))
                {
                    System.out.println("Tower selected");

                    placeAbleTower = placeableTower;
                    placeAbleTowerSprite = placeableTower.sprite().deriveSprite();
                    placeAbleTowerSprite.position = GameScene.GAME_AREA_SIZE.div(2);
                    break;
                }
            }
        }
        else
        {
            if (input.key(KeyEvent.VK_ESCAPE).isDown())
            {
                placeAbleTower = null;
                placeAbleTowerSprite = null;

                return;
            }

            placeAbleTowerSprite.position.lerp(
                placeAbleTowerSprite.position, 
                input.mouse().position().clamp(Vector.zero(), GameScene.GAME_AREA_SIZE), //TODO: add Spritesize of Tower to clamp
                deltaTime * 13 
            );
            
            if (input.left().isDown())
            {
                System.out.println("Tower placed");

                Tower tower = placeAbleTower.createTower();
                tower.position = placeAbleTowerSprite.position;
                
                scene.addObject(tower);

                placeAbleTower = null;
                placeAbleTowerSprite = null;
            }
        }
    }

    @Override
    public void render(RenderLayer layer, double deltaTime) 
    {
        if (layer.isNot("background")) return;

        layer.graphics().setColor(new Color(0x4aa1fa));

        renderTowerToPlace(layer);
        renderSelectionMenu(layer);
        renderUpgradeMenu(layer);
    }

    public void renderTowerToPlace(RenderLayer layer)
    {
        if (placeAbleTowerSprite == null) 
            return;
    
        layer.renderSpriteCentered(placeAbleTowerSprite);
    }

    public void renderSelectionMenu(RenderLayer layer) 
    {
        layer.graphics().setColor(new Color(0x4aa1fa));
        layer.fillRect(GameScene.MENU_AREA_START, GameScene.MENU_AREA_SIZE);

        for (int i = 0; i < placeableTowers.length; i++)
        {
            TowerPlaceable<?> placeableTower = placeableTowers[i];
            layer.renderSprite(placeableTower.sprite());
        }
    }

    public void renderUpgradeMenu(RenderLayer layer) 
    {
        layer.graphics().setColor(new Color(0x8a8a8a));
        layer.fillRect(GameScene.upgradeMenuBegin, GameScene.upgradeMenuEnd);
    }
}
