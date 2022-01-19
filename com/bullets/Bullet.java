package AiCompetition.com.bullets;

public class Bullet
{
    private float xPos;
    private float yPos;
    private float xVel;
    private float yVel;
    private int radius;
    private int speed;

    /**
     * update the bullet
     */
    public void update(float deltaTime)
    {
        //TODO
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
