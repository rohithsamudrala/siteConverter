package com.dynatrace.siteconverter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author U2QM
 *
 */
public class Area {
	
	String type="";
	String name="";
	boolean readOnly = false;
	List<Site> sites = new ArrayList<Site>();
	
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
	public List<Site> getSites() {
		return sites;
	}
	public void setSites(List<Site> sites) {
		this.sites = sites;
	}
	
	
	
}
