package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.SpaceshipStructure;
import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class AiSample1B extends Ai
{
    @Override
    public ArrayList<ShootCommand> shootCommands(float enemyXPos, float enemyYPos, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        if(Math.random() < 0.01)
        {
            shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 5, 5, 10));
        }
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(float enemyXPos, float enemyYPos, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, 1));
         //thrustCommands.add(new ThrustCommand(ThrustCommand.COUNTER_CLOCKWISE_THRUSTER, 1));

        return thrustCommands;
    }

    @Override
    public SpaceshipStructure createStructure()
    {
        return new SpaceshipStructure();
    }
}
