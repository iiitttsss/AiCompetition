/**
 * each spaceship is controlled by an AI. This class keeps all the spaceship related data and updates the spaceship based on the AI's commands
 */
package AiCompetition.com;

import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;
import AiCompetition.com.render.CreateSpaceshipSprite;
import AiCompetition.com.util.MathUtil;
import processing.core.PImage;

import java.util.ArrayList;

public class Spaceship
{
    public final static float TURNING_MULTIPLIER = 0.5f;
    public final static float ACCELERATION_MULTIPLIER = 1f;
    public final static float FRICTION_MULTIPLIER = 0.01f;
    public static final int OVERTIME_POINTS_FOR_DEATH = 3;
    public static final int BASE_DAMAGE_DUE_TO_BORDER = 1;
    private SpaceshipStructure spaceshipStructure;
    private float xPosition;
    private float yPosition;
    private float xVelocity;
    private float yVelocity;
    private float direction;
    private float energy; //the amount of energy the spaceship currently have
    private int hitPoints;
    private PImage spriteBlue;
    private PImage spriteRed;
    private PImage spriteWhite;
    private int overTimePoints;//when the AI takes to long to give back command, it will get a point, after a certain amount of points, it losses
    private boolean didCrash;
    private float previousXPosition;
    private float previousYPosition;
    private float previousDirection;
    private int numberOfUpdatesSinceLastHitByBullet;

    protected void updateBorder(float borderRadius)
    {
        if (MathUtil.distSq(0, 0, this.getXPosition(), this.getYPosition()) > borderRadius * borderRadius)
        {
            this.setHitPoints(this.getHitPoints() - Spaceship.BASE_DAMAGE_DUE_TO_BORDER);
        }
    }

    protected void savePreviousPosition()
    {
        this.setPreviousXPosition(this.getXPosition());
        this.setPreviousYPosition(this.getYPosition());
        this.setPreviousDirection(this.getDirection());
    }

    protected void updateEnergy(float deltaTime)
    {
        this.energy += this.getSpaceshipStructure().getAttribute(UpgradeData.ENERGY_GENERATOR) * deltaTime;
        if (this.getEnergy() > this.getSpaceshipStructure().getAttribute(UpgradeData.BATTERY_SIZE))
        {
            this.setEnergy(this.getSpaceshipStructure().getAttribute(UpgradeData.BATTERY_SIZE));
        }
    }

    /**
     * called when hit by a bullet
     *
     * @param bullet - the bullet
     */
    protected void hitByBullet(Bullet bullet)
    {
        this.setHitPoints(this.getHitPoints() - bullet.getDamage());
        setNumberOfUpdatesSinceLastHitByBullet(0);
    }

    private SpaceshipStructure covertUpgradeDataToSpaceshipStructure(UpgradeData upgradeData)
    {
        return new SpaceshipStructure(upgradeData);
    }

    public int getNumberOfUpdatesSinceLastHitByBullet()
    {
        return numberOfUpdatesSinceLastHitByBullet;
    }

    protected void setNumberOfUpdatesSinceLastHitByBullet(int numberOfUpdatesSinceLastHitByBullet)
    {
        this.numberOfUpdatesSinceLastHitByBullet = numberOfUpdatesSinceLastHitByBullet;
    }

    /**
     * called before the match begins
     *
     * @param upgradeData - the upgrades of the spaceship as created by the AI
     */
    protected void init(UpgradeData upgradeData)
    {
        SpaceshipStructure spaceshipStructure = this.covertUpgradeDataToSpaceshipStructure(upgradeData);
        this.setSpaceshipStructure(spaceshipStructure);
        //this.setDirection((float) (Math.random() * Math.PI * 2));
        this.setXPosition((float) (Math.random() * 2000 - 1000));
        this.setYPosition((float) (Math.random() * 2000 - 1000));
        this.setHitPoints(spaceshipStructure.getAttribute(UpgradeData.HIT_POINTS));

        if(Match.needToRender)
        {
            this.createSprites(upgradeData, spaceshipStructure);
        }
        this.setOverTimePoints(0);
        this.setDidCrash(false);

        this.setEnergy(this.getSpaceshipStructure().getAttribute(UpgradeData.BATTERY_SIZE));
        setNumberOfUpdatesSinceLastHitByBullet(0);
    }

