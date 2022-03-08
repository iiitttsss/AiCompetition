package AiCompetition.com.moreAis;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class Test1 implements Ai
{
    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        // shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 10, 10, 10, 1000));
        float dy = mySpaceship.getYPosition() - otherSpaceship.getYPosition();

        if(Math.abs(dy) < 30 && Math.abs(mySpaceship.getYVelocity()) < 1)
        {
            float dx = mySpaceship.getXPosition() - otherSpaceship.getXPosition();
            if(dx > 0)
            {
                shootCommands.add(new ShootCommand(ShootCommand.BACK_GUN, 10, 10, 10, 1000));
            }
            else
            {
                shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 10, 10, 10, 1000));
            }
        }

        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        // thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, 10));
        float dy = mySpaceship.getYPosition() - otherSpaceship.getYPosition();
        int ThrustPower = 5;

        if(dy > 20)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, ThrustPower));
        }
        else if(dy < -20)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, ThrustPower));
        }
        else
        {
            float velocity = mySpaceship.getYVelocity();
            if(velocity > 0)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, ThrustPower));
            }
            else
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, ThrustPower));
            }
        }
        return thrustCommands;
    }

    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();

        upgradeData.setUpgrade(UpgradeData.LEFT_THRUSTER, 5);
        upgradeData.setUpgrade(UpgradeData.RIGHT_THRUSTER, 5);

        upgradeData.setUpgrade(UpgradeData.FRONT_GUN_RANGE, 5);
        upgradeData.setUpgrade(UpgradeData.FRONT_GUN_SPEED, 5);
        upgradeData.setUpgrade(UpgradeData.FRONT_GUN_DAMAGE, 5);

        upgradeData.setUpgrade(UpgradeData.BACK_GUN_RANGE, 5);
        upgradeData.setUpgrade(UpgradeData.BACK_GUN_SPEED, 5);
        upgradeData.setUpgrade(UpgradeData.BACK_GUN_DAMAGE, 5);

        upgradeData.setUpgrade(UpgradeData.ENERGY_GENERATOR, 10);

        System.out.println(upgradeData.calculateUpgradesCost());

        return upgradeData;
    }
}
