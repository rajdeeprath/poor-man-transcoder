package com.flashvisions.server.rtmp.transcoder.pojo;

import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Format;

public class Container implements IContainer {
	
	private Format type;
	

	public Container(Format type){
		this.setType(type);
	}

	@Override
	public Format getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public void setType(Format type) {
		// TODO Auto-generated method stub
		this.type = type;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.type).toLowerCase();
	}
	
	
}
