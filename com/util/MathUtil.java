package AiCompetition.com.util;

public class MathUtil
{
    public static float map(float value, float minFrom, float maxFrom, float minTo, float maxTo)
    {
        return (value - minFrom) * (maxTo - minTo) / (maxFrom - minFrom) + minTo;
    }

    public static float distSq(float x1, float y1, float x2, float y2)
    {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    public static float dist(float x1, float y1, float x2, float y2)
    {
        return (float) Math.sqrt(distSq(x1, y1, x2, y2));
    }
}
