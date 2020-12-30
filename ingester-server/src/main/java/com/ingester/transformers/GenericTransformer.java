package com.ingester.transformers;

import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.validator.GenericValidator;
import org.springframework.util.StringUtils;

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
		GenericOutgoingPayload genericOutgoing = new GenericOutgoingPayload();

		System.out.println("Incoming record" + genericIncoming.toString());
		TransformerUtils tUtils = new TransformerUtils();

		//Copy the sequence number from input to output
		String intPropertyName = "inFieldSeq";
		int intPropertyValue = tUtils.getIntProperty(intPropertyName, genericIncoming);
		tUtils.setIntProperty(intPropertyName, intPropertyValue, genericOutgoing);		


		//Loop thru the rest of the input fields, transforming as required in the mapping sheet
		int i=1;
		while (i<=mapSheetFromSingleton.size()) {
			String propertyName = String.format("inField%03d", i);
			String propertyValue = tUtils.getStringProperty(propertyName, genericIncoming);
			//Initialize the outgoing propertyValue to the incoming propertyValue
			tUtils.setStringProperty(propertyName, propertyValue, genericOutgoing);		
			MappingSheet mapSheetForField = mapSheetFromSingleton.get(propertyName);
			//Do the transformation
			this.transformOneIncomingField(refDataFromSingleton, 
					propertyName, 
					propertyValue, 
					mapSheetForField, 
					genericOutgoing, 
					tUtils);
			i++;
		};

		//BEGIN these are POC lines
		if (genericIncoming.getInField001().equalsIgnoreCase("LOC1") || genericIncoming.getInField001().equalsIgnoreCase("LOC5") ) { 
			String errorMsg = "BADREC"; 
			genericOutgoing.setinField040(errorMsg); 
			throw new TransformException(errorMsg); 
		}
		//END these are POC lines

		return genericOutgoing;
	}

	private void transformOneIncomingField(HashMap<String, String> refDataFromSingleton, 
			String propertyName, 
			String propertyValue, 
			MappingSheet mapSheetForField, 
			GenericOutgoingPayload genericOutgoing, 
			TransformerUtils tUtils) throws NoSuchMethodException, TransformException {

		String requiredFlag = mapSheetForField.getREQUIRED_FLAG();
		if (requiredFlag.equalsIgnoreCase(TransformConstants.MappingSheetRules.REQUIRED) && StringUtils.isEmpty(propertyValue)) {
			System.out.println("Incoming field value for field: " + propertyName + ": " + propertyValue + ", requiredFlag:" + requiredFlag);
			String errorMsg = "EMPTY"; 
			genericOutgoing.setinField040(errorMsg); 
			throw new TransformException(errorMsg); 			
		}
		if (requiredFlag.equalsIgnoreCase(TransformConstants.MappingSheetRules.OPTIONAL) && StringUtils.isEmpty(propertyValue)) {
			return;
		}

		String ingestionRule = mapSheetForField.getINGESTION_RULE();	
		if (!ingestionRule.equalsIgnoreCase(TransformConstants.IngestionRules.NONE)) {
			this.executeIngestionRule(refDataFromSingleton, 
					propertyName, 
					propertyValue, 
					ingestionRule, 
					genericOutgoing,
					tUtils);
		}
	}

	private void executeIngestionRule(HashMap<String, String> refDataFromSingleton, 
			String propertyName, 
			String propertyValue, 
			String ingestionRule, 
			GenericOutgoingPayload genericOutgoing, 
			TransformerUtils tUtils) throws NoSuchMethodException, TransformException {

		switch (ingestionRule.trim()) {
		case TransformConstants.IngestionRules.NONE:
			break;
		case TransformConstants.IngestionRules.DATE_MM_SL_DD_SL_YYYY:
			boolean isDateValid = GenericValidator.isDate(propertyValue, Locale.ENGLISH);
			if (!isDateValid) {
				System.out.println("Incoming field value for field: " + propertyName + ": " + propertyValue + ", ingestionRule:" + ingestionRule);
				String errorMsg = "BADDATE"; 
				genericOutgoing.setinField040(errorMsg); 
				throw new TransformException(errorMsg); 							
			}
			break;
		case TransformConstants.IngestionRules.DECIMAL:
			break;
		default:
			String errorMsg = "BADRULE"; 
			genericOutgoing.setinField040(errorMsg); 
			throw new TransformException(errorMsg); 			
		}
		
		return;
	}

}


