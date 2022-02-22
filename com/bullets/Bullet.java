/**
 * this class keep the data associated with a bullet and in-charge on updating it
 */
package AiCompetition.com.bullets;

import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;

public class Bullet
{
    private static final float SPEED_MULTIPLIER = 20;
    private float xPosition;
    private float yPosition;
    private float xVelocity;
    private float yVelocity;
    private int radius;
    private int speed;
    private int damage;
    private int range;
    private Spaceship owner; // who shoot that bullet - can be used in order to prevent bullets hit their owners
    private int id; // each bullet have a unique ID
    private boolean isActive;
    private int lifeDistance;
    private float startXPosition;
    private float startYPosition;
    private float previousXPosition;
    private float previousYPosition;

    public void savePreviousPosition()
    {
        this.setPreviousXPosition(this.getxPosition());
        this.setPreviousYPosition(this.getyPosition());
    }

    /**
     * called when hitting a spaceship
     */
    public void hitSpaceship()
    {
        this.setActive(false);
    }

    /**
     * @param spaceship - the spaceship
     * @return - returns true id the spaceship collide with the bullet
     */
    public boolean checkForCollisionWithSpaceship(Spaceship spaceship)
    {
        return (this.getxPosition() - spaceship.getxPos()) * (this.getxPosition() - spaceship.getxPos()) + (this.getyPosition() - spaceship.getyPos()) * (this.getyPosition() - spaceship.getyPos()) <=
                (this.getRadius() + spaceship.getSpaceshipStructure().getAttribute(UpgradeData.RADIUS)) * (this.getRadius() + spaceship.getSpaceshipStructure().getAttribute(UpgradeData.RADIUS));
    }

    /**
     * update the bullet
     */
    public void update(float deltaTime)
    {
        //TODO

        this.setxPosition(this.getxPosition() + deltaTime * SPEED_MULTIPLIER * xVelocity);
        this.setyPosition(this.getyPosition() + deltaTime * SPEED_MULTIPLIER * yVelocity);
        float distSq = (this.getxPosition() - this.getStartXPosition()) * (this.getxPosition() - this.getStartXPosition()) + (this.getyPosition() - this.getStartYPosition()) * (this.getyPosition() - this.getStartYPosition());
        if (distSq > this.getLifeDistance() * this.getLifeDistance())
        {
            this.setActive(false);
        }
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
    public void init(Spaceship owner, float xPos, float yPos, float xVel, float yVel, int radius, int speed, int damage, int range)
    {
        this.setActive(true);
        this.setOwner(owner);
        this.setxPosition(xPos);
        this.setyPosition(yPos);
        this.setxVelocity(xVel);
        this.setyVelocity(yVel);
        this.setRadius(radius);
        this.setSpeed(speed);
        this.setDamage(damage);
        this.setLifeDistance(range);

        this.setStartXPosition(xPos);
        this.setStartYPosition(yPos);
        this.setPreviousXPosition(xPos);
        this.setPreviousYPosition(yPos);
    }

    public int getRange()
    {
        return range;
    }

    public void setRange(int range)
    {
        this.range = range;
    }

    public float getPreviousXPosition()
    {
        return previousXPosition;
    }

    public void setPreviousXPosition(float previousXPosition)
    {
        this.previousXPosition = previousXPosition;
    }

    public float getPreviousYPosition()
    {
        return previousYPosition;
    }

    public void setPreviousYPosition(float previousYPosition)
    {
        this.previousYPosition = previousYPosition;
    }

    public int getLifeDistance()
    {
        return lifeDistance;
    }

    public void setLifeDistance(int lifeDistance)
    {
        this.lifeDistance = lifeDistance;
    }

    public float getStartXPosition()
    {
        return startXPosition;
    }

    public void setStartXPosition(float startXPosition)
    {
        this.startXPosition = startXPosition;
    }

    public float getStartYPosition()
    {
        return startYPosition;
    }

    public void setStartYPosition(float startYPosition)
    {
        this.startYPosition = startYPosition;
    }

    public int getDamage()
    {
        return damage;
    }

    public void setDamage(int damage)
    {
        this.damage = damage;
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

    public float getxPosition()
    {
        return xPosition;
    }

    public void setxPosition(float xPosition)
    {
        this.xPosition = xPosition;
    }

    public float getyPosition()
    {
        return yPosition;
    }

    public void setyPosition(float yPosition)
    {
        this.yPosition = yPosition;
    }

    public float getxVelocity()
    {
        return xVelocity;
    }

    public void setxVelocity(float xVelocity)
    {
        this.xVelocity = xVelocity;
    }

    public float getyVelocity()
    {
        return yVelocity;
    }

    public void setyVelocity(float yVelocity)
    {
        this.yVelocity = yVelocity;
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
