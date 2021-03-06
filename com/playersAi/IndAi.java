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

    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 100, 1, 1, 1000));
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

    private static class AimingCalculations
    {
        private static float angle;
        private static float x0s;
        private static float x0b;
        private static float vsx;
        private static float V;
        private static float y0s;
        private static float y0b;
        private static float vsy;

        private static float vCos;
        private static float vSin;

        private static float minTimeForAngle;
        private static float minTimeForAngleDerivative;
        private static float x1s;
        private static float y1s;
        private static float x1b;
        private static float y1b;

        private static float closestDistanceGivenAngle;
        private static float closestDistanceGivenAngleDerivative;

        public static void main(String[] args)
        {
            //testClosestDistanceGivenAngle();
            testCalculateMinimumDistanceFromStartPosition();
        }

        private static void initTestPosition()
        {
            x0s = 0;
            x0b = 2;
            vsx = 5;
            V = 3;
            y0s = 1;
            y0b = 0;
            vsy = 0;
        }

        private static void testClosestDistanceGivenAngle()
        {
            initTestPosition();

            initAngle((float) (Math.PI * 5 / 6));
            System.out.println(minTimeForAngle);
            System.out.println(minTimeForAngleDerivative);
            System.out.println(closestDistanceGivenAngle);
            System.out.println(closestDistanceGivenAngleDerivative);
        }

        private static void initPositions(Spaceship mySpaceship, Spaceship otherSpaceship)
        {

            x0s = otherSpaceship.getXPosition();
            x0b = mySpaceship.getXPosition();
            vsx = otherSpaceship.getXVelocity();
            V = SHOT_SPEED;
            y0s = otherSpaceship.getYPosition();
            y0b = mySpaceship.getYPosition();
            vsy = otherSpaceship.getYVelocity();
        }

        private static void initAngle(float angle)
        {
            AimingCalculations.angle = angle;

            vCos = (float) (V * Math.cos(angle));
            vSin = (float) (V * Math.sin(angle));

            minTimeForAngle = minTimeForAngle();
            minTimeForAngleDerivative = minTimeForAngleDerivative();

            x1s = x1s();
            y1s = y1s();
            x1b = x1b();
            y1b = y1b();

            closestDistanceGivenAngle = closestDistanceGivenAngle();
            closestDistanceGivenAngleDerivative = closestDistanceGivenAngleDerivative();
        }

        /**
         * @return - returning the time (progress) in which the distance between the bullet and the other space is at minimum for a given angle
         */
        private static float minTimeForAngle()
        {
            float time = -((x0s - x0b) * (vsx - vCos) + (y0s - y0b) * (vsy - vSin)) /
                    ((vsx - vCos) * (vsx - vCos) + (vsy - vSin) * (vsy - vSin));

            return Math.max(time, 0);
        }

        private static float minTimeForAngleDerivative()
        {
            float fox = ((x0s - x0b) * (vsx - vCos) + (y0s - y0b) * (vsy - vSin));
            float gox = ((vsx - vCos) * (vsx - vCos) + (vsy - vSin) * (vsy - vSin));
            float ftox = (x0s - x0b) * vSin - (y0s - y0b) * vCos;
            float gtox = 2 * (vsx - vCos) * vSin - 2 * (vsy - vSin) * vCos;

            return -(gox * ftox - fox * gtox) / (gox * gox);
        }

        private static float x1s()
        {
            return x0s + vsx * minTimeForAngle;
        }

        private static float y1s()
        {
            return y0s + vsy * minTimeForAngle;
        }

        private static float x1b()
        {
            return vCos * minTimeForAngle + x0b;
        }

        private static float y1b()
        {
            return vSin * minTimeForAngle + y0b;
        }

        private static float closestDistanceGivenAngle()
        {
            return MathUtil.dist(x1s, y1s, x1b, y1b);
        }

        private static float closestDistanceGivenAngleDerivative()
        {
            float xPart = (x1s - x1b) * (vsx * minTimeForAngleDerivative - (-vSin * minTimeForAngle + vCos * minTimeForAngleDerivative));
            float yPart = (y1s - y1b) * (vsy * minTimeForAngleDerivative - (vCos * minTimeForAngle + vSin * minTimeForAngleDerivative));
            return (xPart + yPart) / closestDistanceGivenAngle;
        }

        private static void testCalculateMinimumDistanceFromStartPosition()
        {
            initTestPosition();
            calculateMinimumDistanceFromStartPosition((float) (Math.PI));

        }

        private static float calculateMinimumDistanceFromStartPosition(float startAngle)
        {
            float angleChange = 0.01f;
            float testAngle = startAngle;
            int lastSign = (closestDistanceGivenAngleDerivative > 0 ? 1 : -1);
            do
            {
                initAngle(testAngle);
                //System.out.println(closestDistanceGivenAngleDerivative);
                testAngle += (closestDistanceGivenAngleDerivative > 0 ? -angleChange : angleChange);
                int newSign = (closestDistanceGivenAngleDerivative > 0 ? 1 : -1);
                if (newSign != lastSign)
                {
                    lastSign = newSign;
                    angleChange /= 2;
                }

            } while (closestDistanceGivenAngle > 0.001);
            System.out.println(testAngle);
            return 0;
        }
    }
}
