package com.ingester.loaders;

public class StaticJSONMappingSheetLoader {
	private static String targetEntity;
	private static String mappingSheet;
	
	public static String getTargetEntity() {
		return targetEntity;
	}
	public static void setTargetEntity(String targetEntity) {
		StaticJSONMappingSheetLoader.targetEntity = targetEntity;
	}
	public static void LoadTargetEntity(String targetEntity) {
		setTargetEntity(targetEntity);
		System.out.println("MappingSheetLoader static method with input:" + targetEntity);
	}
	public static String getMappingSheet() {
		return mappingSheet;
	}
	public static void setMappingSheet(String mappingSheet) {
		StaticJSONMappingSheetLoader.mappingSheet = mappingSheet;
	}
	public StaticJSONMappingSheetLoader() {
		System.out.println("MappingSheetLoader default constructor done");
	}	
	public static void LoadJSONMappingSheet(String mappingSheet) {
		setMappingSheet(mappingSheet);
		//System.out.println("MappingSheetLoader static method with input string:" + mappingSheet);
	}	

}
