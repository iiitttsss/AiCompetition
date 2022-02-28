package AiCompetition.com;

public class UpgradeData
{
    public static final int MAX_UPGRADE_POINTS = 50;

    public final static int BATTERY_SIZE = 0;//100-5,000
    public final static int ENERGY_GENERATOR = 1;//1-50

    public final static int HIT_POINTS = 2;//100-10,000
    public final static int RADIUS = 3;//100-32

    public final static int FRONT_THRUSTER = 4;//1-20
    public final static int BACK_THRUSTER = 5;
    public final static int RIGHT_THRUSTER = 6;
    public final static int LEFT_THRUSTER = 7;
    public final static int CLOCKWISE_THRUSTER = 8;
    public final static int COUNTER_CLOCKWISE_THRUSTER = 9;

    public final static int FRONT_GUN_DAMAGE = 10;//10-1,000
    public final static int FRONT_GUN_SPEED = 14;//1-50
    public final static int FRONT_GUN_RADIUS = 18;//5-50
    public final static int FRONT_GUN_RANGE = 22;//50-10,000

    public final static int BACK_GUN_DAMAGE = 11;
    public final static int BACK_GUN_SPEED = 15;
    public final static int BACK_GUN_RADIUS = 19;
    public final static int BACK_GUN_RANGE = 23;

    public final static int RIGHT_GUN_DAMAGE = 12;
    public final static int RIGHT_GUN_SPEED = 16;
    public final static int RIGHT_GUN_RADIUS = 20;
    public final static int RIGHT_GUN_RANGE = 24;

    public final static int LEFT_GUN_DAMAGE = 13;
    public final static int LEFT_GUN_SPEED = 17;
    public final static int LEFT_GUN_RADIUS = 21;
    public final static int LEFT_GUN_RANGE = 25;


    private int[] upgrades;

    public UpgradeData()
    {
        upgrades = new int[26];
    }

    public void setUpgrade(int upgradeType, int level)
    {
        upgrades[upgradeType] = level;
    }

    public int getUpgrade(int upgradeType)
    {
        return upgrades[upgradeType];
    }

    public String toString()
    {
        String s = "";
        for (int level : upgrades)
        {
            s += level;
            s += ",\t";
        }
        return s;
    }

    public int calculateUpgradesCost()
    {
        int sum = 0;
        for (int upgrade : this.upgrades)
        {
            sum += upgrade;
        }
        return sum;
    }

    public int[] getUpgrades()
    {
        return upgrades;
    }

    public void setUpgrades(int[] upgrades)
    {
        this.upgrades = upgrades;
    }
}

