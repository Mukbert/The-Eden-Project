package com.mukbert.theEdenProject.game;

import java.awt.Graphics2D;

import com.mukbert.framework.Framework;
import com.mukbert.theEdenProject.junk.JunkManager;

public class Game extends Framework
{
	private static final int SIZE = 50;
	private static final int JUNK_SIZE = 8;
	private static final int DIFF = 10;
	
	private static Resource resource;
	
	private Player player;
	private Camera camera;
	private Inventory inventory;
	private JunkManager junkManager;
	private Info info;
	
	public Game() 
	{
		super("The Eden Projekt", 800, 600);
				
		resource = new Resource();
		
		player = new Player(1, 1);
		junkManager = new JunkManager();
		camera = new Camera();
		info = new Info();
		inventory = new Inventory();
	}

	@Override
	public void init(int width, int height) 
	{
		player.init(junkManager, info);
		junkManager.init(resource, player, this);
		camera.init(player, junkManager);
		info.init(inventory);
		inventory.init(info, player, junkManager);
	}

	@Override
	public void resize(int width, int height) 
	{
		
	}

	@Override
	public void update(double t) 
	{
		player.update();
		camera.update();
		junkManager.update();
		inventory.update();
	}

	@Override
	public void draw(Graphics2D g) 
	{
		camera.translate(g);
		junkManager.draw(g);
		camera.translateBack(g);
		info.draw(g);
		inventory.draw(g);
	}
	
	public Inventory getInventory()
	{
		return inventory;
	}
	
	public static Resource getResource()
	{
		return resource;
	}
	
	public static void main(String[] args) 
	{
		Game game = new Game();
		game.start();
	}
	
	public static int getJunkSize()
	{
		return JUNK_SIZE;
	}
	
	public static int getDiff()
	{
		return DIFF;
	}
	
	public static int getSize()
	{
		return SIZE;
	}
}
