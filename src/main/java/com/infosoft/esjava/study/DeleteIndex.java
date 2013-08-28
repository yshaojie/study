package com.infosoft.esjava.study;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class DeleteIndex {

	/**
	 * @param args
	 */
	private static Client client;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		client=new TransportClient().addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		DeleteByQueryResponse response = client.prepareDeleteByQuery("test")
		        .execute()
		        .actionGet();
	
	}

}
