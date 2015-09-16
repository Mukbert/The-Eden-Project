package com.mukbert.theEdenProject.data;

import java.awt.image.BufferedImage;
import java.io.File;

import com.mukbert.theEdenProject.game.Game;

public enum ImageData
{
	MAP_ENTITY("map_entity"),
	MAP_TERRAIN("map_terrain"),
	
	TEXTURES_WORLD("textures_world", 32),
	TEXTURES_ITEMS("textures_items", 32),
	TEXTURES_ICONS("textures_icons", 32),
	
	//

	ICON_EMERALD_1(TEXTURES_ICONS, 0, 0, 1, 1),
	ICON_EMERALD_2(TEXTURES_ICONS, 1, 0, 1, 1),
	ICON_EMERALD_3(TEXTURES_ICONS, 2, 0, 1, 1),
	ICON_EMERALD_4(TEXTURES_ICONS, 3, 0, 1, 1),
	ICON_COIN_BRONZE(TEXTURES_ICONS, 0, 1, 1, 1),
	ICON_COIN_SILBER(TEXTURES_ICONS, 1, 1, 1, 1),
	ICON_COIN_GOLD(TEXTURES_ICONS, 2, 1, 1, 1),
	ICON_COINS(TEXTURES_ICONS, 3, 1, 1, 1),
	ICON_BUTTON_OFF(TEXTURES_ICONS, 0, 2, 1, 1),
	ICON_BUTTON_ON(TEXTURES_ICONS, 1, 2, 1, 1),
	ICON_PET_1(TEXTURES_ICONS, 2, 2, 1, 1),
	ICON_PET_2(TEXTURES_ICONS, 3, 2, 1, 1),
	ICON_BAG(TEXTURES_ICONS, 0, 3, 1, 1),
	ICON_SWORDS(TEXTURES_ICONS, 1, 3, 1, 1),
	ICON_FOOD(TEXTURES_ICONS, 2, 3, 1, 1),
	ICON_SETTINGS(TEXTURES_ICONS, 3, 3, 1, 1),
	ICON_CONSOLE(TEXTURES_ICONS, 0, 4, 1, 1),
	ICON_KOMPASS(TEXTURES_ICONS, 1, 4, 1, 1),
	ICON_FOTO(TEXTURES_ICONS, 2, 4, 1, 1),
	ICON_CROSS(TEXTURES_ICONS, 3, 4, 1, 1),
	ICON_PLAYER(TEXTURES_ICONS, 0, 5, 1, 1),
	ICON_RETURN(TEXTURES_ICONS, 1, 5, 1, 1),
	ICON_SPOT(TEXTURES_ICONS, 2, 5, 1, 1),
	ICON_SLOT_1(TEXTURES_ICONS, 0, 6, 1, 1),
	ICON_SLOT_2(TEXTURES_ICONS, 1, 6, 1, 1),
	ICON_SLOT_3(TEXTURES_ICONS, 2, 6, 1, 1),
	;

	private BufferedImage image;
	private int tileWidth;
	private int tileHeight;
	
	private ImageData(String path, int tileWidth, int tileHeight)
	{
		image = Game.getImage(new File("src/main/"+ path + ".png"));
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
	
	private ImageData(String path, int tileSize)
	{
		this(path, tileSize, tileSize);
	}
	
	private ImageData(String name)
	{
		this(name, 0, 0);
	}
	
	private ImageData(ImageData res, int xPos, int yPos, int w, int h)
	{
		int x = xPos * res.tileWidth;
		int y = yPos * res.tileHeight;
		int width  = w * res.tileWidth;
		int height = h * res.tileHeight;
		
		image = res.getImage().getSubimage(x, y, width, height);
	}	
	
	public BufferedImage getImage()
	{
		return image;
	}
}
