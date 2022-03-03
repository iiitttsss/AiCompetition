package AiCompetition.com.render;

import AiCompetition.com.Global;
import AiCompetition.com.SpaceshipStructure;
import AiCompetition.com.UpgradeData;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

import java.util.ArrayList;

public class CreateSpaceshipSprite
{

    public static final int BLUE_SPRITE = 0;
    public static final int RED_SPRITE = 1;
    public static final int WHITE_SPRITE = 2;
    private final static int SPRITE_SIZE = 32;
    private static ArrayList<PImage> sprites;

    public static void loadSprites(String fileName)
    {
        if (sprites != null)
        {
            return;
        }
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

    private static PImage evaluateWingsSprite(UpgradeData upgradeData, int spriteColor)
    {
        int level = Math.max(upgradeData.getUpgrade(UpgradeData.FRONT_THRUSTER),
                Math.max(upgradeData.getUpgrade(UpgradeData.BACK_THRUSTER),
                        Math.max(upgradeData.getUpgrade(UpgradeData.LEFT_THRUSTER),
                                upgradeData.getUpgrade(UpgradeData.RIGHT_THRUSTER))));

        level = Math.min(level, 24) / 6;

        return getSpriteAt(level + 6 * spriteColor, 2);
    }

    private static PImage evaluateGunSprite(UpgradeData upgradeData, int spriteColor)
    {
        int level = Math.max(upgradeData.getUpgrade(UpgradeData.FRONT_GUN_DAMAGE),
                Math.max(upgradeData.getUpgrade(UpgradeData.BACK_GUN_DAMAGE),
                        Math.max(upgradeData.getUpgrade(UpgradeData.LEFT_GUN_DAMAGE),
                                upgradeData.getUpgrade(UpgradeData.RIGHT_GUN_DAMAGE))));

        int x = 0;
        int y = 6;

        if (level > 15)
        {
            x = 3;
        } else if (level > 10)
        {
            x = 2;
        } else if (level > 5)
        {
            x = 1;
        } else
        {
            x = 0;
        }
        return getSpriteAt(x + 6 * spriteColor, y);
    }

    private static PImage evaluateBodySprite(UpgradeData upgradeData, int spriteColor)
    {
        int x = 0;
        int y = 0;

        int level = upgradeData.getUpgrade(UpgradeData.HIT_POINTS);
        if (level > 20)
        {
            x = 5;
        } else if (level > 15)
        {
            x = 4;
        } else if (level > 10)
        {
            x = 3;
        } else if (level > 5)
        {
            x = 2;
        } else if (level > 2)
        {
            x = 1;
        } else
        {
            x = 0;
        }
        return getSpriteAt(x + 6 * spriteColor, y);
    }

    private static PImage evaluateThrusterSprite(UpgradeData upgradeData, int spriteColor)
    {
        //return getSpriteAt(x + 6 * spriteColor, y);
        return getSpriteAt(2, 6);

    }

    /**
     * @param upgradeData
     * @return - creating a returning a spaceship sprite based on the spaceship stats
     */
    public static PImage createSpaceshipSprite(UpgradeData upgradeData, SpaceshipStructure spaceshipStructure, int spriteColor)
    {
        // wings < thruster < body < gun
        PGraphics spaceshipSprite = Global.getPro().createGraphics(SPRITE_SIZE, SPRITE_SIZE);

        spaceshipSprite.beginDraw();

        spaceshipSprite.image(evaluateWingsSprite(upgradeData, spriteColor), 0, 0);
        spaceshipSprite.image(evaluateThrusterSprite(upgradeData, spriteColor), 0, 0);
        spaceshipSprite.image(evaluateBodySprite(upgradeData, spriteColor), 0, 0);
        spaceshipSprite.image(evaluateGunSprite(upgradeData, spriteColor), 0, 0);

        spaceshipSprite.endDraw();
        PImage spaceshipSpriteImage = spaceshipSprite.get();
        spaceshipSpriteImage.resize(spaceshipStructure.getAttribute(UpgradeData.RADIUS) * 2, spaceshipStructure.getAttribute(UpgradeData.RADIUS) * 2);

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
