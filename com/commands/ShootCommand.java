package AiCompetition.com.commands;

import AiCompetition.com.CostFunction;

public class ShootCommand extends Command
{

    public static final int FRONT_GUN = 0;
    public static final int BACK_GUN = 1;
    public static final int RIGHT_GUN = 2;
    public static final int LEFT_GUN = 3;

    private int whichGun;
    private int radius;//0-150
    private int speed;//0-100
    private int damage;//0-500
    private int range;//0-10000

    public ShootCommand(int whichGun, int radius, int speed, int damage, int range)
    {
        this.setWhichGun(whichGun);
        this.setRadius(radius);
        this.setSpeed(speed);
        this.setDamage(damage);
        this.setRange(range);
    }

    @Override
    public int calculateCost(float deltaTime)
    {
        float cost = 0;
        //(int) (damage + speed * speed + radius + Math.pow(range, 0.01));
        cost += CostFunction.costFunction(radius, 1.5f, 5);
        cost += CostFunction.costFunction(speed, 1.5f, 5);
        cost += CostFunction.costFunction(damage, 2f, 40);
        cost += CostFunction.costFunction(range, 1.7f, 250);

        final float COST_MULTIPLIER = 5f;

        return (int) (deltaTime * COST_MULTIPLIER * cost);
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

    public int getWhichGun()
    {
        return whichGun;
    }

    public void setWhichGun(int whichGun)
    {
        this.whichGun = whichGun;
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
