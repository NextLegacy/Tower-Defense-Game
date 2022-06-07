package game.gameObjects.tower;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;
import game.gameObjects.tower.towers.CrossbowTower;
import game.gameObjects.tower.towers.ShapeBraker;
import game.gameObjects.tower.towers.TestTower;
import game.gameObjects.tower.upgrades.Upgrade;
import game.gameObjects.tower.upgrades.UpgradePath;
import game.scenes.GameScene;

public class TowerMenu extends GameObject
{
    public TowerPlaceablePreview<?> placeableTowerPreview;
    
    public TowerPlaceable<?>[] placeableTowers;
    public TowerPlaceable<?> mouseOverTowerPlaceable;
    
    public GameScene gameScene;
    
    private Tower selectedTower;
    
    private Upgrade mouseOverUpgrade;

    public TowerMenu()
    {
        this.placeableTowers = new TowerPlaceable[]
        {
            new TowerPlaceable<ShapeBraker>("ShapeBraker", "Zerstört Formen\nliebend gern", 10, new Sprite("towers/shapebraker_default").setSize(ShapeBraker.SIZE), ShapeBraker::new),
            new TowerPlaceable<CrossbowTower>("Crossbow", "Wirft Pfeile\nErweist sich als\nsehr nützlich gegen\nFormen", 3245, new Sprite("towers/crossbow_default").setSize(CrossbowTower.SIZE), CrossbowTower::new),
            new TowerPlaceable<TestTower>("Spongebob", "Wer wohnt in einer Ananas\nganz tiem im Meer", 13, "testTower", TestTower::new),
            new TowerPlaceable<TestTower>("Köln", "Ganz in der\nnähe von Deutschland", 234, "box_20x20", TestTower::new),
            new TowerPlaceable<TestTower>("Steini", "Mr Stein", 2314, "testTower", TestTower::new),
            new TowerPlaceable<TestTower>("Camper Joè", "Auch gennant:\nVercampter Joè", 213, "testTower", TestTower::new),
            new TowerPlaceable<TestTower>("Fortnite", "FORTNITE", 234, "testTower", TestTower::new),
            new TowerPlaceable<TestTower>("Minecraft", "BlockSchlacht", 835, "testTower", TestTower::new),
            new TowerPlaceable<TestTower>("Salzburg", "ungesüßt", 548, "testTower", TestTower::new),
            new TowerPlaceable<TestTower>("Nochmal Peter", "ist wieder der Peter", 452, "testTower", TestTower::new),
            new TowerPlaceable<TestTower>("Patrick Star", "Spongebobs bester Freund", 272, "testTower", TestTower::new),
            new TowerPlaceable<TestTower>("King julien", "Der allbekannte\nKönig King Julien der III", 52725, "testTower", TestTower::new),
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
                if (input.mouse().isInBounds(placeableTower.towerInMenuSprite.position, GameScene.PLACEABLE_TOWER_IN_MENU_SIZE))
                {
                    mouseOverTowerPlaceable = placeableTower;
                    break;
                }
            }
        }

        if (mouseOverTowerPlaceable != null)
            if (!input.mouse().isInBounds(GameScene.PLACEABLE_TOWER_AREA_START, GameScene.PLACEABLE_TOWER_AREA_SIZE)) 
                mouseOverTowerPlaceable = null;

        else if (mouseOverTowerPlaceable != null && input.left().isClicked() && gameScene.money >= mouseOverTowerPlaceable.cost)
        {
            placeableTowerPreview = new TowerPlaceablePreview<>(mouseOverTowerPlaceable);
            
            scene.addObject(placeableTowerPreview);

            mouseOverTowerPlaceable = null;
            setSelectedTower(null);
        }

        if (selectedTower != null)
        for (UpgradePath upgradePath : selectedTower.upgradeManager.UPGRADE_PATHS)
        {
            for (Upgrade upgrade : upgradePath.upgrades())
            {
                if (input.mouse().isInBounds(upgrade.sprite().position, GameScene.UPGRADE_BUTTON_SIZE))
                {
                    mouseOverUpgrade = upgrade;

                    break;
                }
            }
        }

        if (mouseOverUpgrade != null)
        {
            if (mouseOverUpgrade.canBeActivated(gameScene.money))
            if (input.left().isClickedInBounds(mouseOverUpgrade.sprite().position, GameScene.UPGRADE_BUTTON_SIZE))
            {
                if (!mouseOverUpgrade.isUnlocked) 
                    gameScene.money -= mouseOverUpgrade.cost();
                    
                mouseOverUpgrade.activateUpgrade();
            }
        }

        if (input.mouse().position().isOutOfBounds(GameScene.UPGRADE_MENU_START, GameScene.UPGRADE_MENU_SIZE))
            mouseOverUpgrade = null;

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
        if (mouseOverTowerPlaceable != null)
            renderTowerPlaceableDetails(layer);
        else
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

