package com.flashvisions.server.rtmp.transcoder.data.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.flashvisions.server.rtmp.transcoder.exception.TranscodeConfigurationException;
import com.flashvisions.server.rtmp.transcoder.helpers.TemplateParseHelper;
import com.flashvisions.server.rtmp.transcoder.interfaces.IArbitaryProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IDisposable;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncode;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFlag;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlay;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlayCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlayLocation;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfigDao;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.pojo.ArbitaryProperty;
import com.flashvisions.server.rtmp.transcoder.pojo.AudioBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.AudioChannel;
import com.flashvisions.server.rtmp.transcoder.pojo.AudioCodec;
import com.flashvisions.server.rtmp.transcoder.pojo.AudioProperty;
import com.flashvisions.server.rtmp.transcoder.pojo.AudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.pojo.Codec;
import com.flashvisions.server.rtmp.transcoder.pojo.Flag;
import com.flashvisions.server.rtmp.transcoder.pojo.FrameRate;
import com.flashvisions.server.rtmp.transcoder.pojo.FrameSize;
import com.flashvisions.server.rtmp.transcoder.pojo.KeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.pojo.OutMedia;
import com.flashvisions.server.rtmp.transcoder.pojo.Overlay;
import com.flashvisions.server.rtmp.transcoder.pojo.VideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.VideoCodec;
import com.flashvisions.server.rtmp.transcoder.pojo.VideoProperty;
import com.flashvisions.server.rtmp.transcoder.vo.Audio;
import com.flashvisions.server.rtmp.transcoder.vo.Encode;
import com.flashvisions.server.rtmp.transcoder.vo.TranscodeConfig;
import com.flashvisions.server.rtmp.transcoder.vo.Video;
import com.flashvisions.server.rtmp.transcoder.vo.collection.EncodeCollection;
import com.flashvisions.server.rtmp.transcoder.vo.collection.OverlayCollection;

@SuppressWarnings("unused")
public class TemplateDao implements ITranscodeConfigDao, IDisposable {

	private static Logger logger = LoggerFactory.getLogger(TemplateDao.class);
	
	private String templatePath;
	private String templateName;
	private File templateFile;
	
	private DocumentBuilderFactory builderFactory = null;
	private DocumentBuilder builder = null;
	private Document document = null;
	private XPath xpath;
	
	private ITranscodeConfig session;
	
	public TemplateDao()
	{
		// Default
	}
	
	public TemplateDao(String templatePath, boolean lazyLoad)
	{
		this.templatePath = templatePath;
		if(!lazyLoad) this.readTemplate();
	}
	
	public TemplateDao(String templatePath)
	{
		this.templatePath = templatePath;
		this.readTemplate();
	}	
	
