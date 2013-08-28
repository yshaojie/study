package com.infosoft.esjava.study;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class Search {
    private Node node;
	Client client;

	public void setupNode() {
		
		  node = NodeBuilder.nodeBuilder().local(true).node();
		  client = node.client();
		
/*
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "elasticsearch").build();
		client = new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(
						"localhost", 9300));*/
	}

	public void searchIndex() {
		String queryString = "鲁棒性";
		QueryBuilder query = QueryBuilders.queryString(queryString);
		SearchRequestBuilder searchBuilder = client.prepareSearch("pdfindex")
				.setQuery(query)
				.addField("title")
				.addHighlightedField("file");

		SearchResponse search = searchBuilder.execute().actionGet();
		System.out.println(search.getHits().totalHits());
		System.out.println(search.getHits().getHits().length);
		// System.out.println(search.getHits().getAt(0).getFields().get("file"));
		System.out.println(search.getHits().getAt(0).highlightFields().get("file"));
	}

	public void closeNode() {
		client.close();
		node.close();
	}

	public static void main(String[] args) {
		Search search = new Search();
		search.setupNode();
		search.searchIndex();
		search.closeNode();
	}

}
