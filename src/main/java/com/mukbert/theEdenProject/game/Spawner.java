package com.mukbert.theEdenProject.game;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.mukbert.theEdenProject.game.Game;
import com.mukbert.framework.EntityBasic;
import com.mukbert.framework.extension.MathUtil;
import com.mukbert.theEdenProject.data.Data;
import com.mukbert.theEdenProject.junk.JunkManager;

public class Spawner extends EntityBasic
{
	private Data enemyData;
	private JunkManager junkManager;
	private Game game;
	
	private List<Enemy> enemies;
	
	private double spawnTime;
	private double spawnTimeMax;
	
	public Spawner(Data enemyData, double x, double y) 
	{
		super(RECTANGLE);
		setX(x);
		setY(y);
		setWidth(Game.getSize());
		setHeight(Game.getHeight());
		
		this.enemyData = enemyData;
		
		enemies = new ArrayList<Enemy>();
		
		spawnTimeMax = 5;
	}
	
	public void init(JunkManager junkManager, Game game)
	{
		this.junkManager = junkManager;
		this.game = game;
	}
	
	public void update()
	{
		updateEnemySpawn();
		updateEnemies();
	}
	
	private void updateEnemySpawn()
	{
		if(enemies.size() < 5)
		{
			spawnTime += Game.getTimeDelta();
			
			if(spawnTime >= spawnTimeMax)
			{
				spawnTime = 0;
				
				enemies.add(createEnemy());
			}
		}
	}
	
	private void updateEnemies()
	{
		for(int i = 0; i < enemies.size(); i++)
		{
			Enemy enemy = enemies.get(i);
			
			if(enemy.isAlive())
			{
				if(enemy.isMoving())
				{
					enemy.getAnimation().update();
				}
				else if(!enemy.isWaiting())
				{
					Point2D point = getRandomPoint(enemy);
					
					enemy.moveToPoint(point);
				}
				
				enemy.move();
			}
			else
			{
				enemies.remove(enemy);
				ItemBag bag = new ItemBag(enemy, game);
				bag.getInventory().setVisible(false);
				junkManager.addItem(bag);
			}
		}
	}
	
	private Point2D getRandomPoint(EntityBasic start)
	{		
		double xPoint = getCenterX() + MathUtil.getRandomSin() * enemyData.getEntityData().getRange();
		double yPoint = getCenterY() + MathUtil.getRandomSin() * enemyData.getEntityData().getRange();
		
		return new Point2D.Double(xPoint, yPoint);
	}
	
	private Enemy createEnemy()
	{
		Enemy entity = new Enemy(enemyData, this.getCenterX(), this.getBottom());
		entity.moveX(- getWidth() / 2);
		entity.moveY(- getHeight());
		return entity;
	}
	
	public List<Enemy> getEnemies()
	{
		return enemies;
	}
}
