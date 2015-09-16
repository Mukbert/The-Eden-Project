package com.mukbert.theEdenProject.junk;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mukbert.theEdenProject.game.Game;
import com.mukbert.framework.EntityImage;
import com.mukbert.theEdenProject.data.Data;
import com.mukbert.theEdenProject.data.TerrainData;
import com.mukbert.theEdenProject.game.ItemBag;
import com.mukbert.theEdenProject.game.Spawner;

public class Junk extends EntityImage
{	
	private int xPos;
	private int yPos;
	
	private List<EntityImage> entities;
	private List<Spawner> spawner;
	private List<ItemBag> bags;
	private HashMap<Color, Data> terrainData;
	private HashMap<Color, Data> terrainEntityData;
	
	private JunkManager junkManager;
	private Game game;
	
	public Junk(int xPos, int yPos, HashMap<Color, Data> terrainData, HashMap<Color, Data> terrainEntityData)
	{
		super(RECTANGLE);
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.terrainData = terrainData;
		this.terrainEntityData = terrainEntityData;
		
		
		setWidth(Game.getJunkSize()  * Game.getSize());
		setHeight(Game.getJunkSize() * Game.getSize());
		setX(xPos * getWidth());
		setY(yPos * getHeight());
		
		bags    = new ArrayList<ItemBag>();
		spawner  = new ArrayList<Spawner>();
		
	}
	
	public void init(BufferedImage terrainImage, BufferedImage entityImage, JunkManager junkManager, Game game)
	{
		this.junkManager = junkManager;
		this.game = game;

		int textureX = xPos * Game.getJunkSize();
		int textureY = yPos * Game.getJunkSize();
		
		createTerrainList(terrainImage, textureX, textureY);
		createEntityList(entityImage, textureX, textureY);
	}
	
	public void update()
	{
		for(Spawner s : spawner)
		{
			s.update();
		}
		
		for(int i = 0; i < bags.size(); i++)
		{
			ItemBag bag = bags.get(i);
			
			if(bag.isAlive()) bag.update();
			else bags.remove(bag);
		}
	}
	
	private void createTerrainList(BufferedImage terrainImage, int textureX, int textureY)
	{
		BufferedImage image = new BufferedImage((int)getWidth(), (int)getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = image.createGraphics();
		
		for(int yPos = 0; yPos < Game.getJunkSize(); yPos++)
		{
			for(int xPos = 0; xPos < Game.getJunkSize(); xPos++)
			{
				int RGB = terrainImage.getRGB(xPos + textureX, yPos + textureY);
				
				double terrainX = xPos * Game.getSize();
				double terrainY = yPos * Game.getSize();
				
				Color c = new Color(RGB);
				Data data = terrainData.get(c);
				

				g.translate(terrainX, terrainY);
				g.drawImage(data.getIcon(), 0, 0, Game.getSize(), Game.getSize(), null);
				g.translate(-terrainX, -terrainY);
				
				TerrainData t = data.getTerrainData();
				
				if(t != null && t.getExtraLayer() > 0 && Math.random() <= t.getExtraLayer())
				{

					g.translate(terrainX, terrainY);
					g.drawImage(t.getExtraLayerImage(), 0, 0, Game.getSize(), Game.getSize(), null);
					g.translate(-terrainX, -terrainY);
				}
			}
		}
		
		setBufferedImage(image);
	}
	
	private void createEntityList(BufferedImage entityImage, int textureX, int textureY)
	{
		entities = new ArrayList<EntityImage>();
		
		for(int yPos = 0; yPos < Game.getJunkSize(); yPos++)
		{
			for(int xPos = 0; xPos < Game.getJunkSize(); xPos++)
			{
				int RGB = entityImage.getRGB(xPos + textureX, yPos + textureY);
				
				Color c = new Color(RGB);
				
				double xEntity = getX() + xPos * Game.getSize();
				double yEntity = getY() + yPos * Game.getSize();
				
				Data data = terrainEntityData.get(c);
				
				if(data == null) continue;
				
				if(data.getSpawnerData() != null)
				{
					Spawner entity = new Spawner(Game.getResource().getData(data.getSpawnerData().getSpawnerID()), xEntity, yEntity);
					
					entity.init(junkManager, game);
					spawner.add(entity);
				}
				else
				{
					EntityImage entity = new EntityImage(RECTANGLE);
					entity.setBufferedImage(data.getIcon());
					entity.setWidth(Game.getSize() * data.getScale());
					entity.setHeight(Game.getSize() * data.getScale());
					entity.setX(xEntity + Game.getSize() / 2 - entity.getWidth()  / 2);
					entity.setY(yEntity + Game.getSize() - entity.getHeight());
					
					entities.add(entity);
				}
			}
		}
	}
	
	public void addItem(ItemBag item)
	{
		bags.add(item);
	}
	
	public List<ItemBag> getItemBags()
	{
		return bags;
	}
	
	public List<Spawner> getSpawner()
	{
		return spawner;
	}
	
	public List<EntityImage> getEntities() 
	{
		return entities;
	}
	
	public int getXPos()
	{
		return xPos;
	}
	
	public int getYPos()
	{
		return yPos;
	}
}
