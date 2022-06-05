package game.gameObjects.tower.upgrades;

import engine.utils.Lambda.Action2;
import engine.utils.Lambda.Func2;

public enum UpgradePathType 
{
    ONE_BY_ONE
    (
        (upgradePath, upgradeIndex) -> upgradePath.currentUpgradeIndex + 1 == upgradeIndex,
        (upgradePath, upgradeIndex) -> 
        {
            upgradePath.currentUpgradeIndex++;
        }
    ),
    ONLY_ONE
    (
        (upgradePath, upgradeIndex) -> true,
        (upgradePath, upgradeIndex) -> 
        {
            if (upgradePath.currentUpgrade().isActive())
                upgradePath.currentUpgrade().deactivate();

            upgradePath.currentUpgradeIndex = upgradeIndex;
        }
    ),
    FIRST_AND_ONLY_ONE
    (
        (upgradePath, upgradeIndex) -> (upgradePath.currentUpgradeIndex == -1 && upgradeIndex == 0) || upgradePath.currentUpgradeIndex >= 0,
        (upgradePath, upgradeIndex) -> 
        {
            if (upgradePath.currentUpgrade().isActive() && upgradePath.currentUpgradeIndex < 0)
                upgradePath.currentUpgrade().deactivate();

            upgradePath.currentUpgradeIndex = upgradeIndex;
        }
    ),
    ;
    
    public Func2<Boolean, UpgradePath, Integer> canBeActivated;
    public Action2<UpgradePath, Integer> activateUpagrade;

    UpgradePathType(Func2<Boolean, UpgradePath, Integer> canBeActivated, Action2<UpgradePath, Integer> activateUpagrade)
    {
        this.canBeActivated = canBeActivated;
        this.activateUpagrade = activateUpagrade;
    }
}
