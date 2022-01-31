package AiCompetition.com.render;

import AiCompetition.com.Global;
import AiCompetition.com.Match;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class RenderBackground
{
    public static final PImage translatedBackgroundTile = Global.getPro().loadImage("src/AiCompetition/com/render/translatedBackground.png");
    public static PImage translatedBackground;
    public static int[] translatedBackgroundDirections;
    public static int backgroundX;
    public static int backgroundY;

    private static int[] calculateNewBackgroundSize(PApplet pro, PGraphics pg, Match match, int centerX, int centerY, float scale)
    {
        pg.background(150);
        int startX = (int) (centerX - pg.width / 2 / scale);
        int endX = (int) (centerX + pg.width / 2 / scale);
        int startY = (int) (centerY - pg.height / 2 / scale);
        int endY = (int) (centerY + pg.height / 2 / scale);

        int counterXPositive = 0;
        int counterYPositive = 0;
        int counterXNegative = 0;
        int counterYNegative = 0;

        for (int x = -translatedBackgroundTile.width; x > startX - translatedBackgroundTile.width; x -= translatedBackgroundTile.width)
        {
            counterXNegative++;
        }
        for (int x = 0; x < endX + translatedBackgroundTile.width; x += translatedBackgroundTile.width)
        {
            counterXPositive++;
        }
        for (int y = 0; y < endY + translatedBackgroundTile.height; y += translatedBackgroundTile.height)
        {
            counterYPositive++;
        }
        for (int y = -translatedBackgroundTile.height; y > startY - translatedBackgroundTile.height; y -= translatedBackgroundTile.height)
        {
            counterYNegative++;
        }
        int[] newTranslatedBackgroundDirections = {counterXPositive, counterXNegative, counterYPositive, counterYNegative};
        return newTranslatedBackgroundDirections;
    }


    public static void renderBackground(PApplet pro, PGraphics pg, Match match, int centerX, int centerY, float scale)
    {
        boolean theSame = false;
        int[] newTranslatedBackgroundDirections = calculateNewBackgroundSize(pro, pg, match, centerX, centerY, scale);
        if(translatedBackgroundDirections == null)
        {
            translatedBackgroundDirections = newTranslatedBackgroundDirections;
        }
        else
        {
            theSame = true;
            for (int i = 0; i < 4; i++)
            {
                if (newTranslatedBackgroundDirections[i] != translatedBackgroundDirections[i])
                {
                    theSame = false;
                    break;
                }
            }
        }
        if ((!theSame) || translatedBackground == null)
        {
            translatedBackgroundDirections = newTranslatedBackgroundDirections;
            int newWidth = (newTranslatedBackgroundDirections[0] + newTranslatedBackgroundDirections[1]);
            int newHeight = (newTranslatedBackgroundDirections[2] + newTranslatedBackgroundDirections[3]);

            translatedBackground = createBackground(pro, pg, match, centerX, centerY, scale, newWidth, newHeight);
            backgroundX = -newTranslatedBackgroundDirections[1] * translatedBackgroundTile.width;
            backgroundY = -newTranslatedBackgroundDirections[3] * translatedBackgroundTile.height;

            for (int t : newTranslatedBackgroundDirections)
            {
                System.out.print(t + ", ");
            }
            System.out.println();
        }

        pg.imageMode(PConstants.CORNER);
        pg.image(translatedBackground, backgroundX, backgroundY);

    }

    private static PImage createBackground(PApplet pro, PGraphics pg, Match match, int centerX, int centerY, float scale, int newWidth, int newHeight)
    {
        PGraphics newBackground = pro.createGraphics(newWidth * translatedBackgroundTile.width, newHeight * translatedBackgroundTile.height);
        newBackground.beginDraw();
        newBackground.background(150);

        for (int y = 0; y < newHeight; y++)
        {
            for (int x = 0; x < newWidth; x++)
            {
                newBackground.image(translatedBackgroundTile, x * translatedBackgroundTile.width, y * translatedBackgroundTile.height);
            }
        }
        newBackground.endDraw();
        return newBackground.get();
    }
}
