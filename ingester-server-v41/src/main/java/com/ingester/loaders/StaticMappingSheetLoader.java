package com.ingester.loaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ingester.beans.MappingSheet;

public class StaticMappingSheetLoader {
	private static HashMap<String, MappingSheet> mappingSheetDataMapUsingGenericColumn = new HashMap(); 
	private static HashMap<String, MappingSheet> mappingSheetDataMapUsingSourceColumn = new HashMap(); 
	//private static String DBInsertSQL = "insert into SalesLt.LOC (LOC,DESCR,OHPOST,FRZSTART,SOURCECAL,DESTCAL,TYPE,ALTPLANTID,CUST,TRANSZONE,LAT,LON,ENABLESW,FF_TRIGGER_CONTROL,LOC_TYPE,VENDID,COMPANYID,WORKINGCAL,SEQINTEXPORTDUR,SEQINTIMPORTDUR,SEQINTLASTEXPORTEDTOSEQ,SEQINTLASTIMPORTEDFROMSEQ,POSTALCODE,COUNTRY,CURRENCY,WDDAREA,BORROWINGPCT,HIERARCHYLEVEL,ORDERCOST,SOURCELOC,U_NATIONLOCATIONID,U_PROFILE_DESIGNATION,U_ETHNICITY,U_CORP_VOLUME_GROUP,U_SCHOOL_START_WK,U_TOURIST_FLAG,U_COLD_SUMMER,U_WARM_WEATHER,U_EXTREME_GROWTH,U_CER_STORE,U_FLAGSHIP,U_LATINO,U_SMALL_DOOR_PILOT,U_SOUTHERN_LIVING,U_BEST_MALLS,U_CLIMATE,U_BEST_BETTER_GOOD,U_STATE,U_STATE_NAME,U_SUPER_REGION,U_REGION,U_DISTRICT,U_PRIMARY_LOC_LIFESTYLE,U_LOCN_TYP_CODE,U_NATIONDESCRIPTION,U_REGIONID,U_REGIONDESCRIPTION,U_DISTRICTID,U_DISTRICTDESCRIPTION,U_LOCATIONID,U_DIVISION,U_OPEN_DATE,U_CLOSE_DATE,U_CITY,U_ZIP_CODE,U_LATITUDE,U_LONGITUDE) values (:inField001, :inField002, :inField003, :inField004, :inField005, :inField006, :inField007,:inField008, :inField009, :inField010,:inField011, :inField012, :inField013, :inField014, :inField015, :inField016, :inField017,:inField018, :inField019, :inField020, :inField021, :inField022, :inField023, :inField024, :inField025, :inField026, :inField027,:inField028, :inField029, :inField030, :inField031, :inField032, :inField033, :inField034, :inField035, :inField036, :inField037,:inField038, :inField039, :inField040, :inField041, :inField042, :inField043, :inField044, :inField045, :inField046, :inField047,:inField048, :inField049, :inField050, :inField051, :inField052, :inField053, :inField054, :inField055, :inField056, :inField067,:inField058, :inField059, :inField060, :inField061, :inField062, :inField063, :inField064, :inField065, :inField066, :inField067)";
	; 

	public StaticMappingSheetLoader() {
		System.out.println("MappingSheetLoader default constructor done");
	}	
	public static HashMap<String, MappingSheet> getMappingSheetDataMapUsingGenericColumn() {
		return mappingSheetDataMapUsingGenericColumn;
	}
	public static HashMap<String, MappingSheet> getMappingSheetDataMapUsingSourceColumn() {
		return mappingSheetDataMapUsingSourceColumn;
	}

	public static String GetDBInsertSQL(String schema) {
		String targetEntity = ParameterLoader.getTargetEntity();

		StringBuilder dbInsertSQLColumns = new StringBuilder().append("INSERT INTO ").append(schema.trim()).append(".").append(targetEntity.trim()).append("(");
		StringBuilder dbInsertSQLValues = new StringBuilder().append(" VALUES ").append("(");
		//Validate/transform all other fields in the incoming message, using the mapping sheet to drive the flow
		int i=1;
		while (i<=mappingSheetDataMapUsingGenericColumn.size()) {
			String columnValue = String.format("inField%03d", i);
			MappingSheet mapSheetForField = mappingSheetDataMapUsingGenericColumn.get(columnValue);
			String columnName = mapSheetForField.getTARGET_COL_NAME().trim();

			dbInsertSQLColumns.append(columnName).append(",");
			dbInsertSQLValues.append(":").append(columnValue).append(",");

			i++;
		};
		
		
		String strtdbInsertSQLColumns = dbInsertSQLColumns.toString();
		if (strtdbInsertSQLColumns.endsWith(",")) {
			strtdbInsertSQLColumns = strtdbInsertSQLColumns.substring(0, strtdbInsertSQLColumns.length() - 1);
		}

		String strdbInsertSQLValues = dbInsertSQLValues.toString();
		if (strdbInsertSQLValues.endsWith(",")) {
			strdbInsertSQLValues = strdbInsertSQLValues.substring(0, strdbInsertSQLValues.length() - 1);
		}
		
		StringBuilder dbInsertSQLColumnsAndValues = new StringBuilder().append(strtdbInsertSQLColumns).append(")").append(strdbInsertSQLValues).append(")");
		
		return dbInsertSQLColumnsAndValues.toString();
	}

