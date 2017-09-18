package com.dynatrace.siteconverter.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.dynatrace.siteconverter.model.Area;
import com.dynatrace.siteconverter.model.Domains;
import com.dynatrace.siteconverter.model.Region;
import com.dynatrace.siteconverter.model.Site;

/**
 * 
 * @author U2QM
 *
 */
public class DcrumToAppMon {
	
	private final String filename = "sites.json";	
	public List<Region> region_list = new ArrayList<Region>();
	private final String country = "United States";
	private final String region_name = "Texas";
	
	/**
	 * Parse the JSON File and convert it into POJO
	 */
	public void parser() {
		JSONParser parser = new JSONParser();

		try {

			System.out.println("Opening file...");
			Object obj = parser.parse(new FileReader(filename));

			JSONArray jsonArray = (JSONArray) obj;

			System.out.println("Reading file and converting JSON Objects to JAVA Objects...");
			// loop through region list
			for (Object regionObject : jsonArray) {

				// if the object is not null
				if (regionObject != null) {
					// create a new region object
					Region regObj = new Region();

					// convert to json object and read it.
					JSONObject region = (JSONObject) regionObject;
					regObj.setType(region.get("type").toString());
					regObj.setName(region.get("name").toString());
					regObj.setReadOnly((Boolean) (region.get("read-only")));
					regObj.setAreas(parseAreas((JSONArray) region.get("areas")));

					// add region object to the list of regions.
					region_list.add(regObj);

					System.out.println("Region: "+regObj.getName()+" - parsed ...");
				}

			}
			
			// convert to AppMon format
			convertToAppMon(region_list);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Convert ip site data to AppMon format.
	 * @param region_list
	 * @throws FileNotFoundException 
	 */
	private void convertToAppMon(List<Region> region_list) throws FileNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("Begin conversion to AppMon format ...");
		PrintWriter pw = new PrintWriter(new File("mappings.csv"));
        StringBuilder sb = new StringBuilder();
		
		for (Region region : region_list){
			for (Area area : region.getAreas()){
				for (Site site : area.getSites()){
					for (Domains domain : site.getDomains()){
						sb.append(domain.getFirst());
						sb.append(",");
						sb.append(calculateCidr(domain));
						sb.append(",");
						sb.append(country);
						sb.append(",");
						sb.append(region_name);
						sb.append(",");
						sb.append(site.getName());
						sb.append('\n'); 
						System.out.println(sb.toString());
					}
				}
			}
		}
		
		// write to file
		pw.write(sb.toString());
		pw.close();
		System.out.println("Writing to CSV complete...");
		
	}

	/**
	 * Calculate CIDR for the passed IPs
	 * @param domain
	 * @return
	 */
	private String calculateCidr(Domains domain) {
		String cidr = "";
		String first_4 = domain.getFirst();
		String last_4 = domain.getLast();
		String first_3 = first_4.substring(0, first_4.lastIndexOf(".")); 
		String last_3 = last_4.substring(0, last_4.lastIndexOf("."));
		String first_2 = first_3.substring(0, first_3.lastIndexOf(".")); 
		String last_2 = last_3.substring(0, last_3.lastIndexOf("."));
		String first_1 = first_2.substring(0, first_2.lastIndexOf(".")); 
		String last_1 = last_2.substring(0, last_2.lastIndexOf("."));
		
		if(first_4.equals(last_4)){
			// first and last are same
			cidr = "32";
		}else if (first_3.equals(last_3)){
			// digits after the last "." are different example: 10.10.10.0 to 10.10.10.255
			cidr = "24";
		}else if (first_2.equals(last_2)){ 
			// First two terms are equal example: 10.10.0.0 to 10.10.255.255
			cidr = "16";
		}else if (first_1.equals(last_1)){
			// Only the first term is equal example 10.0.0.0 to 10.255.255.255
			cidr = "8";
		}else{
			System.out.println("unknown cidr...");
		}
		return cidr;
	}

	/**
	 * Parse JSON Array and return list of areas.
	 * @param jsonArray
	 * @return
	 */
	private List<Area> parseAreas(JSONArray areasArray) {
		List<Area> area_list = new ArrayList<Area>();
		
		// loop through list of areas
		for (Object obj : areasArray){
			if (obj != null){
				JSONObject area = (JSONObject) obj;
				Area areaObj = new Area();
				areaObj.setType(area.get("type").toString());
				areaObj.setName(area.get("name").toString());
				areaObj.setReadOnly((Boolean) area.get("read-only"));
				areaObj.setSites(parseSites((JSONArray) area.get("sites")));
				
				area_list.add(areaObj);
			}
		}
		
		return area_list;
	}

	/**
	 * Parse JSON Array and return list of sites.
	 * @param jsonArray
	 * @return
	 */
	private List<Site> parseSites(JSONArray sitesArray) {
		List<Site> site_list = new ArrayList<Site>();
		
		for (Object obj : sitesArray){
			if(obj != null){
				JSONObject site = (JSONObject) obj;
				Site siteObj = new Site();
				siteObj.setAux((String) site.get("aux"));
				siteObj.setComment((String) site.get("comment"));
				siteObj.setDns((String) site.get("dns"));
				siteObj.setDnsTimeStamp((Long) site.get("dnsTimestamp"));
				siteObj.setName((String) site.get("name"));
				siteObj.setReadOnly((Boolean) site.get("read-only"));
				siteObj.setSubtype((String) site.get("subtype"));
				siteObj.setType((String) site.get("type"));
				siteObj.setUDL((Boolean) site.get("isUDL"));
				siteObj.setWAN((Boolean) site.get("isWAN"));
				siteObj.setDomains(parseDomain((JSONArray) site.get("domains")));
				
				site_list.add(siteObj);
			}
		}
		
		return site_list;
	}

	/**
	 *  Parse JSON Array and return list of domain.
	 * @param jsonArray
	 * @return
	 */
	private List<Domains> parseDomain(JSONArray domainArray) {
		List<Domains> domains_list = new ArrayList<Domains>();
		 
		for (Object obj : domainArray){
			if (obj != null){
				JSONObject domain = (JSONObject) obj;
				Domains domainObj = new Domains();
				domainObj.setFirst(domain.get("first").toString());
				domainObj.setLast(domain.get("last").toString());
				
				domains_list.add(domainObj);
			}
		}
		return domains_list;
	}
	
}
