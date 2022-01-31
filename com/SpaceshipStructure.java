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
    private int maxThrustForce;

    public SpaceshipStructure()
    {
        radius = 40;
    }

    public int getMaxThrustForce()
    {
        return maxThrustForce;
    }

    public void setMaxThrustForce(int maxThrustForce)
    {
        this.maxThrustForce = maxThrustForce;
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
