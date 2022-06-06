package game.gameObjects.tower.upgrades;

import engine.math.Vector;
import game.scenes.GameScene;

public class UpgradeManager
{
    public final UpgradePath[] UPGRADE_PATHS;
    
    public UpgradeManager(UpgradePath... upgradePaths)
    {
        if (upgradePaths.length > 3)
        {
            throw new IllegalArgumentException("UpgradeManager needs less than 4 upgrade paths");
        }

        this.UPGRADE_PATHS = upgradePaths;
    }

    public void setupSprites()
    {
        for (int i = 0; i < UPGRADE_PATHS.length; i++)
        {
            UpgradePath path = UPGRADE_PATHS[i];

            for (int j = 0; j < path.upgrades().length; j++)
            {
                Upgrade upgrade = path.upgrades()[j];

                upgrade.ui_position = new Vector
                (
                    GameScene.UPGRADE_MENU_START.x + 7.5 + i * (30 + GameScene.UPGRADE_BUTTON_SIZE.x), 
                    GameScene.UPGRADE_MENU_START.y + 110  + j * (15 + GameScene.UPGRADE_BUTTON_SIZE.y)
                );

                upgrade.sprite().position = upgrade.ui_position;
                upgrade.sprite().setSize(GameScene.UPGRADE_BUTTON_SIZE);
            }
        }
    }
}
