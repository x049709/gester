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
		String inFieldSeq = "inFieldSeq";
		int inFieldSeqValue = tUtils.getIntProperty(inFieldSeq, genericIncoming);
		tUtils.setIntProperty(inFieldSeq, inFieldSeqValue, genericOutgoing);		
		//Set the message in output to 'NONE'
		String inFieldMsg = "inFieldMsg";
		String inFieldMsgValue = "NONE";
		tUtils.setStringProperty(inFieldMsg, inFieldMsgValue, genericOutgoing);		


		//Loop thru the rest of the input fields, transforming as required in the mapping sheet
		int i=1;
		while (i<=mapSheetFromSingleton.size()) {
			String propertyName = String.format("inField%03d", i);
			String propertyValue = tUtils.getStringProperty(propertyName, genericIncoming);
			//Initialize the outgoing propertyValue to the incoming propertyValue
			tUtils.setStringProperty(propertyName, propertyValue, genericOutgoing);		
			MappingSheet mapSheetForField = mapSheetFromSingleton.get(propertyName);
			//Do the transformation
			String message = this.transformOneIncomingField(refDataFromSingleton, 
					propertyName, 
					propertyValue, 
					mapSheetForField, 
					genericOutgoing, 
					tUtils);
			if (!message.equalsIgnoreCase(inFieldMsgValue)) { 
				String errorMsg = message; 
				System.out.println(errorMsg);
				throw new TransformException(errorMsg); 
			}
			
			i++;
			
		};

		//BEGIN these are POC lines
		//if (genericIncoming.getInField001().equalsIgnoreCase("LOC1") || genericIncoming.getInField001().equalsIgnoreCase("LOC5") ) { 
		//	String errorMsg = "BADREC"; 
		//	System.out.println(errorMsg);
		//	throw new TransformException(errorMsg); 
		//}
		//END these are POC lines

		return genericOutgoing;
	}

	private String transformOneIncomingField(HashMap<String, String> refDataFromSingleton, 
			String propertyName, 
			String propertyValue, 
			MappingSheet mapSheetForField, 
			GenericOutgoingPayload genericOutgoing, 
			TransformerUtils tUtils) throws NoSuchMethodException, TransformException {

		int inFieldSeq = genericOutgoing.getinFieldSeq();
		String inFieldMsg = "NONE";
		String requiredFlag = mapSheetForField.getREQUIRED_FLAG();
		if (requiredFlag.equalsIgnoreCase(TransformConstants.MappingSheetRules.REQUIRED) && StringUtils.isEmpty(propertyValue)) {
			String errorMsg = "Required field is empty for record:" + inFieldSeq + ",field:" + propertyName + "," + propertyValue + ", requiredFlag:" + requiredFlag;
			System.out.println(errorMsg);
			inFieldMsg = errorMsg; 			
			return inFieldMsg;
		}
		if (requiredFlag.equalsIgnoreCase(TransformConstants.MappingSheetRules.OPTIONAL) && StringUtils.isEmpty(propertyValue)) {
			return inFieldMsg;
		}

		String ingestionRule = mapSheetForField.getINGESTION_RULE();	
		if (!ingestionRule.equalsIgnoreCase(TransformConstants.IngestionRules.NONE)) {
			inFieldMsg = this.executeIngestionRule(refDataFromSingleton, 
					propertyName, 
					propertyValue, 
					ingestionRule, 
					genericOutgoing,
					tUtils);
		}
		return inFieldMsg;
		
	}

	private String executeIngestionRule(HashMap<String, String> refDataFromSingleton, 
			String propertyName, 
			String propertyValue, 
			String ingestionRule, 
			GenericOutgoingPayload genericOutgoing, 
			TransformerUtils tUtils) throws NoSuchMethodException, TransformException {

		int inFieldSeq = genericOutgoing.getinFieldSeq();
		String inFieldMsg = "NONE";
		switch (ingestionRule.trim()) {
		case TransformConstants.IngestionRules.NONE:
			return inFieldMsg;
		case TransformConstants.IngestionRules.DATE:
			boolean isDateValid = GenericValidator.isDate(propertyValue, Locale.ENGLISH);
			if (!isDateValid) {
				String errorMsg = "Error in date validation for record:" + inFieldSeq + ",field:" + propertyName + "," + propertyValue + ", ingestionRule:" + ingestionRule;
				System.out.println(errorMsg);
				inFieldMsg = errorMsg; 							
				return inFieldMsg;
			}
			break;
		case TransformConstants.IngestionRules.DECIMAL:
			break;
		default:
			String errorMsg = "No such ingestion rule for record:" + inFieldSeq + ",field:" + propertyName + "," + propertyValue + ", ingestionRule:" + ingestionRule;
			System.out.println(errorMsg);
			inFieldMsg = errorMsg; 							
			return inFieldMsg;
		}
		
		return inFieldMsg;
	}

}


