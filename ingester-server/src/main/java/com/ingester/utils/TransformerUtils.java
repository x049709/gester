package com.ingester.utils;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.apache.commons.beanutils.PropertyUtils;

import com.ingester.beans.GenericIncomingPayload;
import com.ingester.exceptions.TransformException;
import com.ingester.loaders.LocReferenceDataLoader;

public class TransformerUtils {
	
	private GenericIncomingPayload genericIncoming;

	public TransformerUtils (){}
	
	public TransformerUtils(GenericIncomingPayload genericIncoming) throws NoSuchMethodException {
		

		try {
			PropertyUtils.setSimpleProperty(genericIncoming, "inField021", "Hello!This is a string");
			System.out.println("String Property: " + PropertyUtils.getSimpleProperty(genericIncoming, "inField021"));
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
	public String getProperty(String propertyName, GenericIncomingPayload genericIncoming) throws NoSuchMethodException {
		
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
}