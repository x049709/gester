package com.ingester.transformers;

import java.util.HashMap;

import com.ingester.beans.GenericIncomingPayload;
import com.ingester.beans.GenericOutgoingPayload;
import com.ingester.beans.MappingSheet;
import com.ingester.constants.TransformConstants;
import com.ingester.exceptions.TransformException;
import com.ingester.loaders.MappingSheetSingleton;
import com.ingester.loaders.ReferenceDataSingleton;
import com.ingester.utils.TransformerUtils;

public class GenericTransformer {
	private GenericIncomingPayload genericIncoming;

	public GenericTransformer() {}

	public GenericOutgoingPayload transformIncoming(GenericIncomingPayload genericIncoming) throws TransformException, NoSuchMethodException{
		HashMap<String, String> refDataFromSingleton = ReferenceDataSingleton.getInstance().getReferenceDataMap();
		HashMap<String, MappingSheet> mapSheetFromSingleton = MappingSheetSingleton.getInstance().getMappingSheetDataMap();

		//BEGIN these are POC lines
		if (genericIncoming.getInField001().equalsIgnoreCase("2")) { 
			String errorMsg = "Error in transform"; 
			this.genericIncoming.setInField004(errorMsg); 
			throw new TransformException(errorMsg); 
		}
		//END these are POC lines
		
		GenericOutgoingPayload genericOutgoing = new GenericOutgoingPayload();
		System.out.println("Incoming record" + genericIncoming.toString());
		TransformerUtils tUtils = new TransformerUtils();
		
		//Copy the sequence number from input to output
		String intPropertyName = String.format("inFieldSeq");
		int intPropertyValue = tUtils.getIntProperty(intPropertyName, genericIncoming);
		tUtils.setIntProperty(intPropertyName, intPropertyValue, genericOutgoing);		

		
		//Loop thru the rest of the input fields, transforming as required in the mapping sheet
		int i=1;
		while (i<=mapSheetFromSingleton.size()) {
			String propertyName = String.format("inField%03d", i);
			String propertyValue = tUtils.getStringProperty(propertyName, genericIncoming);
			System.out.println("Incoming field value for field: " + propertyName + ": " + propertyValue);
			MappingSheet mapSheetForField = mapSheetFromSingleton.get(propertyName);
			System.out.println("Mapping sheet for field: " + propertyName + ": " + mapSheetForField.toString());
			this.transformOneIncomingField(refDataFromSingleton, 
					propertyName, 
					propertyValue, 
					mapSheetForField, 
					genericOutgoing, 
					tUtils);
			i++;
		};
		
		return genericOutgoing;
	}
	
	private void transformOneIncomingField(HashMap<String, String> refDataFromSingleton, 
			String propertyName, 
			String propertyValue, 
			MappingSheet mapSheetForField, 
			GenericOutgoingPayload genericOutgoing, 
			TransformerUtils tUtils) throws NoSuchMethodException {

		String ingestionRule = mapSheetForField.getINGESTION_RULE();
		this.executeIngestionRule(refDataFromSingleton, 
				propertyName, 
				propertyValue, 
				ingestionRule, 
				genericOutgoing,
				tUtils);
	}

	private void executeIngestionRule(HashMap<String, String> refDataFromSingleton, 
			String propertyName, 
			String propertyValue, 
			String ingestionRule, 
			GenericOutgoingPayload genericOutgoing, 
			TransformerUtils tUtils) throws NoSuchMethodException {

		if (ingestionRule.equalsIgnoreCase(TransformConstants.NO_INGESTION_RULE)) {
			tUtils.setStringProperty(propertyName, propertyValue, genericOutgoing);
		} else {
			tUtils.setStringProperty(propertyName, propertyValue, genericOutgoing);			
		}
	}

}


