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

    /**
     * @param a - start position
     * @param b - end position
     * @param t - percent (0.0-1.0)
     * @return
     */
    public static float interpretBetweenPositions(float a, float b, float t)
    {
        return ((1 - t) * a + t * b);
    }

    public static float boundRadian(float angle)
    {
        float returnAngle = angle;

        while (returnAngle >= 2 * Math.PI)
        {
            returnAngle -= 2 * Math.PI;
        }
        while (returnAngle < 0)
        {
            returnAngle += 2 * Math.PI;
        }
        return returnAngle;
    }
}
