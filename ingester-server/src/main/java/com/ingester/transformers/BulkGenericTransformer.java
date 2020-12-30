package com.ingester.transformers;

import java.util.ArrayList;

import com.ingester.beans.GenericIncomingPayload;

public class BulkGenericTransformer {
	
	public BulkGenericTransformer() {
		System.out.println("BulkGenericTransformer default constructor done");
	}	
	public ArrayList<GenericIncomingPayload> transformIncoming(ArrayList<GenericIncomingPayload> incomingDataArrayListIn) {
        for(GenericIncomingPayload row : incomingDataArrayListIn) {
        	System.out.println(row.toString());
        }
        return incomingDataArrayListIn;
	}

}
