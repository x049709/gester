package com.ingester.loaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ingester.beans.MappingSheet;

public class StaticMappingSheetLoader {
	private static HashMap<String, MappingSheet> mappingSheetDataMap = new HashMap(); 
	
	public StaticMappingSheetLoader() {
		System.out.println("MappingSheetLoader default constructor done");
	}	
	public static HashMap<String, MappingSheet> getMappingSheetDataMap() {
		return mappingSheetDataMap;
	}
	public static void LoadMappingSheet(ArrayList referenceDataArrayListIn) {
		//System.out.println("loadReferenceDataArrayList static method with input:" + referenceDataArrayListIn);
		mappingSheetDataMap.clear();
        for(Object row : referenceDataArrayListIn) {
    		Map<String, MappingSheet> m = (Map<String,MappingSheet>)row;
    		String hMapKey = null;
    		MappingSheet mSheet = new MappingSheet();
        	for (Map.Entry entry : m.entrySet()) {
        		String hMapValue = null;
        		if (entry.getKey().equals("SOURCE_COL_NAME")) {
        			mSheet.setSOURCE_COL_NAME((String)entry.getValue());
        			//System.out.println(entry.getKey() + ", " + hMapKey);
        		}        		
        		if (entry.getKey().equals("SOURCE_ENTITY")) {
        			hMapValue = (String)entry.getValue();
           			mSheet.setSOURCE_ENTITY((String)entry.getValue());
          			//System.out.println(entry.getKey() + ", " + hMapValue);
        		}
        		if (entry.getKey().equals("GENERIC_COL_NAME")) {
        				hMapKey = (String)entry.getValue();
            			hMapValue = (String)entry.getValue();
               			mSheet.setGENERIC_COL_NAME((String)entry.getValue());
               			//System.out.println(entry.getKey() + ", " + hMapValue);
        		}
        		if (entry.getKey().equals("TARGET_ENTITY")) {
        			hMapValue = (String)entry.getValue();
           			mSheet.setTARGET_ENTITY((String)entry.getValue());
           			//System.out.println(entry.getKey() + ", " + hMapValue);
        		}
        		if (entry.getKey().equals("TARGET_COL_NAME")) {
        			hMapValue = (String)entry.getValue();
           			mSheet.setTARGET_COL_NAME((String)entry.getValue());
          			//System.out.println(entry.getKey() + ", " + hMapValue);
        		}
        		if (entry.getKey().equals("REQUIRED_FLAG")) {
        			hMapValue = (String)entry.getValue();
           			mSheet.setREQUIRED_FLAG((String)entry.getValue());
          			//System.out.println(entry.getKey() + ", " + hMapValue);
        		}
       		if (entry.getKey().equals("INGESTION_RULE")) {
        			hMapValue = (String)entry.getValue();
           			mSheet.setINGESTION_RULE((String)entry.getValue());
           			//System.out.println(entry.getKey() + ", " + hMapValue);
        		}
        	}
    		if (hMapKey != null & mSheet != null) {
    			mappingSheetDataMap.put(hMapKey, mSheet);
    			//System.out.println("Mapping row loaded: KEY:" + hMapKey + ", VALUE:" + mSheet.toString());
    		}
        }
		//System.out.println("Mapping sheet loaded:" + mappingSheetDataMap.toString());
	}

}
