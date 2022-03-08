/**
 * this class will be extended by every AI, it contains the "guidelines" for what an AI need to have
 */
package AiCompetition.com;

import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public interface Ai
{

    /**
     *
     * @param mySpaceship - the spaceship the AI controls
     * @param otherSpaceship - the enemy (other player) spaceship
     * @param bulletsPositions - list of all the bullets (including your own)
     * @return - returns a list of commands for the spaceship
     */
    ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius);

    ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius);


    /**
     * @return - the spaceship structure as specified by the player
     */
    UpgradeData createStructure();

}
