package AiCompetition.com;

import AiCompetition.com.playersAi.AiSample1A;
import AiCompetition.com.playersAi.AiSample1B;
import AiCompetition.com.render.RenderSimulation;
import processing.core.PApplet;
import processing.core.PGraphics;

public class RunMatch extends PApplet
{
    private int lastTime = 0;
    private Match match;
    private PGraphics pg;

    public static void main(String[] args)
    {
        PApplet.main(new String[]{RunMatch.class.getName()});
    }

    @Override
    public void settings()
    {
        this.size(800, 800);
    }

    @Override
    public void setup()
    {
        System.out.println("start program");
        Global.setPro(this);
        this.frameRate(60);

        this.setPg(this.createGraphics(width, height));
        match = new Match(width, height);
        match.init(new AiSample1A(), new AiSample1B());
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
        RenderSimulation.render(this, this.getPg(), this.getMatch());
        image(this.getPg(), 0, 0);
    }

    private void update()
    {
        this.getMatch().update();
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
