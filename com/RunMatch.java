package AiCompetition.com;

import AiCompetition.com.playersAi.*;
import AiCompetition.com.render.CreateSpaceshipSprite;
import AiCompetition.com.render.RenderSimulation;
import processing.core.PApplet;
import processing.core.PGraphics;

public class RunMatch extends PApplet
{
    private static final float DELTA_TIME = 1 / 25f;
    public int numberOfUpdates = 0;
    private int lastTime = 0;
    private Match match;
    private PGraphics pg;
    private int millisSinceLastUpdate = 0;

    public static void main(String[] args)
    {
        PApplet.main(new String[]{RunMatch.class.getName()});
    }

    @Override
    public void settings()
    {
        this.size(800, 800);
        //this.fullScreen();
    }

    @Override
    public void setup()
    {
        System.out.println("start program");
        Global.setPro(this);
        this.frameRate(60);

        CreateSpaceshipSprite.loadSprites("src/AiCompetition/com/render/SpaceshipKit.png"); // TODO - need to move to the match generator because this line only need to be executed once


        this.setPg(this.createGraphics(width, height));
        match = new Match(width, height);
        match.init(new Tester(), new Turret());
        RenderSimulation.init(match, pg);
    }

    @Override
    public void draw()
    {
        // deltaTime calculation
        int currentTime = this.millis();
        Global.setDeltaTime(currentTime - lastTime);
        lastTime = currentTime;

        this.update();
        this.render();
    }

    private void render()
    {
        RenderSimulation.render(DELTA_TIME);
        image(this.getPg(), 0, 0);
        text(1000f * numberOfUpdates / millis(), 40, 500);
    }

    /**
     * @return - returns true if it needs to update the match on that frame
     */
    private boolean needToUpdate()
    {
        int millisForUpdate = (int) (1000 * DELTA_TIME);
        if (millisSinceLastUpdate >= millisForUpdate)
        {
            millisSinceLastUpdate -= millisForUpdate;
            numberOfUpdates++;
            return true;
        }
        return false;
    }

    private void update()
    {
        millisSinceLastUpdate += Global.getDeltaTime();
        while (this.needToUpdate())
        {
            this.getMatch().update(DELTA_TIME);
        }
    }

    public int getMillisSinceLastUpdate()
    {
        return millisSinceLastUpdate;
    }

    public void setMillisSinceLastUpdate(int millisSinceLastUpdate)
    {
        this.millisSinceLastUpdate = millisSinceLastUpdate;
    }

    public int getLastTime()
    {
        return lastTime;
    }

    public void setLastTime(int lastTime)
    {
        this.lastTime = lastTime;
    }

    public Match getMatch()
    {
        return match;
    }

    public void setMatch(Match match)
    {
        this.match = match;
    }

    public PGraphics getPg()
    {
        return pg;
    }

    public void setPg(PGraphics pg)
    {
        this.pg = pg;
    }

}
