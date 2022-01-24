package AiCompetition.com.commands;

public class ThrustCommand extends Command
{
    public static final int FRONT_THRUSTER = 0;
    public static final int BACK_THRUSTER = 1;
    public static final int RIGHT_THRUSTER = 2;
    public static final int LEFT_THRUSTER = 3;
    public static final int RIGHT_TURN_THRUSTER = 4;
    public static final int LEFT_TURN_THRUSTER = 5;

    private int whichThruster;
    private int forceValue;

    public ThrustCommand(int whichThruster, int forceValue)
    {
        this.setWhichThruster(whichThruster);
        this.setForceValue(forceValue);
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
