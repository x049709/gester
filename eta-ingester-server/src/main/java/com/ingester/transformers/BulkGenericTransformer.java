package com.ingester.transformers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ingester.beans.IngesterIncomingPayload;
import com.ingester.beans.MappingSheet;
import com.ingester.loaders.MappingSheetSingleton;
import com.ingester.loaders.ReferenceDataSingleton;
import com.ingester.utils.GenericValidatorUtils;

public class BulkGenericTransformer {
	private HashMap<String, String> refDataFromSingleton;
	HashMap<String, MappingSheet> mapSheetFromSingleton;
	
	public BulkGenericTransformer() {
		refDataFromSingleton = ReferenceDataSingleton.getInstance().getReferenceDataMap();
		mapSheetFromSingleton = MappingSheetSingleton.getInstance().getMappingSheetDataMap();
	}	

	public ArrayList<IngesterIncomingPayload> transformIncoming(ArrayList<IngesterIncomingPayload> incomingDataArrayListIn) throws NoSuchMethodException {
		for(IngesterIncomingPayload ingesterIncoming : incomingDataArrayListIn) {
			this.transformIncoming(ingesterIncoming, this.refDataFromSingleton, this.mapSheetFromSingleton);
		}
		return incomingDataArrayListIn;
	}
	private void transformIncoming(IngesterIncomingPayload ingesterIncoming,
			HashMap<String, String> refDataFromSingleton, HashMap<String, MappingSheet> mapSheetFromSingleton) {
		
		GenericValidatorUtils vUtils = new GenericValidatorUtils();

		//Set the message in incoming message to 'NONE'
		String inFieldMsgValue = "NONE";
		ingesterIncoming.setInFieldMsg(inFieldMsgValue);

	}

	public ArrayList<Object> transformIncomingArrayList(ArrayList<Object> incomingDataArrayListIn) {
        for(Object row : incomingDataArrayListIn) {
    		System.out.println("Mapping sheet loaded:" + row.toString());
        }
		return incomingDataArrayListIn;
	}

}
