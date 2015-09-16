package com.mukbert.theEdenProject.data;

import java.awt.image.BufferedImage;

import com.mukbert.framework.Animation;

public class AnimationData
{
	private Animation animation;
	
	public AnimationData()
	{
		setAnimation(new Animation());
	}
	
	public void setAnimation(Animation animation)
	{
		this.animation = animation;
	}
	
	public void setAnimationImage(BufferedImage image) 
	{
		animation.setAnimationImage(image);
	}
	
	public void setAnimationTime(double time) 
	{
		animation.setTime(time);
	}

	public void setAnimationOrder(String order) 
	{
		animation.setAnimationOrder(order);
	}
	
	public Animation getAnimation()
	{
		return animation;
	}
}
