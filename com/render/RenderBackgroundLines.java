package AiCompetition.com.render;

import AiCompetition.com.Global;

public class RenderBackgroundLines
{


    public static void renderBackground()
    {
        for (int i = 1; i <= 400; i *= 2)
        {
            int mod = i * 200;
            if (mod * RenderSimulation.getScale() > 100)
            {
                createLines(mod, i, RenderSimulation.getStartX(), RenderSimulation.getEndX(), RenderSimulation.getStartY(), RenderSimulation.getEndY());
                break;
            }
        }
    }

    private static void createLines(int mod, int weight, int startX, int endX, int startY, int endY)
    {
        RenderSimulation.getPgReference().pushStyle();
        //pg.strokeWeight(weight);
        RenderSimulation.getPgReference().stroke(Global.getPro().color(150));
        for (int x = 0; x < endX; x += mod)
        {
            RenderSimulation.getPgReference().line(x, startY, x, endY);
        }
        for (int x = 0; x > startX; x -= mod)
        {
            RenderSimulation.getPgReference().line(x, startY, x, endY);
        }
        for (int y = 0; y < endY; y += mod)
        {
            RenderSimulation.getPgReference().line(startX, y, endX, y);
        }
        for (int y = 0; y > startY; y -= mod)
        {
            RenderSimulation.getPgReference().line(startX, y, endX, y);
        }
        RenderSimulation.getPgReference().popStyle();
    }



}
