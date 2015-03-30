package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import uk.co.jaimon.SimpleImageInfo;

import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlay;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlayLocation;
import com.flashvisions.server.rtmp.transcoder.pojo.base.Mutable;

public class Overlay extends Mutable implements IOverlay, Serializable {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 3055247918283162290L;

public static class Location implements IOverlayLocation, Serializable{
		
		/**
	 * 
	 */
	private static final long serialVersionUID = -8679229337020703748L;

		public static enum ALIGNMENT {
		    TOPLEFT, TOPRIGHT, BOTTOMLEFT, BOTTOMRIGHT, CENTERLEFT, CENTERRIGHT, CENTERTOP,  CENTERBOTTOM, CENTERMIDDLE 
		}
		
		private int x;
		private int y;
		private int width;
		private int height;
		private ALIGNMENT align;
		
		
		public int getX() {
			return x;
		}
		
		public void setX(int x) {
			this.x = x;
		}
		
		public int getY() {
			return y;
		}
		
		public void setY(int y) {
			this.y = y;
		}
		
		public int getWidth() {
			return width;
		}
		
		public void setWidth(int width) {
			this.width = width;
		}
		
		public int getHeight() {
			return height;
		}
		
		public void setHeight(int height) {
			this.height = height;
		}
		
		public ALIGNMENT getAlign() {
			return align;
		}
		
		public void setAlign(ALIGNMENT align) {
			this.align = align;
		}
		
		public void setAlign(String alignment) 
		{
			try
			{
				switch(ALIGNMENT.valueOf(alignment.toUpperCase()))
				{
					case TOPLEFT:
						setAlign(ALIGNMENT.TOPLEFT);
					break;
					
					case TOPRIGHT:
						setAlign(ALIGNMENT.TOPRIGHT);
					break;
						
					case BOTTOMLEFT:
						setAlign(ALIGNMENT.BOTTOMLEFT);
					break;
						
					case BOTTOMRIGHT:
						setAlign(ALIGNMENT.BOTTOMRIGHT);
					break;
						
					case CENTERLEFT:
						setAlign(ALIGNMENT.CENTERLEFT);
					break;
						
					case CENTERRIGHT:
						setAlign(ALIGNMENT.CENTERRIGHT);
					break;
						
					case CENTERTOP:
						setAlign(ALIGNMENT.CENTERTOP);
					break;
						
					case CENTERBOTTOM:
						setAlign(ALIGNMENT.CENTERBOTTOM);
					break;
					
					case CENTERMIDDLE:
						setAlign(ALIGNMENT.CENTERMIDDLE);
					break;
				}
			}
			catch(Exception e)
			{
				throw new IllegalArgumentException("Invalid alignment constant " + alignment);
			}
		}
	}

	private String label;
	private int zindex;
	private String overlayImagePath;
	private int opacity;
	private IOverlayLocation location;
	
	private int imageWidth;
	private int imageHeight;
	
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getZindex() {
		return zindex;
	}
	
	public void setZindex(int zindex) {
		this.zindex = zindex;
	}
	
	public String getOverlayImagePath() {
		return overlayImagePath;
	}
	
	public void setOverlayImagePath(String overlayImagePath) throws IOException {
		this.overlayImagePath = overlayImagePath;
		this.evaluateImageDimensions();
	}
	
	public int getOpacity() {
		return opacity;
	}
	
	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}
	
	public IOverlayLocation getLocation() {
		return location;
	}
	
	public void setLocation(IOverlayLocation location) {
		this.location = location;
	}
	
	protected void evaluateImageDimensions() throws IOException
	{
		SimpleImageInfo imageInfo = null;
		
		try {
			imageInfo = new SimpleImageInfo(new File(getOverlayImagePath()));
			this.setImageWidth(imageInfo.getWidth());
			this.setImageHeight(imageInfo.getHeight());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.setImageWidth(0);
			this.setImageHeight(0);
			throw(e);
		}finally{
			imageInfo = null;
		}
	}

	public int getOverlayImageWidth() {
		return imageWidth;
	}

	private void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getOverlayImageHeight() {
		return imageHeight;
	}

	private void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
}
