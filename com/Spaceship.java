/**
 * each spaceship is controlled by an AI. This class keeps all the spaceship related data and updates the space ship based on the AI's commands
 */
package AiCompetition.com;

public class Spaceship
{
    private SpaceshipStructure structure;
    private float xPos;
    private float yPos;
    private float xVel;
    private float yVel;
    private float direction;

    /**
     * called before the match begins
     * @param structure - the structure of the spaceship as created by the AI
     */
    public void init(SpaceshipStructure structure)
    {
        this.setStructure(structure);
        //TODO
    }

    /**
     * updates the spaceship
     *
     * @param deltaTime
     */
    public void update(float deltaTime)
    {
        //TODO
    }

    public SpaceshipStructure getStructure()
    {
        return structure;
    }

    public void setStructure(SpaceshipStructure structure)
    {
        this.structure = structure;
    }

    public float getxPos()
    {
        return xPos;
    }

    public void setxPos(float xPos)
    {
        this.xPos = xPos;
    }

    public float getyPos()
    {
        return yPos;
    }

    public void setyPos(float yPos)
    {
        this.yPos = yPos;
    }

    public float getxVel()
    {
        return xVel;
    }

    public void setxVel(float xVel)
    {
        this.xVel = xVel;
    }

    public float getyVel()
    {
        return yVel;
    }

    public void setyVel(float yVel)
    {
        this.yVel = yVel;
    }

    public float getDirection()
    {
        return direction;
    }

    public void setDirection(float direction)
    {
        this.direction = direction;
    }
}
