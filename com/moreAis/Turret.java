package AiCompetition.com.moreAis;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class Turret implements Ai
{
    private boolean needToShoot;
    private boolean isOnShootInterval;

    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        if (Math.random() < 0.01)
        {
            isOnShootInterval = (!isOnShootInterval);
        }
        if (isOnShootInterval && Math.random() < 0.1)
        {
            shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 1, 230, 10, 2000));
        }
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();

        float dx = otherSpaceship.getXPosition() - mySpaceship.getXPosition();
        float dy = otherSpaceship.getYPosition() - mySpaceship.getYPosition();

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

        upgradeData.setUpgrade(UpgradeData.ENERGY_GENERATOR, 20);
        upgradeData.setUpgrade(UpgradeData.FRONT_GUN_DAMAGE, 10);
        upgradeData.setUpgrade(UpgradeData.FRONT_GUN_RANGE, 10);
        upgradeData.setUpgrade(UpgradeData.FRONT_GUN_SPEED, 10);



        return upgradeData;
    }
}
