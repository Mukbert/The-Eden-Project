package com.mukbert.theEdenProject.data;

import java.awt.Color;

public class MapData 
{
	private int r;
	private int g;
	private int b;
	private Color color;
	
	public MapData() 
	{
		setColor(Color.white);
	}
	
	public void setR(int r)
	{
		this.r = r;
		setColor(new Color(r, g, b));
	}
	
	public void setG(int g)
	{
		this.g = g;
		setColor(new Color(r, g, b));
	}
	
	public void setB(int b)
	{
		this.b = b;
		setColor(new Color(r, g, b));
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}

	public Color getColor()
	{
		return color;
	}
}
