package com.mukbert.theEdenProject.game;

import java.awt.Graphics2D;

import com.mukbert.theEdenProject.game.Game;
import com.mukbert.theEdenProject.junk.JunkManager;

public class Camera 
{	
	private double x;
	private double y;
	private double xMax;
	private double yMax;
	
	private Player player;
	
	public void update()
	{
		if(player == null) return;
		
		x = (double)Game.getWidth()  / 2 - player.getCenterX();
		y = (double)Game.getHeight() / 2 - player.getBottom();
				
		if(x > 0) x = 0;
		else if(x < xMax) x = xMax;
		
		if(y > 0) y = 0;
		else if(y < yMax) y = yMax;
	}
	
	public void init(Player player, JunkManager junkManager)
	{
		setPlayer(player);
		setJunkManager(junkManager);
	}
	
	public void translate(Graphics2D g)
	{
		g.translate(x, y);
	}
	
	public void translateBack(Graphics2D g)
	{
		g.translate(-x, -y);
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	private void setPlayer(Player player)
	{
		this.player = player;
	}
	
	private void setJunkManager(JunkManager junkManager)
	{
		xMax = Game.getWidth()  - junkManager.getWidth();
		yMax = Game.getHeight() - junkManager.getHeight();
	}
}
