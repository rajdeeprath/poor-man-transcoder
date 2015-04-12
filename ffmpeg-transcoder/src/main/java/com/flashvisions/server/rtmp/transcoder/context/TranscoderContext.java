package com.flashvisions.server.rtmp.transcoder.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;

import com.flashvisions.server.rtmp.transcoder.pool.TranscodeSessionPool;

public class TranscoderContext extends ContextBase implements Context {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2155230520667339379L;
	private HashMap<Object, Object> map;
	
	private String ffmpegPath;
	private String homeDirectoryPath;
	private String workingDirectoryPath;
	private String templateDirectoryPath;
	private String server;
	
	private List<String> libraries;
	private String ffmpegVersion;
	
	private TranscodeSessionPool pool;
	
	
	public void setFFmpegPath(String ffmpegPath) {
		// TODO Auto-generated method stub
		this.ffmpegPath = ffmpegPath;
	}

	public  String getFFmpegPath() {
		// TODO Auto-generated method stub
		return ffmpegPath;
	}

	public void setOperatingMediaServer(String serverName) {
		// TODO Auto-generated method stub
		this.server = serverName;
	}

	public String getOperatingMediaServer() {
		// TODO Auto-generated method stub
		return server;
	}

	public void setWorkingDirectory(String workingDirectoryPath) {
		// TODO Auto-generated method stub
		this.workingDirectoryPath = workingDirectoryPath;
	}

	public  String getWorkingDirectory() {
		// TODO Auto-generated method stub
		return workingDirectoryPath;
	}
	
	public void setTemplateDirectory(String templateDirectoryPath) {
		// TODO Auto-generated method stub
		this.templateDirectoryPath = templateDirectoryPath;
	}

	public String getTemplateDirectory() {
		// TODO Auto-generated method stub
		return templateDirectoryPath;
	}

	public void setHomeDirectory(String homeDirectoryPath) {
		// TODO Auto-generated method stub
		this.homeDirectoryPath = homeDirectoryPath;
	}

	public String getHomeDirectory() {
		// TODO Auto-generated method stub
		return homeDirectoryPath;
	}
	
	public TranscoderContext()
	{
		this.map = new HashMap<Object, Object>();
	}
	
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.map.clear();
	}

	@Override
	public boolean containsKey(Object arg0) {
		// TODO Auto-generated method stub
		return this.map.containsKey(arg0);
	}

	@Override
	public boolean containsValue(Object arg0) {
		// TODO Auto-generated method stub
		return this.map.containsValue(arg0);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Set entrySet() {
		// TODO Auto-generated method stub
		return this.map.entrySet();
	}

	@Override
	public Object get(Object arg0) {
		// TODO Auto-generated method stub
		return this.map.get(arg0);
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.map.isEmpty();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Set keySet() {
		// TODO Auto-generated method stub
		return this.map.keySet();
	}

	@Override
	public Object put(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return this.map.put(arg0,  arg1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void putAll(@SuppressWarnings("rawtypes") Map arg0) {
		// TODO Auto-generated method stub
		this.map.putAll(arg0);
	}

	@Override
	public Object remove(Object arg0) {
		// TODO Auto-generated method stub
		return this.map.remove(arg0);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.map.size();
	}

	@Override
	public Collection<?> values() {
		// TODO Auto-generated method stub
		return this.map.values();
	}

	public TranscodeSessionPool getPool() {
		return pool;
	}

	public void setPool(TranscodeSessionPool pool) {
		this.pool = pool;
	}

	public List<String> getSupportedLibraries() {
		return libraries;
	}

	public void setSupportedLibraries(List<String> libraries) {
		this.libraries = libraries;
	}

	public String getFfmpegVersion() {
		return ffmpegVersion;
	}

	public void setFfmpegVersion(String ffmpegVersion) {
		this.ffmpegVersion = ffmpegVersion;
	}

}
