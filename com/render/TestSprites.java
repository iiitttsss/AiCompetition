package AiCompetition.com.render;

import processing.core.PApplet;
import processing.core.PImage;

public class TestSprites extends PApplet
{
    public static void main(String[] args)
    {
        PApplet.main(new String[]{TestSprites.class.getName()});
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
        this.frameRate(60);
        noSmooth();
        CreateSpaceshipSprite.loadSprites("src/AiCompetition/com/render/SpaceshipKit.png");
    }

    @Override
    public void draw()
    {
        background(150);
        image(CreateSpaceshipSprite.getSprites().get(0), 0, 0);
        PImage spaceship = CreateSpaceshipSprite.createSpaceshipSprite(null, null, CreateSpaceshipSprite.BLUE_SPRITE);
        image(spaceship.get(), 0, 32);
    }
}
