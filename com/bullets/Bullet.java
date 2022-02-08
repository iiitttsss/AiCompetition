/**
 * this class keep the data associated with a bullet and in-charge on updating it
 */
package AiCompetition.com.bullets;

import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;

public class Bullet
{
    private static final float SPEED_MULTIPLIER = 100;
    private float xPos;
    private float yPos;
    private float xVel;
    private float yVel;
    private int radius;
    private int speed;
    private int damage;
    private Spaceship owner; // who shoot that bullet - can be used in order to prevent bullets hit their owners
    private int id; // each bullet have a unique ID
    private boolean isActive;
    private int lifeDistance;

    private float startXPos;
    private float startYPos;

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
        return (this.getxPos() - spaceship.getxPos()) * (this.getxPos() - spaceship.getxPos()) + (this.getyPos() - spaceship.getyPos()) * (this.getyPos() - spaceship.getyPos()) <=
                (this.getRadius() + spaceship.getSpaceshipStructure().getAttribute(UpgradeData.RADIUS)) * (this.getRadius() + spaceship.getSpaceshipStructure().getAttribute(UpgradeData.RADIUS));
    }

    /**
     * update the bullet
     */
    public void update(float deltaTime)
    {
        //TODO

        this.setxPos(this.getxPos() + deltaTime * SPEED_MULTIPLIER * xVel);
        this.setyPos(this.getyPos() + deltaTime * SPEED_MULTIPLIER * yVel);
        float distSq = (this.getxPos() - this.getStartXPos()) * (this.getxPos() - this.getStartXPos()) + (this.getyPos() - this.getStartYPos()) * (this.getyPos() - this.getStartYPos());
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
    public void init(Spaceship owner, float xPos, float yPos, float xVel, float yVel, int radius, int speed, int damage)
    {
        this.setActive(true);
        this.setOwner(owner);
        this.setxPos(xPos);
        this.setyPos(yPos);
        this.setxVel(xVel);
        this.setyVel(yVel);
        this.setRadius(radius);
        this.setSpeed(speed);
        this.setDamage(damage);

        this.setLifeDistance(100000);
        this.setStartXPos(xPos);
        this.setStartYPos(yPos);
    }


    public int getLifeDistance()
    {
        return lifeDistance;
    }

    public void setLifeDistance(int lifeDistance)
    {
        this.lifeDistance = lifeDistance;
    }

    public float getStartXPos()
    {
        return startXPos;
    }

    public void setStartXPos(float startXPos)
    {
        this.startXPos = startXPos;
    }

    public float getStartYPos()
    {
        return startYPos;
    }

    public void setStartYPos(float startYPos)
    {
        this.startYPos = startYPos;
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
