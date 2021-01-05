package com.ingester.transformers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;
import org.springframework.util.StringUtils;

import com.ingester.beans.IngesterIncomingPayload;
import com.ingester.beans.MappingSheet;
import com.ingester.constants.TransformConstants;
import com.ingester.loaders.MappingSheetSingleton;
import com.ingester.loaders.ReferenceDataSingleton;
import com.ingester.utils.GenericValidatorUtils;

public class BulkGenericTransformer {
	HashMap<String, String> refDataFromSingleton = new HashMap<String, String>();
	HashMap<String, MappingSheet> mapSheetUsingGenericColumnFromSingleton = new HashMap<String, MappingSheet>();
	HashMap<String, MappingSheet> mapSheetUsingSourceColumnFromSingleton = new HashMap<String, MappingSheet>();
	GenericValidatorUtils vUtils;

	public BulkGenericTransformer() {
		this.refDataFromSingleton = ReferenceDataSingleton.getInstance().getReferenceDataMap();
		this.mapSheetUsingGenericColumnFromSingleton = MappingSheetSingleton.getInstance().getMappingSheetDataMapUsingGenericColumn();
		this.mapSheetUsingSourceColumnFromSingleton = MappingSheetSingleton.getInstance().getMappingSheetDataMapUsingSourceColumn();
		this.vUtils = new GenericValidatorUtils();
	}	

	public ArrayList<IngesterIncomingPayload> transformIncoming(ArrayList incomingDataArrayListIn) {

		ArrayList<IngesterIncomingPayload> outgoingDataArrayListIn = new ArrayList<IngesterIncomingPayload>();
		IngesterIncomingPayload ingesterPayload = new IngesterIncomingPayload();	

		int recordSeq = 1;
		for(Object row : incomingDataArrayListIn) {
			Map<String, HashMap> rowCasted = (Map<String, HashMap>)row;
			for (Map.Entry entry : rowCasted.entrySet()) {
				//System.out.println("Raw row from inbound: " + entry.getKey() + "=" + entry.getValue());
				Map<String, String> rowZero = (Map<String, String>) entry.getValue();
				//System.out.println("Row zero: " + rowZero.toString());
				
				//Convert the incoming hashmap record into a JavaBean, starting with the sequence and msg
				ingesterPayload = this.convertIncomingHashMapToJavaBean(recordSeq, rowZero);

				//System.out.println("Converted row from inbound: " + ingesterPayload.toString());
				
				//Validate/transform all other fields in the incoming message, using the mapping sheet to drive the flow
				int i=1;
				while (i<=this.mapSheetUsingGenericColumnFromSingleton.size()) {
					String propertyName = String.format("inField%03d", i);
					String propertyValue;
					MappingSheet mapSheetForField = this.mapSheetUsingGenericColumnFromSingleton.get(propertyName);
					try {
						propertyValue = this.vUtils.getStringProperty(propertyName, ingesterPayload);
						this.transformOneIncomingField(ingesterPayload, propertyName, propertyValue, mapSheetForField);
					} catch (NoSuchMethodException e) {
						propertyValue = null;
						this.transformOneIncomingField(ingesterPayload, propertyName, propertyValue, mapSheetForField);
					} catch (Exception e) {
						ingesterPayload.setInFieldMsg(e.getMessage());
					}
					i++;
				};

			}
						
			outgoingDataArrayListIn.add(ingesterPayload);
			recordSeq++;
		}
		//System.out.println("Outgoing arraylist: " + outgoingDataArrayListIn.toString());
		return outgoingDataArrayListIn;
	
	}
		
	private IngesterIncomingPayload convertIncomingHashMapToJavaBean(int recordSeq, Map<String, String> rowZero) {
		IngesterIncomingPayload ingesterPayload = new IngesterIncomingPayload();	
		String inFieldMsgValue = "NONE";
		ingesterPayload.setInFieldSeq(recordSeq);
		ingesterPayload.setInFieldMsg(inFieldMsgValue);
		
		//Convert the rest of the fields
		for (Map.Entry actualColumnMappingFromInbound : rowZero.entrySet()) {
			//System.out.println("Actual key=value from inbound: " + actualColumnMappingFromInbound.getKey() + "=" + actualColumnMappingFromInbound.getValue());
			String sourceColumn = (String)actualColumnMappingFromInbound.getKey();
			
			try {
				MappingSheet mapSheetForFieldUsingSourceColumn = this.mapSheetUsingSourceColumnFromSingleton.get(sourceColumn);
				String genericColumn = mapSheetForFieldUsingSourceColumn.getGENERIC_COL_NAME();
				String sourceValue = (String)actualColumnMappingFromInbound.getValue();
				this.vUtils.setStringProperty(genericColumn, sourceValue, ingesterPayload);
			} catch (Exception e) {
				ingesterPayload.setInFieldMsg("NOTMAPPED");
			}		
		}

		return ingesterPayload;
	}

