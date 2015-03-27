package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.File;
import java.io.IOException;

import uk.co.jaimon.SimpleImageInfo;

import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlay;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlayLocation;
import com.flashvisions.server.rtmp.transcoder.pojo.base.Mutable;

public class Overlay extends Mutable implements IOverlay {
	
public static class Location implements IOverlayLocation{
		
		public static enum ALIGNMENT {
		    TOPLEFT, TOPRIGHT, BOTTOMLEFT, BOTTOMRIGHT, CENTERLEFT, CENTERRIGHT, CENTERMIDDLE 
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
	
	public void setOverlayImagePath(String overlayImagePath) {
		this.overlayImagePath = overlayImagePath;
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
	
	protected void evaluateImageDimensions()
	{
		SimpleImageInfo imageInfo = null;
		
		try {
			imageInfo = new SimpleImageInfo(new File(getOverlayImagePath()));
			this.setImageWidth(imageInfo.getWidth());
			this.setImageHeight(imageInfo.getHeight());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.setImageWidth(-1);
			this.setImageHeight(-1);
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
