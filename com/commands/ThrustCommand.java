package AiCompetition.com.commands;

import AiCompetition.com.CostFunction;

public class ThrustCommand extends Command
{
    public static final int FRONT_THRUSTER = 0;
    public static final int BACK_THRUSTER = 1;
    public static final int RIGHT_THRUSTER = 2;
    public static final int LEFT_THRUSTER = 3;
    public static final int CLOCKWISE_THRUSTER = 4;
    public static final int COUNTER_CLOCKWISE_THRUSTER = 5;

    private int whichThruster;
    private int forceValue;

    public ThrustCommand(int whichThruster, int forceValue)
    {
        this.setWhichThruster(whichThruster);
        this.setForceValue(forceValue);
    }

    @Override
    public int calculateCost(float deltaTime)
    {
        final float COST_MULTIPLIER = 5f;
        return (int) (deltaTime * COST_MULTIPLIER * CostFunction.costFunction(forceValue, 2.5f, 5));
    }

    public int getWhichThruster()
    {
        return whichThruster;
    }

    public void setWhichThruster(int whichThruster)
    {
        this.whichThruster = whichThruster;
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
