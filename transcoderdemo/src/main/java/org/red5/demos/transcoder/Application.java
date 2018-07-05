package org.red5.demos.transcoder;

/*
 * RED5 Open Source Flash Server - http://www.osflash.org/red5
 * 
 * Copyright (c) 2006-2008 by respective authors (see below). All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or modify it under the 
 * terms of the GNU Lesser General Public License as published by the Free Software 
 * Foundation; either version 2.1 of the License, or (at your option) any later 
 * version. 
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along 
 * with this library; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
 */

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.scope.IScope;
//import org.slf4j.Logger;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.util.ScopeUtils;

import com.flashvisions.server.rtmp.transcoder.TranscodeSessionManager;
import com.flashvisions.server.rtmp.transcoder.context.TranscodeRequest;
import com.flashvisions.server.rtmp.transcoder.exception.InvalidTranscoderResourceException;
import com.flashvisions.server.rtmp.transcoder.exception.MalformedTranscodeQueryException;
import com.flashvisions.server.rtmp.transcoder.exception.MediaIdentifyException;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeSessionManager;

/**
 * Sample application that uses the client manager.
 * 
 * @author The Red5 Project (red5@osflash.org)
 */
public class Application extends MultiThreadedApplicationAdapter {


	@Override
	public boolean appStart(IScope arg0) {
		// TODO Auto-generated method stub
		return super.appStart(arg0);
	}

	@Override
	public void appStop(IScope arg0) {
		// TODO Auto-generated method stub
		super.appStop(arg0);
	}

	@Override
	public void streamBroadcastClose(IBroadcastStream stream) 
	{
		ITranscodeSessionManager manager = TranscodeSessionManager.getInstance();
		
		try 
		{
			manager.stopTranscode(stream);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		super.streamBroadcastClose(stream);
	}
	
	@Override
	public void streamBroadcastStart(IBroadcastStream stream) {
		
		ITranscodeSessionManager manager = TranscodeSessionManager.getInstance();
		
		TranscodeRequest request = new TranscodeRequest();
		request.setTemplateFileName("record-mp4-template.xml");
		request.setCleanUpSegmentsOnExit(true);
		
		try 
		{
			manager.doTranscode(stream, request);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		super.streamBroadcastStart(stream);
	}

}
