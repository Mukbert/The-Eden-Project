package com.mukbert.theEdenProject.data;

import java.awt.image.BufferedImage;

import com.mukbert.framework.Animation;

public class TerrainData
{
	private double extraLayer;
	
	private Data data;
	
	public TerrainData(Data data)
	{
		this.data = data;
	}
	
	public double getExtraLayer()
	{
		return extraLayer;
	}
	
	public void setExtraLayer(double extraLayer)
	{
		this.extraLayer = extraLayer;
	}
	
	public BufferedImage getExtraLayerImage()
	{
		Animation animation = data.getAnimationData() == null ? null : data.getAnimationData().getAnimation();
				
		if(animation.isEmpty()) return null;
		
		animation.setRandomID();
		
		return animation.getImage();
	}
}
