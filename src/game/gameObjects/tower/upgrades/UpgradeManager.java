package game.gameObjects.tower.upgrades;

public class UpgradeManager
{
    public final UpgradePath PATH_ONE;
    public final UpgradePath PATH_TWO;
    public final UpgradePath PATH_THREE;
    
    public UpgradeManager(UpgradePath upgradePathOne, UpgradePath upgradePathTwo, UpgradePath upgradePathThree)
    {
        PATH_ONE = upgradePathOne;
        PATH_TWO = upgradePathTwo;
        PATH_THREE = upgradePathThree;
    }
}