    public PImage getSpriteWhite()
    {
        return spriteWhite;
    }

    protected void setSpriteWhite(PImage spriteWhite)
    {
        this.spriteWhite = spriteWhite;
    }

    /**
     * creates the spaceship sprites according to the upgrades
     *
     * @param upgradeData - the upgrades of the spaceship from the AI
     */
    private void createSprites(UpgradeData upgradeData, SpaceshipStructure spaceshipStructure)
    {
        this.setSpriteBlue(CreateSpaceshipSprite.createSpaceshipSprite(upgradeData, spaceshipStructure, CreateSpaceshipSprite.BLUE_SPRITE));
        this.setSpriteRed(CreateSpaceshipSprite.createSpaceshipSprite(upgradeData, spaceshipStructure, CreateSpaceshipSprite.RED_SPRITE));
        this.setSpriteWhite(CreateSpaceshipSprite.createSpaceshipSprite(upgradeData, spaceshipStructure, CreateSpaceshipSprite.WHITE_SPRITE));

    }

    /**
     * to prevent the AI being able to execute 2 or more commands on the same component at the same turn, this method will delete the extra commands
     *
     * @param shootCommands -
     */
    private void fixShootCommandsList(ArrayList<ShootCommand> shootCommands)
    {
        for (int i = 0; i < shootCommands.size(); i++)
        {
            for (int j = i + 1; j < shootCommands.size(); j++)
            {
                if (shootCommands.get(i).getWhichComponent() == shootCommands.get(j).getWhichComponent())
                {
                    shootCommands.remove(j);
                    j--;
                }
            }
        }
    }

    /**
     * @param shootCommands - the commands that were generated by the AI
     */
    protected void executeShootCommands(float deltaTime, ArrayList<ShootCommand> shootCommands, BulletManager bulletManager)
    {
        if (shootCommands == null)
        {
            return;
        }
        this.fixShootCommandsList(shootCommands);
        int cost = this.calculateShootCommandsCost(deltaTime, shootCommands);
        if (cost > this.getEnergy()) // if all the moves are to0 expensive, do not execute any of them
        {
            return;
        }
        this.energy -= cost;

        for (ShootCommand sc : shootCommands)
        {
            sc.fixCommand(this);
            float direction = 0;
            switch (sc.getWhichComponent())
            {
                case ShootCommand.FRONT_GUN:
                    direction = this.getDirection();
                    break;
                case ShootCommand.BACK_GUN:
                    direction = (float) (this.getDirection() + Math.PI);
                    break;
                case ShootCommand.LEFT_GUN:
                    direction = (float) (this.getDirection() + 3 * Math.PI / 2);
                    break;
                case ShootCommand.RIGHT_GUN:
                    direction = (float) (this.getDirection() + Math.PI / 2);
                    break;
            }
            float xVelBasedDirection = (float) (sc.getSpeed() * Math.cos(direction));
            float yVelBasedDirection = (float) (sc.getSpeed() * Math.sin(direction));

            float bulletXVel = xVelBasedDirection + xVelocity;
            float bulletYVel = yVelBasedDirection + yVelocity;

            float bulletSpeed = (float) Math.sqrt(bulletXVel * bulletXVel + bulletYVel * bulletYVel);


            bulletManager.addBullet(this, this.getXPosition(), this.getYPosition(), bulletXVel, bulletYVel, sc.getRadius(), (int) bulletSpeed, sc.getDamage(), sc.getRange());
        }
    }

    protected void updateMovement(float deltaTime)
    {
        this.numberOfUpdatesSinceLastHitByBullet++;
        this.setXPosition(this.getXPosition() + deltaTime * this.getXVelocity());
        this.setYPosition(this.getYPosition() + deltaTime * this.getYVelocity());
    }

    /**
     * @param thrustCommands - the list of thrust commands that the AI made
     * @return - calculating the cost of all the thrust commands together
     */
    public int calculateThrustCommandsCost(float deltaTime, ArrayList<ThrustCommand> thrustCommands)
    {
        int valueSum = 0;
        for (ThrustCommand tc : thrustCommands)
        {
            valueSum += tc.calculateCost(deltaTime);
        }
        return valueSum;
    }

