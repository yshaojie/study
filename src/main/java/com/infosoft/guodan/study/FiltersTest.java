package com.infosoft.guodan.study;

import java.util.Map;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class FiltersTest {

	/**
	 * @param args
	 */
	Client client = new TransportClient()
	.addTransportAddress(new InetSocketTransportAddress(
			"168.160.99.12", 9300));;
	public static void main(String[] args) {
		FiltersTest ft=new FiltersTest();
		ft.andFilter();
		ft.boolFilter();
		ft.prefixFilter();

	}
	/**
	 * and filter
	 */
   public void andFilter(){
	   SearchResponse sr = client.prepareSearch("products").setTypes("study")
			   .setFilter(FilterBuilders.andFilter(
					   FilterBuilders.rangeFilter("price").from(40).to(60),
					   FilterBuilders.termFilter("id", "3")))
					   .execute()
					   .actionGet();
	   SearchHits sh=sr.getHits();
	   SearchHit[] s=sh.getHits();
	   for(int i=0;i<s.length;i++){
		   SearchHit result=s[i];
		   Map rs=result.getSource();
		   System.out.println("and filter结果："+rs);
	   }
   }
   /**
    * bool filter
    */
   public void boolFilter(){
	   SearchResponse sr = client.prepareSearch("products").setTypes("study")
			   .setFilter(FilterBuilders.boolFilter()
					   .must(FilterBuilders.termFilter("id", "2"))
					   .should(FilterBuilders.rangeFilter("price").from(20).to(60).includeLower(false).includeUpper(true)))
					   .execute()
					   .actionGet();
	   SearchHits sh=sr.getHits();
	   SearchHit[] s=sh.getHits();
	   for(int i=0;i<s.length;i++){
		   SearchHit result=s[i];
		   Map rs=result.getSource();
		   System.out.println("bool filter结果："+rs);	   
   }
   }
   /**
    * prefix filter
    */
   public void prefixFilter(){
	   SearchResponse sr = client.prepareSearch("products")
			   .setFilter(FilterBuilders.prefixFilter("name", "洗"))
					   .execute()
					   .actionGet();
	   SearchHits sh=sr.getHits();
	   SearchHit[] s=sh.getHits();
	   for(int i=0;i<s.length;i++){
		   SearchHit result=s[i];
		   Map rs=result.getSource();
		   System.out.println("prefix filter结果："+rs);
   }
   } 
}
