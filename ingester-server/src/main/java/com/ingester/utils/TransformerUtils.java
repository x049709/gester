package com.ingester.utils;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import com.ingester.beans.IngesterIncomingPayload;
import com.ingester.beans.IngesterOutgoingPayload;

public class TransformerUtils {
	
	public TransformerUtils (){}
	
	public String getStringProperty(String propertyName, IngesterIncomingPayload genericIncoming) throws NoSuchMethodException {
		
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

	public void setStringProperty(String propertyName, String propertyValue, IngesterOutgoingPayload genericOutgoing) throws NoSuchMethodException {
		
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
	public int getIntProperty(String propertyName, IngesterIncomingPayload genericIncoming) throws NoSuchMethodException {
		
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

	public void setIntProperty(String propertyName, int propertyValue, IngesterOutgoingPayload genericOutgoing) throws NoSuchMethodException {
		
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

	public void setStringProperty(String propertyName, String propertyValue, IngesterIncomingPayload genericIncoming) throws NoSuchMethodException {
		
		try {
			PropertyUtils.setSimpleProperty(genericIncoming, propertyName, propertyValue);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
public void setIntProperty(String propertyName, int propertyValue, IngesterIncomingPayload genericIncoming) throws NoSuchMethodException {
		
		try {
			PropertyUtils.setSimpleProperty(genericIncoming, propertyName, propertyValue);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}