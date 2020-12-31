package com.ingester.utils;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import com.ingester.beans.IngesterIncomingPayload;
import com.ingester.beans.IngesterOutgoingPayload;

public class GenericValidatorUtils {
	
	public GenericValidatorUtils (){}
	
	public String getStringProperty(String propertyName, IngesterIncomingPayload incomingPayload) throws NoSuchMethodException {
		
		String propertyValue = null;
		try {
			propertyValue = (String)PropertyUtils.getSimpleProperty(incomingPayload, propertyName);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propertyValue;
		
	}
	public void setStringProperty(String propertyName, String propertyValue, IngesterIncomingPayload incomingPayload) throws NoSuchMethodException {
		
		try {
			PropertyUtils.setSimpleProperty(incomingPayload, propertyName, propertyValue);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getStringProperty(String propertyName, IngesterOutgoingPayload outgoingPayload) throws NoSuchMethodException {
		
		String propertyValue = null;
		try {
			propertyValue = (String)PropertyUtils.getSimpleProperty(outgoingPayload, propertyName);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propertyValue;
		
	}
	public void setStringProperty(String propertyName, String propertyValue, IngesterOutgoingPayload outgoingPayload) throws NoSuchMethodException {
		
		try {
			PropertyUtils.setSimpleProperty(outgoingPayload, propertyName, propertyValue);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public int getIntProperty(String propertyName, IngesterIncomingPayload incomingPayload) throws NoSuchMethodException {
		
		int propertyValue = 0;
		try {
			propertyValue = (int)PropertyUtils.getSimpleProperty(incomingPayload, propertyName);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propertyValue;
		
	}
	public void setIntProperty(String propertyName, int propertyValue, IngesterOutgoingPayload outgoingPayload) throws NoSuchMethodException {
		
		try {
			PropertyUtils.setSimpleProperty(outgoingPayload, propertyName, propertyValue);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}