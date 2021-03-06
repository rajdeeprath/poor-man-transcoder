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


[![Overview](https://github.com/rajdeeprath/poor-man-transcoder/blob/master/images/transcoder-service%20-%20small.png "Verifying")](https://github.com/rajdeeprath/poor-man-transcoder/blob/master/images/transcoder-service.png)


## Libraries (Dependencies) used

1. slf4j-simple - Logging framework
2. commons-exec - command line executior
3. commons-chain - command chaining utility
4. validation-api - Custom validations
5. hibernate-validator - Custom validations
6. httpclient
7. commons-io


## Usage

Setup:

1. Make sure you have latest ffmpeg installed on your system with librtmp, libx264 and other necessary libraries compiled in.

2. From the project copy the transcoder/templates folder to a location on your media server (ie: {red5-home}/transcoder/templates/)

3. You can test the codebase from eclipse by creating a simple java project or implementing the code given below in a red5/wowza live type application. ~~If you decide to try this code inside a rtmp application (NOT RECOMMENDED!!) make sure to initialize the facade in appStart and make the transcoder request in the publish callback method of the rtmp server. [ Since the project is in early stages i will recommend testing it from a separate java application and not from within your rtmp server application code. ]~~
4. You can use the GenericTranscoderFacade for running the code from a simple java app or Red5TranscoderFacade to run the code from within a red5 app.
5. Use the red5 sample app as a reference point for red5 integration:
**[https://github.com/rajdeeprath/red5-server/tree/master/transcoderLive](https://github.com/rajdeeprath/red5-server/tree/master/transcoderLive)**

Red5 sample concept code:
```
https://github.com/rajdeeprath/red5-server/blob/master/transcoderlive/src/org/red5/core/Application.java
```


First time bootsrap (onAppStart / onServerStart):
```
/* Boot strap */
ITranscoderFacade facade = GenericTranscoderFacade.getInstance();
facade.setFFmpegPath("/home/rajdeeprath/bin/ffmpeg");
facade.setHomeDirectory("/home/rajdeeprath/red5-server/");
facade.setWorkingDirectory("/home/rajdeeprath/red5-server/webapps/");
facade.setTemplateDirectory("/home/rajdeeprath/red5-server/transcoder/templates/");
facade.setOperatingMediaServer("red5");
facade.init();
```


Form a transcode request object::
```
/* Transcode request Object - these params override the global params set in bootsrap */
TranscodeRequest request = new TranscodeRequest();
request.setWorkingDirectory("/home/rajdeeprath/red5-server/webapps/live/streams/hls/");
request.setTemplateFileName("hls-template.xml");
request.setCleanUpSegmentsOnExit(true);
```


To transcode your stream::
```
/* fire request */
ArrayList<IProperty> inputflags = new ArrayList<IProperty>(Arrays.asList(new Property("-re")));
facade.doTranscode(new RTMPTranscoderResource(new StreamMedia("rtmp://localhost/live/test"),inputflags), request);
```

OR


For low latency try : (This command aims to reduce the overall FFMPEG transcoding start-up time)

```
/* fire request */
ArrayList<IProperty> inputflags = new ArrayList<IProperty>(Arrays.asList(new Property("-probesize"), new Property("32"), new Property("-analyzeduration"), new Property("1000000")));
facade.doTranscode(new RTMPTranscoderResource(new StreamMedia("rtmp://localhost/live/test"),inputflags), request);
```

**(Where "test" is the stream name and rtmp://localhost/live is the application where the stream is being published)**


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
						<StreamName>rtmp://localhost/oflaDemo/mp4:${SourceStreamName}</StreamName>
						
						<!-- mp4, flv etc.. -->
						<Container></Container>
						
						<Parameters>
							<!--<Parameter>
								<Key></Key>
								<Value></Value>
							</Parameter>-->
						</Parameters>
					
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


###### 07 - 08 - 2015

+ HLS folder generation
+ HLS folder auto cleanup
+ Multi bitrate HLS
+ Live image snapshot extraction
+ Major Bug fixes


TO DO:

##### Updates to do (Lots of work)

1. Casual Testing
2. Unit testting
3. Performance testing
4. Integrated testing
5. Java docs
6. Red5 plugin development
7. Wowza plugin develpment
8. ~~HLS enhancements~~
9. User defined RTMP url format interpret strategy
10. Easy overlays
11. video cropping
12. Auto SMIL generation
13. ~~HLS cleanup~~
14. Secure url params based url interpret strategy
15. ~~Live image snapshot extraction~~



## Special Thanks

To: Mr. Chris Allen, Dominick Accattato and the entire infrared5 team for inspiration.



## Building the project & deploying on Media server


This is a maven project, so you need to install maven on your computer.


A. **Install Maven**

[https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)


***

B. **Build process** (using maven)
```
$ mvn clean
```

```
$ mvn compile
```

```
$ mvn package
```

***

C. **Deployment on media server (Java based servers only)**

* The output will be a jar file:  **ffmpeg-transcoder.jar**
* Copy the jar file and other jars from maven project dependency-jars folder into media server's lib folder.
* Copy the **transcoder** folder to appropriate location on your server. **Example: C://red5/transcoder/**
* Restart your media server

***

D. **Getting transcoder working**

* Checkout and Compile the red5 application [TranscoderLive](https://github.com/rajdeeprath/red5-server/tree/master/transcoderLive)
* Edit TranscoderLive/WEB-INF/red5-web.properties file and confgure required paths

```
# Path to ffmpeg executable
webapp.ffmpegPath=C:\\ffmpeg\\bin\\ffmpeg.exe
#  Transcoder directory should be located at server home ie: red5/
webapp.templateDirectory=transcoder/templates
# file name of the template to be used
webapp.transcoderTemplate=multibitrate-rtmp-template.xml
# working directory can be located anywhere. This can be overridden with TranscodeRequest object 
webapp.workingDirectory=webapps
```

**Save the file and restart media server**

* Ensure that you have the red5 oflaDemo application deployed as well. This is used as a transit point for rtmp single / multiple bitrate transcoding.

* Launch Adobe flash media live encoder and connect to **rtmp://{host}/transcoderLive**
* Set stream name to **test**
* **Start publishing**

***

E. **Output**

Output will appear according to the template selected.
* For HLS templates single bitrate or multipel bitrate, the playback should be available at 
``` http://{host}:5080/transcoderLive/streams/hls/{output_name} ```
***
* For RTMP single / multiple bitrate transcoding the playback is available at 
``` rtmp://{host}/oflaDemo/{output_name} ```
***
* For record to file transcoding the recorded file(s) can be found inside the configured workingDirectory
***
* For live image extraction the extracted sequence can be found inside the configured workingDirectory


**Note: {output_name} is the output name set in the template's output section for a encode configuration**

## Detailed Guide on setup, api and templates

Detailed document on understanding the library and making the best out of it :
[https://goo.gl/YW0Vje](https://goo.gl/YW0Vje) => **(Important!!)**
