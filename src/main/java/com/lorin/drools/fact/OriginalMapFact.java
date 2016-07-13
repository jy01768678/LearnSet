package com.lorin.drools.fact;


import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class OriginalMapFact implements Serializable{

	private String name;
	private Map<String,String> value;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String,String> getValue() {
		return value;
	}

	public void setValue(Map<String,String> value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
