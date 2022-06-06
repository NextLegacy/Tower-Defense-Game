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

            if (upgradePath.currentUpgrade() != null)
                upgradePath.currentUpgrade().activate();
        }
    ),
    ONLY_ONE
    (
        (upgradePath, upgradeIndex) -> true,
        (upgradePath, upgradeIndex) -> 
        {
            if (upgradePath.currentUpgrade() != null && upgradePath.currentUpgrade().isActive())
                upgradePath.currentUpgrade().deactivate();

            upgradePath.currentUpgradeIndex = upgradeIndex;

            if (upgradePath.currentUpgrade() != null)
                upgradePath.currentUpgrade().activate();
        }
    ),
    FIRST_AND_ONLY_ONE
    (
        (upgradePath, upgradeIndex) -> (upgradePath.currentUpgradeIndex == -1 && upgradeIndex == 0) || upgradePath.currentUpgradeIndex >= 0,
        (upgradePath, upgradeIndex) -> 
        {
            if (upgradePath.currentUpgradeIndex == -1)
            {
                upgradePath.currentUpgradeIndex = 0;
                upgradePath.currentUpgrade().activate();
            }

            if (upgradeIndex > 0)
            {
                if (upgradePath.currentUpgrade() != null && upgradePath.currentUpgrade().isActive() && upgradePath.currentUpgrade().upgradeIndex != 0)
                    upgradePath.currentUpgrade().deactivate();

                upgradePath.currentUpgradeIndex = upgradeIndex;

                if (upgradePath.currentUpgrade() != null)
                    upgradePath.currentUpgrade().activate();
            }

            /*
            if (upgradePath.currentUpgrade() != null && upgradePath.currentUpgrade().isActive() && upgradePath.currentUpgradeIndex > 0)
                upgradePath.currentUpgrade().deactivate();

            upgradePath.currentUpgradeIndex = upgradeIndex;

            if (upgradePath.currentUpgrade() != null)
                upgradePath.currentUpgrade().activate();
            */
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
