package AiCompetition.com.moreAis;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class GoTo implements Ai
{
    private boolean thrust;

    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();

        int targetX = 1500;
        int targetY = 1500;

        int td = 50;
        int tv = 1;
        int p = 20;
        int lp = 5;

        int dx = (int) (mySpaceship.getXPosition() - targetX);
        if (dx > td)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, p));
        } else if (dx < -td)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, p));
        } else
        {
            int dvx = (int) (mySpaceship.getXVelocity());
            if (dvx > tv)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, lp));
            } else if (dvx < -tv)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, lp));
            }
        }
        int dy = (int) (mySpaceship.getYPosition() - targetY);

        if (dy > td)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, p));
        } else if (dy < -td)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, p));
        } else
        {
            int dvy = (int) (mySpaceship.getYVelocity());
            if (dvy > tv)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, lp));
            } else if (dvy < -tv)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, lp));
            }
        }


        return thrustCommands;
    }

    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();
//        upgradeData.setUpgrade(UpgradeData.HIT_POINTS, 14);
        upgradeData.setUpgrade(UpgradeData.ENERGY_GENERATOR, 30);

        //upgradeData.setUpgrade(UpgradeData.RIGHT_GUN_DAMAGE, 19);

        return upgradeData;
    }
}
