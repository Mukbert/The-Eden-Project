package com.mukbert.theEdenProject.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import com.mukbert.framework.EntityAnimation;
import com.mukbert.theEdenProject.data.Data;
import com.mukbert.theEdenProject.game.Game;

public class Enemy extends EntityAnimation
{
	private double xTarget;
	private double yTarget;
	private boolean isMoving;
	private boolean isWaiting;
	private double sleepTime;
	private Data data;
	private int life;
	
	public Enemy(Data data, double xCenter, double yCenter) 
	{
		super(RECTANGLE);
		setAnimation(data.getAnimationData().getAnimation().getClone());
		setWidth (Game.getSize() * data.getScale());
		setHeight(Game.getSize() * data.getScale());
		setX(xCenter - getWidth()  / 2);
		setY(yCenter - getHeight() / 2);
				
		this.data = data;
		this.life = data.getEntityData().getLife();
	}
	
	public void move()
	{
		if(isMoving)
		{
			double xDiff = xTarget - getX();
			double yDiff = yTarget - getY();
			double rotation = Math.atan2(yDiff, xDiff);
			double xSpeed = Math.cos(rotation) * data.getEntityData().getSpeed();
			double ySpeed = Math.sin(rotation) * data.getEntityData().getSpeed();
			
			moveX(xSpeed * Game.getTimeDelta());
			moveY(ySpeed * Game.getTimeDelta());
			
			if(Math.abs(xDiff) < 3) setX(xTarget);
			if(Math.abs(yDiff) < 3) setY(yTarget);
			
			if(Math.abs(xDiff) < 10 || Math.abs(yDiff) < 10)
			{
				sleepTime = 2 + 2 * Math.random();
				isWaiting = true;
				isMoving = false;
				getAnimation().reset();
			}
			
			if(xDiff < 0) setFlipX(true);
			else setFlipX(false);
		}
		else if(isWaiting)
		{
			sleepTime -= Game.getTimeDelta();
			
			if(sleepTime <= 0)
			{
				isWaiting = false;
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		super.draw(g);
		
		g.setColor(Color.green);
		
//		RenderUtil.drawStringCenterX(g, "" + life, getCenterX(), getTop() - 5);
	}
	
	public void moveToPoint(Point2D p)
	{
		if(!isMoving)
		{
			xTarget = p.getX() - getWidth() / 2;
			yTarget = p.getY() - getHeight();
			isMoving = true;
		}
	}
	
	public void hit(Data weapon)
	{
		life -= weapon.getItemData().getDamage();
		
		if(life <= 0)
		{
			setAlive(false);
		}
	}
	
	public Data getEnemyData()
	{
		return data;
	}
	
	public boolean isWaiting()
	{
		return isWaiting;
	}
	
	public boolean isMoving()
	{
		return isMoving;
	}
}
