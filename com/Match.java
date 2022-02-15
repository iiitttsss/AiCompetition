/**
 * Handle a match between 2 spaceships
 */
package AiCompetition.com;

import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.bullets.BulletManager;
import AiCompetition.com.render.RenderBackgroundLines;
import AiCompetition.com.render.RenderSimulation;

public class Match
{
    private static final int MILLIS_FOR_THREAD = 20;
    private static final int CRITICAL_MILLIS_FOR_THREAD = 50;
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

    public void savePreviousPosition()
    {
        this.getSpaceship1().savePreviousPosition();
        this.getSpaceship2().savePreviousPosition();
        for(Bullet bullet : this.getBulletManager().getActiveBullets())
        {
            bullet.savePreviousPosition();
        }
        RenderSimulation.savePreviousPosition();
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
            this.update(0.2f);
        }
    }

    private void aiCommands(float deltaTime)
    {
        EvaluateAiCommands executeAi1 = new EvaluateAiCommands(this.getAi1(), this.getSpaceship1(), this.getSpaceship2(), bulletManager.getActiveBullets());
        this.handleThread(executeAi1, this.getSpaceship1());

        EvaluateAiCommands executeAi2 = new EvaluateAiCommands(this.getAi2(), this.getSpaceship2(), this.getSpaceship1(), bulletManager.getActiveBullets());
        this.handleThread(executeAi2, this.getSpaceship2());

        this.getSpaceship1().executeThrustCommands(executeAi1.getThrustCommands(), deltaTime);
        this.getSpaceship2().executeThrustCommands(executeAi2.getThrustCommands(), deltaTime);
        this.getSpaceship1().executeShootCommands(executeAi1.getShootCommands(), this.getBulletManager());
        this.getSpaceship2().executeShootCommands(executeAi2.getShootCommands(), this.getBulletManager());
    }

    private void handleThread(Runnable runnable, Spaceship spaceship)
    {
        Thread thread = new Thread(runnable);
        thread.start();
        try
        {
            long startTime = System.currentTimeMillis();
            thread.join(CRITICAL_MILLIS_FOR_THREAD);
            long endTime = System.currentTimeMillis();
            long timePassed = endTime - startTime;
            if (timePassed >= CRITICAL_MILLIS_FOR_THREAD)
            {
                spaceship.setOverTimePoints(Spaceship.OVERTIME_POINTS_FOR_DEATH);
            } else if (timePassed >= MILLIS_FOR_THREAD)
            {
                spaceship.addOverTimePoint();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            System.out.println("AI thread InterruptedException");
            spaceship.setDidCrash(true);
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
        // - AIs shoot (try-catch)
        this.aiCommands(deltaTime);

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
