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
        return null;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(float enemyXpos, float enemyYpos, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        thrustCommands.add(new ThrustCommand(ThrustCommand.CLOCKWISE_THRUSTER, 1));
        thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, 1));

        return thrustCommands;
    }

    @Override
    public SpaceshipStructure createStructure()
    {
        return null;
    }
}
