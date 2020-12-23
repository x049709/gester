package com.ingester.loaders;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import com.ingester.beans.MappingSheet;

public final class MappingSheetSingleton implements Iterable<MappingSheet>, Serializable {

 	private static final long serialVersionUID = 2615746884227658816L;
	private static volatile MappingSheetSingleton mappingSheetInstance = null;
	private HashMap<String, MappingSheet> mappingSheetDataMap = new HashMap(); 

	private MappingSheetSingleton() {
    	mappingSheetDataMap = StaticMappingSheetLoader.getMappingSheetDataMap();
    }

    public HashMap<String, MappingSheet> getMappingSheetDataMap() {
		return mappingSheetDataMap;
	}

    public static MappingSheetSingleton getInstance() {
        if (mappingSheetInstance == null) {
            synchronized(MappingSheetSingleton.class) {
                if (mappingSheetInstance == null) {
                    mappingSheetInstance = new MappingSheetSingleton();
                }
            }
        }

        return mappingSheetInstance;
    }

	@Override
	public Iterator<MappingSheet> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
    
}