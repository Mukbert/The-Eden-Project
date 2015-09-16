package com.mukbert.theEdenProject.game;

import com.mukbert.framework.EntityImage;
import com.mukbert.framework.extension.Slot;
import com.mukbert.theEdenProject.data.Data;
import com.mukbert.theEdenProject.data.ImageData;
import com.mukbert.framework.extension.Inventory;

import java.awt.Graphics2D;

public class ItemBag extends EntityImage
{
	private Inventory inventory;
	
	private Slot slot;
	
	private Game game;
	
	public ItemBag(Enemy enemy, Game game) 
	{
		super(RECTANGLE);
		this.game = game;
		
		setWidth (Game.getSize() * 0.5);
		setHeight(Game.getSize() * 0.5);
		setX(enemy.getCenterX() - getWidth()  / 2);
		setY(enemy.getCenterY() - getHeight() / 2);
		setBufferedImage(ImageData.ICON_BAG.getImage());
		
		inventory = new Inventory(3, 3, new String[]{enemy.getEnemyData().getName()});
		inventory.setPosition(Inventory.LEFT);
		inventory.setPosition(Inventory.BOTTOM);
		inventory.setSize(Game.getSize());
		inventory.setCanMoveItem(true);
		inventory.setCanMoveInventory(true);
		inventory.updateBounds();
		for(Data item : enemy.getEnemyData().getEntityData().getItems(Game.getResource()))
		{
			inventory.addItem(item.getItemData().getItem());
		}
	}
	
	public void update()
	{
		inventory.update();
		
		for(Slot slot : inventory.getSlots())
		{
			if(slot.contains(Game.getMouse().getPoint()))
			{
				if(Game.getMouse().isLeftDown())
				{
					this.slot = slot;
				}
				if(Game.getMouse().isDraggedLeft())
				{
					this.slot = null;
				}
				if(!Game.getMouse().isLeftDown() && this.slot != null && slot.getItem() != null)
				{
					boolean canAdd = game.getInventory().addItem(slot.getItem());
					
					if(canAdd) slot.removeItem();
					this.slot = null;
				}
				
				break;
			}
		}
		
		if(inventory.isEmpty())
		{
			setAlive(false);
			inventory.setAlive(false);
			game.getInventory().setItemBag(null);
		}
	}
	
	public void drawInventory(Graphics2D g) 
	{
		inventory.draw(g);
	}
	
	public Inventory getInventory()
	{
		return inventory;
	}
}