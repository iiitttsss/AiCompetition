/**
 * Handle a match between 2 spaceships
 */
package AiCompetition.com;

import AiCompetition.com.bullets.BulletManager;

public class Match
{
    private Ai ai1;
    private Ai ai2;

    private Spaceship spaceship1;
    private Spaceship spaceship2;
    private BulletManager bulletManager;

    public Match()
    {
        this.setBulletManager(new BulletManager());
        this.setSpaceship1(new Spaceship());
        this.setSpaceship2(new Spaceship());
    }

    /**
     * Called at the beginning of the match. Loads and initializes the match/AIs/spaceships.
     *
     * @param ai1 - the 1st AI
     * @param ai2 - the 2nd AI
     */
    public void init(Ai ai1, Ai ai2)
    {
        this.getBulletManager().init();
        this.setAi1(ai1);
        this.setAi2(ai2);
        this.getSpaceship1().init(this.getAi1().createStructure());
        this.getSpaceship2().init(this.getAi2().createStructure());

    }

    /**
     * called after the init method. In-charge on running all the iterations.
     */
    public void run()
    {
        while (!this.isGameOver())
        {
            this.update();
        }
    }

    /**
     * Handle each iteration of the simulation
     */
    private void update()
    {
        // TODO - add all the steps bellow

        // - update active bullets
        this.getBulletManager().updateActiveBullets();
        // - AIs shoot (try-catch)
        // - AIs update thrusters (try-catch)
        // - spaceships move
        // - spaceships handle screed edges
        // - spaceship/bullets collisions - take damage | bullets deactivate as needed
        // - simulation log
    }

    public Spaceship getSpaceship1()
    {
        return spaceship1;
    }

    public void setSpaceship1(Spaceship spaceship1)
    {
        this.spaceship1 = spaceship1;
    }

    public Spaceship getSpaceship2()
    {
        return spaceship2;
    }

    public void setSpaceship2(Spaceship spaceship2)
    {
        this.spaceship2 = spaceship2;
    }

    public BulletManager getBulletManager()
    {
        return bulletManager;
    }

    public void setBulletManager(BulletManager bulletManager)
    {
        this.bulletManager = bulletManager;
    }

    /**
     * @return - returns true if game is over
     */
    private boolean isGameOver()
    {
        //TODO - game over conditions:
        //1. one or both of the spaceships are dead
        //2. times-up
        //3. one or both of the AIs have crushed

        return false;// return false is temporary, need to implement the method
    }

    public Ai getAi1()
    {
        return ai1;
    }

    public void setAi1(Ai ai1)
    {
        this.ai1 = ai1;
    }

    public Ai getAi2()
    {
        return ai2;
    }

    public void setAi2(Ai ai2)
    {
        this.ai2 = ai2;
    }
}
