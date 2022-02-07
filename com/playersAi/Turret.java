package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class Turret extends Ai
{
    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        if (Math.random() < 0.01)
        {
            shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 5, 5, 10));
        }
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        //thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, 1));

        float dx = otherSpaceship.getxPos() - mySpaceship.getxPos();
        float dy = otherSpaceship.getxPos() - mySpaceship.getyPos();

        float dir = (float) Math.atan2(dy, dx);
        float dAngle =  (mySpaceship.getDirection()) - dir;

        System.out.println(dAngle*180/3.1415);

        if (dAngle > 0)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.COUNTER_CLOCKWISE_THRUSTER, 1));
            //System.out.println("1");
        } else
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.CLOCKWISE_THRUSTER, 1));
            //System.out.println("2");

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
