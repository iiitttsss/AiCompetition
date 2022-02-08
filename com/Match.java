/**
 * Handle a match between 2 spaceships
 */
package AiCompetition.com;

import AiCompetition.com.bullets.BulletManager;
import AiCompetition.com.render.RenderSimulation;

public class Match
{
    private Ai ai1;
    private Ai ai2;

    private Spaceship spaceship1;
    private Spaceship spaceship2;
    private BulletManager bulletManager;

    private int sizeX;
    private int sizeY;

    public Match(int sizeX, int sizeY)
    {
        this.setSizeX(sizeX);
        this.setSizeY(sizeY);

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

        RenderSimulation.init();
    }

    /**
     * called after the init method. In-charge on running all the iterations.
     */
    public void run()
    {
        while (!this.isGameOver())
        {
            this.update(0.2f);
        }
    }

    /**
     * Handle each iteration of the simulation
     */
    public void update(float deltaTime)
    {
        // TODO - add all the steps bellow
        this.getSpaceship1().updateEnergy(deltaTime);
        this.getSpaceship2().updateEnergy(deltaTime);
        // - update active bullets
        this.getBulletManager().updateActiveBullets();
        // - AIs update thrusters (try-catch)
        this.getSpaceship1().executeThrustCommands(this.getAi1().thrustCommands(this.getSpaceship1(), this.getSpaceship2(), bulletManager.getActiveBullets()), deltaTime);
        this.getSpaceship2().executeThrustCommands(this.getAi2().thrustCommands(this.getSpaceship2(), this.getSpaceship1(), bulletManager.getActiveBullets()), deltaTime);
        // - AIs shoot (try-catch)
        this.getSpaceship1().executeShootCommands(this.getAi1().shootCommands(this.getSpaceship1(), this.getSpaceship2(), bulletManager.getActiveBullets()), this.getBulletManager());
        this.getSpaceship2().executeShootCommands(this.getAi2().shootCommands(this.getSpaceship2(), this.getSpaceship1(), bulletManager.getActiveBullets()), this.getBulletManager());
        // - spaceships move
        this.getSpaceship1().updateMovement(deltaTime);
        this.getSpaceship2().updateMovement(deltaTime);
        // - spaceships handle screen edges
//        this.handleBoarders();
        // - spaceship/bullets collisions - take damage | bullets deactivate as needed
        this.getBulletManager().update(deltaTime);
        this.getBulletManager().checkForCollisionsWithSpaceship(spaceship1);
        this.getBulletManager().checkForCollisionsWithSpaceship(spaceship2);
        // - simulation log
    }

    private void handleBoarders()
    {
        this.getSpaceship1().handleReflectiveBorders(this.getSizeX(), this.getSizeY());
        this.getSpaceship2().handleReflectiveBorders(this.getSizeX(), this.getSizeY());
    }

    public int getSizeX()
    {
        return sizeX;
    }

    public void setSizeX(int sizeX)
    {
        this.sizeX = sizeX;
    }

    public int getSizeY()
    {
        return sizeY;
    }

    public void setSizeY(int sizeY)
    {
        this.sizeY = sizeY;
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
