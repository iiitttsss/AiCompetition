package AiCompetition.com;

import AiCompetition.com.bullets.Bullet;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class EvaluateThrustCommandForAi implements Runnable
{
    private Ai ai;
    private ArrayList<ThrustCommand> thrustCommands;
    private Spaceship self;
    private Spaceship other;
    private ArrayList<Bullet> bullets;

    public EvaluateThrustCommandForAi(Ai ai, Spaceship self, Spaceship other, ArrayList<Bullet> bullets)
    {
        this.setAi(ai);
        this.setSelf(self);
        this.setOther(other);
        this.setBullets(bullets);
    }

    @Override
    public void run()
    {
        this.setThrustCommands(this.getAi().thrustCommands(this.getSelf(),this.getOther(), this.getBullets()));
    }

    public Spaceship getSelf()
    {
        return self;
    }

    public void setSelf(Spaceship self)
    {
        this.self = self;
    }

    public Spaceship getOther()
    {
        return other;
    }

    public void setOther(Spaceship other)
    {
        this.other = other;
    }

    public ArrayList<Bullet> getBullets()
    {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets)
    {
        this.bullets = bullets;
    }

    public ArrayList<ThrustCommand> getThrustCommands()
    {
        return thrustCommands;
    }

    public void setThrustCommands(ArrayList<ThrustCommand> thrustCommands)
    {
        this.thrustCommands = thrustCommands;
    }

    public Ai getAi()
    {
        return ai;
    }

    public void setAi(Ai ai)
    {
        this.ai = ai;
    }
}
