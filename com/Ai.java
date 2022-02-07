/**
 * this class will be extended by every AI, it contains the "guidelines" for what an AI need to have
 */
package AiCompetition.com;

import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public abstract class Ai
{
    /**
     * @param enemyXpos - the x position of the enemy
     * @param enemyYpos - the y position of the enemy
     * @param bulletsPositions - a list of all the bullets that can hit you (enemy bullets)
     * @return - a list of shoot commands the AI want to execute
     */
    public abstract ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions);

    public abstract ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship,Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions);


    /**
     * @return - the spaceship structure as specified by the player
     */
    public abstract UpgradeData createStructure();

}
