/**
 * to be more efficient, this class will handle the bullets instead of just putting them into an array
 */
package AiCompetition.com.bullets;

import AiCompetition.com.Ai;

import java.util.ArrayList;

public class BulletManager
{
    private ArrayList<Bullet> allBullets;
    private ArrayList<Bullet> activeBullets;

    public BulletManager()
    {
        this.setAllBullets(new ArrayList<>());
        this.setActiveBullets(new ArrayList<>());
    }

    /**
     * updating the list of what bullets are active
     */
    public void updateActiveBullets()
    {
        this.getActiveBullets().clear();
        for (Bullet bullet : this.getAllBullets())
        {
            if (bullet.isActive())
            {
                this.getActiveBullets().add(bullet);
            }
        }
    }

    /**
     * called in the beginning of a match - deactivate all the bullets
     */
    public void init()
    {
        for (Bullet bullet : this.getAllBullets())
        {
            bullet.setActive(false);
        }
    }

    /**
     * update
     */
    public void update(float deltaTime)
    {
        for (Bullet bullet : this.getActiveBullets())
        {
            bullet.update(deltaTime);
        }
    }

    /**
     * smart bullet adition - instead of creating and deleting Bullet objects, the BulletManager class keep all the
     * in-active bullet objects a activate them as needed. If there are no inactive bullets, then it will create a new one
     *
     * @param xPos   - the x position of the new bullet
     * @param yPos   - the y position of the new bullet
     * @param xVel   - the x velocity of the new bullet
     * @param yVel   - the y velocity of the new bullet
     * @param radius - the radius of the bullet
     * @param speed  of the bullet
     * @return - returning the added bullet object
     */
    public Bullet addBullet(Ai owner, float xPos, float yPos, float xVel, float yVel, int radius, int speed)
    {
        Bullet currentBullet = null;
        boolean foundBullet = false;

        // finding unused bullet
        for (Bullet bullet : this.getAllBullets())
        {
            if (!bullet.isActive())
            {
                currentBullet = bullet;
                foundBullet = true;
                break;
            }
        }

        // if you could not find unused bullet, create a new one
        if (!foundBullet)
        {
            currentBullet = new Bullet();
            currentBullet.setId(this.getAllBullets().size());
            this.getAllBullets().add(currentBullet);
        }

        currentBullet.init(owner, xPos, yPos, xVel, yVel, radius, speed);
        return currentBullet;
    }

    public ArrayList<Bullet> getActiveBullets()
    {
        return activeBullets;
    }

    public void setActiveBullets(ArrayList<Bullet> activeBullets)
    {
        this.activeBullets = activeBullets;
    }

    public ArrayList<Bullet> getAllBullets()
    {
        return allBullets;
    }

    public void setAllBullets(ArrayList<Bullet> allBullets)
    {
        this.allBullets = allBullets;
    }
}
