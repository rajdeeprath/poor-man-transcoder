package com.flashvisions.server.rtmp.transcoder.pojo.collection;

import java.util.ArrayList;
import java.util.List;

import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlay;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlayCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlayIterator;

public class OverlayCollection implements IOverlayCollection {

	private List<IOverlay> overlayList;
	
	public OverlayCollection()
	{
		overlayList = new ArrayList<IOverlay>();
	}
	
	@Override
	public void addOverlay(IOverlay e) 
	{
		// TODO Auto-generated method stub
		this.overlayList.add(e);
	}

	
	@Override
	public void removeOverlay(IOverlay e) 
	{
		// TODO Auto-generated method stub
		this.overlayList.remove(e);
	}

	
	@Override
	public IOverlayIterator iterator() 
	{
		// TODO Auto-generated method stub
		return new OverlayIterator(this.overlayList);
	}

	
	public List<IOverlay> getoverlayList() 
	{
		return overlayList;
	}

	public void setoverlayList(List<IOverlay> overlayList) 
	{
		this.overlayList = overlayList;
	}

	@Override
	public void clear() 
	{
		this.overlayList.clear();	
	}
	
	private class OverlayIterator implements IOverlayIterator {
		
		private List<IOverlay> overlayList;
        private int position;
		
		public OverlayIterator(List<IOverlay> overlayList) 
		{
            this.overlayList = overlayList;
            this.position = 0;
        }
 

		@Override
		public boolean hasNext() 
		{
			if(position < overlayList.size()){
			return true;			
			}
			position++;
			return false;
		}

		@Override
		public IOverlay next() 
		{
			IOverlay e = overlayList.get(position);
            position++;
            return e;
		}
	}

	@Override
	public int getTotalOverlays() {
		// TODO Auto-generated method stub
		return overlayList.size();
	}
}
