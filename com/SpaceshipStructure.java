/**
 * this class keep the structure of the spaceship - that is how the tiles are connected, in what order and direction
 */
package AiCompetition.com;

public class SpaceshipStructure
{
    private int energyPerTurn; //according to the structure, calculate how much energy this structure generate per turn
    private int maxEnergy;//according to the structure, calculate the maximum amount of energy that can be store
    //TODO init method that loads a structure and calculate all the relevant values
    private int radius;
    private int maxHitPoints;

    public SpaceshipStructure()
    {
        radius = 25;
        maxHitPoints = 100;
    }

    public int getMaxHitPoints()
    {
        return maxHitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints)
    {
        this.maxHitPoints = maxHitPoints;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }

    public int getEnergyPerTurn()
    {
        return energyPerTurn;
    }

    public void setEnergyPerTurn(int energyPerTurn)
    {
        this.energyPerTurn = energyPerTurn;
    }

    public int getMaxEnergy()
    {
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy)
    {
        this.maxEnergy = maxEnergy;
    }
}
