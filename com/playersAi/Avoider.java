package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class Avoider extends Ai
{
    private boolean needToShoot;

    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        if (needToShoot && Math.random() < 0.1)
        {
            shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 5, 5, 10));
        }
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();

        float wantedVelX = 0;
        float wantedVelY = 0;


        for (Bullet bullet : bulletsPositions)
        {
            float dx = mySpaceship.getxPos() - bullet.getxPos();
            float dy = mySpaceship.getyPos() - bullet.getyPos();
            float distSq = (dx * dx + dy * dy);

            dx = Math.min(Math.max(dx, -1), 1);
            dy = Math.min(Math.max(dy, -1), 1);

            wantedVelX += dx / distSq;
            wantedVelY += dy / distSq;

        }
        wantedVelX *= 1000000;
        wantedVelY *= 1000000;

        int power = 10;
        if (wantedVelX > 0)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, power));
        } else if (wantedVelX < 0)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, power));
        }
        if (wantedVelY > 0)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, power));
        } else if (wantedVelY < 0)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, power));
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
