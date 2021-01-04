package com.ingester.loaders;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import com.ingester.beans.MappingSheet;

public final class MappingSheetSingleton implements Iterable<MappingSheet>, Serializable {

 	private static final long serialVersionUID = 2615746884227658816L;
	private static volatile MappingSheetSingleton mappingSheetInstance = null;
	private HashMap<String, MappingSheet> mappingSheetDataMapUsingGenericColumn = new HashMap(); 
	private HashMap<String, MappingSheet> mappingSheetDataMapUsingSourceColumn = new HashMap(); 

	private MappingSheetSingleton() {
		mappingSheetDataMapUsingGenericColumn = StaticMappingSheetLoader.getMappingSheetDataMapUsingGenericColumn();
		mappingSheetDataMapUsingSourceColumn = StaticMappingSheetLoader.getMappingSheetDataMapUsingSourceColumn();
    }

    public HashMap<String, MappingSheet> getMappingSheetDataMapUsingGenericColumn() {
		return mappingSheetDataMapUsingGenericColumn;
	}

    public HashMap<String, MappingSheet> getMappingSheetDataMapUsingSourceColumn() {
		return mappingSheetDataMapUsingSourceColumn;
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