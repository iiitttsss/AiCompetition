/**
 * handle rendering of the simulation
 */
package AiCompetition.com.render;

import AiCompetition.com.Match;
import AiCompetition.com.Spaceship;
import processing.core.PApplet;
import processing.core.PGraphics;

public class RenderSimulation
{
    public static void render(PApplet pro, PGraphics pg, Match match)
    {
        pg.beginDraw();
        pg.background(150);
        //TODO
        //background
        //spaceships
        renderSpaceships(pro, pg, match);
        //bullets
        //UI - life, energy, energy per turn, name
        pg.endDraw();
    }

    private static void renderSpaceships(PApplet pro, PGraphics pg, Match match)
    {
        renderASpaceship(pro, pg, match.getSpaceship1());
        renderASpaceship(pro, pg, match.getSpaceship2());
    }

    private static void renderASpaceship(PApplet pro, PGraphics pg, Spaceship spaceship)
    {
        pg.circle(spaceship.getxPos(), spaceship.getyPos(), 50);
        float lineLength = 75;
        pg.line(spaceship.getxPos(), spaceship.getyPos(),
                spaceship.getxPos() + lineLength * (float) Math.cos(spaceship.getDirection()), spaceship.getyPos() + lineLength * (float) Math.sin(spaceship.getDirection()));
        pg.line(spaceship.getxPos(), spaceship.getyPos(),
                spaceship.getxPos() + lineLength * spaceship.getxVel(), spaceship.getyPos() + lineLength * spaceship.getyVel());
    }
}
