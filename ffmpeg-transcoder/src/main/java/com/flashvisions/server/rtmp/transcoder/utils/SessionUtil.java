package com.flashvisions.server.rtmp.transcoder.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncode;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeIterator;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscode;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.pojo.Encode;
import com.flashvisions.server.rtmp.transcoder.pojo.Transcode;
import com.flashvisions.server.rtmp.transcoder.pojo.TranscodeOutput;
import com.flashvisions.server.rtmp.transcoder.pojo.audio.Audio;
import com.flashvisions.server.rtmp.transcoder.pojo.audio.AudioBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.audio.AudioChannel;
import com.flashvisions.server.rtmp.transcoder.pojo.audio.AudioCodec;
import com.flashvisions.server.rtmp.transcoder.pojo.audio.AudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.pojo.collection.EncodeCollection;
import com.flashvisions.server.rtmp.transcoder.pojo.io.base.MediaDestination;
import com.flashvisions.server.rtmp.transcoder.pojo.video.FrameRate;
import com.flashvisions.server.rtmp.transcoder.pojo.video.FrameSize;
import com.flashvisions.server.rtmp.transcoder.pojo.video.KeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.pojo.video.Video;
import com.flashvisions.server.rtmp.transcoder.pojo.video.VideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.video.VideoCodec;

public class SessionUtil {

	public static String generateSessionSignature(String source, String template)
	{
		String content = source + template;
		MessageDigest md = null;
		byte[] thedigest = null;
		String hash = null;
		
		try
		{
			md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(content.getBytes());
			thedigest = md.digest();
			BigInteger bigInt = new BigInteger(1,thedigest);
			hash = bigInt.toString(16);
			while(hash.length() < 32 ){
			hash = "0"+hash;
			}
		}
		catch(Exception e)
		{
			// TO DO
		}
		finally
		{
			md = null;
			thedigest = null;
		}
		
		return hash;
	}
	
	public static ITranscode cloneTranscodeConfiguration(ITranscode original)
	{
		IEncodeCollection dollyEncodes = new EncodeCollection();
		IEncodeCollection originalEncodes =  original.getEncodes();
		IEncodeIterator et = originalEncodes.iterator();
		
		
		while(et.hasNext())
		{
			IEncode cencode = new Encode();
			IEncode oencode = et.next();
			
			cencode.setEnabled(oencode.getEnabled());
			
			
			/******************************************************************/
			
			IAudio caConfig = new Audio();
			IAudio oaConfig = oencode.getAudioConfig();
			caConfig.setEnabled(oaConfig.getEnabled());
			caConfig.setCodec(new AudioCodec(oaConfig.getCodec()));
			caConfig.setSamplerate(new AudioSampleRate(oaConfig.getSamplerate()));
			caConfig.setBitrate(new AudioBitrate(oaConfig.getBitrate()));
			caConfig.setChannel(new AudioChannel(oaConfig.getChannel()));
			
			ArrayList<IProperty> caProperties = new ArrayList<IProperty>();
			ArrayList<IProperty> oaProperties = oaConfig.getExtraProperties();
			for(IProperty p : oaProperties) caProperties.add(p.clone());
			caConfig.setExtraProperties(caProperties);
			
			
			ArrayList<IParameter> caParameters = new ArrayList<IParameter>();
			ArrayList<IParameter> oaParameters = oaConfig.getExtraParams();
			for(IParameter pa : oaParameters) caParameters.add(pa.clone());
			caConfig.setExtraParams(caParameters);
			
			
			/******************************************************************/
			
			IVideo cvConfig = new Video(); 
			IVideo ovConfig = oencode.getVideoConfig();
			cvConfig.setEnabled(ovConfig.getEnabled());
			cvConfig.setCodec(new VideoCodec(ovConfig.getCodec()));
			cvConfig.setFramerate(new FrameRate(ovConfig.getFramerate()));
			cvConfig.setFramesize(new FrameSize(ovConfig.getFramesize()));
			cvConfig.setKeyFrameInterval(new KeyFrameInterval(ovConfig.getKeyFrameInterval()));
			cvConfig.setBitrate(new VideoBitrate(ovConfig.getBitrate()));
			
			ArrayList<IProperty> cvProperties = new ArrayList<IProperty>();
			ArrayList<IProperty> ovProperties = ovConfig.getExtraProperties();
			for(IProperty p : ovProperties) cvProperties.add(p.clone());
			cvConfig.setExtraProperties(cvProperties);
			
			ArrayList<IParameter> cvParameters = new ArrayList<IParameter>();
			ArrayList<IParameter> ovParameters = ovConfig.getExtraParams();
			for(IParameter pa : ovParameters) cvParameters.add(pa.clone());
			cvConfig.setExtraParams(cvParameters);
			
			
			/******************************************************************/
			
			ITranscodeOutput coutput = new TranscodeOutput();
			ITranscodeOutput ooutput = oencode.getOutput();
			
			ArrayList<IProperty> cProperties = new ArrayList<IProperty>();
			ArrayList<IProperty> oProperties = coutput.getOutputProperties();
			for(IProperty p : oProperties) cProperties.add(p.clone());
			
			ooutput.setMediaOutput(new MediaDestination(coutput.getMediaOutput()));
			ooutput.setOutputProperties(cProperties);
			
			/******************************************/
			
			dollyEncodes.addEncode(cencode);
		}
		
		
		ITranscode dolly = Transcode.Builder.newTranscode()
							.withLabel(original.getLabel())
							.withDescription(original.getDescription())
							.usingEncodes(dollyEncodes)
							.asValid(original.getEnabled())
							.build();
		
		return dolly;
	}
	
}
