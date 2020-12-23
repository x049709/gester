package com.ingester.transformers;

import java.util.HashMap;

import com.ingester.beans.GenericIncomingPayload;
import com.ingester.beans.MappingSheet;
import com.ingester.exceptions.TransformException;
import com.ingester.loaders.MappingSheetSingleton;
import com.ingester.loaders.ReferenceDataSingleton;
import com.ingester.utils.TransformerUtils;

public class GenericTransformer {
	private GenericIncomingPayload genericIncoming;

	public GenericIncomingPayload getGenericIncoming() {
		return genericIncoming;
	}
	public void setGenericIncoming(GenericIncomingPayload genericIncomingIn) 
	{
		this.genericIncoming = genericIncomingIn;
	}

	public GenericTransformer() {
	}
	
	public GenericIncomingPayload transformIncoming(GenericIncomingPayload genericIncoming) throws TransformException, NoSuchMethodException{
		this.setGenericIncoming(genericIncoming);
		HashMap<String, String> refDataFromSingleton= ReferenceDataSingleton.getInstance().getReferenceDataMap();
		HashMap<String, MappingSheet> mapSheetFromSingleton= MappingSheetSingleton.getInstance().getMappingSheetDataMap();
		
		//BEGIN these are POC lines
		if (genericIncoming.getInField001().equalsIgnoreCase("2")) { 
			String errorMsg = "Error in transform"; 
			this.genericIncoming.setInField004(errorMsg); 
			throw new TransformException(errorMsg); 
		}
		//END these are POC lines
		
		this.transformOneIncomingRecord(refDataFromSingleton, mapSheetFromSingleton);
		
		return this.getGenericIncoming();
	}
	
	private void transformOneIncomingRecord(HashMap<String, String> refDataFromSingleton, HashMap<String, MappingSheet> mapSheetFromSingleton) throws NoSuchMethodException {
		System.out.println("Incoming record" + this.getGenericIncoming().toString());
		TransformerUtils tUtils = new TransformerUtils();
		int i=1;
		while (i<=mapSheetFromSingleton.size()) {
			this.transformOneIncomingField(i, refDataFromSingleton,mapSheetFromSingleton, tUtils);
			i++;
		};
		
	}

	private void transformOneIncomingField(int i, HashMap<String, String> refDataFromSingleton, HashMap<String, MappingSheet> mapSheetFromSingleton, TransformerUtils tUtils) throws NoSuchMethodException {
		String inFieldName = String.format("inField%03d", i);
		MappingSheet mapSheetForField = mapSheetFromSingleton.get(inFieldName);
		System.out.println("Mapping sheet for field: " + inFieldName + ": " + mapSheetForField.toString());
		String propertyValue = tUtils.getProperty(inFieldName, this.getGenericIncoming());
		System.out.println("Incoming field value for field: " + inFieldName + ": " + propertyValue);
		
		
	}


}


