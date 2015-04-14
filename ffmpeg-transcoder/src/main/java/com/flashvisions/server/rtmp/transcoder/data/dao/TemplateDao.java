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
import java.util.Set;

import javax.imageio.ImageIO;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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

import com.flashvisions.server.rtmp.transcoder.decorator.TranscoderOutputResource;
import com.flashvisions.server.rtmp.transcoder.exception.TranscodeConfigurationException;
import com.flashvisions.server.rtmp.transcoder.helpers.TemplateParseHelper;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioBitrate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioChannel;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodecImplementation;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameSize;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IDisposable;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncode;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscode;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeDao;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.CodecImplementation;
import com.flashvisions.server.rtmp.transcoder.pojo.Container;
import com.flashvisions.server.rtmp.transcoder.pojo.Parameter;
import com.flashvisions.server.rtmp.transcoder.pojo.Codec;
import com.flashvisions.server.rtmp.transcoder.pojo.Encode;
import com.flashvisions.server.rtmp.transcoder.pojo.Property;
import com.flashvisions.server.rtmp.transcoder.pojo.Transcode;
import com.flashvisions.server.rtmp.transcoder.pojo.TranscodeOutput;
import com.flashvisions.server.rtmp.transcoder.pojo.audio.Audio;
import com.flashvisions.server.rtmp.transcoder.pojo.audio.AudioBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.audio.AudioChannel;
import com.flashvisions.server.rtmp.transcoder.pojo.audio.AudioCodec;
import com.flashvisions.server.rtmp.transcoder.pojo.audio.AudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.pojo.collection.EncodeCollection;
import com.flashvisions.server.rtmp.transcoder.pojo.io.base.MediaDestination;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Format;
import com.flashvisions.server.rtmp.transcoder.pojo.video.AverageBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.video.DeviceBuffer;
import com.flashvisions.server.rtmp.transcoder.pojo.video.FrameRate;
import com.flashvisions.server.rtmp.transcoder.pojo.video.FrameSize;
import com.flashvisions.server.rtmp.transcoder.pojo.video.Gop;
import com.flashvisions.server.rtmp.transcoder.pojo.video.KeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.pojo.video.MaximumBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.video.MinKeyframeInterval;
import com.flashvisions.server.rtmp.transcoder.pojo.video.MinimumBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.video.Video;
import com.flashvisions.server.rtmp.transcoder.pojo.video.VideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.video.VideoCodec;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;

@SuppressWarnings("unused")
public class TemplateDao implements ITranscodeDao {

	private static Logger logger = LoggerFactory.getLogger(TemplateDao.class);
	private static Validator validator;
	
	private String templatePath;
	private String templateName;
	private File templateFile;
	
	
	public TemplateDao(String templatePath)
	{
		this.templatePath = templatePath;
	}	
	
