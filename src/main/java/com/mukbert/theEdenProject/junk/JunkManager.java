package com.mukbert.theEdenProject.junk;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.mukbert.theEdenProject.data.Data;
import com.mukbert.theEdenProject.game.Game;
import com.mukbert.framework.EntityImage;
import com.mukbert.theEdenProject.data.ImageData;
import com.mukbert.theEdenProject.game.Enemy;
import com.mukbert.theEdenProject.game.ItemBag;
import com.mukbert.theEdenProject.game.Player;
import com.mukbert.theEdenProject.game.Resource;
import com.mukbert.theEdenProject.game.Spawner;

public class JunkManager 
{	
	private int width;
	private int height;
	
	private List<Junk> junks;
	private List<EntityImage> entities;
	private List<Junk> activeJunks;
	private List<Enemy> enemies;
	private Junk[][] junkMap;
	private Comparator<EntityImage> sort;
	
	private int xJunkMax;
	private int yJunkMax;
	
	private Player player;
	
	BufferedImage map_terrain;
	BufferedImage map_entity;
	
	public JunkManager() 
	{
		map_terrain = ImageData.MAP_TERRAIN.getImage();
		map_entity  = ImageData.MAP_ENTITY.getImage();

		xJunkMax = map_terrain.getWidth()  / Game.getJunkSize();
		yJunkMax = map_terrain.getHeight() / Game.getJunkSize();
		
		width  = xJunkMax * Game.getJunkSize() * Game.getSize();
		height = yJunkMax * Game.getJunkSize() * Game.getSize();
		
		junks = new ArrayList<Junk>();
		activeJunks = new ArrayList<Junk>();
		entities = new ArrayList<EntityImage>();
		enemies = new ArrayList<Enemy>();
		junkMap = new Junk[xJunkMax][yJunkMax];
		sort = new Sort();
	}
	
	public void init(Resource resource, Player player, Game game)
	{
		this.player = player;
		
		HashMap<Color, Data> terrainData = new HashMap<Color, Data>();
		HashMap<Color, Data> terrainEntityData = new HashMap<Color, Data>();
		for(Data data : resource.getData().values())
		{
			if(data.getMapData() != null)
			{
				if(data.getType().equals("terrain"))
				{
					terrainData.put(data.getMapData().getColor(), data);
				}
				else if(data.getType().equals("worldObject") || data.getType().equals("spawner"))
				{
					terrainEntityData.put(data.getMapData().getColor(), data);
				}
			}
		}
		
		for(int yPos = 0; yPos < yJunkMax; yPos++)
		{
			for(int xPos = 0; xPos < xJunkMax; xPos++)
			{
				Junk junk = new Junk(xPos, yPos, terrainData, terrainEntityData);
				
				junks.add(junk);
				junkMap[xPos][yPos] = junk;
			}
		}
		
		for(Junk junk : junks)
		{
			junk.init(map_terrain, map_entity, this, game);
		}
	}
	
	public void update()
	{
		prepare();
		calculateAktiveJunks();
		setEnemies();
		sortEntities();
		
		for(Junk junk : activeJunks)
		{
			junk.update();
		}
	}
	
	public void draw(Graphics2D g)
	{
		for(Junk junk : activeJunks)
		{
			junk.draw(g);
		}
		
		for(EntityImage entity : entities)
		{
			entity.draw(g);
		}
	}
	
	private void setEnemies()
	{
		for(Junk junk : activeJunks)
		{
			for(Spawner spawner : junk.getSpawner())
			{
				enemies.addAll(spawner.getEnemies());
			}
		}
	}
	
	private void sortEntities()
	{
		entities.clear();
		
		for(Junk junk : activeJunks)
		{
			entities.addAll(junk.getEntities());
			entities.addAll(junk.getItemBags());
		}
		entities.addAll(enemies);
		entities.add(player);
		
		Collections.sort(entities, sort);
	}
	
	private void prepare()
	{
		activeJunks.clear();
		enemies.clear();
	}
	
	public void addItem(ItemBag item)
	{
		Junk junk = getJunk(item.getCenterX(), item.getCenterY());
		junk.addItem(item);
	}
	
	private Junk getJunk(double x, double y)
	{
		int xTile = (int) (x / Game.getSize());
		int yTile = (int) (y / Game.getSize());
		
		int xJunk = (int) (xTile / Game.getJunkSize());
		int yJunk = (int) (yTile / Game.getJunkSize());
		
		return getJunk(xJunk, yJunk);
	}
	
	private Junk getJunk(int xPos, int yPos)
	{
		if(xPos < 0 || yPos < 0 || xPos >= junkMap.length || yPos >= junkMap[0].length) return null;
		
		return junkMap[xPos][yPos];
	}
	
	public void calculateAktiveJunks()
	{
		double x = player.getCenterX();
		double y = player.getBottom();
		
		double xMin = Math.max(Math.min(x - Game.getWidth()  / 2, width  - Game.getWidth()), 0);
		double yMin = Math.max(Math.min(y - Game.getHeight() / 2, height - Game.getHeight()), 0);
		double xMax = Math.max(Math.min(x + Game.getWidth()  / 2, width),  Game.getWidth());
		double yMax = Math.max(Math.min(y + Game.getHeight() / 2, height), Game.getHeight());
		
		Junk left 	= getJunk(xMin, y);
		Junk right 	= getJunk(xMax, y);
		Junk top 	= getJunk(x, yMin);
		Junk bottom = getJunk(x, yMax);
		
		int xPosMin = left   == null ? 0 		: left.getXPos();
		int xPosMax = right  == null ? xJunkMax : right.getXPos();
		int yPosMin = top 	 == null ? 0 		: top.getYPos();
		int yPosMax = bottom == null ? yJunkMax : bottom.getYPos();
		
		for(int yPos = yPosMin; yPos <= yPosMax; yPos++)
		{
			for(int xPos = xPosMin; xPos <= xPosMax; xPos++)
			{				
				addAktiveJunk(xPos, yPos);
			}
		}
	}
	
	private void addAktiveJunk(int xPos, int yPos)
	{
		Junk junk = getJunk(xPos, yPos);
		
		if(junk != null && !activeJunks.contains(junk))
		{
			activeJunks.add(junk);
		}
	}

	public List<Junk> getJunks() 
	{
		return junks;
	}

	public Junk[][] getJunkMap() 
	{
		return junkMap;
	}

	public List<Junk> getActiveJunks() 
	{
		return activeJunks;
	}
	
	public List<Enemy> getEnemies()
	{
		return enemies;
	}

	public int getWidth() 
	{
		return width;
	}

	public int getHeight() 
	{
		return height;
	}
	
	private class Sort implements Comparator<EntityImage>
	{
		public int compare(EntityImage entity1, EntityImage entity2) 
		{
			if(entity1 == null || entity2 == null) return 0;
			
			if(entity1.getBottom() < entity2.getBottom()) return -1;
			if(entity1.getBottom() > entity2.getBottom()) return +1;
			return 0;
		}
	}
}
