package com.dynatrace.siteconverter.driver;

import com.dynatrace.siteconverter.converter.DcrumToAppMon;

public class Driver {

	public static void main(String[] args) {
		DcrumToAppMon toAppMon = new DcrumToAppMon();
		toAppMon.parser();
	}

}
