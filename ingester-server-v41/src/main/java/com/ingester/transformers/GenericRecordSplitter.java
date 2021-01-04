package com.ingester.transformers;


import com.ingester.beans.IngesterIncomingPayload;
import com.ingester.exceptions.TransformException;

public class GenericRecordSplitter {	

	public GenericRecordSplitter() {}

	public IngesterIncomingPayload transformIncoming(IngesterIncomingPayload incomingPayload) throws TransformException {

		if (incomingPayload.getInFieldMsg().equalsIgnoreCase("NONE") || incomingPayload.getInFieldMsg().equalsIgnoreCase("NOTMAPPED")) {} 
		else {throw new TransformException("Error in validating record");}
		
		return incomingPayload;
	}

}


