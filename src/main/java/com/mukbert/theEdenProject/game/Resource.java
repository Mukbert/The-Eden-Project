package com.mukbert.theEdenProject.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import com.mukbert.framework.extension.Item;
import com.mukbert.theEdenProject.data.Data;
import com.mukbert.theEdenProject.game.Game;

public class Resource 
{	
	private final String RES_DATA_FOLDER_PATH = "src/main/data";
	
	private Hashtable<Integer, Data> dataItem;
	private Hashtable<Item, Data> items;
	
	public Resource() 
	{
		dataItem = new Hashtable<Integer, Data>();
		items = new Hashtable<Item, Data>();
		
		File folder = new File(RES_DATA_FOLDER_PATH);
		
		for(File file : folder.listFiles())
		{
			int ID = Integer.valueOf(file.getName().substring(0, file.getName().indexOf("_")));
			
			Data data = new Data(ID);
			
			File animation = new File(file.getPath() + "/animation.png");
			File icon = new File(file.getPath() + "/icon.png");
			File info = new File(file.getPath() + "/info.txt");
			
			if(icon.exists())
			{
				BufferedImage image = Game.getImage(icon);
				
				data.setIcon(image);
			}
			
			if(animation.exists())
			{
				BufferedImage image = Game.getImage(animation);
				
				data.createAnimationData().setAnimationImage(image);
			}
			
			if(info.exists())
			{
				String[] commands = getFileCommands(info);
				
				String s;
				
				for(String command : commands)
				{					
					try 
					{
						if(command.startsWith(s = "#TYPE"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.setType(command);
						}
						else if(command.startsWith(s = "#NAME"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.setName(command);
						}
						else if(command.startsWith(s = "#SCALE"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.setScale(Double.valueOf(command));
						}
						else if(command.startsWith(s = "#RCOLOR"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createMapData().setR(Integer.valueOf(command));
						}
						else if(command.startsWith(s = "#GCOLOR"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createMapData().setG(Integer.valueOf(command));
						}
						else if(command.startsWith(s = "#BCOLOR"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createMapData().setB(Integer.valueOf(command));
						}
						else if(command.startsWith(s = "#ANIMATIONSPEED"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createAnimationData().setAnimationTime(Double.valueOf(command));
						}
						else if(command.startsWith(s = "#ANIMATIONORDER"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createAnimationData().setAnimationOrder(command);
						}
						else if(command.startsWith(s = "#PRICE"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createItemData().setPrice(Integer.valueOf(command));
						}
						else if(command.startsWith(s = "#STACK"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createItemData().setStackMax(Integer.valueOf(command));
						}
						else if(command.startsWith(s = "#EQUIPMENTID"))
						{
							command = command.replaceAll(s, "").trim();
							
//							if(command.equals("helmet"))
//							{
//								data.createItemData().setEquipmentID(Equipment.ID_HELMET);
//							}
//							else if(command.equals("cheast"))
//							{
//								data.createItemData().setEquipmentID(Equipment.ID_CHEAST);
//							}
//							else if(command.equals("gem"))
//							{
//								data.createItemData().setEquipmentID(Equipment.ID_GEM);
//							}
//							else if(command.equals("gloves"))
//							{
//								data.createItemData().setEquipmentID(Equipment.ID_GLOVES);
//							}
//							else if(command.equals("legs"))
//							{
//								data.createItemData().setEquipmentID(Equipment.ID_LEGS);
//							}
//							else if(command.equals("necklace"))
//							{
//								data.createItemData().setEquipmentID(Equipment.ID_NECKLACE);
//							}
//							else if(command.equals("cheast"))
//							{
//								data.createItemData().setEquipmentID(Equipment.ID_CHEAST);
//							}
//							else if(command.equals("ring"))
//							{
//								data.createItemData().setEquipmentID(Equipment.ID_RING_BOTH);
//							}
//							else if(command.equals("shoes"))
//							{
//								data.createItemData().setEquipmentID(Equipment.ID_SHOES);
//							}
//							else if(command.equals("shirt"))
//							{
//								data.createItemData().setEquipmentID(Equipment.ID_SHIRT);
//							}
//							else if(command.equals("weapon_main"))
//							{							
//								data.createItemData().setEquipmentID(Equipment.ID_WEAPON_1);
//							}
//							else if(command.equals("weapon_second"))
//							{
//								data.createItemData().setEquipmentID(Equipment.ID_WEAPON_2);
//							}
//							else if(command.equals("weapon_both"))
//							{
//								data.createItemData().setEquipmentID(Equipment.ID_WEAPON_BOTH);
//							}
						}
						else if(command.startsWith(s = "#DAMAGE"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createItemData().setDamage(Integer.valueOf(command));
						}
						else if(command.startsWith(s = "#RANGE"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createEntityData().setRange(Double.valueOf(command));
						}
						else if(command.startsWith(s = "#LIFE"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createEntityData().setLife(Integer.valueOf(command));
						}
						else if(command.startsWith(s = "#MOVESPEED"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createEntityData().setSpeed(Double.valueOf(command));
						}
						else if(command.startsWith(s = "#KILLXP"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createEntityData().setXP(Integer.valueOf(command));
						}
						else if(command.startsWith(s = "#DROP"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createEntityData().addItemID(Integer.valueOf(command));
						}
						else if(command.startsWith(s = "#SPAWNERID"))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createSpawnerData().setSpawnerID(Integer.valueOf(command));
						}
						else if(command.startsWith(s = "#TERRAINRANDOM "))
						{
							command = command.replaceAll(s, "").trim();
							
							data.createTerrainData().setExtraLayer(Double.valueOf(command));
						}
					} 
					catch (Exception e) 
					{
						System.err.println(data.getName());
						e.printStackTrace();
						System.exit(1);
					}
				}
			}
			
			dataItem.put(ID, data);
			
			if(data.getItemData() != null)
			{
				items.put(new Item(data.getIcon()), data);
			}
		}
	}
	
	private String[] getFileCommands(File info)
	{
		List<String> commands = new ArrayList<String>();
		
		try 
		{
			Scanner scanner = new Scanner(info);
			
			while(scanner.hasNextLine())
			{
				String command = scanner.nextLine();
				
				if(command != null && !command.trim().equals(""))
				{
					commands.add(command);
				}
			}
			
			scanner.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return commands.toArray(new String[commands.size()]);
	}
	
	public Hashtable<Integer, Data> getData()
	{
		return dataItem;
	}
	
	public Data getData(int ID)
	{
		return dataItem.get(Integer.valueOf(ID));
	}
	
	public Data getData(Item item)
	{
		return items.get(item);
	}
}
