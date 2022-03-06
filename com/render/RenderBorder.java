package AiCompetition.com.render;

import AiCompetition.com.Global;
import AiCompetition.com.util.MathUtil;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class RenderBorder
{
    private static PGraphics borderColor;
    private static PGraphics maskImage;

    public static void init()
    {
        borderColor = Global.getPro().createGraphics(Global.getPro().width, Global.getPro().height);
        borderColor.beginDraw();
        borderColor.background(255, 253, 107);
        borderColor.endDraw();

        maskImage = Global.getPro().createGraphics(Global.getPro().width, Global.getPro().height);
    }

    public static void render(PGraphics pg)
    {
        maskImage.beginDraw();
        maskImage.background(100); //alpha channel (lower is more transparent)
        maskImage.applyMatrix(pg.getMatrix());
        maskImage.fill(0);
        float pixelX = MathUtil.map(0, RenderSimulation.getStartX(), RenderSimulation.getEndX(), 0, pg.width);
        float pixelY = MathUtil.map(0, RenderSimulation.getStartY(), RenderSimulation.getEndY(), 0, pg.height);

        float radius = RenderSimulation.interpretBetweenPositions(RenderSimulation.getMatchReference().getBorderRadius(), RenderSimulation.getMatchReference().getPreviousBorderRadius());
        float scale = RenderSimulation.interpretBetweenPositions(RenderSimulation.getScale(), RenderSimulation.getPreviousScale());

        maskImage.circle(pixelX, pixelY, radius * scale * 2);
        maskImage.endDraw();

        PImage border = borderColor.get();
        border.mask(maskImage);
        pg.imageMode(PConstants.CORNER);
        pg.image(border, 0, 0);
    }
}
