package com.ingester.transformers;

import java.util.HashMap;

import com.ingester.beans.LocIncoming;
import com.ingester.loaders.LocReferenceDataLoader;

public class LocTransformer {
	private LocIncoming locIncoming;
	private String incomingCity;
	private String transformedCity;

	public LocIncoming getLocIncoming() {
		return locIncoming;
	}
	public void setLocIncoming(LocIncoming locIncoming) {
		this.locIncoming = locIncoming;
	}

	public String getIncomingCity() {
		return incomingCity;
	}
	public void setIncomingCity(String incomingCity) {
		this.incomingCity = incomingCity;
	}
	public String getTranformedCity() {
		return transformedCity;
	}
	public void setTranformedCity(String transformedCity) {
		this.transformedCity = transformedCity;
	}
	public LocTransformer(LocIncoming locIncoming) {
		this.setLocIncoming(locIncoming);
		this.setIncomingCity(locIncoming.getU_CITY());
		this.setTranformedCity("Seattle");
		System.out.println("LocTransformer Constructor Input: " + locIncoming.toString());
		HashMap<String, String> locRef= LocReferenceDataLoader.getLocReference();
		System.out.println("LocTransformer LocReferenceData:");
		locRef.entrySet().forEach(entry->{
			System.out.println(entry.getKey() + " " + entry.getValue());  
		});
		String season = locRef.get("SPRING");
		this.getLocIncoming().setU_CITY(season);

	}

	public LocIncoming calcPay() {
		System.out.println("LocTransformer calcPay invoked");
		Double pay = 2000.0;
		String loc = this.locIncoming.getLOC();
		boolean isNumeric = loc.chars().allMatch( Character::isDigit );
		if (isNumeric) {
			pay = pay + Integer.valueOf(loc);
		}
		System.out.println("LocTransformer calcPay: " + pay + " loc: " + loc);
		this.getLocIncoming().setU_DISTRICTDESCRIPTION(pay.toString());
		this.getLocIncoming().setU_DISTRICTID("C-ID");
		System.out.println("LocTransformer calcPay is returning:" + this.getLocIncoming());
		return this.getLocIncoming();

	}

}
