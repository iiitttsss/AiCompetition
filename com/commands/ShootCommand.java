package AiCompetition.com.commands;

public class ShootCommand extends Command
{

    public static final int FRONT_GUN = 0;
    public static final int BACK_GUN = 1;
    public static final int RIGHT_GUN = 2;
    public static final int LEFT_GUN = 3;

    private int whichGun;
    private int radius;
    private int speed;
    private int damage;

    public ShootCommand(int whichGun, int radius, int speed, int damage)
    {
        this.setWhichGun(whichGun);
        this.setRadius(radius);
        this.setSpeed(speed);
        this.setDamage(damage);
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
