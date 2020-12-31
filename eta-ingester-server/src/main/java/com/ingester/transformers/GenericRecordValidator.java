package com.ingester.transformers;

import java.util.HashMap;

import org.apache.commons.validator.GenericValidator;
import org.springframework.util.StringUtils;

import com.ingester.beans.IngesterIncomingPayload;
import com.ingester.beans.IngesterOutgoingPayload;
import com.ingester.beans.MappingSheet;
import com.ingester.constants.TransformConstants;
import com.ingester.exceptions.TransformException;
import com.ingester.loaders.MappingSheetSingleton;
import com.ingester.loaders.ReferenceDataSingleton;
import com.ingester.utils.GenericValidatorUtils;

public class GenericRecordValidator {	

	public GenericRecordValidator() {}

	public IngesterOutgoingPayload transformIncoming(IngesterIncomingPayload incomingPayload) throws TransformException, NoSuchMethodException{
		HashMap<String, String> refDataFromSingleton = ReferenceDataSingleton.getInstance().getReferenceDataMap();
		HashMap<String, MappingSheet> mapSheetFromSingleton = MappingSheetSingleton.getInstance().getMappingSheetDataMap();
		IngesterOutgoingPayload outgoingPayload = new IngesterOutgoingPayload();

		System.out.println("Incoming record" + incomingPayload.toString());
		GenericValidatorUtils vUtils = new GenericValidatorUtils();

		//Copy the incoming payload to the instance
		//Copy the sequence number from input to output
		String inFieldSeq = "inFieldSeq";
		int inFieldSeqValue = vUtils.getIntProperty(inFieldSeq, incomingPayload);
		vUtils.setIntProperty(inFieldSeq, inFieldSeqValue, outgoingPayload);	
		//Set the message in output to 'NONE'
		String inFieldMsg = "inFieldMsg";
		String inFieldMsgValue = "NONE";
		vUtils.setStringProperty(inFieldMsg, inFieldMsgValue, outgoingPayload);		

		//Loop thru the rest of the input fields, transforming as required in the mapping sheet
		int i=1;
		while (i<=mapSheetFromSingleton.size()) {
			String propertyName = String.format("inField%03d", i);
			String propertyValue = vUtils.getStringProperty(propertyName, incomingPayload);
			//First, initialize the outgoing propertyValue to the incoming propertyValue
			vUtils.setStringProperty(propertyName, propertyValue, outgoingPayload);		
			MappingSheet mapSheetForField = mapSheetFromSingleton.get(propertyName);
			//Then, do the transformation
			try {
				this.transformOneIncomingField(refDataFromSingleton, 
						propertyName, 
						propertyValue, 
						mapSheetForField, 
						outgoingPayload, 
						vUtils);
			}
			catch(Exception e) {
				throw new TransformException(e.getMessage()); 
			}			

			i++;

		};

		return outgoingPayload;
	}

	private void transformOneIncomingField(HashMap<String, String> refDataFromSingleton, 
			String propertyName, 
			String propertyValue, 
			MappingSheet mapSheetForField, 
			IngesterOutgoingPayload outgoingPayload, 
			GenericValidatorUtils vUtils) throws NoSuchMethodException, TransformException {

		int inFieldSeq = outgoingPayload.getInFieldSeq();
		String requiredFlag = mapSheetForField.getREQUIRED_FLAG();

		try {
			if (requiredFlag.equalsIgnoreCase(TransformConstants.MappingSheetRules.REQUIRED) && StringUtils.isEmpty(propertyValue)) {
				String errorMsg = "Required field is empty for record:" + inFieldSeq + ", field:" + propertyName + "=>" + propertyValue + "EMPTY, requiredFlag:" + requiredFlag;
				System.out.println(errorMsg);
				throw new TransformException(errorMsg); 
			}
			if (requiredFlag.equalsIgnoreCase(TransformConstants.MappingSheetRules.OPTIONAL) && StringUtils.isEmpty(propertyValue)) {
				return;
			}

			String ingestionRule = mapSheetForField.getINGESTION_RULE();
			if (!ingestionRule.equalsIgnoreCase(TransformConstants.IngestionRules.NONE)) {
				this.executeIngestionRule(refDataFromSingleton,
						mapSheetForField,
						propertyName, 
						propertyValue, 
						ingestionRule, 
						outgoingPayload,
						vUtils);
			}
			return;

		}
		catch (Exception e) {
			throw new TransformException(e.getMessage()); 			
		}

	}

