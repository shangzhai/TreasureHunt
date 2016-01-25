package com.example.treasurehunt;

import java.util.ArrayList;
import java.util.List;

public class Station {
	private String name="";
	private String address="";
	private String phone="";
	private List<String> servinglines=new ArrayList<String>();
	private String zone="";
	private List<String> facilitynames=new ArrayList<String>();
	private List<String> facilities=new ArrayList<String>();

	private String coordinates="";
	
	public String getname(){
		return name;
		
	}
	
	public void setname(String name){
		this.name=name;
		
	}
	
	public String getaddress(){
		return address;
		
	}
	
	public void setaddress(String address){
		this.address=address;
		
	}
	
	public String getphone(){
		return phone;
		
	}
	
	public void setphone(String phone){
		this.phone=phone;
		
	}
	
	public List<String> getservinglines(){
		return servinglines;
		
	}
	
	public void setservinglines(String servinglines){
		this.servinglines.add(servinglines);
		
	}
	
	public String getzone(){
		return zone;
		
	}
	
	public void setzone(String zone){
		this.zone=zone;
		
	}
	
	public List<String> getfacilitynames(){
		return facilitynames;
		
	}
	
	public void setfacilitynames(String facilitynames){
		this.facilitynames.add(facilitynames);
		
	}
	
	public List<String> getfacilities(){
		return facilities;
		
	}
	
	public void setfacilities(String facilities){
		this.facilities.add(facilities);
		
	}
	
	
	public String getcoordinates(){
		return coordinates;
		
	}
	
	public void setcoordinates(String coordinates){
		this.coordinates=coordinates;
		
	}

}
