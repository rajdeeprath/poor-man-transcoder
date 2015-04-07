package com.flashvisions.server.rtmp.transcoder.pojo.collection;

import java.util.ArrayList;
import java.util.List;

import com.flashvisions.server.rtmp.transcoder.interfaces.IEncode;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeIterator;

public class EncodeCollection implements IEncodeCollection  {

	private List<IEncode> encodeList;
	
	public EncodeCollection()
	{
		encodeList = new ArrayList<IEncode>();
	}

	public void addEncode(IEncode e) 
	{
		// TODO Auto-generated method stub
		this.encodeList.add(e);
	}

	
	public void removeEncode(IEncode e) 
	{
		// TODO Auto-generated method stub
		this.encodeList.remove(e);
	}

	
	public IEncodeIterator iterator() 
	{
		// TODO Auto-generated method stub
		return new EncodeIterator(this.encodeList);
	}

	
	public List<IEncode> getEncodeList() 
	{
		return encodeList;
	}

	public void setEncodeList(List<IEncode> encodeList) 
	{
		this.encodeList = encodeList;
	}

	
	public void clear() 
	{
		this.encodeList.clear();	
	}
	
	
	private class EncodeIterator implements IEncodeIterator {
		
		private List<IEncode> encodeList;
        private int position;
		
		public EncodeIterator(List<IEncode> encodeList) 
		{
            this.encodeList = encodeList;
            this.position = 0;
        }
 

		public boolean hasNext() 
		{
			if(position < encodeList.size()){
			return true;			
			}
			position++;
			return false;
		}

		public IEncode next() 
		{
			IEncode e = encodeList.get(position);
            position++;
            return e;
		}
	}


	@Override
	public int getTotalEncodes() {
		// TODO Auto-generated method stub
		return this.encodeList.size();
	}
}
