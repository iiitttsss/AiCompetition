package AiCompetition.com;

public class CostFunction
{

    /**
     * @param level
     * @return
     */
    /**
     *
     * @param inValue - the level that need to be used
     * @param power
     * @param levelToBeDoubled
     * @return - the value the component will have
     */
    public static float costFunction(float inValue, float power, float levelToBeDoubled)
    {
        final float DOUBLE_CHANGE = (float) (Math.pow((levelToBeDoubled + 1), power) / 2);
        return (float) (Math.pow((inValue + 1), power) + DOUBLE_CHANGE - 1) / DOUBLE_CHANGE;
    }
}
