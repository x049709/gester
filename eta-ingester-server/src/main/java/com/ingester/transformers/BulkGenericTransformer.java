package com.ingester.transformers;

import java.util.ArrayList;
import java.util.HashMap;

import com.ingester.beans.IngesterIncomingPayload;
import com.ingester.beans.MappingSheet;
import com.ingester.loaders.MappingSheetSingleton;
import com.ingester.loaders.ReferenceDataSingleton;
import com.ingester.utils.GenericValidatorUtils;

public class BulkGenericTransformer {

	public BulkGenericTransformer() {
	}	
	public ArrayList<IngesterIncomingPayload> transformIncoming(ArrayList<IngesterIncomingPayload> incomingDataArrayListIn) throws NoSuchMethodException {
		HashMap<String, String> refDataFromSingleton = ReferenceDataSingleton.getInstance().getReferenceDataMap();
		HashMap<String, MappingSheet> mapSheetFromSingleton = MappingSheetSingleton.getInstance().getMappingSheetDataMap();
		for(IngesterIncomingPayload ingesterIncoming : incomingDataArrayListIn) {
			this.transformIncoming(ingesterIncoming, refDataFromSingleton, mapSheetFromSingleton);
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

}
