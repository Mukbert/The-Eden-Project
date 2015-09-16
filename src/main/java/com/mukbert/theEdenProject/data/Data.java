package com.mukbert.theEdenProject.data;

import java.awt.image.BufferedImage;

public class Data
{
	private int ID;
	private double scale;
	private String name;
	private String type;
	private BufferedImage image;
	
	private MapData mapData;
	private AnimationData animationData;
	private ItemData itemData;
	private EntityData entityData;
	private SpawnerData spawnerData;
	private TerrainData terrainData;
	
	public Data(int ID) 
	{
		setID(ID);
		setScale(1);
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setType(String Type)
	{
		this.type = Type;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setID(int ID)
	{
		this.ID = ID;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public void setIcon(BufferedImage icon)
	{
		this.image = icon;
	}
	
	public BufferedImage getIcon()
	{
		return image;
	}
	
	public void setScale(double scale)
	{
		this.scale = scale;
	}
	
	public double getScale()
	{
		return scale;
	}
	
	public MapData createMapData()
	{
		if(mapData == null)
		{
			mapData = new MapData();
		}
		return mapData;
	}
	
	public MapData getMapData()
	{
		return mapData;
	}
	
	public AnimationData createAnimationData()
	{
		if(animationData == null)
		{
			animationData = new AnimationData();
		}
		return animationData;
	}
	
	public AnimationData getAnimationData()
	{
		return animationData;
	}
	
	public ItemData createItemData()
	{
		if(itemData == null)
		{
			itemData = new ItemData();
		}
		
		if(itemData.getItem() == null)
		{
			itemData.setItem(image);
		}
		
		return itemData;
	}
	
	public ItemData getItemData()
	{
		return itemData;
	}
	
	public EntityData createEntityData()
	{
		if(entityData == null)
		{
			entityData = new EntityData();
		}
		return entityData;
	}
	
	public EntityData getEntityData()
	{
		return entityData;
	}
	
	public SpawnerData createSpawnerData()
	{
		if(spawnerData == null)
		{
			spawnerData = new SpawnerData();
		}
		return spawnerData;
	}
	
	public SpawnerData getSpawnerData()
	{
		return spawnerData;
	}
	
	public TerrainData createTerrainData()
	{
		if(terrainData == null)
		{
			terrainData = new TerrainData(this);
		}
		return terrainData;
	}
	
	public TerrainData getTerrainData()
	{
		return terrainData;
	}
}
