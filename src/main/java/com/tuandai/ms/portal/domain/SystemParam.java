package com.tuandai.ms.portal.domain;

import java.io.Serializable;

public class SystemParam implements Serializable{
	
	private int id;
	
	private String code;
	
	private String value;
	
	private String channel;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
	
}
