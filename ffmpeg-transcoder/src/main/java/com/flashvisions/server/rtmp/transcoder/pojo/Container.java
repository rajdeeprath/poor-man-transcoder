package com.flashvisions.server.rtmp.transcoder.pojo;

import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.ContainerType;

public class Container implements IContainer {
	
	private ContainerType type;
	

	public Container(ContainerType type){
		this.setType(type);
	}

	@Override
	public ContainerType getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public void setType(ContainerType type) {
		// TODO Auto-generated method stub
		this.type = type;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.type).toLowerCase();
	}
	
	
}
