package game.gameObjects.tower.upgrades;

public class UpgradePath 
{
    private final UpgradePathType type;
    private final Upgrade[] upgrades;

    public int currentUpgradeIndex;

    public UpgradePath(final UpgradePathType type, final Upgrade... upgrades)
    {
        this.currentUpgradeIndex = -1;

        this.type = type;
        this.upgrades = upgrades;

        for (int i = 0; i < upgrades.length; i++)
        {
            upgrades[i].upgradeIndex = i;
        }
    }

    public UpgradePathType type() { return type; }
    public Upgrade[] upgrades() { return upgrades; }

    public Upgrade currentUpgrade() { return upgrades[currentUpgradeIndex]; }
    public Upgrade getUpgrade(int upgradeIndex) { return upgrades[upgradeIndex]; }

    public boolean canBeActivated(int upgradeIndex)
    {
        return type.canBeActivated.invoke(this, upgradeIndex);
    }

    public void activateUpgrade(int upgradeIndex)
    {
        if (canBeActivated(upgradeIndex))
            type.activateUpagrade.invoke(this, upgradeIndex);
    }
}
