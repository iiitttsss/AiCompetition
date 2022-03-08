package AiCompetition.com.moreAis;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;
import AiCompetition.com.util.MathUtil;

import java.util.ArrayList;

public class Avoider implements Ai
{

    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
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
            if (bullet.getOwner() != mySpaceship)
            {
                float dx = mySpaceship.getXPosition() - bullet.getXPosition();
                float dy = mySpaceship.getYPosition() - bullet.getYPosition();
                float distSq = (dx * dx + dy * dy);
                if (distSq < minDist)
                {
                    minBullet = bullet;
                    minDist = distSq;
                }
            }
        }

        if (minBullet != null && minDist <= bulletDistance * bulletDistance)
        {
            float dir = (float) Math.atan2(minBullet.getYVelocity(), minBullet.getXVelocity());
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
            float dx = mySpaceship.getXPosition() - otherSpaceship.getXPosition();
            float dy = mySpaceship.getYPosition() - otherSpaceship.getYPosition();
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
