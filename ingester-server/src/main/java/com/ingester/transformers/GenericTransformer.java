package com.ingester.transformers;

import java.util.HashMap;

import com.ingester.beans.GenericIncomingPayload;
import com.ingester.exceptions.TransformException;
import com.ingester.loaders.LocReferenceDataLoader;
import com.ingester.utils.TransformerUtils;

public class GenericTransformer {
	private GenericIncomingPayload genericIncoming;

	public GenericIncomingPayload getGenericIncoming() {
		return genericIncoming;
	}
	public void setGenericIncoming(GenericIncomingPayload genericIncoming) {
		this.genericIncoming = genericIncoming;
	}

	public GenericTransformer(GenericIncomingPayload genericIncoming) throws TransformException, NoSuchMethodException{
		this.setGenericIncoming(genericIncoming);
		TransformerUtils tUtils = new TransformerUtils(genericIncoming);
		HashMap<String, String> locRef= LocReferenceDataLoader.getLocReference();
		if (genericIncoming.getInField001().equalsIgnoreCase("1")) {
			String errorMsg = "Error in transform";
			this.genericIncoming.setInField050(errorMsg);		
			throw new TransformException(errorMsg);		
		}
	}

	/*
	 * public GenericTransformer(GenericIncomingPayload genericIncoming) {
	 * this.setGenericIncoming(genericIncoming);
	 * System.out.println("GenericTransformer Constructor Input: " +
	 * genericIncoming.toString()); HashMap<String, String> locRef=
	 * LocReferenceDataLoader.getLocReference();
	 * System.out.println("LocTransformer LocReferenceData:");
	 * locRef.entrySet().forEach(entry->{ System.out.println(entry.getKey() + " " +
	 * entry.getValue()); });
	 * 
	 * }
	 */
	/*
	 * public LocIncoming calcPay() {
	 * System.out.println("LocTransformer calcPay invoked"); Double pay = 2000.0;
	 * String loc = this.locIncoming.getLOC(); boolean isNumeric =
	 * loc.chars().allMatch( Character::isDigit ); if (isNumeric) { pay = pay +
	 * Integer.valueOf(loc); } System.out.println("LocTransformer calcPay: " + pay +
	 * " loc: " + loc);
	 * this.getLocIncoming().setU_DISTRICTDESCRIPTION(pay.toString());
	 * this.getLocIncoming().setU_DISTRICTID("C-ID");
	 * System.out.println("LocTransformer calcPay is returning:" +
	 * this.getLocIncoming()); return this.getLocIncoming();
	 * 
	 * }
	 */
}


