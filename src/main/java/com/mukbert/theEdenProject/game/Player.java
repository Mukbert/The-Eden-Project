package com.mukbert.theEdenProject.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;

import com.mukbert.framework.EntityAnimation;
import com.mukbert.theEdenProject.data.Data;
import com.mukbert.theEdenProject.game.Game;
import com.mukbert.theEdenProject.junk.JunkManager;

public class Player extends EntityAnimation
{	
	private boolean isMoving;
	private boolean isAttacking;
	
	private Weapon weapon;
	private JunkManager junkManager;
	private Info info;
	
	private Data data;
	
	public Player(double xPos, double yPos) 
	{
		super(RECTANGLE);
		
		setSize(Game.getSize(), Game.getSize());
		setX(xPos * getWidth());
		setY(yPos * getHeight());
		setData(2000);
		setAnimation(Game.getResource().getData(2000).getAnimationData().getAnimation());
		
		weapon = new Weapon(Game.getResource().getData(1004));
		
		weapon.setWeapon(Game.getResource().getData(1000));
	}
	
	public void init(JunkManager junkManager, Info info)
	{
		this.junkManager = junkManager;
		this.info = info;
		
		weapon.init(this);
	}
	
	public void update()
	{
		move();
		animate();
		attack();
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		super.draw(g);
		
		if(isAttacking)
		{
			weapon.draw(g);
		}
	}
	
	private void attack()
	{
		weapon.prepare();
		
		if(isAttacking)
		{
			weapon.update();
						
			if(weapon.getAnimation().hasFinished())
			{
				List<Enemy> list = junkManager.getEnemies();
				
				isAttacking = false;
				weapon.getAnimation().reset();
								
				for(Enemy enemy : list)
				{
					if(enemy.intersects(weapon))
					{
						enemy.hit(weapon.getWeaponData());
						
						if(!enemy.isAlive())
						{
							info.addXP(enemy.getEnemyData().getEntityData().getXP());
						}
					}
				}
			}
		}
		else if(Game.getKeyboard().isKeyDown(KeyEvent.VK_SPACE))
		{
			getAnimation().reset();
			isAttacking = true;
		}
	}
	
	private void animate()
	{
		if(isMoving)
		{
			getAnimation().update();
		}
		else
		{
			getAnimation().reset();
		}
	}
	
	private void move()
	{		
		boolean moveUp    = Game.getKeyboard().isKeyDown(KeyEvent.VK_W);
		boolean moveDown  = Game.getKeyboard().isKeyDown(KeyEvent.VK_S);
		boolean moveLeft  = Game.getKeyboard().isKeyDown(KeyEvent.VK_A);
		boolean moveRight = Game.getKeyboard().isKeyDown(KeyEvent.VK_D);
		
		if(moveUp && moveDown)
		{
			moveUp = false;
			moveDown = false;
		}
		if(moveLeft && moveRight)
		{
			moveLeft = false;
			moveRight = false;
		}
		
		isMoving = moveLeft || moveRight || moveUp || moveDown;
		
		double xSpeed = 0;
		double ySpeed = 0;
		double SPEED = data.getEntityData().getSpeed();
		
		if(moveUp)
		{
			ySpeed = -SPEED;
		}
		else if(moveDown)
		{
			ySpeed = SPEED;
		}
		
		if(moveLeft)
		{
			xSpeed = -SPEED;
			setFlipX(true);
		}
		else if(moveRight)
		{
			xSpeed = SPEED;
			setFlipX(false);
		}
		
		if(xSpeed != 0)
		{
			super.moveX(xSpeed * Game.getTimeDelta());
		}
		if(ySpeed != 0)
		{
			super.moveY(ySpeed * Game.getTimeDelta());
		}
	}
	
	@Override
	public void setFlipX(boolean flip)
	{
		super.setFlipX(flip);
		weapon.setFlipX(flip);
	}
	
	public boolean isMoving()
	{
		return isMoving;
	}
	
	public boolean isAttack()
	{
		return isAttacking;
	}
	
	public void setData(int ID)
	{
		data = Game.getResource().getData(ID);
	}
}
