package AiCompetition.com.moreAis;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class TestReflection implements Ai
{
    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        // shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 10, 10, 10, 1000));
        //System.out.println("shootCommands");
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        // thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, 10));
        //System.out.println("thrustCommands");
        return thrustCommands;
    }

    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();
        // upgradeData.setUpgrade(UpgradeData.HIT_POINTS, 5);
        // System.out.println("creating structure");
        return upgradeData;
    }
}
