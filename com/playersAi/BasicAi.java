package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class BasicAi extends Ai
{
    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        float dy = otherSpaceship.getYPosition() - mySpaceship.getYPosition();
        dy = Math.abs(dy);

        if (dy < 60 && Math.abs(mySpaceship.getYVelocity()) < 1.25f)
        {
            if (otherSpaceship.getXPosition() > mySpaceship.getXPosition())
            {
                shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 5, 10, 10, 2000));
            } else
            {
                shootCommands.add(new ShootCommand(ShootCommand.BACK_GUN, 5, 10, 10, 2000));
            }
        }
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        float dy = otherSpaceship.getYPosition() - mySpaceship.getYPosition();
        float threshold = 50;
        int thrusterPower = 5;
        if (dy > threshold)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, thrusterPower));
        }
        else if (dy < -threshold)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, thrusterPower));
        }
        else
        {
            float speedThreshold = 1;
            float velocity = mySpaceship.getYVelocity();
            if (velocity > speedThreshold)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, thrusterPower));
            }
            else if (velocity < -speedThreshold)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, thrusterPower));
            }
        }
        return thrustCommands;
    }

    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();
        upgradeData.setUpgrade(UpgradeData.ENERGY_GENERATOR, 15);
        upgradeData.setUpgrade(UpgradeData.BATTERY_SIZE, 5);
        upgradeData.setUpgrade(UpgradeData.RADIUS, 5);
        upgradeData.setUpgrade(UpgradeData.HIT_POINTS, 5);


        return upgradeData;
    }
}
