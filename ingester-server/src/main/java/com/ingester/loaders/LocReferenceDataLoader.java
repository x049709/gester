package com.ingester.loaders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ingester.beans.LocReferenceData;
import com.ingester.beans.LocReferenceDataArray;

public class LocReferenceDataLoader {
	private static HashMap<String, String> LocReference = new HashMap(); 
	
	public LocReferenceDataLoader() {
		System.out.println("LocReferenceDataLoader default constructor done");
	}	
	public static void LoadLocReferenceData() {
		System.out.println("LoadLocReferenceData static method");
	}	
	public static void LoadLocReferenceDataArrayList(ArrayList locReferenceDataArrayListIn) {
		//System.out.println("LoadLocReferenceData static method with input:" + locReferenceDataArrayListIn);
        for(Object row : locReferenceDataArrayListIn) {
    		String hMapKey = null;
    		String hMapValue = null;
    		Map<String, String> m = (Map<String,String>)row;
        	for (Map.Entry entry : m.entrySet()) {
        		if (entry.getKey().equals("REF_DATA_CLASS_KEY")) {
            			hMapKey = (String)entry.getValue();
            			//System.out.println(entry.getKey() + ", " + hMapKey);
       		}
        		if (entry.getKey().equals("REF_DATA_CLASS_VALUE")) {
            			hMapValue = (String)entry.getValue();
               			//System.out.println(entry.getKey() + ", " + hMapValue);
        		}
        	}
    		if (hMapKey != null & hMapValue != null) {
    			LocReference.put(hMapKey, hMapValue);
    		}
        }
		System.out.println("LoadLocReferenceData Map Loaded:" + LocReference);
	}
        
	public Double calcPay() {
		Double pay = 0.0;
		//if (getLocIncoming().getLOC().equalsIgnoreCase("01")) pay = 200.00;
		//if (getLocIncoming().getLOC().equalsIgnoreCase("02")) pay = 300.00;
		return pay;
		
	}
	public static HashMap<String, String> getLocReference() {
		return LocReference;
	}

}
