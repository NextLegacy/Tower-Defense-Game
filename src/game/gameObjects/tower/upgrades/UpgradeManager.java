package game.gameObjects.tower.upgrades;

import engine.math.Vector;
import engine.utils.SpriteSheet;
import game.scenes.GameScene;

public class UpgradeManager
{
    /*
     * UpgradeManager manager = new UpgradeManager(spriteSheet)
     * manager.addPath(UpgradePathType.ONE_BY_ONE)
     * manager.path(0).addUpgrade(title: "", description: "", cost: 100, onActivate: NO_EFFECT, onDeactivate: NO_EFFECT)
     * 
     */

    public final SpriteSheet UPGRADE_SPRITE_SHEET;
    public final UpgradePath[] UPGRADE_PATHS;
    
    public UpgradeManager(final SpriteSheet upgradeSheet, final UpgradePath... upgradePaths)
    {
        this.UPGRADE_SPRITE_SHEET = upgradeSheet;
        this.UPGRADE_PATHS = upgradePaths;
    }

    public void setupUpgrades()
    {
        for (int x = 0; x < UPGRADE_PATHS.length; x++)
        {
            UpgradePath upgradePath = UPGRADE_PATHS[x];

            for (int y = 0; y < upgradePath.upgrades().length; y++)
            {
                Upgrade upgrade = upgradePath.upgrades()[y];

                upgrade.upgradePath = upgradePath;

                upgrade.ui_position = new Vector
                (
                    GameScene.UPGRADE_MENU_START.x + 7.5 + x * (30 + GameScene.UPGRADE_BUTTON_SIZE.x), 
                    GameScene.UPGRADE_MENU_START.y + 110 + y * (15 + GameScene.UPGRADE_BUTTON_SIZE.y)
                );
             
                upgrade.setSprite(UPGRADE_SPRITE_SHEET.getSprite(x, y));

                upgrade.sprite().position = upgrade.ui_position;
                upgrade.sprite().setSize(GameScene.UPGRADE_BUTTON_SIZE);
            }
        }
    }
}
