package com.flashvisions.server.rtmp.transcoder.pojo.video;

import java.io.File;
import java.io.IOException;

import uk.co.jaimon.SimpleImageInfo;

import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlay;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlayLocation;
import com.flashvisions.server.rtmp.transcoder.pojo.base.MutableObject;

public class Overlay extends MutableObject implements IOverlay {
	

public static class Location implements IOverlayLocation{


		public static enum ALIGNMENT {
		    TOPLEFT, TOPRIGHT, BOTTOMLEFT, BOTTOMRIGHT, CENTERLEFT, CENTERRIGHT, CENTERTOP,  CENTERBOTTOM, CENTERMIDDLE 
		}
		
		private int x;
		private int y;
		private int width;
		private int height;
		private ALIGNMENT align;
		
		
		@Override
		public int getX() {
			return x;
		}
		
		@Override
		public void setX(int x) {
			this.x = x;
		}
		
		@Override
		public int getY() {
			return y;
		}
		
		@Override
		public void setY(int y) {
			this.y = y;
		}
		
		@Override
		public int getWidth() {
			return width;
		}
		
		@Override
		public void setWidth(int width) {
			this.width = width;
		}
		
		@Override
		public int getHeight() {
			return height;
		}
		
		@Override
		public void setHeight(int height) {
			this.height = height;
		}
		
		@Override
		public ALIGNMENT getAlign() {
			return align;
		}
		
		@Override
		public void setAlign(ALIGNMENT align) {
			this.align = align;
		}
		
		@Override
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
	
	
	@Override
	public String getLabel() {
		return label;
	}
	
	@Override
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public int getZindex() {
		return zindex;
	}
	
	@Override
	public void setZindex(int zindex) {
		this.zindex = zindex;
	}
	
	@Override
	public String getOverlayImagePath() {
		return overlayImagePath;
	}
	
	@Override
	public void setOverlayImagePath(String overlayImagePath) throws IOException {
		this.overlayImagePath = overlayImagePath;
		this.evaluateImageDimensions();
	}
	
	@Override
	public int getOpacity() {
		return opacity;
	}
	
	@Override
	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}
	
	@Override
	public IOverlayLocation getLocation() {
		return location;
	}
	
	@Override
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

	@Override
	public int getOverlayImageWidth() {
		return imageWidth;
	}

	private void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	@Override
	public int getOverlayImageHeight() {
		return imageHeight;
	}

	private void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
}
