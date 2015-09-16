package com.mukbert.theEdenProject.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.mukbert.framework.extension.MathUtil;
import com.mukbert.theEdenProject.junk.Junk;
import com.mukbert.theEdenProject.junk.JunkManager;

public class Inventory extends com.mukbert.framework.extension.Inventory
{
	private int coins;
	private boolean canShowItem; 
	
	private Info info;
	private ItemBag itemBag;
	private Player player;
	private JunkManager junkManager;
	
	public Inventory() 
	{
		super(5, 5, new String[]{"Waffen", "Zeug"});
		
		setSize(50);
		setCanMoveInventory(false);
		setCanMoveItem(true);
		updateBounds();
	}
	
	public void init(Info info, Player player, JunkManager junkManager)
	{
		this.info = info;
		this.player = player;
		this.junkManager = junkManager;
		setCoins(15);
	}
	
	public void update()
	{
		super.update();
		
		if(itemBag != null)
		{
			itemBag.update();
		}
		
		openItem();
	}
	
	@Override
	public void draw(Graphics2D g) 
	{
		if(itemBag != null)
		{
			itemBag.drawInventory(g);
		}
		
		super.draw(g);
	}
	
	private void openItem()
	{
		if(Game.getKeyboard().isKeyDown(KeyEvent.VK_E))
		{
			if(canShowItem)
			{
				canShowItem = false;
				
				if(itemBag == null)
				{
					double d = -1;
					for(Junk junk : junkManager.getActiveJunks())
					{
						for(ItemBag item : junk.getItemBags())
						{
							double distance = MathUtil.getDistance(item, player);
							
							if((d < 0 || distance < d) && distance < Game.getSize() * 3)
							{
								d = distance;
								itemBag = item;
							}
						}
					}
					if(itemBag != null)
					{
						itemBag.getInventory().switchVisibility();
					}
					return;
				}
				else
				{
					itemBag.getInventory().setVisible(false);
					itemBag = null;
				}
			}
		}
		else canShowItem = true;
	}
	
	public void setCoins(int coins)
	{
		this.coins = coins;
		info.setTextCoin();
	}
	
	public void addCoins(int coinsDiff)
	{
		setCoins(getCoins() + coinsDiff);
	}
	
	public void removeCoins(int coinsDiff)
	{
		setCoins(getCoins() - coinsDiff);
	}
	
	public int getCoins()
	{
		return coins;
	}

	public void setItemBag(ItemBag itemBag) 
	{
		this.itemBag = itemBag;
	}
}
