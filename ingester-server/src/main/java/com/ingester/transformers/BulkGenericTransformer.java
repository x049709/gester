package com.ingester.transformers;

import java.util.ArrayList;

import com.ingester.beans.IngesterIncomingPayload;

public class BulkGenericTransformer {
	
	public BulkGenericTransformer() {
		System.out.println("BulkGenericTransformer default constructor done");
	}	
	public ArrayList<IngesterIncomingPayload> transformIncoming(ArrayList<IngesterIncomingPayload> incomingDataArrayListIn) {
        for(IngesterIncomingPayload row : incomingDataArrayListIn) {
        	System.out.println(row.toString());
        }
        return incomingDataArrayListIn;
	}

}
