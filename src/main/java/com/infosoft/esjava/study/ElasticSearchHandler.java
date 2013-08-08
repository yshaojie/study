package com.infosoft.esjava.study;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class ElasticSearchHandler {

	/**创建索引并进行搜索
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ElasticSearchHandler esh=new ElasticSearchHandler();
		String indexname="products";//索引名
		String type="study";//类型名
		List<String> jsondata=DataFactory.getInitJsonData();//json数据集
		esh.creatIndex(indexname,type ,jsondata);//创建索引
		QueryBuilder queryBuilder=QueryBuilders.fieldQuery("name","洗面奶");//查询条件
		List<Products> result=esh.searchIndex(queryBuilder, indexname, type);//搜索
		for(int i=0;i<result.size();i++){
			Products p=result.get(i);
			System.out.println("("+p.getId()+")商品名称："+p.getName()+"商品单价："+p.getPrice());
		}
	}
	
	private Client client;
	public ElasticSearchHandler(){
		this("127.0.0.1");
	}
	public ElasticSearchHandler(String ipAddress){
		client=new TransportClient().addTransportAddress(new InetSocketTransportAddress(ipAddress,9300));
		
	}
	/**
	 * 创建索引
	 * @param indexname
	 * @param type
	 * @param jsondata
	 */
	public void creatIndex(String indexname,String type,List<String>jsondata){
		IndexRequestBuilder requestBuilder=client.prepareIndex(indexname,type).setRefresh(true);
		for(int i=0;i<jsondata.size();i++){
	
			IndexResponse response=requestBuilder.setSource(jsondata.get(i)).execute().actionGet();
		}	
	}
	/**
	 * 搜索
	 * @param queryBuilder
	 * @param indexname
	 * @param type
	 * @return List<Products>
	 */
	public List<Products> searchIndex(QueryBuilder queryBuilder, String indexname, String type){
		List<Products> list=new ArrayList<Products>();
		SearchResponse searchResponse=client.prepareSearch(indexname).setTypes(type)//进行搜索
				.setQuery(queryBuilder)
				.execute()
				.actionGet();
		SearchHits hits=searchResponse.getHits();//得到搜索结果
		System.out.println("搜索后得到的总条数："+hits.getTotalHits());
		SearchHit[] hitlist=hits.getHits();
		if(hitlist.length>0){//遍历结果得到list
			for(SearchHit hit:hitlist){
				Integer id=(Integer)hit.getSource().get("id");
				String name=(String)hit.getSource().get("name");
				Integer price=(Integer)hit.getSource().get("price");
				list.add(new Products(id,name,price));
			}
		}
		return list;
			
	}
		

  
}
