package com.dynatrace.siteconverter.model;

import java.util.ArrayList;
import java.util.List;

public class Site {

	String type="";
	String name="";
	String comment="";
	String subtype="";
	boolean isUDL = false;
	boolean isWAN = false;
	long dnsTimeStamp = 0;
	String dns ="";
	String aux ="";
	List<Domains> domains = new ArrayList<Domains>();
	boolean readOnly = false;
	
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public boolean isUDL() {
		return isUDL;
	}
	public void setUDL(boolean isUDL) {
		this.isUDL = isUDL;
	}
	public boolean isWAN() {
		return isWAN;
	}
	public void setWAN(boolean isWAN) {
		this.isWAN = isWAN;
	}
	public long getDnsTimeStamp() {
		return dnsTimeStamp;
	}
	public void setDnsTimeStamp(long dnsTimeStamp) {
		this.dnsTimeStamp = dnsTimeStamp;
	}
	public String getDns() {
		return dns;
	}
	public void setDns(String dns) {
		this.dns = dns;
	}
	public String getAux() {
		return aux;
	}
	public void setAux(String aux) {
		this.aux = aux;
	}
	public List<Domains> getDomains() {
		return domains;
	}
	public void setDomains(List<Domains> domains) {
		this.domains = domains;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	
}
