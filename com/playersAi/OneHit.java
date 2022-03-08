package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class OneHit implements Ai
{
    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        float dy = otherSpaceship.getYPosition() - mySpaceship.getYPosition();
        float dx = otherSpaceship.getXPosition() - mySpaceship.getXPosition();
        float threshold = 25;

        if(Math.abs(dx) < threshold && Math.abs(dy) < threshold)
        {
            ShootCommand sc = new ShootCommand(ShootCommand.FRONT_GUN, 1, 0, 500, 50);
            //System.out.println(sc.calculateCost(0.2f));
            shootCommands.add(sc);
        }
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        float dy = otherSpaceship.getYPosition() - mySpaceship.getYPosition();
        float threshold = 20;
        int thrusterPower = 3;
        if (dy > threshold)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, thrusterPower));
        } else if (dy < -threshold)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, thrusterPower));

        }
        float dx = otherSpaceship.getXPosition() - mySpaceship.getXPosition();
        if (dx > threshold)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, thrusterPower));
        } else if (dx < -threshold)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, thrusterPower));

        }
        return thrustCommands;
    }

    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();
        upgradeData.setUpgrade(UpgradeData.HIT_POINTS, 17);
        upgradeData.setUpgrade(UpgradeData.FRONT_GUN_DAMAGE, 10);

        upgradeData.setUpgrade(UpgradeData.BATTERY_SIZE, 5);
        upgradeData.setUpgrade(UpgradeData.ENERGY_GENERATOR, 10);

        upgradeData.setUpgrade(UpgradeData.FRONT_THRUSTER, 2);
        upgradeData.setUpgrade(UpgradeData.BACK_THRUSTER, 2);
        upgradeData.setUpgrade(UpgradeData.RIGHT_THRUSTER, 2);
        upgradeData.setUpgrade(UpgradeData.LEFT_THRUSTER, 2);

        // System.out.println(upgradeData.calculateUpgradesCost());

        return upgradeData;
    }
}