	protected ITranscode readTemplate() 
	{
		ITranscode session = null;
		
		DocumentBuilderFactory builderFactory = null;
		DocumentBuilder builder = null;
		Document document = null;
		XPath xpath;
		
		
		try
		{
			logger.debug("preparing new transcode session");
			
			
			
			this.templateFile = new File(this.templatePath);
			if(!this.templateFile.exists()) throw new FileNotFoundException("Template not found");
			
			logger.debug("loading template " + this.templateFile.getName());
			
			builderFactory = DocumentBuilderFactory.newInstance();
			builder = builderFactory.newDocumentBuilder();
			xpath = XPathFactory.newInstance().newXPath();
			document = builder.parse(new FileInputStream(this.templateFile.getAbsolutePath()));
			
			ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();  
			validator = validatorFactory.getValidator();  
			
			logger.debug("Updating document with expression variables");
			TemplateParseHelper.updateDocumentWithVariables(document, xpath);
			
			/****************** template name ****************/
			String templateNameExpression = "/Template/Transcode/Name";
			String name = xpath.compile(templateNameExpression).evaluate(document);
			
			
			/****************** template description ****************/
			String templateDescriptionExpression = "/Template/Transcode/Description";
			String description = xpath.compile(templateDescriptionExpression).evaluate(document);
			
			
			/****************** look for encode objects ****************/
			String encodeNodesExpression = "/Template/Transcode/Encodes/Encode";
			NodeList encodeNodes = (NodeList) xpath.compile(encodeNodesExpression).evaluate(document, XPathConstants.NODESET);
			
			IEncodeCollection encodeList = new EncodeCollection();
			for(int i=0;i<encodeNodes.getLength();i++){
				
				IEncode encode = new Encode();
				/******************* generic encode information **************************************/
				
				String encodeNodeEnabledExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Enable";
				boolean encodeNodeEnabled = Boolean.parseBoolean(xpath.compile(encodeNodeEnabledExpression).evaluate(document));
				encode.setEnabled(encodeNodeEnabled);
				
				String encodeNodeNameExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Name";
				String encodeNodeName = xpath.compile(encodeNodeNameExpression).evaluate(document);
				encode.setName(encodeNodeName);
						
				
				
				/******************* Video codec information **************************************/
				IVideo video = new Video();
			
				try
				{
					String encodeNodeVideoCodecExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Codec";
					String encodeNodeVideoCodec = xpath.compile(encodeNodeVideoCodecExpression).evaluate(document);
					
					ICodec videocodec = new VideoCodec(encodeNodeVideoCodec);
					video.setCodec(videocodec);
				}
				catch(Exception e)
				{
					throw new TranscodeConfigurationException("Invalid video codec specified");
				}
				
				
				
				try
				{
					String encodeNodeVideoCodecImplementationExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Implementation";
					String encodeNodeVideoCodecImplementation = xpath.compile(encodeNodeVideoCodecImplementationExpression).evaluate(document);
					
					ICodecImplementation codecImpl = new CodecImplementation(encodeNodeVideoCodecImplementation);
					video.setImplementation(codecImpl);
				}
				catch(Exception e)
				{
					throw new TranscodeConfigurationException("Invalid video codec implementation specified");
				}
				
				
				
				/***** Width / Height *******/
				try	
				{	
					String encodeNodeVideoFrameSizeExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/FrameSize";
					Node encodeNodeVideoFrameSizeNode = (Node) xpath.compile(encodeNodeVideoFrameSizeExpression).evaluate(document, XPathConstants.NODE);
					
					if(encodeNodeVideoFrameSizeNode != null && encodeNodeVideoFrameSizeNode.hasChildNodes()) 
					{
						String encodeNodeVideoFrameWidthExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/FrameSize/Width";
						Double encodeNodeVideoFrameWidth = (Double) xpath.compile(encodeNodeVideoFrameWidthExpression).evaluate(document, XPathConstants.NUMBER);
						String encodeNodeVideoFrameHeightExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/FrameSize/Height";
						Double encodeNodeVideoFrameHeight = (Double) xpath.compile(encodeNodeVideoFrameHeightExpression).evaluate(document, XPathConstants.NUMBER);
						Integer width = encodeNodeVideoFrameWidth.intValue();
						Integer height = encodeNodeVideoFrameHeight.intValue();
						IFrameSize dimension = new FrameSize(width, height);
								
						video.setFramesize(dimension);
					
					}
					else 
					{
						video.setFramesize(new FrameSize(true));
					}
				}
				catch(Exception e)
				{
					logger.info("Invalid frame size specified. {"+e.getMessage()+"} Following source...");
					video.setFramesize(new FrameSize(true));
				}
				
				
				/***** Frame-Rate *******/
				
				try
				{
					String encodeNodeVideoFrameRateExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/FrameRate";
					String encodeNodeVideoFrameRate = xpath.compile(encodeNodeVideoFrameRateExpression).evaluate(document);
					if(encodeNodeVideoFrameRate != null) 
					{
						Integer encodeFrameRate = Integer.parseInt(encodeNodeVideoFrameRate);
						IFrameRate fps = new FrameRate(encodeFrameRate);
						
						video.setFramerate(fps);
					}
					else 
					{
						video.setFramerate(new FrameRate(true));
					}
				}
				catch(Exception e)
				{
					logger.info("Invalid frame rate specified. {"+e.getMessage()+"} Following source...");
					video.setFramerate(new FrameRate(true));
				}
				
				
				
				try
				{
					String encodeNodeVideoBitrateExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Bitrate";
					Node encodeNodeVideoBitrateNode = (Node) xpath.compile(encodeNodeVideoBitrateExpression).evaluate(document, XPathConstants.NODE);
					if(encodeNodeVideoBitrateNode != null && encodeNodeVideoBitrateNode.hasChildNodes())
					{
						String encodeNodeVideoBitrateAvgExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Bitrate/Average";
						Integer encodeNodeVideoBitrateAverage = Integer.parseInt(xpath.compile(encodeNodeVideoBitrateAvgExpression).evaluate(document));
						
						String encodeNodeVideoBitrateMinExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Bitrate/Min";
						Integer encodeNodeVideoBitrateMin = Integer.parseInt(xpath.compile(encodeNodeVideoBitrateMinExpression).evaluate(document));
						
						String encodeNodeVideoBitrateMaxExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Bitrate/Max";
						Integer encodeNodeVideoBitrateMax = Integer.parseInt(xpath.compile(encodeNodeVideoBitrateMaxExpression).evaluate(document));
						
						String encodeNodeVideoBitrateBufferExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Bitrate/Buffer";
						Integer encodeNodeVideoBitrateBuffer = Integer.parseInt(xpath.compile(encodeNodeVideoBitrateBufferExpression).evaluate(document));
						
						IParameter avgBitrate = new AverageBitrate(encodeNodeVideoBitrateAverage);
						IParameter minBitrate = new MinimumBitrate(encodeNodeVideoBitrateMin);
						IParameter maxBitrate = new MaximumBitrate(encodeNodeVideoBitrateMax);
						IParameter deviceBuffer = new DeviceBuffer(encodeNodeVideoBitrateBuffer);
						IVideoBitrate bitrate = new VideoBitrate(avgBitrate, minBitrate, maxBitrate, deviceBuffer);
						
						video.setBitrate(bitrate);
					}
					else 
					{
						video.setBitrate(new VideoBitrate(true));
					}
				}
				catch(Exception e)
				{
					logger.info("Invalid video bitrate(s) settings.{"+e.getMessage()+"} Following source...");
					video.setBitrate(new VideoBitrate(true));
				}
				
				
				
				
				try
				{
					String encodeNodeVideoKeyFrameIntervalExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/KeyFrameInterval";
					Node encodeNodeVideoKeyFrameIntervalNode = (Node) xpath.compile(encodeNodeVideoKeyFrameIntervalExpression).evaluate(document, XPathConstants.NODE);
					if(encodeNodeVideoKeyFrameIntervalNode != null && encodeNodeVideoKeyFrameIntervalNode.hasChildNodes())
					{
						String encodeNodeVideoKeyFrameIntervalGopExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/KeyFrameInterval/Gop";
						String encodeNodeVideoGop = xpath.compile(encodeNodeVideoKeyFrameIntervalGopExpression).evaluate(document);
						if(encodeNodeVideoGop == null) throw new TranscodeConfigurationException("Invalid gop size");
						Integer encodeNodeVideoKFIGop = Integer.parseInt(encodeNodeVideoGop);
						
						String encodeNodeVideoKeyFrameIntervalMinExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/KeyFrameInterval/IntervalMin";
						String encodeNodeVideoKeyFrameIntervalMin = xpath.compile(encodeNodeVideoKeyFrameIntervalMinExpression).evaluate(document);
						if(encodeNodeVideoKeyFrameIntervalMin == null) throw new TranscodeConfigurationException("Invalid min keyframeinterval");
						Integer encodeNodeVideoKFIMin = Integer.parseInt(encodeNodeVideoKeyFrameIntervalMin);
						
						KeyFrameInterval kfi = new KeyFrameInterval(new Gop(encodeNodeVideoKFIGop), new MinKeyframeInterval(encodeNodeVideoKFIMin));
						video.setKeyFrameInterval(kfi);
					}
					else
					{
						video.setKeyFrameInterval(new KeyFrameInterval(true));
					}
				}
				catch(Exception e)
				{
					logger.info("Invalid video keyframe/gop settings. {"+e.getMessage()+"} Following source...");
					video.setKeyFrameInterval(new KeyFrameInterval(true));
				}
				
				
				
				/*
				
				String overlayNodesExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay";
				NodeList overlayNodes = (NodeList) xpath.compile(overlayNodesExpression).evaluate(document, XPathConstants.NODESET);
				
				IOverlayCollection overlays = new OverlayCollection();
				for(int m=0;m<overlayNodes.getLength();m++)
				{
					IOverlay overlay = new Overlay();
					
					try
					{
						String overlayNodeExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]";
						Node overlayNode = (Node) xpath.compile(overlayNodeExpression).evaluate(document, XPathConstants.NODE);
						
						
						String overlayNodeEnableExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Enable";
						boolean overlayEnable = (Boolean) xpath.compile(overlayNodeEnableExpression).evaluate(document, XPathConstants.BOOLEAN);
						overlay.setEnabled(overlayEnable);
						
						
						String overlayNodeNameExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Name";
						String overlayName = (String) xpath.compile(overlayNodeNameExpression).evaluate(document, XPathConstants.STRING);
						overlay.setLabel(overlayName);
						
						
						String overlayNodeIndexExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Index";
						Double overlayIndex = (Double) xpath.compile(overlayNodeIndexExpression).evaluate(document, XPathConstants.NUMBER);
						overlay.setZindex(overlayIndex.intValue());	
						
						
						String overlayNodeImageExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/ImagePath";
						String overlayImage = (String) xpath.compile(overlayNodeImageExpression).evaluate(document, XPathConstants.STRING);
						overlay.setOverlayImagePath(overlayImage);
						
						
						String overlayNodeOpacityExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Opacity";
						Double overlayOpacity = (Double) xpath.compile(overlayNodeOpacityExpression).evaluate(document, XPathConstants.NUMBER);
						overlay.setOpacity(overlayOpacity.intValue());
						
						
						String locationNodeExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Location";
						Node locationNode = (Node) xpath.compile(overlayNodeExpression).evaluate(document, XPathConstants.NODE);
						
						IOverlayLocation location  = new Overlay.Location();
						
						
						String locationNodeXExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Location/X";
						Double locationNodeX = (Double) xpath.compile(locationNodeXExpression).evaluate(document, XPathConstants.NUMBER);
						location.setX(locationNodeX.intValue());
						
						
						String locationNodeYExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Location/Y";
						Double locationNodeY = (Double) xpath.compile(locationNodeYExpression).evaluate(document, XPathConstants.NUMBER);
						location.setX(locationNodeY.intValue());
						
						
						String locationNodeWidthExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Location/Width";
						String locationNodeWidthContent = (String) xpath.compile(locationNodeWidthExpression).evaluate(document, XPathConstants.STRING);
						if(overlay.getOverlayImageWidth()<=0) throw new IllegalStateException("Invalid overlay width");
						int width = TemplateParseHelper.evaluateExpressionForInt(locationNodeWidthContent, "ImageWidth", overlay.getOverlayImageWidth());
		                location.setWidth(width);
						
						
						String locationNodeHeightExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Location/Height";
						String locationNodeHeightContent = (String) xpath.compile(locationNodeHeightExpression).evaluate(document, XPathConstants.STRING);
						if(overlay.getOverlayImageHeight()<=0) throw new IllegalStateException("Invalid overlay height");
						int height = TemplateParseHelper.evaluateExpressionForInt(locationNodeHeightContent, "ImageHeight", overlay.getOverlayImageHeight());
		                location.setHeight(height);
						
						
						String locationNodeAlignExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Overlays/Overlay["+(m+1)+"]/Location/Align";
						String locationNodeAligntContent = (String) xpath.compile(locationNodeAlignExpression).evaluate(document, XPathConstants.STRING);
						location.setAlign(locationNodeAligntContent);
						
						overlay.setLocation(location);
						
						overlays.addOverlay(overlay);
					}
					catch(Exception w)
					{
						logger.error("An error occured in processing overlay configuration.Disabling overlay : " + w.getMessage());
						overlay.setEnabled(false);
					}
				}
				
				video.setOverlays(overlays);
				*/
				
				
				
				/******** Extra video flags ***********/
				
				try
				{
					String encodeNodeVideoExtraParamsExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Parameters";
					Node encodeNodeVideoExtraParamsNode = (Node) xpath.compile(encodeNodeVideoExtraParamsExpression).evaluate(document, XPathConstants.NODE);
					
					if(encodeNodeVideoExtraParamsNode != null && encodeNodeVideoExtraParamsNode.hasChildNodes())
					{
						String videoExtraParamsExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Parameters/Parameter";
						NodeList videoParams = (NodeList) xpath.compile(videoExtraParamsExpression).evaluate(document, XPathConstants.NODESET);
						ArrayList<IParameter> extras = new ArrayList<IParameter>();
						
						for(int j=0;j<videoParams.getLength();j++)
						{
							String videoParamsKeyExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Parameters/Parameter["+(j+1)+"]/Key";
							String videoParamsKey = xpath.compile(videoParamsKeyExpression).evaluate(document);
							
							String videoParamsValueExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Video/Parameters/Parameter["+(j+1)+"]/Value";
							String videoParamsValue = xpath.compile(videoParamsValueExpression).evaluate(document);
							
							IParameter param = new Parameter(videoParamsKey, videoParamsValue);
							extras.add(param);
						}
						
						video.setExtraParams(extras);
					}
				}
				catch(Exception e)
				{
					logger.info("Error in extra video params {"+e.getMessage()+"}");
					video.setExtraParams(new ArrayList<IParameter>());
				}
				
				
				
				/******** Extra video properties ***********/
				try
				{
					String videoPropertiesExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Properties/Property";
					NodeList videoPropertiesNodes = (NodeList) xpath.compile(videoPropertiesExpression).evaluate(document, XPathConstants.NODESET);
					ArrayList<IProperty> videoProperties = new ArrayList<IProperty>(); 
					for(int l=0;l<videoPropertiesNodes.getLength();l++){
					Node n = videoPropertiesNodes.item(l);
					String flag = n.getFirstChild().getNodeValue();
					videoProperties.add(new Property(flag));
					}
					
					video.setExtraProperties(videoProperties);
				}
				catch(Exception e)
				{
					logger.info("Error in extra audio properties {"+e.getMessage()+"}");
					video.setExtraProperties(new ArrayList<IProperty>());
				}

				
				/******************** Final validation of video object *****************************/
				try
				{
					Set<ConstraintViolation <IVideo>> constraintViolations = validator.validate(video);  
					
					//@ Validating VideoBitrate object
					if(constraintViolations.size()>0){
					 for(ConstraintViolation<IVideo> constraintViolation : constraintViolations){  
					  throw new Exception(constraintViolation.getMessage());
					 }}
					
					video.setEnabled(true);
				}
				catch(Exception e)
				{
					logger.info("Video Config Error : " + e.getMessage()+". Disabling video processing for this {Encode} " + encode.getName() + " configuration.");
					video.setEnabled(false);
				}
				
				/**** store video configuration into encode object *****/
				encode.setVideoConfig(video);
				
				
				
				
				
				/******************* Audio codec information *************************************
				 * ******************************************************************************/
				IAudio audio = new Audio();
				
				try
				{
					String encodeNodeAudioCodecExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Codec";
					String encodeNodeAudioCodec = xpath.compile(encodeNodeAudioCodecExpression).evaluate(document);
					
					ICodec acodec = new AudioCodec(encodeNodeAudioCodec);
					audio.setCodec(acodec);
				}
				catch(Exception e)
				{
					throw new TranscodeConfigurationException("Invalid audio codec specified");
				}
				
				
				try
				{
					String encodeNodeAudioCodecImplementationExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Implementation";
					String encodeNodeAudioCodecImplementation = xpath.compile(encodeNodeAudioCodecImplementationExpression).evaluate(document);
					
					ICodecImplementation codecImpl = new CodecImplementation(encodeNodeAudioCodecImplementation);
					audio.setImplementation(codecImpl);
				}
				catch(Exception e)
				{
					throw new TranscodeConfigurationException("Invalid audio codec specified");
				}
				
				
				
				try
				{
					String encodeNodeAudioBitrateExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Bitrate";
					String encodeNodeAudioBitrate = xpath.compile(encodeNodeAudioBitrateExpression).evaluate(document);
					if(encodeNodeAudioBitrate != null && Integer.parseInt(encodeNodeAudioBitrate)>0)
					{
						IAudioBitrate ab = new AudioBitrate(Integer.parseInt(encodeNodeAudioBitrate));
						audio.setBitrate(ab);
					}
					else 
					{
						audio.setBitrate(new AudioBitrate(true));
					}
				}
				catch(Exception e)
				{
					logger.info("Improper audio bitrate. {"+e.getMessage()+"} Following source");
					audio.setBitrate(new AudioBitrate(true));
				}
				
				
				try
				{
					String encodeNodeAudioSamplerateExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/SampleRate";
					String encodeNodeAudioSampleRate = xpath.compile(encodeNodeAudioSamplerateExpression).evaluate(document);
					if(encodeNodeAudioSampleRate != null && Integer.parseInt(encodeNodeAudioSampleRate)>0)
					{
						IAudioSampleRate ar = new AudioSampleRate(Integer.parseInt(encodeNodeAudioSampleRate));
						audio.setSamplerate(ar);
					}
					else
					{
						audio.setSamplerate(new AudioSampleRate(true));
					}
				}
				catch(Exception e)
				{
					logger.info("Improper audio sample rate. {"+e.getMessage()+"} Following source");
					audio.setSamplerate(new AudioSampleRate(true));
				}
				
				
				
				try
				{
					String encodeNodeAudioChannelTypeExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Channels";
					String encodeNodeAudioChannelType = xpath.compile(encodeNodeAudioChannelTypeExpression).evaluate(document);
					if(encodeNodeAudioChannelType != null)
					{
						IAudioChannel channel = new AudioChannel(encodeNodeAudioChannelType);
						audio.setChannel(channel);
					}
					else
					{
						audio.setChannel(new AudioChannel(true));
					}
				}
				catch(Exception e)
				{
					logger.info("Audio channel error :"+e.getMessage()+" Following source");
					audio.setChannel(new AudioChannel(true));
				}
				
				
				/******** Extra audio flags ***********/
				
				try
				{
					String encodeNodeAudioExtraParamsExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Parameters";
					Node encodeNodeAudioExtraParamsNode = (Node) xpath.compile(encodeNodeAudioExtraParamsExpression).evaluate(document, XPathConstants.NODE);
					
					if(encodeNodeAudioExtraParamsNode != null && encodeNodeAudioExtraParamsNode.hasChildNodes())
					{
						String audioExtraParamsExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Parameters/Parameter";
						NodeList audioParams = (NodeList) xpath.compile(audioExtraParamsExpression).evaluate(document, XPathConstants.NODESET);
						
						ArrayList<IParameter> extras = new ArrayList<IParameter>(); 
						for(int j=0;j<audioParams.getLength();j++)
						{
							String audioParamsKeyExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Parameters/Parameter["+(j+1)+"]/Key";
							String audioParamsKey = xpath.compile(audioParamsKeyExpression).evaluate(document);
							
							String audioParamsValueExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Parameters/Parameter["+(j+1)+"]/Value";
							String audioParamsValue = xpath.compile(audioParamsValueExpression).evaluate(document);
							
							IParameter param = new Parameter(audioParamsKey, audioParamsValue);
							extras.add(param);
						}
						audio.setExtraParams(extras);
					}
				}
				catch(Exception e)
				{
					logger.info("Error in extra audio params {"+e.getMessage()+"}");
					audio.setExtraParams(new ArrayList<IParameter>());
				}
				
				
				
				/******** Extra audio properties ***********/
				try
				{
					String audioPropertiesExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Audio/Properties/Property";
					NodeList audioPropertiesNodes = (NodeList) xpath.compile(audioPropertiesExpression).evaluate(document, XPathConstants.NODESET);
					ArrayList<IProperty> audioProperties = new ArrayList<IProperty>(); 
					for(int l=0;l<audioPropertiesNodes.getLength();l++){
					Node n = audioPropertiesNodes.item(l);
					String flag = n.getFirstChild().getNodeValue();
					audioProperties.add(new Property(flag));
					}
					
					audio.setExtraProperties(audioProperties);
				}
				catch(Exception e)
				{
					logger.info("Error in extra audio properties {"+e.getMessage()+"}");
					audio.setExtraProperties(new ArrayList<IProperty>());
				}
				
				
				/*** all ok with audio configuration */
				audio.setEnabled(true);
				
				/**** store audio configuration into encode object *****/
				encode.setAudioConfig(audio);
				
				
				
				/*************************************************
				 * **********look for output Properties***********
				 ************************************************/
				ITranscodeOutput encodeOutput = new TranscodeOutput();
				
				
				try
				{
					String encodeNodeOutputNameExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Output/StreamName";
					String encodeNodeOutputName = xpath.compile(encodeNodeOutputNameExpression).evaluate(document);
					
					String encodeNodeOutputContainerExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Output/Container";
					String encodeNodeOutputContainer = xpath.compile(encodeNodeOutputContainerExpression).evaluate(document);
					
					ITranscoderResource output = new TranscoderOutputResource(new MediaDestination(encodeNodeOutputName, true));
					//IOUtils.IdentifyOutput(output);
					
					if(!encodeNodeOutputContainer.equals("") && encodeNodeOutputContainer.length()>=3)
					output.setContainer(new Container(Format.valueOf(encodeNodeOutputContainer.toUpperCase())));
					 
					encodeOutput.setMediaOutput(output);
				}
				catch(Exception e)
				{
					throw new TranscodeConfigurationException("Invalid output specified "+e.getMessage());
				}
				
				
				
				try
				{
					String outputFlagsExpression = "/Template/Transcode/Encodes/Encode["+(i+1)+"]/Output/Properties/Property";
					NodeList outputflagNodes = (NodeList) xpath.compile(outputFlagsExpression).evaluate(document, XPathConstants.NODESET);
					ArrayList<IProperty> outputflags = new ArrayList<IProperty>(); 
					for(int l=0;l<outputflagNodes.getLength();l++){
					Node n = outputflagNodes.item(l);
					String flag = n.getFirstChild().getNodeValue();
					outputflags.add(new Property(flag));
					}
					
					encodeOutput.setOutputProperties(outputflags);
				}
				catch(Exception e)
				{
					logger.info("Error in extra output properties {"+e.getMessage()+"}");
					encodeOutput.setOutputProperties(new ArrayList<IProperty>());
				}		
				
				/***** store output data *****/
				encode.setOutput(encodeOutput);
				
				
				/*******************************************
				 **** Add encode object to encodeslist ****/
				encodeList.addEncode(encode);
			}
			
			/**** add encodes list to transcode session object ***/
			
			session = Transcode.Builder.newTranscode()
					.withLabel(name)
					.withDescription(description)
					.usingEncodes(encodeList)
					.asValid(true)
					.build();
		}
		catch(TranscodeConfigurationException | IllegalArgumentException | SAXException | IOException | ParserConfigurationException | XPathExpressionException ee)
		{
			session.setEnabled(false);
			logger.error("Error parsing template " + ee.getMessage());
		}
		finally
		{
			builderFactory = null;
			builder = null;
			xpath = null;
			document = null;
		}
		
		return session;
	}

	public String getTemplate() {
		return this.templatePath;
	}

	public void setTemplate(String templatePath) {
		this.templatePath = templatePath;
	}

	@Override
	public ITranscode getTranscodeConfig() {
		// TODO Auto-generated method stub
		return this.readTemplate();
	}
	 

}
