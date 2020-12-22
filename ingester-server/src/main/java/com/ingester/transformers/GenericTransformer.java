package com.ingester.transformers;

import java.util.HashMap;

import com.ingester.beans.GenericIncomingPayload;
import com.ingester.beans.MappingSheet;
import com.ingester.exceptions.TransformException;
import com.ingester.loaders.LocReferenceDataLoader;
import com.ingester.loaders.MappingSheetSingleton;
import com.ingester.loaders.StaticMappingSheetLoader;
import com.ingester.utils.TransformerUtils;

public class GenericTransformer {
	private GenericIncomingPayload genericIncoming;

	public GenericIncomingPayload getGenericIncoming() {
		return genericIncoming;
	}
	public void setGenericIncoming(GenericIncomingPayload genericIncomingIn) 
	{
		this.genericIncoming = genericIncomingIn;
	}

	public GenericTransformer() {}
	
	public GenericTransformer(GenericIncomingPayload genericIncoming) throws TransformException, NoSuchMethodException{
	}

	public GenericIncomingPayload transformIncoming(GenericIncomingPayload genericIncoming) throws TransformException, NoSuchMethodException{
		this.setGenericIncoming(genericIncoming);
		TransformerUtils tUtils = new TransformerUtils(genericIncoming);
		HashMap<String, String> locRef= LocReferenceDataLoader.getLocReference();
		HashMap<String, MappingSheet> mapSheet= StaticMappingSheetLoader.getMappingSheetDataMap();
		HashMap<String, MappingSheet> mapSheetFromSingleton= MappingSheetSingleton.getInstance().getMappingSheetDataMap();
		
		if (genericIncoming.getInField001().equalsIgnoreCase("1")) { 
			String errorMsg = "Error in transform"; 
			this.genericIncoming.setInField004(errorMsg); 
			throw new TransformException(errorMsg); 
		}
		 
		return this.getGenericIncoming();
	}

}


