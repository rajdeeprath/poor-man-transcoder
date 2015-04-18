# ffmpeg-transcoder

Transcoder for live streams to be used with red5 / wowza ( java based rtmp servers )



## The Project

ffmpeg transcoder is a maven project towards building a cli wrapper initiative for java based rtmp servers. The purpose of this project is to mimic popular standards and create a natural user friendly external transcoding system, which will open a whole lot of possibilities such as:

1. Multi-bitrate transcoding
2. Multi-bitrate hls transcoding
3. Re-Broadcasting
4. Record stream to file
5. Broadcast file to stream
6. Convert file to file
7. Apply video/audio filters to live streams
8. Apply video/audio filters to recorded file
9. Add logo to live stream
9. Add logo to recorded file

Much more....

The best part of this system is :  "Very Little programming needed to get your transcoding process going"

## Overview


[![Overview](https://raw.githubusercontent.com/rajdeeprath/ffmpeg-transcoder/93b441c7851d5e08ee2158e345ee43d94f80e40b/images/transcoder-service%20-%20small.png "Verifying")](https://raw.githubusercontent.com/rajdeeprath/ffmpeg-transcoder/93b441c7851d5e08ee2158e345ee43d94f80e40b/images/transcoder-service.png)


## Libraries (Dependencies) used

1. slf4j-simple - Logging framework
2. commons-exec - command line executior
3. commons-chain - command chaining utility
4. validation-api - Custom validations
5. hibernate-validator - Custom validations
6. httpclient
7. commons-io


## Usage

First time bootsrap (onAppStart / onServerStart):
```
ITranscoderFacade facade = TranscoderFacade.getInstance();
facade.setFFmpegPath("/path/to/ffmpeg/");
facade.setHomeDirectory("/path-to/server-home-directory/");
facade.setWorkingDirectory("/path-to/working-directory/);
facade.setTemplateDirectory("/path-to/templates/directory/");
facade.setOperatingMediaServer("red5"); // server string => red5 / wowza
facade.init();
```

To transcode your stream::
```
ArrayList<IProperty> inputflags = new ArrayList<IProperty>(Arrays.asList(new Property("-re")));
facade.doTranscode(new RTMPTranscoderResource(new StreamMedia("rtmp://localhost/live/stream"),inputflags), "sample-rtmp-template.xml");
```

## Template Structure

```
<?xml version="1.0" encoding="UTF-8" ?>
<!-- sample transcoder template for java-ffmpeg wrapper -->
<Template version="1">
	<Transcode>
		<Name>Sample rtmp 2 rtmp </Name>
		<Description>Sample to transcode rtmp to rtmp</Description>	
		<Encodes>
			<Encode>
				<Enable>true</Enable>
				<Name>Sample</Name>								
				<!-- must be unique in this sequence of encodes -->
				<Video>
					<!-- Any supported codec, copy, Disable, {codecname} -->
					<Codec>libx264</Codec>
					<!-- very, strict, normal, unofficial, experimental (dont set if you dont know) -->
					<Implementation>normal</Implementation>
					<FrameSize>
						<Width>320</Width>
						<Height>240</Height>
					</FrameSize>
					<FrameRate>24</FrameRate>
					<Bitrate>
						<Average>0</Average><!-- average auto excludes min max buffer and uses ABR enoding-->
						<Min>200</Min><!-- K -->
						<Max>250</Max><!-- K -->
						<Buffer>300</Buffer> <!-- K (device buffer)-->
					</Bitrate> 
					<KeyFrameInterval>
						<Gop>24</Gop>
						<IntervalMin>48</IntervalMin>
					</KeyFrameInterval>
					
					<Parameters>
						<Parameter>
							<Key>profile:v</Key>
							<Value>baseline</Value>
						</Parameter>
					</Parameters>
					
					<Properties>
						<!--<Property></Property>-->
					</Properties>
				</Video>
				
				<Audio>
					<!-- Any supported codec, Copy, Disable, {codecname} -->
					<Codec>aac</Codec>
					<!-- very, strict, normal, unofficial, experimental (dont set if you dont know) -->
					<Implementation>experimental</Implementation>
					<Bitrate>128</Bitrate> <!-- Kbps -->
					<!-- samplerate -->
					<SampleRate>22050</SampleRate>
					<!-- mono, stereo -->
					<Channels>mono</Channels>
					<!-- Extra params such as filters -->
					<Parameters> 
						<!--<Parameter>
							<Key></Key>
							<Value></Value>
						</Parameter>-->
					</Parameters>
					<Properties>
						<!--<Property></Property>-->
					</Properties>
				</Audio>
				
				<!-- Extra params to appear towards the end of command such as format container definition or hls flags -->		
				<Output>
						<StreamName>${SourceApplication}/mp4:${SourceStreamName}</StreamName>
						<!-- mp4, flv etc.. -->
						<Container></Container>
						<Properties>
							<!--<Property></Property>-->
						</Properties>
				</Output>
			</Encode>
		</Encodes>
	</Transcode>
</Template>

```

## Status

####### [ Unstable ]

TO DO:

##### Updates to do (Lots of work)

1. Casual Testing
2. Unit testting
3. Performance testing
4. Integrated testing
5. Java docs
6. Red5 plugin development
7. Wowza plugin develpment
8. HLS enhancements
9. User defined RTMP url format interpret strategy
10. Easy overlays
11. video cropping
12. Auto SMIL generation
13. HLS cleanup
14. Secure url params based url interpret strategy



## Special Thanks

To: Mr. Chris Allen, Dominick Accattato and the entire infrared5 team for inspiration.