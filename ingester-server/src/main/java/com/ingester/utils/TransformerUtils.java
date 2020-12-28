package com.ingester.utils;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import com.ingester.beans.GenericIncomingPayload;
import com.ingester.beans.GenericOutgoingPayload;

public class TransformerUtils {
	
	public TransformerUtils (){}
	
	public String getStringProperty(String propertyName, GenericIncomingPayload genericIncoming) throws NoSuchMethodException {
		
		String propertyValue = null;
		try {
			propertyValue = (String)PropertyUtils.getSimpleProperty(genericIncoming, propertyName);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propertyValue;
		
	}

	public void setStringProperty(String propertyName, String propertyValue, GenericOutgoingPayload genericOutgoing) throws NoSuchMethodException {
		
		try {
			PropertyUtils.setSimpleProperty(genericOutgoing, propertyName, propertyValue);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public int getIntProperty(String propertyName, GenericIncomingPayload genericIncoming) throws NoSuchMethodException {
		
		int propertyValue = 0;
		try {
			propertyValue = (int)PropertyUtils.getSimpleProperty(genericIncoming, propertyName);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propertyValue;
		
	}

	public void setIntProperty(String propertyName, int propertyValue, GenericOutgoingPayload genericOutgoing) throws NoSuchMethodException {
		
		try {
			PropertyUtils.setSimpleProperty(genericOutgoing, propertyName, propertyValue);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}