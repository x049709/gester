package com.ingester.loaders;

public class ParameterLoader {
	private static String targetEntity;
	private static String sourceEntity;
	private static String targetSys;
	private static String sourceType;

	public static String getTargetEntity() {
		return targetEntity;
	}
	public static void setTargetEntity(String targetEntity) {
		ParameterLoader.targetEntity = targetEntity;
	}
	public static String getSourceEntity() {
		return sourceEntity;
	}
	public static void setSourceEntity(String sourceEntity) {
		ParameterLoader.sourceEntity = sourceEntity;
	}
	public static String getTargetSys() {
		return targetSys;
	}
	public static void setTargetSys(String targetSys) {
		ParameterLoader.targetSys = targetSys;
	}
	public static String getSourceType() {
		return sourceType;
	}
	public static void setSourceType(String sourceType) {
		ParameterLoader.sourceType = sourceType;
	}
	public ParameterLoader() {
		System.out.println("ParameterLoader default constructor done");
	}	
	public static void LoadParameterList(String sourceTypeIn, String sourceEntityIn, String targetSysIn, String targetEntityIn) {
		setSourceType(sourceTypeIn);
		setSourceEntity(sourceEntityIn);
		setTargetSys(targetSysIn);
		setTargetEntity(targetEntityIn);
		System.out.println("ParameterLoader parameters loaded: SOURCETYPE:" + getSourceType() + ", SOURCENTITY:" + getSourceEntity() + ", TARGETSYS:" + getTargetSys() + ", TARGETENTITY:" + getTargetEntity());
	}   
}
