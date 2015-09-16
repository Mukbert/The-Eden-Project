package com.mukbert.theEdenProject.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.mukbert.framework.EntityColor;
import com.mukbert.framework.Text;

public class Info extends EntityColor
{
	private int lvl;
	private int life = 10;
	private int lifeMax = 20;
	private int XP;
	private int XP_UP = 20;
	
	private Text lvlText;
	private Text expText;
	private Text lifeText;
	private Text coinText;
	
	private Inventory inventory;
	
	public Info() 
	{
		super(RECTANGLE);
		setLocation(Game.getDiff(), Game.getDiff());
		setSize(Game.getSize() * 1.5, Game.getSize() * 1.5);

		lvl = 1;

		lifeText = new Text();
		lifeText.setX(Game.getDiff() + getX() + getWidth());
		lifeText.setY(getY());
		lifeText.setWidth(getWidth() * 2);
		lifeText.setHeight(getHeight() / 3 - Game.getDiff() / 2);
		
		expText = new Text();
		expText.setX(lifeText.getX());
		expText.setY(lifeText.getY() + lifeText.getHeight() + Game.getDiff() / 2);
		expText.setWidth(lifeText.getWidth());
		expText.setHeight(lifeText.getHeight());
		
		lvlText = new Text();
		lvlText.setWidth(lifeText.getWidth());
		lvlText.setHeight(getHeight() / 2);
		lvlText.setX(lifeText.getX());
		lvlText.setY(getY() + getHeight() - lvlText.getHeight());
		lvlText.setOrientationX(Text.LEFT);
		lvlText.setOrientationY(Text.BOTTOM);
		lvlText.setColor(Color.pink);		
		lvlText.setFont(new Font("Dialog", Font.BOLD, 16));
		
		coinText = new Text();
		coinText.setBounds(lvlText);
		coinText.setColor(Color.yellow);
		coinText.setOrientationX(Text.RIGHT);
		coinText.setOrientationY(Text.BOTTOM);
		
	}
	
	public void init(Inventory inventory)
	{
		this.inventory = inventory;
		
		setTextLvl();
		setTextCoin();
		setTextLife();
		setTextXP();
	}
	
	public void draw(Graphics2D g)
	{		
		translate(g);
		g.setColor(new Color(0, 0, 0, 100));
		g.fillRect(0, 0, (int)getWidth(), (int)getHeight());
		g.setColor(Color.black);
		g.drawRect(0, 0, (int)getWidth(), (int)getHeight());
		g.drawImage(Game.getResource().getData(2000).getIcon(), 0, 0, (int)getWidth(), (int)getHeight(), null);
		
		translateBack(g);
		
		int w;
		int h;
		
		double limit;
		
		
		w = (int) lifeText.getWidth();
		h = (int) lifeText.getHeight();
		limit = (double)life / (double)lifeMax;
		
		lifeText.translate(g);
		g.setColor(new Color(200, 20, 80, 200).brighter());
		g.fillRoundRect(0, 0, (int)(w * limit), h, h, h);
		g.setColor(Color.black);
		g.drawRoundRect(0, 0, w - 1, h - 1, h, h);
		lifeText.translateBack(g);
		lifeText.draw(g);
		
		w = (int) expText.getWidth();
		h = (int) expText.getHeight();
		limit = (double)XP / (double)XP_UP;
		
		expText.translate(g);
		g.setColor(new Color(100, 20, 150, 200));
		g.fillRoundRect(0, 0, (int)(w * limit), h, h, h);
		g.setColor(Color.black);
		g.drawRoundRect(0, 0, w - 1, h - 1, h, h);
		expText.translateBack(g);
		expText.draw(g);
		
		lvlText.draw(g);
		coinText.draw(g);
		
//		y = getHeight();
//		
//		RenderUtil.setHighlightColor(Color.pink);
//		RenderUtil.drawString(g, "Lvl. " + lvl, x, y, true, RenderUtil.STRING_LEFT_BOTTOM);
//		RenderUtil.setHighlightColor(Color.white);
//
//		x = x + w / 2;
//		
//		RenderUtil.drawString(g, Framework.getFPS() + "", x, y, true, RenderUtil.STRING_CENTER_BOTTOM);
//		
//		double coinSize = g.getFont().getSize2D();
//		
//		x = x + w / 2 - coinSize;
//		
//		RenderUtil.drawImage(g, ResourceLoader.getData(500).getIcon(), x, y - coinSize + 2, coinSize, coinSize, false);
//		
//		RenderUtil.setHighlightColor(Color.yellow);
////		RenderUtil.drawString(g, World.getInstance().getInventory().getCoins()+"", x - 4, y, true, RenderUtil.STRING_RIGHT_BOTTOM);
//		RenderUtil.setHighlightColor(Color.white);
	}
	
	private void setTextLife()
	{
		lifeText.setText(life + " / " + lifeMax);
	}
	
	private void setTextXP()
	{
		expText.setText(XP + " / " + XP_UP);
	}
	
	private void setTextLvl()
	{
		lvlText.setText("LVL " + lvl);
	}
	
	public void setTextCoin()
	{
		coinText.setText(inventory.getCoins());
	}
	
	public void addXP(int XP) 
	{
		this.XP += XP;
		
		if(this.XP >= this.XP_UP)
		{
			this.lvl++;
			this.XP = this.XP - this.XP_UP;
			setTextLvl();
		}
		setTextXP();
	}
	
}
