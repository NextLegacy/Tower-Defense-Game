package game.gameObjects.tower.upgrades;

public class UpgradePath 
{
    public final UpgradePathType TYPE;
    public final Upgrade[] UPGRADES;

    public int currentUpgradeIndex;

    public UpgradePath(final UpgradePathType type, final Upgrade... upgrades)
    {
        this.currentUpgradeIndex = 0;

        this.TYPE = type;
        this.UPGRADES = upgrades;

        for (int i = 0; i < upgrades.length; i++)
        {
            upgrades[i].upgradeIndex = i + 1;
        }
    }

    public UpgradePathType type() { return TYPE; }
    public Upgrade[] upgrades() { return UPGRADES; }

    public Upgrade currentUpgrade() { return currentUpgradeIndex >= 1 && currentUpgradeIndex <= UPGRADES.length ? UPGRADES[currentUpgradeIndex - 1] : null; }
    public Upgrade getUpgrade(int upgradeIndex) { return UPGRADES[upgradeIndex]; }

    public boolean canBeActivated(int upgradeIndex)
    {
        return TYPE.canBeActivated.invoke(this, upgradeIndex);
    }

    public void activateUpgrade(int upgradeIndex)
    {
        if (canBeActivated(upgradeIndex))
            TYPE.activateUpagrade.invoke(this, upgradeIndex);
    }
}
