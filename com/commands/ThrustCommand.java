package AiCompetition.com.commands;

import AiCompetition.com.CostFunction;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;

public class ThrustCommand extends Command
{
    public static final int FRONT_THRUSTER = 0;
    public static final int BACK_THRUSTER = 1;
    public static final int RIGHT_THRUSTER = 2;
    public static final int LEFT_THRUSTER = 3;
    public static final int CLOCKWISE_THRUSTER = 4;
    public static final int COUNTER_CLOCKWISE_THRUSTER = 5;

    private int forceValue;

    public ThrustCommand(int whichThruster, int forceValue)
    {
        this.setWhichComponent(whichThruster);
        this.setForceValue(forceValue);
    }

    @Override
    public int calculateCost(float deltaTime)
    {
        switch (this.getWhichComponent())
        {
            case FRONT_THRUSTER:
            case BACK_THRUSTER:
            case RIGHT_THRUSTER:
            case LEFT_THRUSTER:
                final float COST_MULTIPLIER = 8f;
                return (int) (deltaTime * COST_MULTIPLIER * CostFunction.costFunction(forceValue, 2f, 5));
            case CLOCKWISE_THRUSTER:
            case COUNTER_CLOCKWISE_THRUSTER:
                return 1;
            default:
                System.out.println("not supposed to get here!");
                return  1000;
        }
    }

    @Override
    public void fixCommand(Spaceship spaceship)
    {
        this.setForceValue(this.fixAttribute(this.getForceValue(), spaceship, UpgradeData.FRONT_THRUSTER, 1));
    }

    public int getForceValue()
    {
        return forceValue;
    }

    public void setForceValue(int forceValue)
    {
        this.forceValue = forceValue;
    }


}
