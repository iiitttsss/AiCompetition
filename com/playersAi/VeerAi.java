package AiCompetition.com.playersAi;

import AiCompetition.com.Ai;
import AiCompetition.com.Spaceship;
import AiCompetition.com.UpgradeData;
import AiCompetition.com.Bullet;
import AiCompetition.com.commands.ShootCommand;
import AiCompetition.com.commands.ThrustCommand;
import AiCompetition.com.SpaceshipStructure;
import AiCompetition.com.util.MathUtil;


import java.util.ArrayList;

public class VeerAi implements Ai
{


    private float estimatedBulletXVel;
    private float estimatedBulletYVel;
    private float estimatedDistance;
    private short count = 6;
    private int escapeDirX;
    private int escapeDirY;
    private boolean right = true;
    private short WAIT_CONST = 1000;
    private short wait = WAIT_CONST;
    boolean up = true;
    private final static short SPEED_CONST = 43;
    private float previousBulletVel;
    private int turnPause = 6;
    private boolean first = true;




    @Override
    public ArrayList<ShootCommand> shootCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();

        if(mySpaceship.getEnergy() >= 1000)
            first = false;

        if(first)
            return shootCommands;



        if(waitShoot() && mySpaceship.getEnergy() > 400)
        {
              shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 1, SPEED_CONST, 35, (int)estimatedDistance));
        }      
        else if(willHit(mySpaceship, otherSpaceship, bulletsPositions) && mySpaceship.getEnergy() > 400)
        {
            shootCommands.add(new ShootCommand(ShootCommand.FRONT_GUN, 1, SPEED_CONST, 20, (int)estimatedDistance));
            count = 0;
        }


        //if(willHit(mySpaceship, otherSpaceship, bulletsPositions))

                /*
        this.setXPosition(this.getXPosition() + deltaTime * SPEED_MULTIPLIER * xVelocity);
        this.setYPosition(this.getYPosition() + deltaTime * SPEED_MULTIPLIER * yVelocity);
        float distSq = (this.getXPosition() - this.getStartXPosition()) * (this.getXPosition() - this.getStartXPosition()) + (this.getYPosition() - this.getStartYPosition()) * (this.getYPosition() - this.getStartYPosition());
        if (distSq > this.getLifeDistance() * this.getLifeDistance())
        {
            this.setActive(false);
        }
        */

        return shootCommands;
    }

   private void stayAway(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<ThrustCommand> thrustCommands)
   {
       float threshold = 1500;

       float dx = otherSpaceship.getXPosition() - mySpaceship.getXPosition();
       float dy = otherSpaceship.getYPosition() - mySpaceship.getYPosition();

       //float dy = otherSpaceship.getYVelocity();
       //float dx = otherSpaceship.getXVelocity();
       float dist = (float) Math.sqrt(dx * dx + dy * dy);

       if (dist > (threshold))
       {
           thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, 7));
       } else if (dist < (threshold))
       {
           thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, 7));
       }

       if(Math.random() < .005)
         rotateToOther(mySpaceship, otherSpaceship, thrustCommands);



       //float dy = otherSpaceship.getYVelocity();
	   //float dx = otherSpaceship.getXVelocity();
	   //adjustDir(dx, dy, mySpaceship.getDirection());
	  // avoidBullets(thrustCommands);

   }


    private void avoidBullets(ArrayList<ThrustCommand> thrustCommands)
    {
        int tempX = escapeDirY * -1;
        int tempY = escapeDirX;

        if(tempX < 0)
            thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, tempX * -1));
        else if(tempX > 0)
            thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, tempX));

        if(tempY < 0)
            thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, tempY * -1));
        else if(tempY > 0)
            thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, tempY));

    }

    private void rotateToOther(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<ThrustCommand> thrustCommands)
    {       
        float dx = otherSpaceship.getXPosition() - mySpaceship.getXPosition();
        float dy = otherSpaceship.getYPosition() - mySpaceship.getYPosition();

        //rotate
        float dir = (float) (Math.atan2(dy, dx));


        float myAngle = (mySpaceship.getDirection()+ (float)(Math.PI * 2));

        float dAngle = myAngle - dir;



        while (dAngle < 0)
        {
            dAngle += 2 * Math.PI;
        }
        while (dAngle > 2 * Math.PI)
        {
            dAngle -= 2 * Math.PI;
        }


	if (dAngle > Math.PI)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.CLOCKWISE_THRUSTER, 6));
        } else if (dAngle >= 0)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.COUNTER_CLOCKWISE_THRUSTER, 6));
        }
    }

     private boolean waitMove(ArrayList<Bullet> bulletsPositions, Spaceship mySpaceship)
     {
         boolean check = false;
         for (Bullet bullet : bulletsPositions)
         {
             if (bullet.getOwner() != mySpaceship)
             {
                 check = true;
             }
         }

         if(!check)
             return false;

         if(wait < WAIT_CONST)
         {
             wait++;
             return true;
         }
         else
         {
             return false;
         }
     }

    private void avoidBorder(Spaceship mySpaceship, ArrayList<ThrustCommand> thrustCommands, float borderRadius)
    {

        float dy = mySpaceship.getYVelocity();
        float dx = mySpaceship.getXVelocity();

        if(mySpaceship.getYPosition() < 0)
            borderRadius *= -1;
        if(mySpaceship.getXPosition() < 0)
            borderRadius *= -1;

        adjustDir(-dy, dx, mySpaceship.getDirection());
        float angle = mySpaceship.getDirection() + (float)Math.PI/2; //new

        if (escapeDirX > 0)
            angle += (float) (Math.PI);
        else if (escapeDirX < 0) ;

        if (escapeDirY * -1 < 0)
            angle -= (float) (Math.PI / 2);
        else if (escapeDirY * -1 > 0)
            angle += angle = (float) (Math.PI / 2);


        float xAcc = (float) Math.cos(angle);
        float yAcc = (float) Math.sin(angle);

        if(mySpaceship.getXPosition() - borderRadius > xAcc - borderRadius ||
                mySpaceship.getYPosition() - borderRadius > yAcc - borderRadius)
        {
            escapeDirX *= -1;
            escapeDirY *= -1;
        }

         avoidBullets(thrustCommands);

    }


    @Override
    public ArrayList<ThrustCommand> thrustCommands(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions, float borderRadius)
    {
        ArrayList<ThrustCommand> thrustCommands = new ArrayList<>();

        if(mySpaceship.getEnergy() >= 1000)
            first = false;

        if(first)
            return thrustCommands;


	    int threshold = 1500;
        float borderThresh = borderRadius * (float).4142 - 50;
        if(Math.abs(mySpaceship.getXPosition()) > borderThresh || Math.abs(mySpaceship.getYPosition()) > borderThresh && mySpaceship.getEnergy() > 100)
        {
            avoidBorder(mySpaceship, thrustCommands, borderThresh);
        }
        else if(waitMove(bulletsPositions, mySpaceship) && mySpaceship.getEnergy() > 300)
        {
            avoidBullets(thrustCommands);
        }
        else if(attackHit(mySpaceship, bulletsPositions, thrustCommands) && mySpaceship.getEnergy()>300)
        {
            avoidBullets(thrustCommands);
            wait = 0;

        }
	    else if(Math.abs(mySpaceship.getXPosition()) < borderThresh-150 && Math.abs(mySpaceship.getYPosition()) < borderThresh-150 &&
                mySpaceship.getEnergy() > 300 &&
                MathUtil.distSq(mySpaceship.getXPosition(), mySpaceship.getYPosition(), otherSpaceship.getXPosition(),
                        otherSpaceship.getYPosition()) <= threshold * threshold)
            stayAway(mySpaceship, otherSpaceship, thrustCommands);
        else if(!willHit(mySpaceship, otherSpaceship, bulletsPositions) && mySpaceship.getEnergy() > 700) {
//            if(rotateToOther(mySpaceship, otherSpaceship, thrustCommands))
//                for(int x = 0; x <= 1000; x++)
//                   thrustCommands.add(new ThrustCommand(ThrustCommand.CLOCKWISE_THRUSTER, 6));
//            else
//                for(int x = 0; x <= 1000; x++)

                thrustCommands.add(new ThrustCommand(ThrustCommand.CLOCKWISE_THRUSTER, 1));


                 //rotateToOther(mySpaceship, otherSpaceship, thrustCommands);


           // turnPause++;

            wait = WAIT_CONST;
        }



            // {
          //  int tempX = escapeDirY * -1;
          // int tempY = escapeDirX;

           // if(tempX < 0)
             //   thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, tempX * -1));
           // else if(tempX > 0)
               // thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, tempX));

           // if(tempY < 0)
              //  thrustCommands.add(new ThrustCommand(ThrustCommand.BACK_THRUSTER, tempY * -1));
           // else if(tempY > 0)
            //    thrustCommands.add(new ThrustCommand(ThrustCommand.FRONT_THRUSTER, tempY));
        //}



/*
        if(mySpaceship.getEnergy() < 700)
            return thrustCommands;

        float dy = otherSpaceship.getYPosition() - mySpaceship.getYPosition();
        float threshold = 50;
        int thrusterPower = 6;
        if (dy > threshold)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, thrusterPower));
        } else if (dy < -threshold)
        {
            thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, thrusterPower));

        } else
        {
            float speedThreshold = 0.1f;
            float velocity = mySpaceship.getYVelocity();
            if (velocity > speedThreshold)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.RIGHT_THRUSTER, thrusterPower));
            } else if (velocity < -speedThreshold)
            {
                thrustCommands.add(new ThrustCommand(ThrustCommand.LEFT_THRUSTER, thrusterPower));
            }
        }
*/
        return thrustCommands;
    }

    

    private boolean willHit(Spaceship mySpaceship, Spaceship otherSpaceship, ArrayList<Bullet> bulletsPositions)
    {

        float DELTA_TIME = 1 / 5f;
        float estimateBulletX = mySpaceship.getXPosition();
        float estimateBulletY = mySpaceship.getYPosition() ;
        float estimateOtherX = otherSpaceship.getXPosition();
        float estimateOtherY = otherSpaceship.getYPosition();

        float distance = 0;
        float distanceFromOtherSqr = (mySpaceship.getYPosition()-otherSpaceship.getYPosition()) * (mySpaceship.getYPosition()-otherSpaceship.getYPosition())
                + (mySpaceship.getXPosition() - otherSpaceship.getXPosition()) * (mySpaceship.getXPosition() - otherSpaceship.getXPosition()) ;

        getEstimatedBulletSpeed(ShootCommand.FRONT_GUN, mySpaceship.getDirection(),
                mySpaceship.getXVelocity(),mySpaceship.getYVelocity());

        final float ALIVE_TIME = Math.min(SpaceshipStructure.levelToValue(UpgradeData.FRONT_GUN_RANGE)* 300, distanceFromOtherSqr+1500);


        while(distance <= ALIVE_TIME)
        {
            float threshold = 200;

            float tempBulletX = estimateBulletX;
            float tempBulletY = estimateBulletY;

            estimateBulletX += DELTA_TIME * estimatedBulletXVel;
            estimateBulletY += DELTA_TIME * estimatedBulletYVel;


            estimateOtherX += DELTA_TIME * otherSpaceship.getXVelocity();
            estimateOtherY += DELTA_TIME * otherSpaceship.getYVelocity();


            distance += (estimateBulletX - tempBulletX) * (estimateBulletX - tempBulletX) + (estimateBulletY - tempBulletY) *
                    (estimateBulletY - tempBulletY);


            distanceFromOtherSqr = (mySpaceship.getYPosition()-estimateOtherX) * (mySpaceship.getYPosition()-estimateOtherX)
                    + (mySpaceship.getXPosition() - estimateOtherY) * (mySpaceship.getXPosition() - estimateOtherY) ;

            float dx = estimateOtherX - estimateBulletX;
            float dy = estimateOtherY - estimateBulletY;

            //rotate
            float dir = (float) (Math.atan2(dy, dx));


            float myAngle = (float) (Math.atan2(estimatedBulletYVel, estimatedBulletXVel));

            float dAngle = myAngle - dir;

            while (dAngle < 0)
            {
                dAngle += 2 * Math.PI;
            }
            while (dAngle > 2 * Math.PI)
            {
                dAngle -= 2 * Math.PI;
            }

            if(Math.abs(dAngle) < .07) {
                estimatedDistance = (float)2 * MathUtil.dist(mySpaceship.getXPosition(), mySpaceship.getYPosition(),
                        estimateOtherX, estimateOtherY);

                return true;
            }

            if((estimateBulletX - estimateOtherX) * (estimateBulletX - estimateOtherX) < threshold * threshold
                    && (estimateBulletY-estimateOtherY) * (estimateBulletY-estimateOtherY) < threshold * threshold &&
                    (estimateBulletX - estimateOtherX) >= 0)
            {
                estimatedDistance = Math.min(SpaceshipStructure.levelToValue(UpgradeData.FRONT_GUN_RANGE)* 300,
                        (float)1.5 * MathUtil.dist(mySpaceship.getXPosition(), mySpaceship.getYPosition(), estimateOtherX, estimateOtherY) + threshold);
                return true;
            }
            else if(distanceFromOtherSqr < (mySpaceship.getYPosition()-estimateBulletY) * (mySpaceship.getYPosition()-estimateBulletY)
                    + (mySpaceship.getXPosition() - estimateBulletX) * (mySpaceship.getXPosition() - estimateBulletX))
                return false;
        }

        return false;
    }

    private boolean attackHit(Spaceship mySpaceship, ArrayList<Bullet> bulletsPositions, ArrayList<ThrustCommand> thrustCommands)
    {
         //avoid - side to side
        float minDistSq = Integer.MAX_VALUE;
        Bullet closest = null;
        for (Bullet bullet : bulletsPositions)
        {
	    if (bullet.getOwner() != mySpaceship)
            {
		        float distSq = MathUtil.distSq(mySpaceship.getXPosition(), mySpaceship.getYPosition(), bullet.getXPosition(), bullet.getYPosition());
                if (distSq < minDistSq)
                {
		            closest = bullet;
                    minDistSq = distSq;
                }
            }
        }
       // System.out.println(mySpaceship.getXPosition() + "\t" + mySpaceship.getYPosition() + "\t");
      //  System.out.println(closest.getXPosition() + "\t" + closest.getYPosition() + "\t");


        if(closest == null)
                return false;

        	float DELTA_TIME = 1;
            float estimateBulletX = closest.getXPosition();
            float estimateBulletY = closest.getYPosition() ;

            float estimateMyX = mySpaceship.getXPosition();
            float estimateMyY = mySpaceship.getYPosition();
            float myXVel = mySpaceship.getXVelocity();
            float myYVel = mySpaceship.getYVelocity();

            float bulletXVel = closest.getXVelocity();
            float bulletYVel = closest.getYVelocity();

            float distance = MathUtil.dist(estimateBulletX, estimateBulletY, estimateMyX, estimateMyY);
            float tempDistance = distance;


		while(distance <= tempDistance)
                {
                    final float FRICTION_MULTIPLIER = 0.01f;

                    tempDistance = distance + 10;
                  float threshold = 250;

                  float tempBulletX = estimateBulletX;
                  float tempBulletY = estimateBulletY;

                  estimateBulletX += DELTA_TIME * bulletXVel;
                  estimateBulletY += DELTA_TIME * bulletYVel;


                  estimateMyX += DELTA_TIME * myXVel;
                  estimateMyY += DELTA_TIME * myYVel;

                  float speed = myXVel * myXVel + myYVel * myYVel;
                  float direction = mySpaceship.getDirection();

                    //this.setDirection(direction);
                    //
                    myXVel = ((float) (myXVel + DELTA_TIME * FRICTION_MULTIPLIER * speed * Math.cos(direction + Math.PI)));
                    myYVel = ((float) (myYVel + DELTA_TIME * FRICTION_MULTIPLIER * speed * Math.sin(direction + Math.PI)));

                  distance = MathUtil.dist(estimateBulletX, estimateBulletY, estimateMyX, estimateMyY);


                  if(distance < threshold)
                  {

                      adjustDir(bulletXVel, bulletYVel, mySpaceship.getDirection());

                      float angle = mySpaceship.getDirection() + (float)Math.PI/2; //new

                      if (escapeDirX > 0)
                          angle += (float) (Math.PI);
                      else if (escapeDirX < 0) ;

                      if (escapeDirY * -1 < 0)
                          angle -= (float) (Math.PI / 2);
                      else if (escapeDirY * -1 > 0)
                          angle += angle = (float) (Math.PI / 2);


                      float xAcc = (float) Math.cos(angle);
                      float yAcc = (float) Math.sin(angle);


	
		 if(bulletXVel == 0)
		    bulletXVel += .00001;

                  float y = bulletYVel / bulletXVel * (estimateMyX - estimateBulletX) + estimateBulletY;
                  if (y < estimateMyY)
                      up = true;
                  else if (y >= estimateMyY)
                      up = false;
                 // System.out.println(up);
                  //System.out.println(bulletYVel/bulletXVel + "\t" + estimateMyX + "\t" + estimateBulletX + "\t" + estimateBulletY + "\t" + estimateMyY);





                  // up here ////////////////////////////////////////////////////////////////////////////////////////////////


                      if(previousBulletVel == yAcc)
                          return true;
                   // if(escapeDirX != 0 || escapeDirY == 0) {
                        if (Math.abs(bulletYVel / bulletXVel) >= 7) {
                            if (xAcc > 0 && estimateBulletX > estimateMyX) {
                                escapeDirX *= -1;
                                escapeDirY *= -1;
                            } else if (xAcc < 0 && estimateBulletX < estimateMyX) {
                                escapeDirX *= -1;
                                escapeDirY *= -1;
                            }
                            System.out.println(bulletYVel / bulletXVel + "\t" + xAcc + "\t" + up + "\t" + yAcc);

                        } else if (1 / Math.abs(bulletYVel / bulletXVel) >= 7) {
                            if (yAcc > 0 && estimateBulletY > estimateMyY) {
                                escapeDirX *= -1;
                                escapeDirY *= -1;
                            } else if (yAcc < 0 && estimateBulletY < estimateMyY) {
                                escapeDirX *= -1;
                                escapeDirY *= -1;
                            }
                            System.out.println(escapeDirX + "\t" + escapeDirY + "\t" + up + "\t" + yAcc);

                        } else if (((!up && yAcc < -.1) || (up && yAcc > .1))) {
                            escapeDirX *= -1;
                            escapeDirY *= -1;
                            System.out.println(escapeDirX + "\t" + escapeDirY + "\t" + up + "\t" + yAcc);

                        }
                        System.out.println(escapeDirX + "\t" + escapeDirY + "\t" + up + "\t" + yAcc);
                        previousBulletVel = yAcc;
                   // }



                      //this.setXVelocity(this.getXVelocity() + deltaTime * ACCELERATION_MULTIPLIER * xAcc);
               // this.setYVelocity(this.getYVelocity() + deltaTime * ACCELERATION_MULTIPLIER * yAcc);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
		            return true;
                }

            }

        return false;
    }

    private void adjustDir(float xVel, float yVel, float dir)
    {


        float velocityDirection = (float) Math.atan2(yVel, xVel);
        dir += Math.PI/2;
        velocityDirection -= dir;

        if(velocityDirection < 0)
            velocityDirection += Math.PI * 2;


        if((velocityDirection - Math.PI/2 <= .02 && velocityDirection - Math.PI/2 >= -.02) ||
                (velocityDirection - 3 * Math.PI/2 <= .02 && velocityDirection - 3 * Math.PI/2 >= -.02))
        {
            escapeDirX = 0;
            escapeDirY = 7;
            System.out.println("Up");
            return;
        }



        float ratio = (float)Math.tan(velocityDirection);
        float diff = Integer.MAX_VALUE;


        if((velocityDirection - Math.PI <= .02 && velocityDirection - Math.PI >= -.02) ||
        (velocityDirection <= .02 || 2 * Math.PI - velocityDirection <= .02))
        {
            escapeDirX = -7;
            escapeDirY = 0;
            System.out.println("Right");
        }


            for(int x = -7; x <= 7; x++)
            {
                if(x == 0)
                    x++;
              for (int y = -7; y <= 7; y++)
                {
                    if(y == 0)
                        y++;

                    float tempDiff = ((float)y / x - ratio)*((float)y / x - ratio);
                  if (tempDiff < diff)
                    {
                        escapeDirX = x;
                        escapeDirY = y;
                        diff = tempDiff;
                    }
                }
            }
		
             

        //System.out.println(diff + "\t" + escapeDirX + "\t" + escapeDirY + "\t" + ratio + "\t" + (float)escapeDirX/escapeDirY + "\t" + velocityDirection + "\t" + dir);


    }


    private void getEstimatedBulletSpeed(int gun, float direction, float xVel, float yVel)
    {
        float dir = 0;
        switch (gun)
        {
            case ShootCommand.FRONT_GUN:
                dir = direction;
                break;
            case ShootCommand.BACK_GUN:
                dir = (float) (direction + Math.PI);
                break;
            case ShootCommand.LEFT_GUN:
                dir = (float) (direction + 3 * Math.PI / 2);
                break;
            case ShootCommand.RIGHT_GUN:
                dir = (float) (direction + Math.PI / 2);
                break;
        }
        float xVelBasedDirection = (float) (/*SpaceshipStructure.levelToValue(gun)*/ SPEED_CONST * Math.cos(direction));
        float yVelBasedDirection = (float) (/* SpaceshipStructure.levelToValue(gun)*/ SPEED_CONST * Math.sin(direction));

        float bulletXVel = xVelBasedDirection + xVel;
        float bulletYVel = yVelBasedDirection + yVel;

        setEstimatedBulletXVel(bulletXVel);
        setEstimatedBulletYVel(bulletYVel);
    }

    private boolean waitShoot()
    {
        if(count <= 5)
        {
            count++;
            return true;
        }
        else
        {
            return false;
        }

    }


    @Override
    public UpgradeData createStructure()
    {
        UpgradeData upgradeData = new UpgradeData();
        upgradeData.setUpgrade(UpgradeData.BATTERY_SIZE, 4);
        upgradeData.setUpgrade(UpgradeData.ENERGY_GENERATOR, 7);

        upgradeData.setUpgrade(UpgradeData.FRONT_GUN_SPEED, 2);
        upgradeData.setUpgrade(UpgradeData.FRONT_GUN_RANGE, 10);
        upgradeData.setUpgrade(UpgradeData.FRONT_GUN_DAMAGE, 6);

        upgradeData.setUpgrade(UpgradeData.BACK_THRUSTER, 3);
        upgradeData.setUpgrade(UpgradeData.FRONT_THRUSTER, 3);

        //upgradeData.setUpgrade(UpgradeData.COUNTER_CLOCKWISE_THRUSTER, 3);

        upgradeData.setUpgrade(UpgradeData.RIGHT_THRUSTER, 3);
        upgradeData.setUpgrade(UpgradeData.LEFT_THRUSTER, 3);


        upgradeData.setUpgrade(UpgradeData.HIT_POINTS, 9);

        System.out.println(upgradeData.calculateUpgradesCost());


        return upgradeData;
    }

    public float getEstimatedBulletXVel() {
        return estimatedBulletXVel;
    }

    public void setEstimatedBulletXVel(float estimatedBulletXVel) {
        this.estimatedBulletXVel = estimatedBulletXVel;
    }

    public float getEstimatedBulletYVel() {
        return estimatedBulletYVel;
    }

    public void setEstimatedBulletYVel(float estimatedBulletYVel) {
        this.estimatedBulletYVel = estimatedBulletYVel;
    }


}


