/**
 * handle rendering of the simulation
 */
package AiCompetition.com.render;

import AiCompetition.com.Global;
import AiCompetition.com.Match;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.bullets.Bullet;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class RenderSimulation
{
    private static float centerX;
    private static float centerY;
    private static float scale;

    private static int startX;
    private static int endX;
    private static int startY;
    private static int endY;
    private static PImage STATIONARY_BACKGROUND;

    private static Match matchReference;
    private static PGraphics pgReference;

    private static float framePercent;
    private static int timeOfLastUpdate;
    private static float previousXCenter;
    private static float previousYCenter;
    private static float previousScale;

    public static void savePreviousPosition()
    {
        setPreviousXCenter(RenderSimulation.getCenterX());
        setPreviousYCenter(RenderSimulation.getCenterY());
        setPreviousScale(RenderSimulation.getScale());
    }

    public static void init(Match matchReference, PGraphics pgReference)
    {
        setMatchReference(matchReference);
        setPgReference(pgReference);
        STATIONARY_BACKGROUND = Global.getPro().loadImage("src/AiCompetition/com/render/background.png");
        final int resizeConstant = 1;
        STATIONARY_BACKGROUND.resize(STATIONARY_BACKGROUND.width / resizeConstant, STATIONARY_BACKGROUND.height / resizeConstant);
    }

    /**
     * this method is called every time the match is updated.
     */
    public static void matchUpdate()
    {
        timeOfLastUpdate = Global.getPro().millis();
    }

    private static void updateFramePercent(float updateDeltaTime)
    {
        int currentTime = Global.getPro().millis();
        float millisDeltaTime = updateDeltaTime * 1000;
        int lastTime = getTimeOfLastUpdate();
        float percent = (currentTime - lastTime) / millisDeltaTime;
        RenderSimulation.setFramePercent(percent);
    }

    private static float interpretBetweenPositions(float currentPosition, float previousPosition)
    {
        float interpretPosition = previousPosition * (1 - getFramePercent()) + currentPosition * (getFramePercent());
        return interpretPosition;
    }

    private static void updateScreenEdges(PGraphics pg)
    {
        RenderSimulation.setStartX((int) (centerX - pgReference.width / 2 / scale));
        RenderSimulation.setEndX((int) (centerX + pgReference.width / 2 / scale));
        RenderSimulation.setStartY((int) (centerY - pgReference.height / 2 / scale));
        RenderSimulation.setEndY((int) (centerY + pgReference.height / 2 / scale));
    }

    private static void renderBorder()
    {
        //processing Draw transparent circle filled outside
        //https://forum.processing.org/two/discussion/26405/how-do-i-fill-the-space-around-a-shape.html
        getPgReference().pushStyle();
        getPgReference().noFill();
        getPgReference().strokeWeight(10);

        getPgReference().circle(0, 0, 2 * getMatchReference().getBorderRadius());


        getPgReference().popStyle();

    }

    public static void render(float updateDeltaTime)
    {
        RenderSimulation.updateFramePercent(updateDeltaTime);
        pgReference.beginDraw();
        //TODO
        pgReference.background(150);
        pgReference.imageMode(PConstants.CORNER);
        pgReference.image(STATIONARY_BACKGROUND, 0, 0);
        pgReference.pushMatrix();
        translateToCenter();

        //background
        renderBackground();

        //border
        renderBorder();

        //spaceships
        renderSpaceships();

        //bullets
        renderBullets();
        pgReference.popMatrix();
        //UI - life, energy, energy per turn, name
        renderUi();
        pgReference.endDraw();
    }

    private static void renderBackground()
    {
//        pg.background(150);
        RenderBackgroundLines.renderBackground();
    }

    private static void translateToCenter()
    {
        pgReference.translate(pgReference.width / 2, pgReference.height / 2);

        float scaleFactor = pgReference.width / Math.max(Math.abs(matchReference.getSpaceship1().getxPos() - matchReference.getSpaceship2().getxPos()), Math.abs(matchReference.getSpaceship1().getyPos() - matchReference.getSpaceship2().getyPos()));
        // scale = scaleFactor / 1.5f;
        scale = Math.min(scaleFactor / 2f, 1);
        float scaleInterpret = RenderSimulation.interpretBetweenPositions(scale, getPreviousScale());

        pgReference.scale(scaleInterpret);

        float centerX = ((matchReference.getSpaceship1().getxPos() + matchReference.getSpaceship2().getxPos()) / 2f);
        float centerY = ((matchReference.getSpaceship1().getyPos() + matchReference.getSpaceship2().getyPos()) / 2f);
        float xPosInterpret = RenderSimulation.interpretBetweenPositions(centerX, getPreviousXCenter());
        float yPosInterpret = RenderSimulation.interpretBetweenPositions(centerY, getPreviousYCenter());
        pgReference.translate(-xPosInterpret, -yPosInterpret);
        RenderSimulation.centerX = centerX;
        RenderSimulation.centerY = centerY;

        updateScreenEdges(pgReference);


    }

    private static void renderUi()
    {
        pgReference.text("bullets: " + matchReference.getBulletManager().getAllBullets().size() + "/" + matchReference.getBulletManager().getActiveBullets().size(), 20, 20);
        pgReference.text("FPS: " + Global.getPro().frameRate, 20, 40);

        pgReference.text("translation: " + centerX + ", " + centerY, 20, 80);
        pgReference.text("scale: " + scale, 20, 100);
    }

    private static void renderBullets()
    {
        pgReference.pushStyle();
        pgReference.noStroke();
        final int BULLET_GLOW_RADIUS = 50;
        int glowLayersNumber = (int) Math.max(BULLET_GLOW_RADIUS * scale / 5, 1);
        int radiusIncreaseMultiplier = BULLET_GLOW_RADIUS / glowLayersNumber;
        int glowAlphaValue = 150 / glowLayersNumber;

        for (Bullet b : matchReference.getBulletManager().getActiveBullets())
        {
            float xPosInterpret = interpretBetweenPositions(b.getxPos(), b.getPreviousXPos());
            float yPosInterpret = interpretBetweenPositions(b.getyPos(), b.getPreviousYPos());

            if (!isOnScreen(xPosInterpret, yPosInterpret))
            {
                continue;
            }

            for (int i = 0; i < glowLayersNumber; i++)
            {
                pgReference.fill(Global.getPro().color(255, 253, 107, glowAlphaValue));
                pgReference.circle(xPosInterpret, yPosInterpret, b.getRadius() * 2 + radiusIncreaseMultiplier * i);
            }
            pgReference.fill(Global.getPro().color(255));
            pgReference.circle(xPosInterpret, yPosInterpret, b.getRadius() * 2);

        }
        pgReference.popStyle();
    }

    private static boolean isOnScreen(float x, float y)
    {
        int t = 50;
        return (x >= startX - t && x <= endX + t) && (y >= startY - t && y <= endY + t);
    }

    private static void renderSpaceships()
    {
        renderASpaceship(matchReference.getSpaceship1());
        renderASpaceship(matchReference.getSpaceship2());
    }

    private static void renderASpaceship(Spaceship spaceship)
    {
        float xPosInterpret = interpretBetweenPositions(spaceship.getxPos(), spaceship.getPreviousXPos());
        float yPosInterpret = interpretBetweenPositions(spaceship.getyPos(), spaceship.getPreviousYPos());
        float directionInterpret = interpretBetweenPositions(spaceship.getDirection(), spaceship.getPreviousDirection());

        pgReference.pushMatrix();
        pgReference.imageMode(PConstants.CENTER);
        pgReference.translate(xPosInterpret, yPosInterpret);
        pgReference.rotate(directionInterpret + PConstants.PI / 2);
        pgReference.image(spaceship.getSpriteBlue(), 0, 0);
        pgReference.popMatrix();

        float lineLength = 75;
        pgReference.line(xPosInterpret, yPosInterpret,
                xPosInterpret + lineLength * (float) Math.cos(directionInterpret), yPosInterpret + lineLength * (float) Math.sin(directionInterpret));
        pgReference.line(xPosInterpret, yPosInterpret,
                xPosInterpret + lineLength * spaceship.getxVel(), yPosInterpret + lineLength * spaceship.getyVel());

        pgReference.pushStyle();
        pgReference.textSize(14 / scale);
        String s = "HP: " + spaceship.getHitPoints() + "/" + spaceship.getSpaceshipStructure().getAttribute(UpgradeData.HIT_POINTS) + "\n" +
                "Energy: " + (int) spaceship.getEnergy() + "/" + spaceship.getSpaceshipStructure().getAttribute(UpgradeData.BATTERY) + "\n" +
                "velocity: " + (Math.sqrt(spaceship.getxVel() * spaceship.getxVel() + spaceship.getyVel() * spaceship.getyVel())) + "\n"
                + "position: " + spaceship.getxPos() + "/" + spaceship.getyPos() + "\n";

        pgReference.text(s, xPosInterpret, yPosInterpret + 20 + spaceship.getSpaceshipStructure().getAttribute(UpgradeData.RADIUS));
        pgReference.popStyle();
    }

    private static void renderASpaceshipDebug(PApplet pro, PGraphics pg, Spaceship spaceship)
    {
        pgReference.circle(spaceship.getxPos(), spaceship.getyPos(), 50);
        pgReference.circle(spaceship.getxPos(), spaceship.getyPos(), spaceship.getSpaceshipStructure().getAttribute(UpgradeData.RADIUS) * 2);
        float lineLength = 75;
        pgReference.line(spaceship.getxPos(), spaceship.getyPos(),
                spaceship.getxPos() + lineLength * (float) Math.cos(spaceship.getDirection()), spaceship.getyPos() + lineLength * (float) Math.sin(spaceship.getDirection()));
        pgReference.line(spaceship.getxPos(), spaceship.getyPos(),
                spaceship.getxPos() + lineLength * spaceship.getxVel(), spaceship.getyPos() + lineLength * spaceship.getyVel());
        pgReference.text("HP: " + spaceship.getHitPoints(), spaceship.getxPos(), spaceship.getyPos() - 50);
    }

    public static float getPreviousScale()
    {
        return previousScale;
    }

    public static void setPreviousScale(float previousScale)
    {
        RenderSimulation.previousScale = previousScale;
    }

    public static float getPreviousXCenter()
    {
        return previousXCenter;
    }

    public static void setPreviousXCenter(float previousXCenter)
    {
        RenderSimulation.previousXCenter = previousXCenter;
    }

    public static float getPreviousYCenter()
    {
        return previousYCenter;
    }

    public static void setPreviousYCenter(float previousYCenter)
    {
        RenderSimulation.previousYCenter = previousYCenter;
    }

    public static float getFramePercent()
    {
        return framePercent;
    }

    public static void setFramePercent(float framePercent)
    {
        RenderSimulation.framePercent = framePercent;
    }

    public static int getTimeOfLastUpdate()
    {
        return timeOfLastUpdate;
    }

    public static void setTimeOfLastUpdate(int timeOfLastUpdate)
    {
        RenderSimulation.timeOfLastUpdate = timeOfLastUpdate;
    }

    public static PGraphics getPgReference()
    {
        return pgReference;
    }

    public static void setPgReference(PGraphics pgReference)
    {
        RenderSimulation.pgReference = pgReference;
    }

    public static Match getMatchReference()
    {
        return matchReference;
    }

    public static void setMatchReference(Match matchReference)
    {
        RenderSimulation.matchReference = matchReference;
    }

    public static float getCenterX()
    {
        return centerX;
    }

    public static void setCenterX(float centerX)
    {
        RenderSimulation.centerX = centerX;
    }

    public static float getCenterY()
    {
        return centerY;
    }

    public static void setCenterY(float centerY)
    {
        RenderSimulation.centerY = centerY;
    }

    public static float getScale()
    {
        return scale;
    }

    public static void setScale(float scale)
    {
        RenderSimulation.scale = scale;
    }

    public static int getStartX()
    {
        return startX;
    }

    public static void setStartX(int startX)
    {
        RenderSimulation.startX = startX;
    }

    public static int getEndX()
    {
        return endX;
    }

    public static void setEndX(int endX)
    {
        RenderSimulation.endX = endX;
    }

    public static int getStartY()
    {
        return startY;
    }

    public static void setStartY(int startY)
    {
        RenderSimulation.startY = startY;
    }

    public static int getEndY()
    {
        return endY;
    }

    public static void setEndY(int endY)
    {
        RenderSimulation.endY = endY;
    }
}
