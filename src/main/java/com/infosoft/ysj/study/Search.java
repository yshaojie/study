package com.infosoft.ysj.study;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class Search {

	Client client;
	String indexname="test";
	String indextype="attachment";
	
	public void setupNode() {
		
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "elasticsearch").build();
		client = new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(
						"localhost", 9300));

	}
	public void searchIndex(String querystring) {

		String queryString = "\""+querystring+"\"";
		QueryBuilder query = QueryBuilders.queryString(queryString);	
		SearchRequestBuilder searchBuilder = client.prepareSearch(indexname)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(query)
				.addField("title")
				.addHighlightedField("file");

		SearchResponse search = searchBuilder.execute().actionGet();
		SearchHits hits=search.getHits();
		SearchHit[] hit=hits.getHits();		
		System.out.println("总共搜索出"+hits.totalHits()+"条结果！！");
		
		for(int i=0;i<hit.length;i++){
		System.out.println("------搜索出第"+(i+1)+"个结果------------");
		System.out.println(search.getHits().getAt(i).highlightFields().get("file"));
	    System.out.println("---------------------------------------");
	    }
		
	}

	public void closeNode() {
		client.close();
	}

	public static void main(String[] args) {
		String querystring=args[0];
		Search search = new Search();
		search.setupNode();
		search.searchIndex(querystring);
		search.closeNode();
	}

}
