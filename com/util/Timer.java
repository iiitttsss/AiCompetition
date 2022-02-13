package AiCompetition.com.util;

import AiCompetition.com.Global;

public class Timer
{
    private static int startTime;
    private static int endTime;

    private static int counter;

    private static void startS()
    {
        Timer.startTime = Global.getPro().millis();
    }

    private static void endS(String name)
    {
        counter++;
        Timer.endTime = Global.getPro().millis();
        int dt = Timer.endTime - Timer.startTime;
        System.out.println("Time " + counter + " (" + name + "): " + dt);
    }

    public static void start()
    {
        startS();
        counter = 0;
    }

    public static void end(String name)
    {
        endS(name);
        System.out.println();
    }

    public static void time(String name)
    {
        Timer.endS(name);
        Timer.startS();
    } public static void end()
{
    endS("");
    System.out.println();
}

    public static void time()
    {
        Timer.endS("");
        Timer.startS();
    }
}
