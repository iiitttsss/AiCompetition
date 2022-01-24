package AiCompetition.com;

import processing.core.PApplet;

public class Global
{
	private static PApplet pro;
	private static int deltaTime;

	public static PApplet getPro()
	{
		return pro;
	}

	protected static void setPro(PApplet pro)
	{
		Global.pro = pro;
	}

	public static int getDeltaTime()
	{
		return deltaTime;
	}

	protected static void setDeltaTime(int deltaTime)
	{
		Global.deltaTime = deltaTime;
	}
}
