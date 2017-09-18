package com.dynatrace.siteconverter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author U2QM
 *
 */
public class Region {
	
	String type="";
	String name="";
	boolean readOnly = false;
	List<Area> areas = new ArrayList<Area>();
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public List<Area> getAreas() {
		return areas;
	}
	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
}