    /**
     * @param shootCommands - the list of shoot commands that the AI made
     * @return - calculating the cost of all the shoot commands together
     */
    public int calculateShootCommandsCost(float deltaTime, ArrayList<ShootCommand> shootCommands)
    {
        int valueSum = 0;
        for (ShootCommand sc : shootCommands)
        {
            valueSum += sc.calculateCost(deltaTime);
        }
        return valueSum;
    }

    /**
     * to prevent the AI being able to execute 2 or more commands on the same component at the same turn, this method will delete the extra commands
     *
     * @param thrustCommands -
     */
    private void fixThrustCommandsList(ArrayList<ThrustCommand> thrustCommands)
    {
        for (int i = 0; i < thrustCommands.size(); i++)
        {
            for (int j = i + 1; j < thrustCommands.size(); j++)
            {
                if (thrustCommands.get(i).getWhichComponent() == thrustCommands.get(j).getWhichComponent())
                {
                    thrustCommands.remove(j);
                    j--;
                }
            }
        }
    }

    /**
     * @param thrustCommands - the commands that were generated by the AI
     */
    protected void executeThrustCommands(float deltaTime, ArrayList<ThrustCommand> thrustCommands)
    {
        if (thrustCommands == null)
        {
            return;
        }
        this.fixThrustCommandsList(thrustCommands);
        int cost = this.calculateThrustCommandsCost(deltaTime, thrustCommands);
        if (cost > this.getEnergy()) // if all the moves are too expensive, do not execute any of them
        {
            return;
        }
        this.energy -= cost;

        float xAcc = 0;
        float yAcc = 0;

        for (ThrustCommand tc : thrustCommands)
        {
            tc.fixCommand(this);
            float thrustForce = deltaTime * tc.getForceValue();
            float angle = 0;

            switch (tc.getWhichComponent())
            {
                case ThrustCommand.BACK_THRUSTER:
                    angle = this.getDirection();
                    break;
                case ThrustCommand.FRONT_THRUSTER:
                    angle = (float) (this.getDirection() + Math.PI);
                    break;
                case ThrustCommand.RIGHT_THRUSTER:
                    angle = (float) (this.getDirection() - Math.PI / 2);
                    break;
                case ThrustCommand.LEFT_THRUSTER:
                    angle = (float) (this.getDirection() + Math.PI / 2);
                    break;
                case ThrustCommand.CLOCKWISE_THRUSTER:
                    angle = deltaTime * deltaTime * TURNING_MULTIPLIER / thrustForce;
                    break;
                case ThrustCommand.COUNTER_CLOCKWISE_THRUSTER:
                    angle = -deltaTime * deltaTime * TURNING_MULTIPLIER / thrustForce;
                    break;
            }
            switch (tc.getWhichComponent())
            {
                case ThrustCommand.BACK_THRUSTER:
                case ThrustCommand.FRONT_THRUSTER:
                case ThrustCommand.RIGHT_THRUSTER:
                case ThrustCommand.LEFT_THRUSTER:
                    xAcc += thrustForce * Math.cos(angle);
                    yAcc += thrustForce * Math.sin(angle);
                    break;
                case ThrustCommand.CLOCKWISE_THRUSTER:
                case ThrustCommand.COUNTER_CLOCKWISE_THRUSTER:
                    this.setDirection(this.getDirection() + angle);
                    break;
            }
        }
        this.fixDirection();

        this.setXVelocity(this.getXVelocity() + deltaTime * ACCELERATION_MULTIPLIER * xAcc);
        this.setYVelocity(this.getYVelocity() + deltaTime * ACCELERATION_MULTIPLIER * yAcc);

        //friction
        float speed = getXVelocity() * getXVelocity() + getYVelocity() * getYVelocity();
        float direction = calculateDirectionBasedOnVelocityComponents();

        //this.setDirection(direction);
        this.setXVelocity((float) (this.getXVelocity() + deltaTime * FRICTION_MULTIPLIER * speed * Math.cos(direction + Math.PI)));
        this.setYVelocity((float) (this.getYVelocity() + deltaTime * FRICTION_MULTIPLIER * speed * Math.sin(direction + Math.PI)));
    }

