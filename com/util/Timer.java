package AiCompetition.com.util;

import AiCompetition.com.Global;

public class Timer
{
    private static int startTime;
    private static int endTime;

    public static void start()
    {
        Timer.startTime = Global.getPro().millis();
    }
    public static void end()
    {
        Timer.endTime = Global.getPro().millis();
        int dt = Timer.endTime - Timer.startTime;
        System.out.println("------Time: " + dt);
    }
    public static void time()
    {
        Timer.end();
        Timer.start();
    }
}
