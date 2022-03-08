/**
 * handle rendering of the simulation
 */
package AiCompetition.com.render;

import AiCompetition.com.Global;
import AiCompetition.com.Match;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.Bullet;
import processing.core.*;

public class RenderSimulation
{
    public static PFont font = Global.getPro().createFont("src/AiCompetition/com/fonts/Technical Forest/font.otf", 20);
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
        STATIONARY_BACKGROUND = Global.getPro().loadImage("AiCompetition/com/render/background.png");
        final int resizeConstant = 1;
        STATIONARY_BACKGROUND.resize(STATIONARY_BACKGROUND.width / resizeConstant, STATIONARY_BACKGROUND.height / resizeConstant);
        RenderBorder.init();
    }

    /**
     * this method is called every time the match is updated.
     */
    public static void matchUpdate()
    {
        setTimeOfLastUpdate(Global.getPro().millis());
    }

    private static void updateFramePercent(float updateDeltaTime)
    {
        int currentTime = Global.getPro().millis();
        float millisDeltaTime = updateDeltaTime * 1000;
        int lastTime = getTimeOfLastUpdate();
        float percent = (currentTime - lastTime) / millisDeltaTime;
        RenderSimulation.setFramePercent(percent);
    }

    public static float interpretBetweenPositions(float currentPosition, float previousPosition)
    {
        return previousPosition * (1 - getFramePercent()) + currentPosition * (getFramePercent());
    }

    private static void updateScreenEdges()
    {
        RenderSimulation.setStartX((int) (centerX - pgReference.width / 2 / scale));
        RenderSimulation.setEndX((int) (centerX + pgReference.width / 2 / scale));
        RenderSimulation.setStartY((int) (centerY - pgReference.height / 2 / scale));
        RenderSimulation.setEndY((int) (centerY + pgReference.height / 2 / scale));
    }

    private static void renderBorder()
    {
        RenderBorder.render(getPgReference());
    }

    private static void renderBackground()
    {
        pgReference.background(150);
        pgReference.imageMode(PConstants.CORNER);
        pgReference.image(STATIONARY_BACKGROUND, 0, 0);
    }

    public static void render(float updateDeltaTime)
    {
        RenderSimulation.updateFramePercent(updateDeltaTime);
        pgReference.beginDraw();
        renderBackground();
        pgReference.pushMatrix();
        translateToCenter();
        renderBackgroundLines();
        renderSpaceships();
        renderBullets();
        pgReference.popMatrix();
        renderBorder();
        renderUi();
        pgReference.endDraw();
    }

    private static void renderBackgroundLines()
    {
        RenderBackgroundLines.renderBackground();
    }

    private static void translateToCenter()
    {
        pgReference.translate(pgReference.width / 2f, pgReference.height / 2f);

        float scaleFactor = pgReference.width / Math.max(Math.abs(matchReference.getSpaceship1().getXPosition() - matchReference.getSpaceship2().getXPosition()), Math.abs(matchReference.getSpaceship1().getYPosition() - matchReference.getSpaceship2().getYPosition()));
        // scale = scaleFactor / 1.5f;
        scale = Math.min(scaleFactor / 2f, 1);
        float scaleInterpret = RenderSimulation.interpretBetweenPositions(scale, getPreviousScale());

        pgReference.scale(scaleInterpret);

        float centerX = ((matchReference.getSpaceship1().getXPosition() + matchReference.getSpaceship2().getXPosition()) / 2f);
        float centerY = ((matchReference.getSpaceship1().getYPosition() + matchReference.getSpaceship2().getYPosition()) / 2f);
        float xPosInterpret = RenderSimulation.interpretBetweenPositions(centerX, getPreviousXCenter());
        float yPosInterpret = RenderSimulation.interpretBetweenPositions(centerY, getPreviousYCenter());
        pgReference.translate(-xPosInterpret, -yPosInterpret);
        RenderSimulation.centerX = centerX;
        RenderSimulation.centerY = centerY;

        updateScreenEdges();


    }

    private static String spaceshipToUiString(Spaceship spaceship)
    {
        String s = "HP: " + spaceship.getHitPoints() + "/" + spaceship.getSpaceshipStructure().getAttribute(UpgradeData.HIT_POINTS) + "\n" +
                "Energy: " + (int) spaceship.getEnergy() + "/" + spaceship.getSpaceshipStructure().getAttribute(UpgradeData.BATTERY_SIZE) + "\n" +
                "speed: " + (String.format("%.2f", Math.sqrt(spaceship.getXVelocity() * spaceship.getXVelocity() + spaceship.getYVelocity() * spaceship.getYVelocity()))) + "\n";
        return s;
    }

    private static void renderUi()
    {
        //UI - life, energy, energy per turn, name
//        pgReference.text("bullets: " + matchReference.getBulletManager().getAllBullets().size() + "/" + matchReference.getBulletManager().getActiveBullets().size(), 20, 20);
//        pgReference.text("FPS: " + Global.getPro().frameRate, 20, 40);
//
//        pgReference.text("translation: " + centerX + ", " + centerY, 20, 80);
//        pgReference.text("scale: " + scale, 20, 100);

        pgReference.pushStyle();
        pgReference.textFont(RenderSimulation.font);
        pgReference.textAlign(PConstants.LEFT, PConstants.CENTER);
        int w = 60;
        pgReference.image(matchReference.getSpaceship1().getSpriteBlue(),w/4,w/4+10,w,w);
        pgReference.text(spaceshipToUiString(matchReference.getSpaceship1()), (float) (1.5*w), 50+10);

        pgReference.image(matchReference.getSpaceship2().getSpriteRed(),w/4,w/4+100+10,w,w);
        pgReference.text(spaceshipToUiString(matchReference.getSpaceship2()), (float) (1.5*w), 50+100+10);

        pgReference.popStyle();
    }

    private static void renderBullets()
    {
        pgReference.pushStyle();
        pgReference.noStroke();
        final int BULLET_GLOW_RADIUS = 50;
        int glowLayersNumber = (int) Math.max(BULLET_GLOW_RADIUS * scale / 5, 1);
        int radiusIncreaseMultiplier = BULLET_GLOW_RADIUS / glowLayersNumber;
        int glowAlphaValue = 175 / glowLayersNumber;

        for (Bullet b : matchReference.getBulletManager().getActiveBullets())
        {
            float xPosInterpret = interpretBetweenPositions(b.getXPosition(), b.getPreviousXPosition());
            float yPosInterpret = interpretBetweenPositions(b.getYPosition(), b.getPreviousYPosition());

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

    /**
     * @param x - sprite center x
     * @param y - sprite center y
     * @return - returns true if the object is on the screen (plus some) and needs to be rendered
     */
    private static boolean isOnScreen(float x, float y)
    {
        int t = 50;//how many pixels outside the screen the object can be and still be rendered
        return (x >= startX - t && x <= endX + t) && (y >= startY - t && y <= endY + t);
    }

    private static void renderSpaceships()
    {
        renderASpaceship(matchReference.getSpaceship1(), CreateSpaceshipSprite.BLUE_SPRITE);
        renderASpaceship(matchReference.getSpaceship2(), CreateSpaceshipSprite.RED_SPRITE);
    }

    private static void renderASpaceship(Spaceship spaceship, int color)
    {
        float xPosInterpret = interpretBetweenPositions(spaceship.getXPosition(), spaceship.getPreviousXPosition());
        float yPosInterpret = interpretBetweenPositions(spaceship.getYPosition(), spaceship.getPreviousYPosition());
        float directionInterpret = interpretBetweenPositions(spaceship.getDirection(), spaceship.getPreviousDirection());

        pgReference.pushMatrix();
        pgReference.imageMode(PConstants.CENTER);
        pgReference.translate(xPosInterpret, yPosInterpret);
        pgReference.rotate(directionInterpret + PConstants.PI / 2);
        if (spaceship.getNumberOfUpdatesSinceLastHitByBullet() < 3)//if the spaceship get hit
        {
            pgReference.image(spaceship.getSpriteWhite(), 0, 0);
        } else
        {
            pgReference.image((color == CreateSpaceshipSprite.BLUE_SPRITE ? spaceship.getSpriteBlue() : spaceship.getSpriteRed()), 0, 0);
        }

        pgReference.popMatrix();

//        float lineLength = 75;
//        pgReference.line(xPosInterpret, yPosInterpret,
//                xPosInterpret + lineLength * (float) Math.cos(directionInterpret), yPosInterpret + lineLength * (float) Math.sin(directionInterpret));
//        pgReference.line(xPosInterpret, yPosInterpret,
//                xPosInterpret + lineLength * spaceship.getXVelocity(), yPosInterpret + lineLength * spaceship.getYVelocity());


    }

    private static void renderASpaceshipDebug(PApplet pro, PGraphics pg, Spaceship spaceship)
    {
        pgReference.circle(spaceship.getXPosition(), spaceship.getYPosition(), 50);
        pgReference.circle(spaceship.getXPosition(), spaceship.getYPosition(), spaceship.getSpaceshipStructure().getAttribute(UpgradeData.RADIUS) * 2);
        float lineLength = 75;
        pgReference.line(spaceship.getXPosition(), spaceship.getYPosition(),
                spaceship.getXPosition() + lineLength * (float) Math.cos(spaceship.getDirection()), spaceship.getYPosition() + lineLength * (float) Math.sin(spaceship.getDirection()));
        pgReference.line(spaceship.getXPosition(), spaceship.getYPosition(),
                spaceship.getXPosition() + lineLength * spaceship.getXVelocity(), spaceship.getYPosition() + lineLength * spaceship.getYVelocity());
        pgReference.text("HP: " + spaceship.getHitPoints(), spaceship.getXPosition(), spaceship.getYPosition() - 50);
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
