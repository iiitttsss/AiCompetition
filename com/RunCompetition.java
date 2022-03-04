/**
 * this call loads all the AIs and run the entire competition
 */
package AiCompetition.com;

import processing.core.PApplet;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class RunCompetition
{
    //TODO
    // check for all AIs names
    // create a .java file that import and create objects of all these AIs
    // pair AIs
    // compete each pair
    // tally the scores
    private static ClassLoader classLoader = RunCompetition.class.getClassLoader();

    public static void main(String[] args)
    {
        final int MATCHES_PER_PAIR = 100;
        PApplet pro = new PApplet();
        Global.setPro(pro);
        String[] aisFileNamesArray = createAisNamesArray("src/AiCompetition/com/playersAi");
        ArrayList<String[]> aiPairs = createPairs(aisFileNamesArray);
        System.out.println("There are " + aiPairs.size() + " AI pairs");
        HashMap<String, Integer> wins = new HashMap<>();
        for (String name : aisFileNamesArray)
        {
            wins.put(name, 0);
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < MATCHES_PER_PAIR; i++)
        {
            for (String[] pair : aiPairs)
            {
                // System.out.println(pair[0] + " vs. " + pair[1]);

                //String[] testPair = {"Orbiter", "TestReflection"};
                int winner = runMatchForPair(pair);
                //System.out.println(winner);
                if (winner == 1 || winner == 2)
                {
                    wins.put(pair[winner - 1], wins.get(pair[winner - 1]) + 1);
                }
            }
            long currentTime = System.currentTimeMillis();
            float totalTime = (float) (currentTime - startTime) * MATCHES_PER_PAIR / (i + 1) / 1000 / 60;
            float timeRemaining = totalTime - (float) (currentTime - startTime) / 1000 / 60;
            System.out.println("totalTimeEstimation: " + totalTime + " min. \testimatedTimeRemaining: " + (int) (timeRemaining * 60) + " sec.");
        }

        for (String key : wins.keySet())
        {
            float score = ((float) wins.get(key) / MATCHES_PER_PAIR) * aisFileNamesArray.length / aiPairs.size();
            System.out.println(key + ": " + score);
        }
    }

    private static int runMatchForPair(String[] pair)
    {
        Ai ai1 = createNewInstanceFromName(pair[0]);
        Ai ai2 = createNewInstanceFromName(pair[1]);
        Match match = new Match(1, 1, false);
        match.init(ai1, ai2);
        match.run();

        boolean s1Alive = match.getSpaceship1().isAlive();
        boolean s2Alive = match.getSpaceship2().isAlive();

        if (!(s1Alive ^ s2Alive))
        {
            return -1;
        } else if (s1Alive)
        {
            return 1;
        } else if (s2Alive)
        {
            return 2;
        } else
        {
            System.out.println("not supposed to get here");
            return -2;
        }
    }

    private static ArrayList<String[]> createPairs(String[] aisFileNamesArray)
    {
        ArrayList<String[]> pairs = new ArrayList<>();
        for (int i = 0; i < aisFileNamesArray.length; i++)
        {
            for (int j = i + 1; j < aisFileNamesArray.length; j++)
            {
                String[] pair = new String[2];
                pair[0] = aisFileNamesArray[i];
                pair[1] = aisFileNamesArray[j];
                pairs.add(pair);
            }
        }
        return pairs;
    }

    private static Ai createNewInstanceFromName(String name)
    {
        try
        {
            Class aiClass = classLoader.loadClass("AiCompetition.com.playersAi." + name);
            Constructor constructor = aiClass.getConstructor();
            Ai aiInstance = (Ai) constructor.newInstance();
            return aiInstance;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static String[] createAisNamesArray(String folderPath)
    {
        File f = new File(folderPath);
        String[] aisFileNamesArray = f.list();
        for (int i = 0; i < aisFileNamesArray.length; i++)
        {
            String fileName = aisFileNamesArray[i];
            aisFileNamesArray[i] = fileName.substring(0, fileName.length() - 5);
            //System.out.println(fileName);
        }
        return aisFileNamesArray;
    }
}
