package com.flashvisions.server.rtmp.transcoder.managers;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;

public class IOManager {

	private static IOManager instance;
	private static Hashtable<ITranscoderResource, ArrayList<ITranscoderResource>> resourceTable;
	
	private IOManager(){	
		resourceTable = new Hashtable<ITranscoderResource, ArrayList<ITranscoderResource>>();
	}
	
	public static IOManager getInstance()
	{
		if(instance == null)
		instance = new IOManager();
		return instance;
	}
	
	public void registerTranscodeSession(ISession session)
	{
		 resourceTable.remove(session.getInputSource());
	}
	
	public void unRegisterTranscodeSession(ISession session)
	{
		resourceTable.remove(session.getInputSource());
	}
	
	public boolean isTranscodeLoopSafe(ITranscoderResource input)
	{
		boolean safe = true;
		
		if(input.isFile()) 
		return safe;
		
		Iterator<Entry<ITranscoderResource, ArrayList<ITranscoderResource>>> iterator = resourceTable.entrySet().iterator();
		while(iterator.hasNext()){
		   Map.Entry<ITranscoderResource, ArrayList<ITranscoderResource>> entry = iterator.next();
		   ArrayList<ITranscoderResource> outputs = entry.getValue();
		   for(ITranscoderResource output : outputs){
			   if(output.isStreamingMedia()){
				if(output.getSourcePath().equalsIgnoreCase(input.getSourcePath())){
				safe = false;
				return safe;
				}
			   }
		   }
		}
		
		return safe;
	}
}