	private void executeIngestionRule(HashMap<String, String> refDataFromSingleton, 
			MappingSheet mapSheetForField, 
			String propertyName, 
			String propertyValue, 
			String ingestionRule, 
			IngesterOutgoingPayload outgoingPayload, 
			GenericValidatorUtils vUtils) throws NoSuchMethodException, TransformException {

		int inFieldSeq = outgoingPayload.getInFieldSeq();
		try {
			switch (ingestionRule.trim()) {
			case TransformConstants.IngestionRules.NONE:
				return;
			case TransformConstants.IngestionRules.DATE:
				boolean isDateValid = GenericValidator.isDate(propertyValue, "MM/dd/yyyy", true);
				if (!isDateValid) {
					String errorMsg = "Error in date validation for record:" + inFieldSeq + ", field:" + propertyName + "=>" + propertyValue + ", ingestionRule:" + ingestionRule;
					System.out.println(errorMsg);
					throw new TransformException(errorMsg); 
				}
				return;
			case TransformConstants.IngestionRules.DECIMAL:
				boolean isNumberADecimal = GenericValidator.isDouble(propertyValue);
				if (!isNumberADecimal) {
					String errorMsg = "Error in decimal validation for record:" + inFieldSeq + ", field:" + propertyName + "=>" + propertyValue + ", ingestionRule:" + ingestionRule;
					System.out.println(errorMsg);
					throw new TransformException(errorMsg); 
				}
				return;
			case TransformConstants.IngestionRules.INTEGER:
				boolean isNumberAnInteger = GenericValidator.isInt(propertyValue);
				if (!isNumberAnInteger) {
					String errorMsg = "Error in integer validation for record:" + inFieldSeq + ", field:" + propertyName + "=>" + propertyValue + ", ingestionRule:" + ingestionRule;
					System.out.println(errorMsg);
					throw new TransformException(errorMsg); 
				}
				return;
			case TransformConstants.IngestionRules.LOOKUP:
				String targetColumnName = mapSheetForField.getTARGET_COL_NAME().trim();
				String translatedField = this.translateField(targetColumnName, propertyValue, refDataFromSingleton);
				if (StringUtils.isEmpty(translatedField)) {
					String errorMsg = "Error in translating for record:" + inFieldSeq + ", field:" + propertyName + "=>" + propertyValue + ", ingestionRule:" + ingestionRule;
					System.out.println(errorMsg);
					throw new TransformException(errorMsg); 
				} else {
					vUtils.setStringProperty(propertyName, translatedField, outgoingPayload);		
					return;
				}
			default:
				String errorMsg = "No such ingestion rule for record:" + inFieldSeq + ", field:" + propertyName + "=>" + propertyValue + ", ingestionRule:" + ingestionRule;
				System.out.println(errorMsg);
				throw new TransformException(errorMsg); 
			}
		}
		catch (Exception e) {
			throw new TransformException(e.getMessage()); 			
		}	

	}

	private String translateField(String targetColumnName, 
			String propertyValue,
			HashMap<String, String> refDataFromSingleton) {

		String refDataKey = new StringBuilder().append(targetColumnName.trim()).append("|").append(propertyValue.trim()).toString();
		String translatedField = refDataFromSingleton.get(refDataKey);
		return translatedField;
	}


}


