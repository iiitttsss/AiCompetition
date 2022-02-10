package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

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
            shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 5, 5, 10));
        }
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        int power = 20;


        float wantedVelX = 0;
        float wantedVelY = 0;


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
            dx = Math.min(Math.max(dx, -1), 1);
            dy = Math.min(Math.max(dy, -1), 1);

//            wantedVelX = dx;
//            wantedVelY = dy;
            wantedVelX += dx / distSq;
            wantedVelY += dy / distSq;
        }

        if (minBullet != null && minDist <= 500 * 500)
        {
            float dir = (float) Math.atan2(minBullet.getyVel(), minBullet.getxVel());
            System.out.println("before space direction: " + dir);

            if (dir < 0)
            {
                dir += 2 * Math.PI;
            }
            dir += Math.PI / 2;
            if (dir > 2 * Math.PI)
            {
                dir -= 2 * Math.PI;
            }

            System.out.println("space direction: " + dir);
            System.out.println("bullet velocity: " + minBullet.getyVel() + " | " + minBullet.getxVel());


            wantedVelX *= 1000000;
            wantedVelY *= 1000000;

            if (dir < Math.PI / 2 || dir > 3 * Math.PI / 2)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, power));
            } else
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, power));
            }
            if (dir < Math.PI)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, power));
            } else
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, power));
            }
        } else
        {
            float dx = mySpaceship.getxPos() - otherSpaceship.getxPos();
            float dy = mySpaceship.getyPos() - otherSpaceship.getyPos();
            if (dx * dx + dy * dy > 700 * 700)
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
            } else if (dx * dx + dy * dy < 500 * 500)
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
//        upgradeData.setUpgrade(UpgradeData.HIT_POINTS, 14);
        upgradeData.setUpgrade(UpgradeData.RADIUS, 20);
        //upgradeData.setUpgrade(UpgradeData.RIGHT_GUN_DAMAGE, 19);

        return upgradeData;
    }
}
