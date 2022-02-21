package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.Global;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class Tester extends Ai
{

    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();

        if (Math.random() < 0.91)
        {
            shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 1, 1, 1, 1000));
            shootCommands.add(new ShootCommand(ShootCommand.BACK_GUN, 1, 1, 1, 1000));
            //shootCommands.add(new ShootCommand(ShootCommand.RIGHT_GUN, 1, 1, 1, 1500));
            //shootCommands.add(new ShootCommand(ShootCommand.LEFT_GUN, 1, 1, 1, 1500));


        }


//        int[] a = new int[0];
//        a[0]=0;

//        boolean b = true;
//        while(b){}


        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        if (Math.random() < 0.9991)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.COUNTER_CLOCKWISE_THRUSTER, 1));
        }
        //thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, 10));



        int startTime = Global.getPro().millis();
        int waitTime = 5;
        while (Global.getPro().millis() < startTime + waitTime)
        {
        }


        return thrustCommands;
    }

    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();
//        upgradeData.setUpgrade(UpgradeData.HIT_POINTS, 14);
        //upgradeData.setUpgrade(UpgradeData.BATTERY_SIZE, 5);
        upgradeData.setUpgrade(UpgradeData.ENERGY_GENERATOR, 30);

        //upgradeData.setUpgrade(UpgradeData.RIGHT_GUN_DAMAGE, 19);

        return upgradeData;
    }
}