	private void transformOneIncomingField(
			IngesterIncomingPayload ingesterIncoming, 
			String genericPropertyName, 
			String propertyValue, 
			MappingSheet mapSheetForField) {

		int inFieldSeq = ingesterIncoming.getInFieldSeq();
		String requiredFlag = mapSheetForField.getREQUIRED_FLAG().trim();
		String ingestionRule = mapSheetForField.getINGESTION_RULE().trim();
		String sourcePropertyName = mapSheetForField.getSOURCE_COL_NAME().trim();
		String genericAndSourceFieldName = new StringBuilder().append(sourcePropertyName).append("/").append(genericPropertyName).toString();

		try {
			if (requiredFlag.equalsIgnoreCase(TransformConstants.MappingSheetRules.REQUIRED) && StringUtils.isEmpty(propertyValue)) {
				String errorMsg = "Required field is empty/not found for record:" + inFieldSeq + ", field:" + genericAndSourceFieldName + "=>" + propertyValue + "EMPTY/NOT FOUND, requiredFlag:" + requiredFlag;
				ingesterIncoming.setInFieldMsg(errorMsg);
			}
			if (requiredFlag.equalsIgnoreCase(TransformConstants.MappingSheetRules.OPTIONAL)) {
				if (StringUtils.isEmpty(propertyValue)) {
					propertyValue = mapSheetForField.getDEFAULT_VALUE_IF_OPTIONAL();
					this.vUtils.setStringProperty(genericPropertyName, propertyValue, ingesterIncoming);		
					this.executeIngestionRule(ingesterIncoming, mapSheetForField, genericPropertyName, sourcePropertyName, propertyValue, genericAndSourceFieldName, ingestionRule);
				}
			}

			if (!ingestionRule.equalsIgnoreCase(TransformConstants.IngestionRules.STRING)) {
				this.executeIngestionRule(ingesterIncoming, mapSheetForField, genericPropertyName, sourcePropertyName, propertyValue, genericAndSourceFieldName, ingestionRule);
			}
			return;

		}
		catch (Exception e) {
			ingesterIncoming.setInFieldMsg(e.getMessage());
		}

	}

	private void executeIngestionRule(IngesterIncomingPayload ingesterIncoming, 
			MappingSheet mapSheetForField, 
			String genericPropertyName, 
			String sourcePropertyName, 
			String propertyValue, 
			String genericAndSourceFieldName, 
			String ingestionRule) {

		int inFieldSeq = ingesterIncoming.getInFieldSeq();
		try {
			switch (ingestionRule.trim()) {
			case TransformConstants.IngestionRules.STRING:
				return;
			case TransformConstants.IngestionRules.DATE:
				boolean isDateValid = GenericValidator.isDate(propertyValue, "MM/dd/yyyy", true);
				if (!isDateValid) {
					String errorMsg = "Error in date validation for record:" + inFieldSeq + ", field:" + genericAndSourceFieldName + "=>" + propertyValue + ", ingestionRule:" + ingestionRule;
					ingesterIncoming.setInFieldMsg(errorMsg);
				}
				return;
			case TransformConstants.IngestionRules.DECIMAL:
				boolean isNumberADecimal = GenericValidator.isDouble(propertyValue);
				if (!isNumberADecimal) {
					String errorMsg = "Error in decimal validation for record:" + inFieldSeq + ", field:" + genericAndSourceFieldName + "=>" + propertyValue + ", ingestionRule:" + ingestionRule;
					ingesterIncoming.setInFieldMsg(errorMsg);
				}
				return;
			case TransformConstants.IngestionRules.INTEGER:
				boolean isNumberAnInteger = GenericValidator.isInt(propertyValue);
				if (!isNumberAnInteger) {
					String errorMsg = "Error in integer validation for record:" + inFieldSeq + ", field:" + genericAndSourceFieldName + "=>" + propertyValue + ", ingestionRule:" + ingestionRule;
					ingesterIncoming.setInFieldMsg(errorMsg);
				}
				return;
			case TransformConstants.IngestionRules.LOOKUP:
				String translatedField = this.translateField(sourcePropertyName, propertyValue, this.refDataFromSingleton);
				if (StringUtils.isEmpty(translatedField)) {
					String errorMsg = "Error in translating for record:" + inFieldSeq + ", field:" + genericAndSourceFieldName + "=>" + propertyValue + ", ingestionRule:" + ingestionRule;
					ingesterIncoming.setInFieldMsg(errorMsg);
				} else {
					this.vUtils.setStringProperty(genericPropertyName, translatedField, ingesterIncoming);		
					return;
				}
			default:
				String errorMsg = "No such ingestion rule for record:" + inFieldSeq + ", field:" + genericAndSourceFieldName + "=>" + propertyValue + ", ingestionRule:" + ingestionRule;
				ingesterIncoming.setInFieldMsg(errorMsg);
			}
		}
		catch (Exception e) {
			ingesterIncoming.setInFieldMsg(e.getMessage());
		}	

	}

	private String translateField(String sourcePropertyName, 
			String propertyValue,
			HashMap<String, String> refDataFromSingleton) {

		String refDataKey = new StringBuilder().append(sourcePropertyName.trim()).append("|").append(propertyValue.trim()).toString();
		String translatedField = refDataFromSingleton.get(refDataKey);
		return translatedField;
	}


}