    private void fixDirection()
    {
        while (this.getDirection() < 0)
        {
            direction += 2 * Math.PI;
            previousDirection += 2 * Math.PI;
        }
        while (this.getDirection() >= 2 * Math.PI)
        {
            direction -= 2 * Math.PI;
            previousDirection -= 2 * Math.PI;
        }
    }

    /**
     * @return - calculating the angle from the x and y velocities
     */
    private float calculateDirectionBasedOnVelocityComponents()
    {
        float dx = getXVelocity();
        float dy = getYVelocity();
        if (dx == 0)
        {
            if (dy >= 0)
            {
                return (float) (Math.PI / 2);
            } else
            {
                return (float) (3 * Math.PI / 2);
            }
        }
        if (dy == 0)
        {
            if (dx >= 0)
            {
                return 0;
            } else
            {
                return (float) Math.PI;
            }
        }
        float atan = (float) Math.atan(Math.abs(getYVelocity() / getXVelocity()));

        if (dx > 0)
        {
            if (dy > 0)
            {
                return atan;//Q1
            } else
            {
                return (float) (2 * Math.PI - atan);//Q4
            }
        } else
        {
            if (dy > 0)
            {
                return (float) (Math.PI - atan);//Q2
            } else
            {
                return (float) (Math.PI + atan);//Q3
            }
        }
    }

    protected void addOverTimePoint()
    {
        this.overTimePoints++;
    }

    public float getPreviousXPosition()
    {
        return previousXPosition;
    }

    protected void setPreviousXPosition(float previousXPosition)
    {
        this.previousXPosition = previousXPosition;
    }

    public float getPreviousYPosition()
    {
        return previousYPosition;
    }

    protected void setPreviousYPosition(float previousYPosition)
    {
        this.previousYPosition = previousYPosition;
    }

    public float getPreviousDirection()
    {
        return previousDirection;
    }

    protected void setPreviousDirection(float previousDirection)
    {
        this.previousDirection = previousDirection;
    }

    /**
     * @return - returns true if the spaceship is alive (false otherwise).
     */
    public boolean isAlive()
    {
        return this.getHitPoints() > 0 && !this.didCrash && this.getOverTimePoints() < OVERTIME_POINTS_FOR_DEATH;
    }

    public boolean isDidCrash()
    {
        return didCrash;
    }

    protected void setDidCrash(boolean didCrash)
    {
        this.didCrash = didCrash;
    }

    public int getOverTimePoints()
    {
        return overTimePoints;
    }

    protected void setOverTimePoints(int overTimePoints)
    {
        this.overTimePoints = overTimePoints;
    }

    public PImage getSpriteRed()
    {
        return spriteRed;
    }

    protected void setSpriteRed(PImage spriteRed)
    {
        this.spriteRed = spriteRed;
    }

    public PImage getSpriteBlue()
    {
        return spriteBlue;
    }

    protected void setSpriteBlue(PImage spriteBlue)
    {
        this.spriteBlue = spriteBlue;
    }

    public int getHitPoints()
    {
        return hitPoints;
    }

    protected void setHitPoints(int hitPoints)
    {
        this.hitPoints = hitPoints;
    }

    public float getEnergy()
    {
        return energy;
    }

    protected void setEnergy(float energy)
    {
        this.energy = energy;
    }

    public SpaceshipStructure getSpaceshipStructure()
    {
        return spaceshipStructure;
    }

    protected void setSpaceshipStructure(SpaceshipStructure spaceshipStructure)
    {
        this.spaceshipStructure = spaceshipStructure;
    }


    public float getXPosition()
    {
        return xPosition;
    }

    protected void setXPosition(float xPosition)
    {
        this.xPosition = xPosition;
    }

    public float getYPosition()
    {
        return yPosition;
    }

    protected void setYPosition(float yPosition)
    {
        this.yPosition = yPosition;
    }

    public float getXVelocity()
    {
        return xVelocity;
    }

    protected void setXVelocity(float xVelocity)
    {
        this.xVelocity = xVelocity;
    }

    public float getYVelocity()
    {
        return yVelocity;
    }

    protected void setYVelocity(float yVelocity)
    {
        this.yVelocity = yVelocity;
    }

    public float getDirection()
    {
        return direction;
    }

    protected void setDirection(float direction)
    {
        this.direction = direction;
    }
}
