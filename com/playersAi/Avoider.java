package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;
import AiCompetition.com.util.MathUtil;

import java.util.ArrayList;

public class Avoider extends Ai
{
    private boolean needToShoot;

    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        if (needToShoot && Math.random() < 0.1)
        {
            shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 5, 5, 10, 1000));
        }
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        final int power = 5;
        final int powerFast = 30;
        final int bulletDistance = 900;
        final int getCloserDistance = 1000;
        final int getFartherDistance = 700;


        float minDist = Integer.MAX_VALUE;
        Bullet minBullet = null;
        for (Bullet bullet : bulletsPositions)
        {
            float dx = mySpaceship.getxPos() - bullet.getxPos();
            float dy = mySpaceship.getyPos() - bullet.getyPos();
            float distSq = (dx * dx + dy * dy);
            if (distSq < minDist)
            {
                minBullet = bullet;
                minDist = distSq;
            }
        }

        if (minBullet != null && minDist <= bulletDistance * bulletDistance)
        {
            float dir = (float) Math.atan2(minBullet.getyVel(), minBullet.getxVel());
            if (dir < 0)
            {
                dir += 2 * Math.PI;
            }
            dir += Math.PI / 2;
            if (dir > 2 * Math.PI)
            {
                dir -= 2 * Math.PI;
            }

            if (dir < Math.PI / 2 || dir > 3 * Math.PI / 2)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, powerFast));
            } else
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, powerFast));
            }
            if (dir < Math.PI)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, powerFast));
            } else
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, powerFast));
            }
        } else if (mySpaceship.getEnergy() > 2500)
        {
            float dx = mySpaceship.getxPos() - otherSpaceship.getxPos();
            float dy = mySpaceship.getyPos() - otherSpaceship.getyPos();
            float distSq = MathUtil.distSq(0, 0, dx, dy);
            if (distSq > getCloserDistance * getCloserDistance)
            {
                if (dx < 0)
                {
                    thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, power));
                } else
                {
                    thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, power));
                }
                if (dy < 0)
                {
                    thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, power));
                } else
                {
                    thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, power));
                }
            } else if (distSq < getFartherDistance * getFartherDistance)
            {
                if (dx < 0)
                {
                    thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, power));
                } else
                {
                    thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, power));
                }
                if (dy < 0)
                {
                    thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, power));
                } else
                {
                    thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, power));
                }
            }
        }

        return thrustCommands;
    }

    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();
        upgradeData.setUpgrade(UpgradeData.RADIUS, 20);
        upgradeData.setUpgrade(UpgradeData.BATTERY_SIZE, 10);

        return upgradeData;
    }
}
