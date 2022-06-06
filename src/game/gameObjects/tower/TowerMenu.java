package game.gameObjects.tower;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.window.RenderLayer;
import game.gameObjects.tower.towers.TestTower;
import game.gameObjects.tower.upgrades.Upgrade;
import game.gameObjects.tower.upgrades.UpgradePath;
import game.scenes.GameScene;

public class TowerMenu extends GameObject
{
    public TowerPlaceablePreview<?> placeableTowerPreview;
    
    public TowerPlaceable<?>[] placeableTowers;
    
    public GameScene gameScene;
    
    private Tower selectedTower;
    
    private Upgrade selectedUpgrade;

    public TowerMenu()
    {
        this.placeableTowers = new TowerPlaceable[]
        {
            new TowerPlaceable<TestTower>("stein", 2345, TestTower::new),
            new TowerPlaceable<TestTower>("marker", 3245, TestTower::new),
            new TowerPlaceable<TestTower>("testTower", 13, TestTower::new),
            new TowerPlaceable<TestTower>("box_20x20", 234, TestTower::new),
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

    public void setSelectedTower(Tower tower)
    {
        if (this.selectedTower != null)
            this.selectedTower.selected = false;
        
        this.selectedTower = tower;

        if (this.selectedTower != null)
            this.selectedTower.selected = true;
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

                if (input.left().isClickedInBounds(placeableTower.towerInMenuSprite.position, GameScene.PLACEABLE_TOWER_IN_MENU_SIZE))
                {
                    placeableTowerPreview = new TowerPlaceablePreview<>(placeableTower);

                    scene.addObject(placeableTowerPreview);
                    
                    setSelectedTower(null);

                    break;
                }
            }
        }

        //boolean isAnySelected = false;

        if (selectedTower != null)
        for (UpgradePath upgradePath : selectedTower.upgradeManager.UPGRADE_PATHS)
        {
            for (Upgrade upgrade : upgradePath.upgrades())
            {
                if (input.mouse().isInBounds(upgrade.sprite().position, GameScene.UPGRADE_BUTTON_SIZE))
                {
                    selectedUpgrade = upgrade;

                    //isAnySelected = true;

                    break;
                }
            }
        }

        //if (!isAnySelected)
        if (input.mouse().position().isOutOfBounds(GameScene.UPGRADE_MENU_START, GameScene.UPGRADE_MENU_SIZE))
            selectedUpgrade = null;

        if (input.key(KeyEvent.VK_ESCAPE).isDown())
        {
            setSelectedTower(null);

            if (placeableTowerPreview != null)
                if (placeableTowerPreview.isActive())
                    placeableTowerPreview.destroy();
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
        //layer.graphics().setColor(new Color(0x8a8a8a));
        //layer.fillRect(GameScene.UPGRADE_MENU_START, GameScene.UPGRADE_MENU_SIZE);

        if (selectedTower == null)
            return;
        
        int colum = 0;
        for (UpgradePath upgradePath : selectedTower.upgradeManager.UPGRADE_PATHS)
        {
            renderUpgradePath(layer, upgradePath, ++colum);
        }

        if (selectedUpgrade != null)
        {
            renderUpgradeDescription(layer, selectedUpgrade);
        } else
        {
            layer.graphics().setFont(UPGRADE_TEXT_FONT.deriveFont(40f));
            layer.drawStringCentered("Upgrades", GameScene.UPGRADE_MENU_START.add(GameScene.UPGRADE_MENU_SIZE.x / 2, 55));
        }
    }

    private Font UPGRADE_TEXT_FONT = GameScene.TEXT_FONT.deriveFont(25f);
    private Font UPGRADE_DESCRIPTION_FONT = GameScene.DESCRIPTION_FONT.deriveFont(17f);
    private Font UPGRADE_MONEY_FONT = GameScene.MONEY_FONT.deriveFont(20f);

    public void renderUpgradeDescription(RenderLayer layer, Upgrade upgrade)
    {
        layer.graphics().setFont(UPGRADE_TEXT_FONT);
        
        FontMetrics textMetrics = layer.getMetrics(UPGRADE_TEXT_FONT);
        FontMetrics descriptionMetrics = layer.getMetrics(UPGRADE_DESCRIPTION_FONT);

        layer.drawStringCentered(upgrade.name(), GameScene.UPGRADE_MENU_START.add(GameScene.UPGRADE_MENU_SIZE.x / 2, 20));

        layer.graphics().setFont(UPGRADE_DESCRIPTION_FONT);

        int i = 0;
        for (String line : upgrade.description().split("\n"))
        {
            layer.drawStringCentered(line, GameScene.UPGRADE_MENU_START.add(GameScene.UPGRADE_MENU_SIZE.x / 2, textMetrics.getHeight() + 15 + (descriptionMetrics.getHeight()/1.5) * i));
            i++;
        }

        layer.graphics().setFont(UPGRADE_MONEY_FONT);

        layer.drawStringCentered((int)upgrade.cost()+"$", GameScene.UPGRADE_MENU_START.add(GameScene.UPGRADE_MENU_SIZE.x / 2, textMetrics.getHeight() + 25 + (descriptionMetrics.getHeight()/1.5) * i));
    }

    public void renderUpgradePath(RenderLayer layer, UpgradePath path, int colum)
    {
        for (Upgrade upgrade : path.upgrades())
        {
            if (upgrade.isActive())
            {
                layer.setColor(0x0000ffff);
            }
            if (upgrade.isUnlocked)
            {
                layer.setColor(0x00ff00ff);
            }
            else if (path.canBeActivated(upgrade.upgradeIndex) && upgrade.cost() < gameScene.money)
            {
                layer.setColor(0xffff00ff);
            }
            else if (upgrade.cost() > gameScene.money)
            {
                layer.setColor(0xff0000ff);
            }
            else
            {
                layer.setColor(0x101010ff);
            }

            layer.renderSprite(upgrade.sprite());
            layer.drawRect(upgrade.sprite().position, upgrade.sprite().size, 5);
        }
    }
}