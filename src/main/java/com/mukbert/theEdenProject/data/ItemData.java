package com.mukbert.theEdenProject.data;

import java.awt.image.BufferedImage;

import com.mukbert.framework.extension.Item;

public class ItemData
{
	private int stackMax;
	private int price;
	private int damage;
	private int eqipmentID;
	private Item item;
	
	public ItemData() 
	{
		setStackMax(1);
		setPrice(0);
		setEquipmentID(-1);
	}
	
	public void setItem(BufferedImage image)
	{
		item = new Item(image);
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public void setStackMax(int stackMax)
	{
		this.stackMax = stackMax;
	}
	
	public int getStackMax()
	{
		return stackMax;
	}
	
	public void setPrice(int price)
	{
		this.price = price;
	}
	
	public int getPrice()
	{
		return price;
	}

	public int getSellCoins() 
	{
		return price / 3 * 2;
	}
	
	public void setEquipmentID(int eqipmentID)
	{
		this.eqipmentID = eqipmentID;
	}
	
	public int getEquipmentID()
	{
		return eqipmentID;
	}
	
	public boolean isEquipment()
	{
		return eqipmentID >= 0;
	}
	
	public boolean isWeapon()
	{
		return false;
		
		//TODO
//		return eqipmentID == Equipment.ID_WEAPON_1 || eqipmentID == Equipment.ID_WEAPON_2 || eqipmentID == Equipment.ID_WEAPON_BOTH;
	}
	
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	
	public int getDamage()
	{
		return damage;
	}
}