package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.SpaceshipStructure;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class AiSample1A extends Ai
{
    @Override
    public ArrayList<ShootCommand> shootCommands(float enemyXPos, float enemyYPos, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        if (Math.random() < 0.01)
        {
            shootCommands.add(new ShootCommand(ShootCommand.LEFT_GUN, 5, 4, 1));
        }

        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(float enemyXPos, float enemyYPos, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        //thrustCommands.add(new ThrustCommand((int) (Math.random() * 4), 1));
        thrustCommands.add(new ThrustCommand((int)ThrustCommand.BACK_THRUSTER, 10));

        //thrustCommands.add(new ThrustCommand(ThrustCommand.COUNTER_CLOCKWISE_THRUSTER, 1));

        return thrustCommands;
    }

    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();



        upgradeData.setUpgrade(UpgradeData.BACK_THRUSTER, 15);

        return upgradeData;
    }
}
