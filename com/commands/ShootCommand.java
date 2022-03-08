package AiCompetition.com.commands;

import AiCompetition.com.CostFunction;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;

public class ShootCommand extends Command
{

    public static final int FRONT_GUN = 0;
    public static final int BACK_GUN = 1;
    public static final int RIGHT_GUN = 2;
    public static final int LEFT_GUN = 3;

    private int radius;//0-150
    private int speed;//0-100
    private int damage;//0-500
    private int range;//0-10000

    public ShootCommand(int whichGun, int radius, int speed, int damage, int range)
    {
        this.setWhichComponent(whichGun);
        this.setRadius(radius);
        this.setSpeed(speed);
        this.setDamage(damage);
        this.setRange(range);
    }

    @Override
    public int calculateCost(float deltaTime)
    {
        float cost = 0;
        cost += CostFunction.costFunction(radius/3f, 2f, 5);
        cost += CostFunction.costFunction(speed/30f, 2f, 5);
        cost += CostFunction.costFunction(damage/10f, 2f, 5);
        cost += CostFunction.costFunction(range/300f, 2f, 5);

        final float COST_MULTIPLIER = 15f;

        return (int) (deltaTime * COST_MULTIPLIER * cost);
    }

    @Override
    public void fixCommand(Spaceship spaceship)
    {
        this.setRadius(this.fixAttribute(this.getRadius(),spaceship,UpgradeData.FRONT_GUN_RADIUS,1));
        this.setSpeed(this.fixAttribute(this.getSpeed(),spaceship,UpgradeData.FRONT_GUN_SPEED,0));
        this.setDamage(this.fixAttribute(this.getDamage(),spaceship,UpgradeData.FRONT_GUN_DAMAGE,1));
        this.setRange(this.fixAttribute(this.getRange(),spaceship,UpgradeData.FRONT_GUN_RANGE,1));
    }

    public int getRange()
    {
        return range;
    }

    public void setRange(int range)
    {
        this.range = range;
    }

    public int getDamage()
    {
        return damage;
    }

    public void setDamage(int damage)
    {
        this.damage = damage;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }
}
