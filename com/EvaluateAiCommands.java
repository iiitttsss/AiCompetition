package AiCompetition.com;

import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;

import java.util.ArrayList;

public class EvaluateAiCommands implements Runnable
{
    private Ai ai;
    private ArrayList<ShootCommand> shootCommands;
    private ArrayList<ThrustCommand> thrustCommands;
    private Spaceship self;
    private Spaceship other;
    private ArrayList<Bullet> bullets;
    private float borderRadius;

    public float getBorderRadius()
    {
        return borderRadius;
    }

    public void setBorderRadius(float borderRadius)
    {
        this.borderRadius = borderRadius;
    }

    public EvaluateAiCommands(Ai ai, Spaceship self, Spaceship other, ArrayList<Bullet> bullets, float borderRadius)
    {
        this.setAi(ai);
        this.setSelf(self);
        this.setOther(other);
        this.setBullets(bullets);
        this.setBorderRadius(borderRadius);
    }

    @Override
    public void run()
    {
        try
        {
            this.setShootCommands(this.getAi().shootCommands(this.getSelf(), this.getOther(), this.getBullets(), borderRadius));
            this.setThrustCommands(this.getAi().thrustCommands(this.getSelf(), this.getOther(), this.getBullets(), borderRadius));
        } catch (Exception e)
        {
            this.getSelf().setDidCrash(true);
        }
    }

    public ArrayList<ThrustCommand> getThrustCommands()
    {
        return thrustCommands;
    }

    public void setThrustCommands(ArrayList<ThrustCommand> thrustCommands)
    {
        this.thrustCommands = thrustCommands;
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

    public ArrayList<ShootCommand> getShootCommands()
    {
        return shootCommands;
    }

    public void setShootCommands(ArrayList<ShootCommand> shootCommands)
    {
        this.shootCommands = shootCommands;
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
