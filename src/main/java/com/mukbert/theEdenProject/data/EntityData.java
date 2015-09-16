package com.mukbert.theEdenProject.data;

import java.util.ArrayList;
import java.util.List;

import com.mukbert.theEdenProject.game.Game;
import com.mukbert.theEdenProject.game.Resource;

public class EntityData
{
	private int life;
	private double range;
	private double speed;
	private int XP;
	private List<Integer> items;
	
	public EntityData()
	{
		setRange(1);
		setLife(1);
		setSpeed(1);
		setXP(0);
		setItemIDs(new ArrayList<Integer>());
	}
	
	public void setXP(int XP)
	{
		this.XP = XP;
	}
	
	public int getXP()
	{
		return XP;
	}
	
	public void setRange(double range)
	{
		this.range = range;
	}
	
	public double getRange()
	{
		return range * Game.getSize();
	}
	
	public void setSpeed(double speed)
	{
		this.speed = speed;
	}
	
	public double getSpeed()
	{
		return speed * Game.getSize();
	}
	
	public void setLife(int life)
	{
		this.life = life;
	}

	public int getLife() 
	{
		return life;
	}
	
	public void addItemID(int itemID)
	{
		items.add(itemID);
	}
	
	public void setItemIDs(List<Integer> items)
	{
		this.items = items;
	}
	
	public Data[] getItems(Resource resource)
	{
		Data[] data = new Data[items.size()];
		for(int i = 0; i < data.length; i++)
		{
			data[i] = resource.getData(items.get(i));
		}
		return data;
	}
	
	public List<Integer> getItemIDs()
	{
		return items;
	}
}
