/**
 * this class keep the structure of the spaceship - that is how the tiles are connected, in what order and direction
 */
package AiCompetition.com;

public class SpaceshipStructure
{
    private int[] attributes;

    public SpaceshipStructure(UpgradeData upgradeData)
    {
        attributes = new int[26];
        this.fixUpdateData(upgradeData);
        for (int upgradeIndex = 0; upgradeIndex < upgradeData.getUpgrades().length; upgradeIndex++)
        {
            int level = upgradeData.getUpgrade(upgradeIndex);
            int value = 0;
            float baseValue = levelToValue(level);
            switch (upgradeIndex)
            {
                case UpgradeData.BATTERY_SIZE:
                    value = (int) (baseValue * 500);
                    break;
                case UpgradeData.ENERGY_GENERATOR:
                    value = (int) (baseValue * 5);
                    break;
                case UpgradeData.HIT_POINTS:
                    value = (int) (baseValue * 100);
                    break;
                case UpgradeData.RADIUS:
                    value = (int) (200 / (level + 2) + 32);
                    break;
                case UpgradeData.BACK_GUN_DAMAGE:
                case UpgradeData.FRONT_GUN_DAMAGE:
                case UpgradeData.LEFT_GUN_DAMAGE:
                case UpgradeData.RIGHT_GUN_DAMAGE:
                    value = (int) (baseValue * 3);
                    break;
                case UpgradeData.BACK_GUN_SPEED:
                case UpgradeData.FRONT_GUN_SPEED:
                case UpgradeData.LEFT_GUN_SPEED:
                case UpgradeData.RIGHT_GUN_SPEED:
                    value = (int) (baseValue * 30);
                    break;
                case UpgradeData.BACK_GUN_RADIUS:
                case UpgradeData.FRONT_GUN_RADIUS:
                case UpgradeData.LEFT_GUN_RADIUS:
                case UpgradeData.RIGHT_GUN_RADIUS:
                    value = (int) (3 * baseValue);
                    break;

                case UpgradeData.BACK_GUN_RANGE:
                case UpgradeData.FRONT_GUN_RANGE:
                case UpgradeData.LEFT_GUN_RANGE:
                case UpgradeData.RIGHT_GUN_RANGE:
                    value = (int) (300 * baseValue);
                    break;

                case UpgradeData.FRONT_THRUSTER:
                case UpgradeData.BACK_THRUSTER:
                case UpgradeData.LEFT_THRUSTER:
                case UpgradeData.RIGHT_THRUSTER:

                    value = (int) (4 * baseValue);
                    break;
                case UpgradeData.CLOCKWISE_THRUSTER:
                case UpgradeData.COUNTER_CLOCKWISE_THRUSTER:
                    value = (int) (6 * baseValue);
                    break;
            }
            attributes[upgradeIndex] = value;
        }
    }

    /**
     * @param level - the level that need to be used
     * @return - the value the component will have
     */
    public static float levelToValue(int level)
    {
        return CostFunction.costFunction(level, 2, 5);
    }

    /**
     * makes sure the upgrades are not too expensive or bellow 0
     *
     * @param upgradeData - the upgrade data from the AI
     */
    private void fixUpdateData(UpgradeData upgradeData)
    {
        for (int i = 0; i < upgradeData.getUpgrades().length; i++)
        {
            if (upgradeData.getUpgrade(i) < 0)
            {
                upgradeData.setUpgrade(i, 0);
            }
        }
        int upgradeIndex = 0;
        while (upgradeData.calculateUpgradesCost() > UpgradeData.MAX_UPGRADE_POINTS)
        {
            if (upgradeData.getUpgrade(upgradeIndex) == 0)
            {
                upgradeIndex++;
            }
            upgradeData.setUpgrade(upgradeIndex, upgradeData.getUpgrade(upgradeIndex) - 1);
        }
    }

    public void setAttribute(int upgradeType, int value)
    {
        attributes[upgradeType] = value;
    }

    public int getAttribute(int upgradeType)
    {
        return attributes[upgradeType];
    }

    public int[] getAttributes()
    {
        return attributes;
    }

    public void setAttributes(int[] attributes)
    {
        this.attributes = attributes;
    }
}
