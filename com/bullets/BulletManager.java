/**
 * to be more efficient, this class will handle the bullets instead of just putting them into an array
 */
package AiCompetition.com.bullets;

import java.util.ArrayList;

public class BulletManager
{
    private ArrayList<Bullet> bullets;

    public BulletManager()
    {
        this.setBullets(new ArrayList<>());
    }

    public void init()
    {
        //TODO - deactivate all the bullets
    }

    public void update()
    {
        //TODO update all the active bullets
    }

    public void addBullet(float xPos, float yPos, float xVel, float yVel, int radius, int speed)
    {
        // TODO - add a bullet (smart addition)
    }

    public ArrayList<Bullet> getBullets()
    {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets)
    {
        this.bullets = bullets;
    }
}
