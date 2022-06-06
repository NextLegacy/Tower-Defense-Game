package game.gameObjects.tower.upgrades;

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
        
    }
}