	public static void LoadMappingSheet(ArrayList referenceDataArrayListIn) {
		//System.out.println("loadReferenceDataArrayList static method with input:" + referenceDataArrayListIn);
		mappingSheetDataMapUsingGenericColumn.clear();
		mappingSheetDataMapUsingSourceColumn.clear();
		for(Object row : referenceDataArrayListIn) {
			Map<String, MappingSheet> m = (Map<String,MappingSheet>)row;
			String hMapKeyGeneric = null;
			String hMapKeySource = null;
			MappingSheet mSheet = new MappingSheet();
			for (Map.Entry entry : m.entrySet()) {
				String hMapValue = null;
				if (entry.getKey().equals("SOURCE_COL_NAME")) {
					hMapKeySource = (String)entry.getValue();
					mSheet.setSOURCE_COL_NAME((String)entry.getValue());
					//System.out.println(entry.getKey() + ", " + hMapKey);
				}        		
				if (entry.getKey().equals("SOURCE_ENTITY")) {
					hMapValue = (String)entry.getValue();
					mSheet.setSOURCE_ENTITY((String)entry.getValue());
					//System.out.println(entry.getKey() + ", " + hMapValue);
				}
				if (entry.getKey().equals("GENERIC_COL_NAME")) {
					hMapKeyGeneric = (String)entry.getValue();
					hMapValue = (String)entry.getValue();
					mSheet.setGENERIC_COL_NAME((String)entry.getValue());
					//System.out.println(entry.getKey() + ", " + hMapValue);
				}
				if (entry.getKey().equals("TARGET_ENTITY")) {
					hMapValue = (String)entry.getValue();
					mSheet.setTARGET_ENTITY((String)entry.getValue());
					//System.out.println(entry.getKey() + ", " + hMapValue);
				}
				if (entry.getKey().equals("TARGET_COL_NAME")) {
					hMapValue = (String)entry.getValue();
					mSheet.setTARGET_COL_NAME((String)entry.getValue());
					//System.out.println(entry.getKey() + ", " + hMapValue);
				}
				if (entry.getKey().equals("REQUIRED_FLAG")) {
					hMapValue = (String)entry.getValue();
					mSheet.setREQUIRED_FLAG((String)entry.getValue());
					//System.out.println(entry.getKey() + ", " + hMapValue);
				}
				if (entry.getKey().equals("DEFAULT_VALUE_IF_OPTIONAL")) {
					hMapValue = (String)entry.getValue();
					mSheet.setDEFAULT_VALUE_IF_OPTIONAL((String)entry.getValue());
					//System.out.println(entry.getKey() + ", " + hMapValue);
				}
				if (entry.getKey().equals("INGESTION_RULE")) {
					hMapValue = (String)entry.getValue();
					mSheet.setINGESTION_RULE((String)entry.getValue());
					//System.out.println(entry.getKey() + ", " + hMapValue);
				}
			}
			if (hMapKeyGeneric != null & mSheet != null) {
				mappingSheetDataMapUsingGenericColumn.put(hMapKeyGeneric, mSheet);
				//System.out.println("Mapping row loaded: KEY:" + hMapKeyGeneric + ", VALUE:" + mSheet.toString());
			}
			if (hMapKeySource != null & mSheet != null) {
				mappingSheetDataMapUsingSourceColumn.put(hMapKeySource, mSheet);
				//System.out.println("Mapping row loaded: KEY:" + hMapKeySource + ", VALUE:" + mSheet.toString());
			}
		}
		System.out.println("Mapping sheet using generic column loaded:" + mappingSheetDataMapUsingGenericColumn.toString());
		System.out.println("Mapping sheet using source column loaded:" + mappingSheetDataMapUsingSourceColumn.toString());
	}

}
