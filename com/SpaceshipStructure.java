/**
 * this class keep the structure of the spaceship - that is how the tiles are connected, in what order and direction
 */
package AiCompetition.com;

public class SpaceshipStructure
{
    private int energyPerTurn; //according to the structure, calculate how much energy this structure generate per turn
    private int maxEnergy;//according to the structure, calculate the maximum amount of energy that can be store
    //TODO init method that loads a structure and calculate all the relevant values
    private int radius;
    private int maxThrustForce;
    private int maxHitPoints;
    private int[] attributes;

    public SpaceshipStructure()
    {
        radius = 32;
        maxHitPoints = 100;
        this.setMaxEnergy(1000);
        this.setEnergyPerTurn(3);
    }

    public SpaceshipStructure(UpgradeData upgradeData)
    {
        this();
        attributes = new int[26];
        this.fixUpdateData(upgradeData);
        for (int upgradeIndex = 0; upgradeIndex < upgradeData.getUpgrades().length; upgradeIndex++)
        {
            int level = upgradeData.getUpgrade(upgradeIndex);
            int value = 0;
            switch (upgradeIndex)
            {
                case UpgradeData.BATTERY:
                    value = level;
                    break;
                case UpgradeData.ENERGY_GENERATOR:
                    value = 1;
                    break;
                case UpgradeData.HIT_POINTS:
                    value = (int) (100 * Math.pow(level + 1, 1.3));
                    break;
                case UpgradeData.RADIUS:
                    break;
                case UpgradeData.BACK_GUN_DAMAGE:
                case UpgradeData.FRONT_GUN_DAMAGE:
                case UpgradeData.LEFT_GUN_DAMAGE:
                case UpgradeData.RIGHT_GUN_DAMAGE:
                    value = (int) (10 * Math.pow(level + 1, 1.3));
                    break;


            }
            attributes[upgradeIndex] = value;
        }

    }

    /**
     * makes sure the upgrades are not too expensive or bellow 0
     *
     * @param upgradeData - the upgrade data from the AI
     */
    private void fixUpdateData(UpgradeData upgradeData)
    {
        System.out.println(upgradeData);
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
        //TODO - covert upgrades to actual numbers
        System.out.println(upgradeData);
    }

    public int[] getAttributes()
    {
        return attributes;
    }

    public void setAttributes(int[] attributes)
    {
        this.attributes = attributes;
    }

    public int getMaxThrustForce()
    {
        return maxThrustForce;
    }

    public void setMaxThrustForce(int maxThrustForce)
    {
        this.maxThrustForce = maxThrustForce;
    }

    public int getMaxHitPoints()
    {
        return maxHitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints)
    {
        this.maxHitPoints = maxHitPoints;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }

    public int getEnergyPerTurn()
    {
        return energyPerTurn;
    }

    public void setEnergyPerTurn(int energyPerTurn)
    {
        this.energyPerTurn = energyPerTurn;
    }

    public int getMaxEnergy()
    {
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy)
    {
        this.maxEnergy = maxEnergy;
    }
}
