package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;
import AiCompetition.com.util.MathUtil;

import java.util.ArrayList;

public class Orbiter implements Ai
{
    private static final int CLOSER_FARTHER_POWER = 10;
    private static final int AVOID_POWER = 30;
    private static final int AVOID_DISTANCE = 500;
    private static final float ORBIT_DISTANCE = 1500;
    private static final float ORBIT_THRESHOLD = 100;
    final int EMERGENCY_ENERGY = 400;
    private boolean inAngle;
    private boolean avoiding;
    private boolean stable;
    private boolean avoidBorder;

    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        if (mySpaceship.getEnergy() > EMERGENCY_ENERGY && !avoidBorder && stable && !avoiding && inAngle && Math.random() < 0.1)
        {
            float dist = MathUtil.dist(mySpaceship.getXPosition(), mySpaceship.getYPosition(), otherSpaceship.getXPosition(), otherSpaceship.getYPosition());
            dist = Math.min(dist + 100, 2000);
            shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 1, 25, 20, (int) dist));
        }
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {

        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();

        this.avoidBullets(thrustCommands, mySpaceship, bulletsPositions);


        if (mySpaceship.getEnergy() > EMERGENCY_ENERGY)
        {
            this.distanceControl(thrustCommands, mySpaceship, otherSpaceship);
        }
        avoidBorder = false;
        if (MathUtil.dist(0, 0, mySpaceship.getXPosition(), mySpaceship.getYPosition()) > borderRadius - 500)
        {
            avoidBorder = true;
            thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, 1));
        } else if (!avoiding)
        {
            this.stabilize(thrustCommands, mySpaceship);
        }

        this.aimingControl(thrustCommands, mySpaceship, otherSpaceship);


        return thrustCommands;
    }

    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();
        upgradeData.setUpgrade(UpgradeData.BACK_THRUSTER, 1);
        upgradeData.setUpgrade(UpgradeData.FRONT_THRUSTER, 4);

        upgradeData.setUpgrade(UpgradeData.RIGHT_THRUSTER, 4);
        upgradeData.setUpgrade(UpgradeData.LEFT_THRUSTER, 4);


        upgradeData.setUpgrade(UpgradeData.FRONT_GUN_SPEED, 5);
        upgradeData.setUpgrade(UpgradeData.FRONT_GUN_RANGE, 10);
        upgradeData.setUpgrade(UpgradeData.FRONT_GUN_DAMAGE, 4);

        upgradeData.setUpgrade(UpgradeData.CLOCKWISE_THRUSTER, 3);
        upgradeData.setUpgrade(UpgradeData.COUNTER_CLOCKWISE_THRUSTER, 3);

        upgradeData.setUpgrade(UpgradeData.ENERGY_GENERATOR, 10);
        upgradeData.setUpgrade(UpgradeData.RADIUS, 2);

        return upgradeData;
    }

    private void stabilize(ArrayList<ThrustCommand> thrustCommands, Spaceship mySpaceship)
    {
        float speed = MathUtil.dist(0, 0, mySpaceship.getXVelocity(), mySpaceship.getYVelocity());

        float velocityDirection = (float) Math.atan2(mySpaceship.getYVelocity(), mySpaceship.getXVelocity());
        if (velocityDirection < 0)
        {
            velocityDirection += 2 * Math.PI;
        }
        float theta = (float) (mySpaceship.getDirection() + Math.PI / 2 - velocityDirection);
        if (theta < 0)
        {
            theta += Math.PI;
        }
        if (theta > Math.PI)
        {
            theta -= Math.PI;
        }
        stable = false;
        if (theta < Math.PI / 2.2)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, 3));
        } else if (theta > Math.PI / 2 + Math.PI / 5)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, 1));
        } else
        {
            stable = true;
        }
    }

    private void distanceControl(ArrayList<ThrustCommand> thrustCommands, Spaceship mySpaceship, Spaceship otherSpaceship)
    {
        //closer/farther
        float dx = otherSpaceship.getXPosition() - mySpaceship.getXPosition();
        float dy = otherSpaceship.getYPosition() - mySpaceship.getYPosition();
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist > (ORBIT_DISTANCE + ORBIT_THRESHOLD))
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, CLOSER_FARTHER_POWER));
        } else if (dist < (ORBIT_DISTANCE - ORBIT_THRESHOLD))
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, CLOSER_FARTHER_POWER));
        }
    }

    private void avoidBullets(ArrayList<ThrustCommand> thrustCommands, Spaceship mySpaceship, ArrayList<Bullet> bulletsPositions)
    {
        //avoid - side to side
        float minDistSq = Integer.MAX_VALUE;
        for (Bullet bullet : bulletsPositions)
        {
            if (bullet.getOwner() != mySpaceship)
            {
                float distSq = MathUtil.distSq(mySpaceship.getXPosition(), mySpaceship.getYPosition(), bullet.getXPosition(), bullet.getYPosition());
                if (distSq < minDistSq)
                {
                    minDistSq = distSq;
                }
            }
        }
        avoiding = false;
        if (Math.sqrt(minDistSq) < AVOID_DISTANCE)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, AVOID_POWER));
            avoiding = true;
        }
    }

    private void aimingControl(ArrayList<ThrustCommand> thrustCommands, Spaceship mySpaceship, Spaceship otherSpaceship)
    {
        float dx = otherSpaceship.getXPosition() - mySpaceship.getXPosition();
        float dy = otherSpaceship.getYPosition() - mySpaceship.getYPosition();
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        //rotate
        float dir = (float) (Math.atan2(dy, dx));
        if (dir < 0)
        {
            dir += 2 * Math.PI;
        }

        float dAngle = (mySpaceship.getDirection()) - dir;

        if (dAngle > Math.PI)
        {
            dAngle -= 2 * Math.PI;
        }
        if (dAngle < -Math.PI)
        {
            dAngle += 2 * Math.PI;
        }

        inAngle = false;
        float otherSpaceshipRadius = otherSpaceship.getSpaceshipStructure().getAttribute(UpgradeData.RADIUS) / 2f;
        float projectX = (float) (otherSpaceship.getXPosition() + otherSpaceshipRadius * Math.cos(dir + Math.PI / 2));
        float projectY = (float) (otherSpaceship.getYPosition() + otherSpaceshipRadius * Math.sin(dir + Math.PI / 2));

        float distanceToProjectionSq = MathUtil.distSq(mySpaceship.getXPosition(), mySpaceship.getYPosition(), projectX, projectY);
        float dynamicAngleThreshold = (float) Math.abs(Math.acos((dist * dist + distanceToProjectionSq - otherSpaceshipRadius * otherSpaceshipRadius) / (2 * dist * Math.sqrt(distanceToProjectionSq))));
        // System.out.println(dynamicAngleThreshold * 180 / Math.PI);

        int rotateLevel = (int) Math.abs(5 * Spaceship.TURNING_MULTIPLIER / dAngle) + 1;
        //        System.out.println(mySpaceship.getSpaceshipStructure().getAttribute(UpgradeData.COUNTER_CLOCKWISE_THRUSTER));
        if (dAngle > dynamicAngleThreshold)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.COUNTER_CLOCKWISE_THRUSTER, rotateLevel));
        } else if (dAngle < -dynamicAngleThreshold)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.CLOCKWISE_THRUSTER, rotateLevel));
        } else
        {
            inAngle = true;
        }
    }
}