package com.ingester.loaders;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public final class ReferenceDataSingleton implements Iterable<String>, Serializable {

	private static final long serialVersionUID = 5458295846058786100L;
	private static volatile ReferenceDataSingleton referenceDataInstance = null;
	private static HashMap<String, String> referenceDataMap = new HashMap(); 

	private ReferenceDataSingleton() {
		referenceDataMap = StaticReferenceDataLoader.getReferenceDataMap();
    }

    public HashMap<String, String> getReferenceDataMap() {
		return referenceDataMap;
	}

    public static ReferenceDataSingleton getInstance() {
        if (referenceDataInstance == null) {
            synchronized(ReferenceDataSingleton.class) {
                if (referenceDataInstance == null) {
                    referenceDataInstance = new ReferenceDataSingleton();
                }
            }
        }

        return referenceDataInstance;
    }

	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
    
}