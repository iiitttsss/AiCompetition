package AiCompetition.com;

import AiCompetition.com.moreAis.Turret;
import AiCompetition.com.playersAi.BasicAi;
import AiCompetition.com.playersAi.IndAi;
import AiCompetition.com.playersAi.Orbiter;
import AiCompetition.com.render.RenderSimulation;
import processing.core.PApplet;
import processing.core.PGraphics;

public class RunMatch extends PApplet
{
    public static final float DELTA_TIME = 1 / 5f;
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


        this.setPg(this.createGraphics(width, height));
        match = new Match(width, height, true);
        match.init(new Orbiter(), new Turret());
        RenderSimulation.init(match, pg);
        //this.textFont(RenderSimulation.font);

    }

    @Override
    public void draw()
    {
        // deltaTime calculation
        int currentTime = this.millis();
        Global.setDeltaTime(currentTime - lastTime);
        lastTime = currentTime;

        for(int i = 0; i < 20; i++)
        {
            this.update();
        }
        this.render();
    }

    private void render()
    {
        RenderSimulation.render(DELTA_TIME);
        image(this.getPg(), 0, 0);
        // text(1000f * numberOfUpdates / millis(), 40, 500); // showing update FPS
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
            match.savePreviousPosition();
            this.getMatch().update(DELTA_TIME);
            RenderSimulation.matchUpdate();
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
