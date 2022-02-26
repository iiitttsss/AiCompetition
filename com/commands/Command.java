/**
 * this is what an AI will use to control a spaceship
 */
package AiCompetition.com.commands;

import AiCompetition.com.Spaceship;

public abstract class Command
{
    private int whichComponent;

    public abstract int calculateCost(float deltaTime);

    public abstract void fixCommand(Spaceship spaceship);

    public int fixAttribute(int currentValue, Spaceship spaceship, int attributeConstant, int minValue)
    {
        if (currentValue < minValue)
        {
            return minValue;
        } else
        {
            int maxValue = spaceship.getSpaceshipStructure().getAttribute(attributeConstant + this.getWhichComponent());
            if (currentValue > maxValue)
            {
                return maxValue;
            }
        }
        return currentValue;
    }

    public int getWhichComponent()
    {
        return whichComponent;
    }

    public void setWhichComponent(int whichComponent)
    {
        this.whichComponent = whichComponent;
    }

}
