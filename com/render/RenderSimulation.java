/**
 * handle rendering of the simulation
 */
package AiCompetition.com.render;

import AiCompetition.com.Global;
import AiCompetition.com.Match;
import AiCompetition.com.Spaceship;
import AiCompetition.com.bullets.Bullet;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public class RenderSimulation
{


    private static int translateX;
    private static int translateY;
    private static float scale;

    public static void render(PApplet pro, PGraphics pg, Match match)
    {
        pg.beginDraw();
        //TODO
        pg.pushMatrix();
        translateToCenter(pro, pg, match);

        //background

        renderBackground(pro, pg, match);
        //spaceships
        renderSpaceships(pro, pg, match);
        //bullets
        renderBullets(pro, pg, match);
        pg.popMatrix();
        //UI - life, energy, energy per turn, name
        renderUi(pro, pg, match);
        pg.endDraw();
    }

    private static void renderBackground(PApplet pro, PGraphics pg, Match match)
    {

        RenderBackground.renderBackground(pro, pg, match, translateX, translateY, scale);

        //pg.background(15);

    }

    private static void translateToCenter(PApplet pro, PGraphics pg, Match match)
    {

        pg.translate(pg.width / 2, pg.height / 2);

        float scaleFactor = pg.width / Math.max(Math.abs(match.getSpaceship1().getxPos() - match.getSpaceship2().getxPos()), Math.abs(match.getSpaceship1().getyPos() - match.getSpaceship2().getyPos()));
        //scale = scaleFactor / 1.5f;
        scale = Math.min(scaleFactor / 1.5f, 1);

        pg.scale(scale);

        int centerX = (int) ((match.getSpaceship1().getxPos() + match.getSpaceship2().getxPos()) / 2);
        int centerY = (int) ((match.getSpaceship1().getyPos() + match.getSpaceship2().getyPos()) / 2);
        pg.translate(-centerX, -centerY);
        translateX = centerX;
        translateY = centerY;


    }

    private static void renderUi(PApplet pro, PGraphics pg, Match match)
    {
        pg.text("bullets: " + match.getBulletManager().getAllBullets().size() + "/" + match.getBulletManager().getActiveBullets().size(), 20, 20);
        pg.text("FPS: " + Global.getPro().frameRate, 20, 40);

        pg.text("translation: " + translateX + ", " + translateY, 20, 80);
        pg.text("scale: " + scale, 20, 100);


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
        pg.pushMatrix();
        pg.imageMode(PConstants.CENTER);
        pg.translate(spaceship.getxPos(), spaceship.getyPos());
        pg.rotate(spaceship.getDirection() + PConstants.PI / 2);
        pg.image(spaceship.getSpriteBlue(), 0, 0);
        pg.popMatrix();

        float lineLength = 75;
        pg.line(spaceship.getxPos(), spaceship.getyPos(),
                spaceship.getxPos() + lineLength * (float) Math.cos(spaceship.getDirection()), spaceship.getyPos() + lineLength * (float) Math.sin(spaceship.getDirection()));
        pg.line(spaceship.getxPos(), spaceship.getyPos(),
                spaceship.getxPos() + lineLength * spaceship.getxVel(), spaceship.getyPos() + lineLength * spaceship.getyVel());
    }

    private static void renderASpaceshipDebug(PApplet pro, PGraphics pg, Spaceship spaceship)
    {
        pg.circle(spaceship.getxPos(), spaceship.getyPos(), 50);
        float lineLength = 75;
        pg.line(spaceship.getxPos(), spaceship.getyPos(),
                spaceship.getxPos() + lineLength * (float) Math.cos(spaceship.getDirection()), spaceship.getyPos() + lineLength * (float) Math.sin(spaceship.getDirection()));
        pg.line(spaceship.getxPos(), spaceship.getyPos(),
                spaceship.getxPos() + lineLength * spaceship.getxVel(), spaceship.getyPos() + lineLength * spaceship.getyVel());
    }
}
