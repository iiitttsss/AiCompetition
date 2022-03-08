/**
 * this class keep the data associated with a bullet and in-charge on updating it
 */
package AiCompetition.com;

import AiCompetition.com.util.MathUtil;

public class Bullet
{
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

    protected void savePreviousPosition()
    {
        this.setPreviousXPosition(this.getXPosition());
        this.setPreviousYPosition(this.getYPosition());
    }

    /**
     * called when hitting a spaceship
     */
    protected void hitSpaceship()
    {
        this.setActive(false);
    }

    /**
     * @param spaceship - the spaceship
     * @return - returns true if the spaceship collide with the bullet
     */
    public boolean checkForCollisionWithSpaceship(Spaceship spaceship)
    {
        return (this.getXPosition() - spaceship.getXPosition()) * (this.getXPosition() - spaceship.getXPosition()) + (this.getYPosition() - spaceship.getYPosition()) * (this.getYPosition() - spaceship.getYPosition()) <=
                (this.getRadius() + spaceship.getSpaceshipStructure().getAttribute(UpgradeData.RADIUS)) * (this.getRadius() + spaceship.getSpaceshipStructure().getAttribute(UpgradeData.RADIUS));
    }

    public boolean advanceCheckForCollisionWithSpaceship(Spaceship spaceship)
    {
        float xa1 = spaceship.getPreviousXPosition();
        float ya1 = spaceship.getPreviousYPosition();
        float xa2 = spaceship.getXPosition();
        float ya2 = spaceship.getYPosition();

        float xb1 = this.getPreviousXPosition();
        float yb1 = this.getPreviousYPosition();
        float xb2 = this.getXPosition();
        float yb2 = this.getYPosition();

        float radiusSum = this.getRadius() + spaceship.getSpaceshipStructure().getAttribute(UpgradeData.RADIUS);

        float distSqAtStart = MathUtil.distSq(xa1, ya1, xb1, yb1);
        float distSqAtEnd = MathUtil.distSq(xa2, ya2, xb2, yb2);
        if (distSqAtStart <= radiusSum * radiusSum || distSqAtEnd <= radiusSum * radiusSum)
        {
            return true;
        } else if ((ya1 - ya2) == (yb1 - yb2) && (xa1 - xa2) == (xb1 - xb2))//if parallel
        {
            return false;
        } else
        {
            float progressAtMinimum = -((xa1 - xb1) * (xa2 + xb1 - xa1 - xb2) + (ya1 - yb1) * (ya2 + yb1 - ya1 - yb2)) /
                    ((xa2 + xb1 - xa1 - xb2) * (xa2 + xb1 - xa1 - xb2) + (ya2 + yb1 - ya1 - yb2) * (ya2 + yb1 - ya1 - yb2));

            if (progressAtMinimum < 0 || progressAtMinimum > 1)
            {
                return false;
            }

            float distSq = MathUtil.distSq(
                    MathUtil.interpretBetweenPositions(xa1, xa2, progressAtMinimum),
                    MathUtil.interpretBetweenPositions(ya1, ya2, progressAtMinimum),
                    MathUtil.interpretBetweenPositions(xb1, xb2, progressAtMinimum),
                    MathUtil.interpretBetweenPositions(yb1, yb2, progressAtMinimum));


            return distSq <= radiusSum * radiusSum;
        }
    }

    /**
     * update the bullet
     */
    protected void update(float deltaTime)
    {
        this.setXPosition(this.getXPosition() + deltaTime * xVelocity);
        this.setYPosition(this.getYPosition() + deltaTime * yVelocity);
        float distSq = (this.getXPosition() - this.getStartXPosition()) * (this.getXPosition() - this.getStartXPosition()) + (this.getYPosition() - this.getStartYPosition()) * (this.getYPosition() - this.getStartYPosition());
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
    protected void init(Spaceship owner, float xPos, float yPos, float xVel, float yVel, int radius, int speed, int damage, int range)
    {
        this.setActive(true);
        this.setOwner(owner);
        this.setXPosition(xPos);
        this.setYPosition(yPos);
        this.setXVelocity(xVel);
        this.setYVelocity(yVel);
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

    protected void setRange(int range)
    {
        this.range = range;
    }

    public float getPreviousXPosition()
    {
        return previousXPosition;
    }

    protected void setPreviousXPosition(float previousXPosition)
    {
        this.previousXPosition = previousXPosition;
    }

    public float getPreviousYPosition()
    {
        return previousYPosition;
    }

    protected void setPreviousYPosition(float previousYPosition)
    {
        this.previousYPosition = previousYPosition;
    }

    public int getLifeDistance()
    {
        return lifeDistance;
    }

    protected void setLifeDistance(int lifeDistance)
    {
        this.lifeDistance = lifeDistance;
    }

    public float getStartXPosition()
    {
        return startXPosition;
    }

    protected void setStartXPosition(float startXPosition)
    {
        this.startXPosition = startXPosition;
    }

    public float getStartYPosition()
    {
        return startYPosition;
    }

    protected void setStartYPosition(float startYPosition)
    {
        this.startYPosition = startYPosition;
    }

    public int getDamage()
    {
        return damage;
    }

    protected void setDamage(int damage)
    {
        this.damage = damage;
    }

    public int getId()
    {
        return id;
    }

    protected void setId(int id)
    {
        this.id = id;
    }

    public boolean isActive()
    {
        return isActive;
    }

    protected void setActive(boolean active)
    {
        isActive = active;
    }

    public Spaceship getOwner()
    {
        return owner;
    }

    protected void setOwner(Spaceship owner)
    {
        this.owner = owner;
    }

    public float getXPosition()
    {
        return xPosition;
    }

    protected void setXPosition(float xPosition)
    {
        this.xPosition = xPosition;
    }

    public float getYPosition()
    {
        return yPosition;
    }

    protected void setYPosition(float yPosition)
    {
        this.yPosition = yPosition;
    }

    public float getXVelocity()
    {
        return xVelocity;
    }

    protected void setXVelocity(float xVelocity)
    {
        this.xVelocity = xVelocity;
    }

    public float getYVelocity()
    {
        return yVelocity;
    }

    protected void setYVelocity(float yVelocity)
    {
        this.yVelocity = yVelocity;
    }

    public int getRadius()
    {
        return radius;
    }

    protected void setRadius(int radius)
    {
        this.radius = radius;
    }

    public int getSpeed()
    {
        return speed;
    }

    protected void setSpeed(int speed)
    {
        this.speed = speed;
    }
}
