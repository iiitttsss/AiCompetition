/**
 * this class keep the data associated with a bullet and in-charge on updating it
 */
package AiCompetition.com.bullets;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;

public class Bullet
{
    private float xPos;
    private float yPos;
    private float xVel;
    private float yVel;
    private int radius;
    private int speed;
    private Spaceship owner; // who shoot that bullet - can be used in order to prevent bullets hit their owners
    private int id; // each bullet have a unique ID
    private boolean isActive;

    /**
     * update the bullet
     */
    public void update()
    {
        //TODO
        this.setxPos(this.getxPos() + xVel);
        this.setyPos(this.getyPos() + yVel);

    }

    /**
     * called when the bullet is shot/"created"
     *
     * @param xPos   - the x position of the new bullet
     * @param yPos   - the y position of the new bullet
     * @param xVel   - the x velocity of the new bullet
     * @param yVel   - the y velocity of the new bullet
     * @param radius - the radius of the bullet
     * @param speed  of the bullet
     */
    public void init(Spaceship owner, float xPos, float yPos, float xVel, float yVel, int radius, int speed)
    {
        this.setActive(true);
        this.setOwner(owner);
        this.setxPos(xPos);
        this.setyPos(yPos);
        this.setxVel(xVel);
        this.setyVel(yVel);
        this.setRadius(radius);
        this.setSpeed(speed);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }

    public Spaceship getOwner()
    {
        return owner;
    }

    public void setOwner(Spaceship owner)
    {
        this.owner = owner;
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

    public int getRadius()
    {
        return radius;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

}
