package AiCompetition.com.render;

import AiCompetition.com.Match;
import processing.core.PApplet;
import processing.core.PGraphics;

public class RenderBackgroundLines
{

    public static void renderBackground(PApplet pro, PGraphics pg, Match match, int centerX, int centerY, float scale)
    {
//        pg.circle(centerX, centerY, 20);

        int startX = (int) (centerX - pg.width / 2 / scale);
        int endX = (int) (centerX + pg.width / 2 / scale);
        int startY = (int) (centerY - pg.height / 2 / scale);
        int endY = (int) (centerY + pg.height / 2 / scale);


//        pg.circle(startX, centerY, 40);
//        pg.circle(endX, centerY, 40);
//        pg.circle(centerX, startY, 40);
//        pg.circle(centerX, endY, 40);


        for (int i = 1; i <= 20; i *= 2)
        {
            int mod = i * 200;
            if (mod * scale > 50)
            {
                createLines(pg, mod, i, startX, endX, startY, endY);
            }
        }
    }

    private static void createLines(PGraphics pg, int mod, int weight, int startX, int endX, int startY, int endY)
    {
        pg.pushStyle();
        pg.strokeWeight(weight);
        for (int x = 0; x < endX; x += mod)
        {
            pg.line(x, startY, x, endY);
        }
        for (int x = 0; x > startX; x -= mod)
        {
            pg.line(x, startY, x, endY);
        }
        for (int y = 0; y < endY; y += mod)
        {
            pg.line(startX, y, endX, y);
        }
        for (int y = 0; y > startY; y -= mod)
        {
            pg.line(startX, y, endX, y);
        }
        pg.popStyle();
    }
}
