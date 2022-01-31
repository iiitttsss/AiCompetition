package AiCompetition.com.render;

import AiCompetition.com.Global;
import AiCompetition.com.SpaceshipStructure;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

import java.util.ArrayList;

public class CreateSpaceshipSprite
{

    private final static int SPRITE_SIZE = 32;
    private static ArrayList<PImage> sprites;

    public static void loadSprites(String fileName)
    {
        // 576x320 spread sheet
        // 18x10 sprites
        // 32x32 pixels per sprite
        sprites = new ArrayList<>();
        PImage spriteSheet = Global.getPro().loadImage(fileName);

        for (int ySprite = 0; ySprite < 10; ySprite++)
        {
            for (int xSprite = 0; xSprite < 18; xSprite++)
            {
                PImage sprite = Global.getPro().createImage(SPRITE_SIZE, SPRITE_SIZE, PConstants.ARGB);
                for (int y = 0; y < SPRITE_SIZE; y++)
                {
                    for (int x = 0; x < SPRITE_SIZE; x++)
                    {
                        sprite.set(x, y, spriteSheet.get(xSprite * SPRITE_SIZE + x, ySprite * SPRITE_SIZE + y));
                    }
                }
                sprite.updatePixels();
                sprites.add(sprite);
            }
        }

    }

    private static PImage getSpriteAt(int x, int y)
    {
        return sprites.get(y * 18 + x);
    }

    private static PImage evaluateWingsSprite(SpaceshipStructure spaceshipStructure)
    {
        return getSpriteAt(2, 2);
    }

    private static PImage evaluateThrusterSprite(SpaceshipStructure spaceshipStructure)
    {
        return getSpriteAt(3, 5);
    }

    private static PImage evaluateBodySprite(SpaceshipStructure spaceshipStructure)
    {
        return getSpriteAt(0, 1);
    }

    private static PImage evaluateGunSprite(SpaceshipStructure spaceshipStructure)
    {
        return getSpriteAt(2, 6);
    }

    /**
     * @param spaceshipStructure
     * @return - creating a returning a spaceship sprite based on the spaceship stats
     */
    public static PImage createSpaceshipSprite(SpaceshipStructure spaceshipStructure)
    {
        // wings < thruster < body < gun
        PGraphics spaceshipSprite = Global.getPro().createGraphics(SPRITE_SIZE, SPRITE_SIZE);
        spaceshipSprite.beginDraw();

        spaceshipSprite.image(evaluateWingsSprite(spaceshipStructure), 0, 0);
        spaceshipSprite.image(evaluateThrusterSprite(spaceshipStructure), 0, 0);
        spaceshipSprite.image(evaluateBodySprite(spaceshipStructure), 0, 0);
        spaceshipSprite.image(evaluateGunSprite(spaceshipStructure), 0, 0);

        spaceshipSprite.endDraw();
        PImage spaceshipSpriteImage = spaceshipSprite.get();
        spaceshipSpriteImage.resize(spaceshipStructure.getRadius() * 2, spaceshipStructure.getRadius() * 2);
        return spaceshipSpriteImage;
    }

    public static ArrayList<PImage> getSprites()
    {
        return sprites;
    }

    public static void setSprites(ArrayList<PImage> sprites)
    {
        CreateSpaceshipSprite.sprites = sprites;
    }
}
