package AiCompetition.com.moreAis;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class Show implements Ai
{
    private int counter = 0;
    private int initial = 50;
    private int between = 100;

    private boolean needToDo(int id)
    {
        return id * between + initial < counter && (id + 1) * between + initial > counter;
    }

    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        counter++;
        System.out.println(counter);
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        if (needToDo(2))
        {
            shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 10, 10, 1, 1000));
        }
        if (needToDo(4))
        {
            shootCommands.add(new ShootCommand(ShootCommand.RIGHT_GUN, 10, 10, 0, 1000));
        }
        if (needToDo(6))
        {
            shootCommands.add(new ShootCommand(ShootCommand.BACK_GUN, 10, 10, 1, 1000));
        }
        if (needToDo(8))
        {
            shootCommands.add(new ShootCommand(ShootCommand.LEFT_GUN, 10, 10, 1, 1000));
        }

        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        if (needToDo(10))
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, 1));
        }
        if (needToDo(12))
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, 1));
        }
        if (needToDo(14))
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, 1));
        }
        if (needToDo(16))
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, 1));
        }
        if (needToDo(18))
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.COUNTER_CLOCKWISE_THRUSTER, 3));
        }
        if (needToDo(20))
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.CLOCKWISE_THRUSTER, 3));
        }
        return thrustCommands;
    }

    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();
        // upgradeData.setUpgrade(UpgradeData.HIT_POINTS, 5);
        return upgradeData;
    }
}
