package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class GoTo extends Ai
{
    private boolean thrust;

    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();

        int targetX = 2500;
        int targetY = 2500;

        int td = 50;
        int tv = 1;
        int p = 10;
        int dx = (int) (mySpaceship.getxPos() - targetX);
        if (dx > td)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, p));
        } else if (dx < -td)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, p));
        } else
        {
            int dvx = (int) (mySpaceship.getxVel());
            if (dvx > tv)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, p));
            } else if (dvx < -tv)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, p));
            }
        }
        int dy = (int) (mySpaceship.getyPos() - targetY);

        if (dy > td)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, p));
        } else if (dy < -td)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, p));
        } else
        {
            int dvy = (int) (mySpaceship.getyVel());
            if (dvy > tv)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, p));
            } else if (dvy < -tv)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, p));
            }
        }


        return thrustCommands;
    }

    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();
//        upgradeData.setUpgrade(UpgradeData.HIT_POINTS, 14);
        upgradeData.setUpgrade(UpgradeData.RADIUS, 20);
        //upgradeData.setUpgrade(UpgradeData.RIGHT_GUN_DAMAGE, 19);

        return upgradeData;
    }
}