    private Font TEXT_FONT = GameScene.TEXT_FONT.deriveFont(25f);
    private Font DESCRIPTION_FONT = GameScene.DESCRIPTION_FONT.deriveFont(19f);
    private Font MONEY_FONT = GameScene.MONEY_FONT.deriveFont(20f);

    public void renderTowerPlaceableDetails(RenderLayer layer) 
    {
        layer.graphics().setColor(Color.black);
        
        FontMetrics textMetrics = layer.getMetrics(TEXT_FONT.deriveFont(30f));
        FontMetrics descriptionMetrics = layer.getMetrics(DESCRIPTION_FONT);

        layer.graphics().setFont(TEXT_FONT.deriveFont(30f));
        layer.drawStringCentered(mouseOverTowerPlaceable.name, GameScene.UPGRADE_MENU_START.add(GameScene.UPGRADE_MENU_SIZE.x / 2, 50));

        layer.graphics().setFont(DESCRIPTION_FONT);

        int i = 0;
        for (String line : mouseOverTowerPlaceable.description.split("\n"))
        {
            layer.drawStringCentered(line, GameScene.UPGRADE_MENU_START.add(GameScene.UPGRADE_MENU_SIZE.x / 2, textMetrics.getHeight() + 50 + (descriptionMetrics.getHeight()/1.5) * i));
            i++;
        }

        String cost = (int)mouseOverTowerPlaceable.cost + "$";

        layer.graphics().setFont(MONEY_FONT.deriveFont(25f));

        layer.drawStringCentered(cost, GameScene.UPGRADE_MENU_START.add(GameScene.UPGRADE_MENU_SIZE.x / 2, textMetrics.getHeight() + 75 + (descriptionMetrics.getHeight()/1.5) * i));
    
    }

    public void renderUpgradeMenu(RenderLayer layer) 
    {
        if (selectedTower == null)
            return;
        
        int colum = 0;
        for (UpgradePath upgradePath : selectedTower.upgradeManager.UPGRADE_PATHS)
        {
            renderUpgradePath(layer, upgradePath, ++colum);
        }

        layer.graphics().setColor(Color.black);
        
        if (mouseOverUpgrade != null)
        {
            renderUpgradeDescription(layer, mouseOverUpgrade);
        } else
        {
            layer.graphics().setFont(TEXT_FONT.deriveFont(40f));
            layer.drawStringCentered("Upgrades", GameScene.UPGRADE_MENU_START.add(GameScene.UPGRADE_MENU_SIZE.x / 2, 55));
        }
    }

    public void renderUpgradeDescription(RenderLayer layer, Upgrade upgrade)
    {
        FontMetrics textMetrics = layer.getMetrics(TEXT_FONT);
        FontMetrics descriptionMetrics = layer.getMetrics(DESCRIPTION_FONT);
        
        layer.graphics().setFont(TEXT_FONT);

        layer.drawStringCentered(upgrade.name(), GameScene.UPGRADE_MENU_START.add(GameScene.UPGRADE_MENU_SIZE.x / 2, 20));

        layer.graphics().setFont(DESCRIPTION_FONT);

        int i = 0;
        for (String line : upgrade.description().split("\n"))
        {
            layer.drawStringCentered(line, GameScene.UPGRADE_MENU_START.add(GameScene.UPGRADE_MENU_SIZE.x / 2, textMetrics.getHeight() + 15 + (descriptionMetrics.getHeight()/1.5) * i));
            i++;
        }

        String cost = upgrade.isUnlocked ? "Unlocked" : ((int)upgrade.cost() + "$");

        layer.graphics().setFont(MONEY_FONT);

        layer.drawStringCentered(cost, GameScene.UPGRADE_MENU_START.add(GameScene.UPGRADE_MENU_SIZE.x / 2, textMetrics.getHeight() + 25 + (descriptionMetrics.getHeight()/1.5) * i));
    }

    public void renderUpgradePath(RenderLayer layer, UpgradePath path, int colum)
    {
        for (Upgrade upgrade : path.upgrades())
        {
            //Dont render Upgrades that are not activatable
            //if(upgrade.isNotActive() && !path.canBeActivated(upgrade.upgradeIndex))
            //{ 
            //    continue;
                //layer.setColor(0x101010ff);
            //}

            if (upgrade.isActive())
            {
                layer.setColor(0x0000ffff);
            }
            else if (upgrade.isUnlocked)
            {
                layer.setColor(0x00ff00ff);
            }
            else if(!path.canBeActivated(upgrade.upgradeIndex))
            {
                layer.setColor(0x101010ff);
            }
            else if (upgrade.cost() < gameScene.money)
            {
                layer.setColor(0xffff00ff);
            }
            else if (upgrade.cost() > gameScene.money)
            {
                layer.setColor(0xff0000ff);
            }

            layer.renderSprite(upgrade.sprite());
            layer.drawRect(upgrade.sprite().position, upgrade.sprite().size, 5);
        }
    }
}