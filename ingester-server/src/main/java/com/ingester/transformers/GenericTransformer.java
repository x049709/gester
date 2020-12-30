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
			//First, initialize the outgoing propertyValue to the incoming propertyValue
			tUtils.setStringProperty(propertyName, propertyValue, genericOutgoing);		
			MappingSheet mapSheetForField = mapSheetFromSingleton.get(propertyName);
			//Then, do the transformation
			try {
				this.transformOneIncomingField(refDataFromSingleton, 
						propertyName, 
						propertyValue, 
						mapSheetForField, 
						genericOutgoing, 
						tUtils);
			}
			catch(Exception e) {
				throw new TransformException(e.getMessage()); 
			}			

			i++;

		};

		return genericOutgoing;
	}

	private void transformOneIncomingField(HashMap<String, String> refDataFromSingleton, 
			String propertyName, 
			String propertyValue, 
			MappingSheet mapSheetForField, 
			GenericOutgoingPayload genericOutgoing, 
			TransformerUtils tUtils) throws NoSuchMethodException, TransformException {

		int inFieldSeq = genericOutgoing.getinFieldSeq();
		String requiredFlag = mapSheetForField.getREQUIRED_FLAG();
		
		try {
			if (requiredFlag.equalsIgnoreCase(TransformConstants.MappingSheetRules.REQUIRED) && StringUtils.isEmpty(propertyValue)) {
				String errorMsg = "Required field is empty for record:" + inFieldSeq + ",field:" + propertyName + "," + propertyValue + ", requiredFlag:" + requiredFlag;
				System.out.println(errorMsg);
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
			return;
			
		}
		catch (Exception e) {
			throw new TransformException(e.getMessage()); 			
		}

	}

	private void executeIngestionRule(HashMap<String, String> refDataFromSingleton, 
			String propertyName, 
			String propertyValue, 
			String ingestionRule, 
			GenericOutgoingPayload genericOutgoing, 
			TransformerUtils tUtils) throws NoSuchMethodException, TransformException {

		int inFieldSeq = genericOutgoing.getinFieldSeq();

		try {
			switch (ingestionRule.trim()) {
			case TransformConstants.IngestionRules.NONE:
				return;
			case TransformConstants.IngestionRules.DATE:
				boolean isDateValid = GenericValidator.isDate(propertyValue, "MM/dd/yyyy", true);
				if (!isDateValid) {
					String errorMsg = "Error in date validation for record:" + inFieldSeq + ",field:" + propertyName + "," + propertyValue + ", ingestionRule:" + ingestionRule;
					System.out.println(errorMsg);
					throw new TransformException(errorMsg); 
				}
				return;
			case TransformConstants.IngestionRules.DECIMAL:
				return;
			default:
				String errorMsg = "No such ingestion rule for record:" + inFieldSeq + ",field:" + propertyName + "," + propertyValue + ", ingestionRule:" + ingestionRule;
				System.out.println(errorMsg);
				throw new TransformException(errorMsg); 
			}
			
		}
		catch (Exception e) {
			throw new TransformException(e.getMessage()); 			
		}	

	}

}


