package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.SpaceshipStructure;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class AiSample1A extends Ai
{
    int direction;
    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        if (Math.random() < 0.01)
        {
            shootCommands.add(new ShootCommand(ShootCommand.LEFT_GUN, 5, 4, 1, 1000));
        }

        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        if (Math.random() < 0.01)
        {
            direction = (int) (Math.random() * 4);
        }

        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        thrustCommands.add(new ThrustCommand(direction, 1));
        //thrustCommands.add(new ThrustCommand((int)ThrustCommand.BACK_THRUSTER, 5));

        //thrustCommands.add(new ThrustCommand(ThrustCommand.COUNTER_CLOCKWISE_THRUSTER, 1));

        return thrustCommands;
    }

    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();



        upgradeData.setUpgrade(UpgradeData.BACK_THRUSTER, 15);
        upgradeData.setUpgrade(UpgradeData.RADIUS, 15);


        return upgradeData;
    }
}
