package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.SpaceshipStructure;
import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class AiSample1A extends Ai
{
    @Override
    public ArrayList<ShootCommand> shootCommands(float enemyXpos, float enemyYpos, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        if(Math.random() < 0.01)
        {
            shootCommands.add(new ShootCommand(ShootCommand.LEFT_GUN, 5, 5));
        }
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(float enemyXpos, float enemyYpos, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, 2));
         //thrustCommands.add(new ThrustCommand(ThrustCommand.COUNTER_CLOCKWISE_THRUSTER, 1));

        return thrustCommands;
    }

    @Override
    public SpaceshipStructure createStructure()
    {
        return null;
    }
}
