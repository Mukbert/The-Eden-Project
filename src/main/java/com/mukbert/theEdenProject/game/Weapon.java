package com.mukbert.theEdenProject.game;

import com.mukbert.framework.Animation;
import com.mukbert.framework.EntityAnimation;
import com.mukbert.theEdenProject.data.Data;

public class Weapon extends EntityAnimation
{
	private Data data;
	
	private Player player;
	
	public Weapon(Data data) 
	{
		super(ELLIPSE);
		setAnimation(new Animation());
		setWidth (Game.getSize());
		setHeight(Game.getSize());
		
		this.data = data;
		setWeapon(data);
	}
	
	public void init(Player player)
	{
		this.player = player;
	}
	
	public void prepare()
	{		
		if(getFlipX())
		{
			setX(player.getCenterX() - getWidth());
		}
		else
		{
			setX(player.getCenterX());
		}
		
		setY(player.getBottom() - getHeight());
	}
	
	public void update()
	{
		getAnimation().update();
	}
	
	public void setWeapon(Data data)
	{
		this.data = data;
		
		super.setAnimation(data.getAnimationData().getAnimation());
		super.setWidth (Game.getSize() * data.getScale());
		super.setHeight(Game.getSize() * data.getScale());
	}
	
	public void reset()
	{
		getAnimation().reset();
	}

	public Data getWeaponData()
	{
		return data;
	}
}
