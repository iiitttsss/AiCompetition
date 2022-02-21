package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class Turret extends Ai
{
    private boolean needToShoot;
    private boolean isOnShootInterval;

    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        if (Math.random() < 0.01)
        {
            isOnShootInterval = (!isOnShootInterval);
        }
        if (isOnShootInterval && Math.random() < 0.1)
        {
            shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 5, 10, 10, 2000));
        }
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();

        float dx = otherSpaceship.getxPos() - mySpaceship.getxPos();
        float dy = otherSpaceship.getyPos() - mySpaceship.getyPos();

        float dir = (float) (Math.atan2(dy, dx));
        if (dir < 0)
        {
            dir += 2 * Math.PI;
        }
        dir = dir * 180 / 3.14f;
        float dAngle = (mySpaceship.getDirection() * 180 / 3.14f) - dir;
        if (dAngle > 180)
        {
            dAngle -= 360;
        }
        if (dAngle < -180)
        {
            dAngle += 360;
        }

        int threshold = 10;

        needToShoot = false;

        if (dAngle > threshold)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.COUNTER_CLOCKWISE_THRUSTER, 1));
        } else if (dAngle < -threshold)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.CLOCKWISE_THRUSTER, 1));
        } else
        {
            needToShoot = true;
        }


        return thrustCommands;
    }

    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();
//        upgradeData.setUpgrade(UpgradeData.HIT_POINTS, 14);

        upgradeData.setUpgrade(UpgradeData.RADIUS, 20);
        upgradeData.setUpgrade(UpgradeData.BATTERY_SIZE, 20);
        //upgradeData.setUpgrade(UpgradeData.RIGHT_GUN_DAMAGE, 19);

        return upgradeData;
    }
}
