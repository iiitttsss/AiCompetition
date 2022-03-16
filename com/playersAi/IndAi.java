package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.Bullet;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;
import AiCompetition.com.util.MathUtil;

import java.util.ArrayList;

public class IndAi implements Ai
{
    private static final int SHOT_SPEED = 20;

    public static void main(String[] args)
    {
        float a[] = {1, 2, 5, 6, 3, 2};
        float b[] = {44, 66, 99, 77, 33, 22, 55};
        System.out.println("Second Largest: " + sortArrayById(a)[a.length - 2]);
        System.out.println("Second Largest: " + sortArrayById(b)[b.length - 2]);

    }

    private static int[] sortArrayById(float[] arr)
    {
        float[] newArr = new float[arr.length];
        int[] arrIds = new int[arr.length];
        for (int i = 0; i < arr.length; i++)
        {
            newArr[i] = arr[i];
            arrIds[i] = i;
        }

        for (int i = 0; i < newArr.length - 1; i++)
        {
            for (int j = 0; j < newArr.length - i - 1; j++)
            {
                if (newArr[j] > newArr[j + 1])
                {
                    // swap arr[j+1] and arr[j]
                    float tempValue = newArr[j];
                    newArr[j] = newArr[j + 1];
                    newArr[j + 1] = tempValue;

                    int tempInt = arrIds[j];
                    arrIds[j] = arrIds[j + 1];
                    arrIds[j + 1] = tempInt;
                }
            }
        }
        return arrIds;
    }

    private static float minTimeForAnglePAS(float angle, Spaceship mySpaceship, Spaceship otherSpaceship)
    {
        float x0s = otherSpaceship.getXPosition();
        float x0b = mySpaceship.getXPosition();
        float vsx = otherSpaceship.getXVelocity();
        float V = SHOT_SPEED;
        float y0s = otherSpaceship.getYPosition();
        float y0b = mySpaceship.getYPosition();
        float vsy = otherSpaceship.getYVelocity();

        float vCos = (float) (V * Math.cos(angle));
        float vSin = (float) (V * Math.sin(angle));

        float time = -((x0s - x0b) * (vsx - vCos) + (y0s - y0b) * (vsy - vSin)) /
                ((vsx - vCos) * (vsx - vCos) + (vsy - vSin) * (vsy - vSin));

        return Math.max(time, 0);
    }

    private static float x1sPAS(float timeOfAngle, Spaceship otherSpaceship)
    {
        float x0s = otherSpaceship.getXPosition();
        float vsx = otherSpaceship.getXVelocity();
        return x0s + vsx * timeOfAngle;
    }

    private static float y1sPAS(float timeOfAngle, Spaceship otherSpaceship)
    {
        float y0s = otherSpaceship.getYPosition();
        float vsy = otherSpaceship.getYVelocity();
        return y0s + vsy * timeOfAngle;
    }

    private static float x1bPAS(float angle, float timeOfAngle, Spaceship mySpaceship)
    {
        float V = SHOT_SPEED;
        float x0b = mySpaceship.getXPosition();
        float vbx = (float) (V * Math.cos(angle));
        return vbx * timeOfAngle + x0b;
    }

    private static float y1bPAS(float angle, float timeOfAngle, Spaceship mySpaceship)
    {
        float V = SHOT_SPEED;
        float y0b = mySpaceship.getYPosition();
        float vby = (float) (V * Math.sin(angle));
        return vby * timeOfAngle + y0b;
    }

    private static float closestDistanceSqGivenAnglePAS(float angle, Spaceship mySpaceship, Spaceship otherSpaceship)
    {
        float minTimeForAngle = minTimeForAnglePAS(angle, mySpaceship, otherSpaceship);
        return MathUtil.distSq(x1sPAS(minTimeForAngle, otherSpaceship), y1sPAS(minTimeForAngle, otherSpaceship),
                x1bPAS(angle, minTimeForAngle, mySpaceship), y1bPAS(angle, minTimeForAngle, mySpaceship));
    }

