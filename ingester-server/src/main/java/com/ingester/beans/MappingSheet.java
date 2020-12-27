package com.ingester.beans;

import java.io.Serializable;

public class MappingSheet implements Serializable {
	private static final long serialVersionUID = 1062480979429311320L;
	private String SOURCE_ENTITY;
	private String SOURCE_COL_NAME;
	private String GENERIC_COL_NAME;
	private String TARGET_ENTITY;
	private String TARGET_COL_NAME;
	private String INGESTION_RULE;
	public String getSOURCE_ENTITY() {
		return SOURCE_ENTITY;
	}
	public void setSOURCE_ENTITY(String sOURCE_ENTITY) {
		SOURCE_ENTITY = sOURCE_ENTITY;
	}
	public String getSOURCE_COL_NAME() {
		return SOURCE_COL_NAME;
	}
	public void setSOURCE_COL_NAME(String sOURCE_COL_NAME) {
		SOURCE_COL_NAME = sOURCE_COL_NAME;
	}
	public String getGENERIC_COL_NAME() {
		return GENERIC_COL_NAME;
	}
	public void setGENERIC_COL_NAME(String gENERIC_COL_NAME) {
		GENERIC_COL_NAME = gENERIC_COL_NAME;
	}
	public String getTARGET_ENTITY() {
		return TARGET_ENTITY;
	}
	public void setTARGET_ENTITY(String tARGET_ENTITY) {
		TARGET_ENTITY = tARGET_ENTITY;
	}
	public String getTARGET_COL_NAME() {
		return TARGET_COL_NAME;
	}
	public void setTARGET_COL_NAME(String tARGET_COL_NAME) {
		TARGET_COL_NAME = tARGET_COL_NAME;
	}
	public String getINGESTION_RULE() {
		return INGESTION_RULE;
	}
	public void setINGESTION_RULE(String iNGESTION_RULE) {
		INGESTION_RULE = iNGESTION_RULE;
	}
	@Override
	public String toString() {
		return "MappingSheet [SOURCE_ENTITY=" + SOURCE_ENTITY + ", SOURCE_COL_NAME=" + SOURCE_COL_NAME
				+ ", GENERIC_COL_NAME=" + GENERIC_COL_NAME + ", TARGET_ENTITY=" + TARGET_ENTITY + ", TARGET_COL_NAME="
				+ TARGET_COL_NAME + ", INGESTION_RULE=" + INGESTION_RULE + "]";
	}
	
}