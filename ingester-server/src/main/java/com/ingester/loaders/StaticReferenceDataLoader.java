package com.ingester.loaders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ingester.beans.ReferenceData;

public class StaticReferenceDataLoader {
	private static HashMap<String, String> referenceDataMap = new HashMap(); 
	
	public static HashMap<String, String> getReferenceDataMap() {
		return referenceDataMap;
	}

	public static void loadReferenceDataArrayList(ArrayList referenceDataArrayListIn) {
		//System.out.println("loadReferenceDataArrayList static method with input:" + referenceDataArrayListIn);
        for(Object row : referenceDataArrayListIn) {
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
    			referenceDataMap.put(hMapKey, hMapValue);
    		}
        }
		System.out.println("Reference data loaded:" + referenceDataMap);
	}
        
}
