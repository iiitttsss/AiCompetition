/**
 * handle rendering of the simulation
 */
package AiCompetition.com.render;

import AiCompetition.com.Match;
import AiCompetition.com.Spaceship;
import AiCompetition.com.bullets.Bullet;
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
        renderBullets(pro, pg, match);
        //UI - life, energy, energy per turn, name
        renderUi(pro, pg, match);
        pg.endDraw();
    }

    private static void renderUi(PApplet pro, PGraphics pg, Match match)
    {
        pg.text("bullets: " + match.getBulletManager().getAllBullets().size() + "/" + match.getBulletManager().getActiveBullets().size(), 20, 20);
    }

    private static void renderBullets(PApplet pro, PGraphics pg, Match match)
    {
        for (Bullet b : match.getBulletManager().getActiveBullets())
        {
            pg.circle(b.getxPos(), b.getyPos(), b.getRadius() * 2);
        }
    }

    private static void renderSpaceships(PApplet pro, PGraphics pg, Match match)
    {
        renderASpaceship(pro, pg, match.getSpaceship1());
        renderASpaceship(pro, pg, match.getSpaceship2());
    }

    private static void renderASpaceship(PApplet pro, PGraphics pg, Spaceship spaceship)
    {
        pg.circle(spaceship.getxPos(), spaceship.getyPos(), spaceship.getStructure().getRadius() * 2);
        float lineLength = 75;
        pg.line(spaceship.getxPos(), spaceship.getyPos(),
                spaceship.getxPos() + lineLength * (float) Math.cos(spaceship.getDirection()), spaceship.getyPos() + lineLength * (float) Math.sin(spaceship.getDirection()));
        pg.line(spaceship.getxPos(), spaceship.getyPos(),
                spaceship.getxPos() + lineLength * spaceship.getxVel(), spaceship.getyPos() + lineLength * spaceship.getyVel());
        pg.text("HP: " + spaceship.getHitPoints(), spaceship.getxPos(), spaceship.getyPos() - 50);
    }
}