	public void readTemplate() {
		// TODO Auto-generated method stub
		// load actual template file here
		// then parse
		
		
		try
		{
			logger.debug("preparing new transcode session");
			session = new TranscodeConfig();
			
			this.templateFile = new File(this.templatePath);
			if(!this.templateFile.exists()) throw new FileNotFoundException("Template not found");
			
			logger.debug("loading template " + this.templateFile.getName());
			
			this.builderFactory = DocumentBuilderFactory.newInstance();
			this.builder = this.builderFactory.newDocumentBuilder();
			this.xpath = XPathFactory.newInstance().newXPath();
			this.document = this.builder.parse(new FileInputStream(this.templateFile.getAbsolutePath()));
			
			logger.debug("Updating document with expression variables");
			TemplateParseHelper.updateDocumentWithVariables(this.document, this.xpath);
			
			/****************** template name ****************/
			String templateNameExpression = "/Template/Transcode/Name";
			String name = this.xpath.compile(templateNameExpression).evaluate(this.document);
			session.setLabel(name);
			
			
			/****************** template description ****************/
			String templateDescriptionExpression = "/Template/Transcode/Description";
			String description = this.xpath.compile(templateDescriptionExpression).evaluate(this.document);
			session.setDescription(description);
			
			
			/****************** look for encode objects ****************/
			String encodeNodesExpression = "/Template/Transcode/Encodes/Encode";
			NodeList encodeNodes = (NodeList) this.xpath.compile(encodeNodesExpression).evaluate(this.document, XPathConstants.NODESET);
			
			IEncodeCollection encodeList = new EncodeCollection();
			for(int i=0;i<encodeNodes.getLength();i++){
				
				IEncode encode = new Encode();
				/******************* generic encode information **************************************/
				
				String encodeNodeEnabledExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Enable";
				boolean encodeNodeEnabled = Boolean.parseBoolean(this.xpath.compile(encodeNodeEnabledExpression).evaluate(this.document));
				encode.setEnabled(encodeNodeEnabled);
				
				String encodeNodeNameExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Name";
				String encodeNodeName = this.xpath.compile(encodeNodeNameExpression).evaluate(this.document);
				encode.setName(encodeNodeName);
				
				String encodeNodeOutputNameExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/StreamName";
				String encodeNodeOutputName = this.xpath.compile(encodeNodeOutputNameExpression).evaluate(this.document);
				IMediaOutput output = new OutMedia(encodeNodeOutputName, true); 
				encode.setOutput(output);
				
				
				
				/******************* Video codec information **************************************/
				IVideo video = new Video();
				
				String encodeNodeVideoCodecExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Codec";
				String encodeNodeVideoCodec = this.xpath.compile(encodeNodeVideoCodecExpression).evaluate(this.document);
				String encodeNodeVideoCodecImplementationExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Implementation";
				String encodeNodeVideoCodecImplementation = this.xpath.compile(encodeNodeVideoCodecImplementationExpression).evaluate(this.document);
				video.setCodec(new VideoCodec(encodeNodeVideoCodec, encodeNodeVideoCodecImplementation));
				
				
				String encodeNodeVideoFrameSizeExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/FrameSize";
				Node encodeNodeVideoFrameSizeNode = (Node) this.xpath.compile(encodeNodeVideoFrameSizeExpression).evaluate(this.document, XPathConstants.NODE);
				
				
				/***** Width / Height *******/
				
				if(encodeNodeVideoFrameSizeNode != null && encodeNodeVideoFrameSizeNode.hasChildNodes()) {
					String encodeNodeVideoFrameWidthExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/FrameSize/Width";
					Double encodeNodeVideoFrameWidth = (Double) this.xpath.compile(encodeNodeVideoFrameWidthExpression).evaluate(this.document, XPathConstants.NUMBER);
					String encodeNodeVideoFrameHeightExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/FrameSize/Height";
					Double encodeNodeVideoFrameHeight = (Double) this.xpath.compile(encodeNodeVideoFrameHeightExpression).evaluate(this.document, XPathConstants.NUMBER);
				
					// check for valid width height combination.. then
					video.setFramesize(new FrameSize(encodeNodeVideoFrameWidth.intValue(), encodeNodeVideoFrameHeight.intValue()));
				
				}else {
					video.setFramesize(new FrameSize(true));
				}
				
				
				/***** Frame-Rate *******/
				
				String encodeNodeVideoFrameRateExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/FrameRate";
				String encodeNodeVideoFrameRate = this.xpath.compile(encodeNodeVideoFrameRateExpression).evaluate(this.document);
				if(encodeNodeVideoFrameRate != null) {
					int encodeFrameRate = Integer.parseInt(encodeNodeVideoFrameRate);
					if(encodeFrameRate <= 0 || encodeFrameRate >= 100)
					throw new IllegalArgumentException("Invalid framerate");
					
					video.setFramerate(new FrameRate(encodeFrameRate));
				}else {
					video.setFramerate(new FrameRate(true));
				}
				
				
				
				String encodeNodeVideoBitrateExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Bitrate";
				Node encodeNodeVideoBitrateNode = (Node) this.xpath.compile(encodeNodeVideoBitrateExpression).evaluate(this.document, XPathConstants.NODE);
				if(encodeNodeVideoBitrateNode != null && encodeNodeVideoBitrateNode.hasChildNodes()){
					
					String encodeNodeVideoBitrateAvgExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Bitrate/Average";
					int encodeNodeVideoBitrateAverage = Integer.parseInt(this.xpath.compile(encodeNodeVideoBitrateAvgExpression).evaluate(this.document));
					
					String encodeNodeVideoBitrateMinExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Bitrate/Min";
					int encodeNodeVideoBitrateMin = Integer.parseInt(this.xpath.compile(encodeNodeVideoBitrateMinExpression).evaluate(this.document));
					
					String encodeNodeVideoBitrateMaxExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Bitrate/Max";
					int encodeNodeVideoBitrateMax = Integer.parseInt(this.xpath.compile(encodeNodeVideoBitrateMaxExpression).evaluate(this.document));
					
					String encodeNodeVideoBitrateBufferExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Bitrate/Buffer";
					int encodeNodeVideoBitrateBuffer = Integer.parseInt(this.xpath.compile(encodeNodeVideoBitrateBufferExpression).evaluate(this.document));
					
					video.setBitrate(new VideoBitrate(encodeNodeVideoBitrateAverage, encodeNodeVideoBitrateMin, encodeNodeVideoBitrateMax, encodeNodeVideoBitrateBuffer));
				}else {
					video.setBitrate(new VideoBitrate(true));
				}
				
				
				
				String encodeNodeVideoKeyFrameIntervalExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/KeyFrameInterval";
				Node encodeNodeVideoKeyFrameIntervalNode = (Node) this.xpath.compile(encodeNodeVideoBitrateExpression).evaluate(this.document, XPathConstants.NODE);
				if(encodeNodeVideoKeyFrameIntervalNode != null && encodeNodeVideoKeyFrameIntervalNode.hasChildNodes()){
					String encodeNodeVideoKeyFrameIntervalGopExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/KeyFrameInterval/Gop";
					String encodeNodeVideoGop = this.xpath.compile(encodeNodeVideoKeyFrameIntervalGopExpression).evaluate(this.document);
					if(encodeNodeVideoGop == null) throw new TranscodeConfigurationException("Invalid gop size");
					int encodeNodeVideoKFIGop = Integer.parseInt(encodeNodeVideoGop);
					
					String encodeNodeVideoKeyFrameIntervalMinExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/KeyFrameInterval/IntervalMin";
					String encodeNodeVideoKeyFrameIntervalMin = this.xpath.compile(encodeNodeVideoKeyFrameIntervalMinExpression).evaluate(this.document);
					if(encodeNodeVideoKeyFrameIntervalMin == null) throw new TranscodeConfigurationException("Invalid min keyframeinterval");
					int encodeNodeVideoKFIMin = Integer.parseInt(encodeNodeVideoKeyFrameIntervalMin);
					
					video.setKeyFrameInterval(new KeyFrameInterval(encodeNodeVideoKFIGop, encodeNodeVideoKFIMin));
				}else{
					video.setKeyFrameInterval(new KeyFrameInterval(true));
				}
				
				
				/**************** Overlays ************/
				
				String overlayNodesExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay";
				NodeList overlayNodes = (NodeList) this.xpath.compile(overlayNodesExpression).evaluate(this.document, XPathConstants.NODESET);
				
				IOverlayCollection overlays = new OverlayCollection();
				for(int m=0;m<overlayNodes.getLength();m++)
				{
					IOverlay overlay = new Overlay();
					
					try
					{
						String overlayNodeExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]";
						Node overlayNode = (Node) this.xpath.compile(overlayNodeExpression).evaluate(this.document, XPathConstants.NODE);
						
						
						String overlayNodeEnableExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Enable";
						boolean overlayEnable = (Boolean) this.xpath.compile(overlayNodeEnableExpression).evaluate(this.document, XPathConstants.BOOLEAN);
						overlay.setEnabled(overlayEnable);
						
						
						String overlayNodeNameExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Name";
						String overlayName = (String) this.xpath.compile(overlayNodeNameExpression).evaluate(this.document, XPathConstants.STRING);
						overlay.setLabel(overlayName);
						
						
						String overlayNodeIndexExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Index";
						Double overlayIndex = (Double) this.xpath.compile(overlayNodeIndexExpression).evaluate(this.document, XPathConstants.NUMBER);
						overlay.setZindex(overlayIndex.intValue());	
						
						
						String overlayNodeImageExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/ImagePath";
						String overlayImage = (String) this.xpath.compile(overlayNodeImageExpression).evaluate(this.document, XPathConstants.STRING);
						overlay.setOverlayImagePath(overlayImage);
						
						
						String overlayNodeOpacityExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Opacity";
						Double overlayOpacity = (Double) this.xpath.compile(overlayNodeOpacityExpression).evaluate(this.document, XPathConstants.NUMBER);
						overlay.setOpacity(overlayOpacity.intValue());
						
						
						/******* location *****/
						
						String locationNodeExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Location";
						Node locationNode = (Node) this.xpath.compile(overlayNodeExpression).evaluate(this.document, XPathConstants.NODE);
						
						IOverlayLocation location  = new Overlay.Location();
						
						
						String locationNodeXExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Location/X";
						Double locationNodeX = (Double) this.xpath.compile(locationNodeXExpression).evaluate(this.document, XPathConstants.NUMBER);
						location.setX(locationNodeX.intValue());
						
						
						String locationNodeYExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Location/Y";
						Double locationNodeY = (Double) this.xpath.compile(locationNodeYExpression).evaluate(this.document, XPathConstants.NUMBER);
						location.setX(locationNodeY.intValue());
						
						/******** evaluate overlay width *****/
						
						String locationNodeWidthExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Location/Width";
						String locationNodeWidthContent = (String) this.xpath.compile(locationNodeWidthExpression).evaluate(this.document, XPathConstants.STRING);
						if(overlay.getOverlayImageWidth()<=0) throw new IllegalStateException("Invalid overlay width");
						int width = TemplateParseHelper.evaluateExpressionForInt(locationNodeWidthContent, "ImageWidth", overlay.getOverlayImageWidth());
		                location.setWidth(width);
						
						
						/******** evaluate overlay height *****/
						
						String locationNodeHeightExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Location/Height";
						String locationNodeHeightContent = (String) this.xpath.compile(locationNodeHeightExpression).evaluate(this.document, XPathConstants.STRING);
						if(overlay.getOverlayImageHeight()<=0) throw new IllegalStateException("Invalid overlay height");
						int height = TemplateParseHelper.evaluateExpressionForInt(locationNodeHeightContent, "ImageHeight", overlay.getOverlayImageHeight());
		                location.setHeight(height);
						
						
						String locationNodeAlignExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Location/Align";
						String locationNodeAligntContent = (String) this.xpath.compile(locationNodeAlignExpression).evaluate(this.document, XPathConstants.STRING);
						location.setAlign(locationNodeAligntContent);
						
						overlay.setLocation(location);
						
						/*** add overlay to list  of overlays */
						overlays.addOverlay(overlay);
					}
					catch(Exception w)
					{
						logger.error("An error occured in processing overlay configuration.Disabling overlay : " + w.getMessage());
						overlay.setEnabled(false);
					}
				}
				
				/********* store all overlays ***/
				video.setOverlays(overlays);
				
				
				
				/******** Extra video flags ***********/
				
				String encodeNodeVideoExtraParamsExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Parameters";
				Node encodeNodeVideoExtraParamsNode = (Node) this.xpath.compile(encodeNodeVideoExtraParamsExpression).evaluate(this.document, XPathConstants.NODE);
				
				if(encodeNodeVideoExtraParamsNode != null && encodeNodeVideoExtraParamsNode.hasChildNodes())
				{
					String videoExtraParamsExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Parameters/Param";
					NodeList videoParams = (NodeList) this.xpath.compile(videoExtraParamsExpression).evaluate(this.document, XPathConstants.NODESET);
					
					ArrayList<IArbitaryProperty> extras = new ArrayList<IArbitaryProperty>();
					
					for(int j=0;j<videoParams.getLength();j++)
					{
						String videoParamsKeyExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Parameters/Param["+(j+1)+"]/Key";
						String videoParamsKey = this.xpath.compile(videoParamsKeyExpression).evaluate(this.document);
						
						String videoParamsValueExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Parameters/Param["+(j+1)+"]/Value";
						String videoParamsValue = this.xpath.compile(videoParamsValueExpression).evaluate(this.document);
						
						extras.add(new VideoProperty(videoParamsKey, videoParamsValue));
					}
					
					video.setExtraParams(extras);
				}
				
				/*** all ok with video configuration */
				video.setEnabled(true);
				
				
				
				/**** store video configuration into encode object *****/
				encode.setVideoConfig(video);
				
				
				
				/******************* Audio codec information *************************************
				 * ******************************************************************************/
				IAudio audio = new Audio();
				
				String encodeNodeAudioCodecExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Codec";
				String encodeNodeAudioCodec = this.xpath.compile(encodeNodeVideoCodecExpression).evaluate(this.document);
				String encodeNodeAudioCodecImplementationExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Implementation";
				String encodeNodeAudioCodecImplementation = this.xpath.compile(encodeNodeVideoCodecImplementationExpression).evaluate(this.document);
				audio.setCodec(new AudioCodec(encodeNodeAudioCodec, encodeNodeAudioCodecImplementation));
				
				
				/* Use set bitrate or else follow from source */
				String encodeNodeAudioBitrateExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Bitrate";
				String encodeNodeAudioBitrate = this.xpath.compile(encodeNodeAudioBitrateExpression).evaluate(this.document);
				if(encodeNodeAudioBitrate != null && Integer.parseInt(encodeNodeAudioBitrate)>0){
				audio.setBitrate(new AudioBitrate(Integer.parseInt(encodeNodeAudioBitrate)));
				}else {
				audio.setBitrate(new AudioBitrate(true));
				}
				
				
				/* Use set samplerate or else follow from source */
				String encodeNodeAudioSamplerateExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/SampleRate";
				String encodeNodeAudioSampleRate = this.xpath.compile(encodeNodeAudioSamplerateExpression).evaluate(this.document);
				if(encodeNodeAudioSampleRate != null && Integer.parseInt(encodeNodeAudioSampleRate)>0){
				audio.setSamplerate(new AudioSampleRate(Integer.parseInt(encodeNodeAudioSampleRate)));
				}else{
				audio.setSamplerate(new AudioSampleRate(true));
				}
				
				
				/* Use set channel type or else follow from source */
				String encodeNodeAudioChannelTypeExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Channels";
				String encodeNodeAudioChannelType = this.xpath.compile(encodeNodeAudioChannelTypeExpression).evaluate(this.document);
				if(encodeNodeAudioChannelType != null && encodeNodeAudioChannelType == "mono" || encodeNodeAudioChannelType == "stereo"){
				audio.setChannel(new AudioChannel(encodeNodeAudioChannelType));
				}else{
				audio.setChannel(new AudioChannel(true));
				}
				
				
				/******** Extra audio flags ***********/
				
				String encodeNodeAudioExtraParamsExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Parameters";
				Node encodeNodeAudioExtraParamsNode = (Node) this.xpath.compile(encodeNodeAudioExtraParamsExpression).evaluate(this.document, XPathConstants.NODE);
				
				if(encodeNodeAudioExtraParamsNode != null && encodeNodeAudioExtraParamsNode.hasChildNodes())
				{
					String audioExtraParamsExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Parameters/Param";
					NodeList audioParams = (NodeList) this.xpath.compile(audioExtraParamsExpression).evaluate(this.document, XPathConstants.NODESET);
					
					ArrayList<IArbitaryProperty> extras = new ArrayList<IArbitaryProperty>(); 
					for(int j=0;j<audioParams.getLength();j++)
					{
						String audioParamsKeyExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Parameters/Param["+(j+1)+"]/Key";
						String audioParamsKey = this.xpath.compile(audioParamsKeyExpression).evaluate(this.document);
						
						String audioParamsValueExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Parameters/Param["+(j+1)+"]/Value";
						String audioParamsValue = this.xpath.compile(audioParamsValueExpression).evaluate(this.document);
						
						extras.add(new AudioProperty(audioParamsKey, audioParamsValue));
					}
					audio.setExtraParams(extras);
				}
				
				/*** all ok with audio configuration */
				audio.setEnabled(true);
				
				
				/**** store audio configuration into encode object *****/
				encode.setAudioConfig(audio);
				
				
				/****************** look for output flags ****************/
				String outputFlagsExpression = "/Template/Transcode/Output/Flags/RawFlag";
				NodeList outputflagNodes = (NodeList) this.xpath.compile(outputFlagsExpression).evaluate(this.document, XPathConstants.NODESET);
				ArrayList<IFlag> outputflags = new ArrayList<IFlag>(); 
				for(int l=0;l<outputflagNodes.getLength();l++){
				Node n = outputflagNodes.item(l);
				String flag = n.getFirstChild().getNodeValue();
				outputflags.add(new Flag(flag));
				}
				
				/**** store any extra output flags *****/
				encode.setOutputflags(outputflags);
				
				
				/**** Add encode object to encodeslist *****/
				encodeList.addEncode(encode);
			}
			
			/**** ad encodes list to transcode session object ***/
			session.setEncodes(encodeList);
			session.setEnabled(true);
		}
		catch(TranscodeConfigurationException | IllegalArgumentException | SAXException | IOException | ParserConfigurationException | XPathExpressionException | IllegalAccessException ee)
		{
			session.setEnabled(false);
			logger.error("Error parsing template " + ee.getMessage());
		}
	}

	public String getTemplate() {
		return this.templatePath;
	}

	public void setTemplate(String templatePath) {
		this.templatePath = templatePath;
	}

	public void dispose() {
		// TODO Auto-generated method stub
		session = null;
	}

	@Override
	public ITranscodeConfig getTranscodeConfig() {
		// TODO Auto-generated method stub
		return session;
	}
	 

}
