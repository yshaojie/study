package com.infosoft.guodan.study;

import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class ESClient {
	
	private Client client;
	/**es客户端连接,并进行信息检索
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ESClient esclient=new ESClient();
		esclient.initESClient();
		esclient.search("guodan");
		esclient.closeESClient();		
		
	}	
	/**
	 * 初始化客户端连接
	 */
	public void initESClient(){
		//配置es
		Settings settings=ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").build();
		//连接集群的服务器
	    client=new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("168.160.99.12", 9300));
	}
	/**
	 * 关闭客户端连接
	 */
	public void closeESClient(){
		client.close();
	}
	/**
	 * 根据字符串进行搜索
	 * @param q
	 */
	public void search(String q){
		//创建查询索引
		SearchRequestBuilder searchRequestBuilder=client.prepareSearch("user");
		//设置查询索引类型
		searchRequestBuilder.setTypes("type");
		// 设置查询类型
				// 1.SearchType.DFS_QUERY_THEN_FETCH = 精确查询
				// 2.SearchType.SCAN = 扫描查询,无序
				// 3.SearchType.COUNT = 不设置的话,这个为默认值...
		searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		//设置查询关键字
		searchRequestBuilder.setQuery(QueryBuilders.fieldQuery("username", q));
		//设置查询数据的位置，分页用
		searchRequestBuilder.setFrom(0);
		//设置查询结果集的最大条数
		searchRequestBuilder.setSize(60);
		//设置是否按查询匹配度排序
		searchRequestBuilder.setExplain(true);
		//返回搜索响应信息
		SearchResponse response=searchRequestBuilder.execute().actionGet();
		SearchHits searchHits=response.getHits();
		SearchHit[] hits=searchHits.getHits();
		for(int i=0;i<hits.length;i++){
			SearchHit hit=hits[i];
			Map result=hit.getSource();
			System.out.println(result);
		}
		System.out.println("search success!!");
	}


}