    private static float calculateMinimumDistance()
    {
        //TODO
        return 0;
    }

    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        // shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 100, 1, 1, 1000));
        return shootCommands;
    }

    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        final int THRUSTER_MAX_POWER = 5;
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();
        thrustCommands.add(new ThrustCommand(ThrustCommand.CLOCKWISE_THRUSTER, 20));

        // check how much each thruster is effective in the X or Y directions (1 most effective, -1 least effective)
        double[][] thrustersEffectiveness = {
                {Math.cos(mySpaceship.getDirection() + Math.PI), Math.sin(mySpaceship.getDirection() + Math.PI)}, // public static final int FRONT_THRUSTER = 0;
                {Math.cos(mySpaceship.getDirection()), Math.sin(mySpaceship.getDirection())}, // public static final int BACK_THRUSTER = 1;
                {Math.cos(mySpaceship.getDirection() - Math.PI / 2), Math.sin(mySpaceship.getDirection() - Math.PI / 2)}, // public static final int RIGHT_THRUSTER = 2;
                {Math.cos(mySpaceship.getDirection() + Math.PI / 2), Math.sin(mySpaceship.getDirection() + Math.PI / 2)}// public static final int LEFT_THRUSTER = 3;
        };


        // where the spaceships want to go
        float dx = otherSpaceship.getXPosition() - mySpaceship.getXPosition();
        float dy = otherSpaceship.getYPosition() - mySpaceship.getYPosition();

        // check how must each thruster is effective to get to the target
        float[] thrustersAbsoluteEffectiveness = new float[4];
        for (int thrusterId = 0; thrusterId < 4; thrusterId++)
        {
            int sign = (dx * thrustersEffectiveness[thrusterId][0] + dy * thrustersEffectiveness[thrusterId][1] >= 0 ? 1 : -1);
            thrustersAbsoluteEffectiveness[thrusterId] = sign * MathUtil.dist(0, 0,
                    (float) (dx * thrustersEffectiveness[thrusterId][0]), (float) (dy * thrustersEffectiveness[thrusterId][1]));
        }

        // get the 2 effective thrusters
        int[] sortByEffectiveIds = sortArrayById(thrustersAbsoluteEffectiveness);
        float ax = (float) thrustersEffectiveness[sortByEffectiveIds[sortByEffectiveIds.length - 1]][0];
        float ay = (float) thrustersEffectiveness[sortByEffectiveIds[sortByEffectiveIds.length - 1]][1];
        float bx = (float) thrustersEffectiveness[sortByEffectiveIds[sortByEffectiveIds.length - 2]][0];
        float by = (float) thrustersEffectiveness[sortByEffectiveIds[sortByEffectiveIds.length - 2]][1];

        // find the proportion each thruster need to be activated at
        float k = (dx * by - dy * bx) / (dy * ax - dx * ay);
        int strongPower = (int) (THRUSTER_MAX_POWER * (k > 1 ? 1 : k));
        int weakPower = (int) (THRUSTER_MAX_POWER * (k > 1 ? 1 / k : 1));

        //activate the thrusters
        thrustCommands.add(new ThrustCommand(sortByEffectiveIds[sortByEffectiveIds.length - 1], strongPower));
        thrustCommands.add(new ThrustCommand(sortByEffectiveIds[sortByEffectiveIds.length - 2], weakPower));

        return thrustCommands;
    }

    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();
        upgradeData.setUpgrade(UpgradeData.CLOCKWISE_THRUSTER, 10);
        upgradeData.setUpgrade(UpgradeData.ENERGY_GENERATOR, 15);

        upgradeData.setUpgrade(UpgradeData.BACK_THRUSTER, 4);
        upgradeData.setUpgrade(UpgradeData.FRONT_THRUSTER, 4);

        upgradeData.setUpgrade(UpgradeData.RIGHT_THRUSTER, 4);
        upgradeData.setUpgrade(UpgradeData.LEFT_THRUSTER, 4);

        return upgradeData;
    }
}
