package AiCompetition.com;

import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;

import java.util.ArrayList;

public abstract class Ai
{
    /**
     *
     * @param enemyXpos
     * @param enemyYpos
     * @param bulletsPositions
     * @return - a list of shoot commands the AI want to execute
     */
    public abstract ArrayList<ShootCommand> shootCommands(float enemyXpos, float enemyYpos, ArrayList<Bullet> bulletsPositions);

    /**
     *
     * @return - the spaceship structure as specified by the player
     */
    public abstract SpaceshipStructure createStructure();

}
