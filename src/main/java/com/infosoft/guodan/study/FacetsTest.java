package com.infosoft.guodan.study;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.histogram.HistogramFacet;
import org.elasticsearch.search.facet.histogram.HistogramFacetBuilder;
import org.elasticsearch.search.facet.range.RangeFacet;
import org.elasticsearch.search.facet.terms.*;

public class FacetsTest {

	/**几种类型的聚类
	 * @param args
	 */
	Client client = new TransportClient()
			.addTransportAddress(new InetSocketTransportAddress(
					"168.160.99.12", 9300));;

	public static void main(String[] args) {
		
		FacetsTest ft = new FacetsTest();
		ft.termsFacet();
		ft.rangeFacet();
		ft.histogramFacet();
	}

	/**
	 * 聚类 terms Facet
	 */
	public void termsFacet() {

		SearchResponse sr = client.prepareSearch("user").setTypes("type")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.termQuery("age", "13"))
				.addFacet(FacetBuilders.termsFacet("tt").field("age").size(3))
				.execute().actionGet();
		TermsFacet tt = (TermsFacet) sr.getFacets().facetsAsMap().get("tt");
		System.out.println("terms facet结果如下：");
		System.out.println("总条数：" + tt.getTotalCount() + "未显示的条数："
				+ tt.getOtherCount() + "丢失的条数：" + tt.getMissingCount());
		for (TermsFacet.Entry entry : tt) {
			System.out.println("得到term：" + entry.getTerm() + "得到条数："
					+ entry.getCount());
		}
	}

	/**
	 * 聚类 range Facet
	 */
	public void rangeFacet() {
		SearchResponse sr = client
				.prepareSearch("products")
				.setTypes("study")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.rangeQuery("price"))
				.addFacet(
						FacetBuilders.rangeFacet("tt").field("price")
								.addUnboundedFrom(40).addRange(40, 90)
								.addUnboundedTo(90)).execute().actionGet();
		RangeFacet tt = (RangeFacet) sr.getFacets().facetsAsMap().get("tt");
		System.out.println("range facet结果如下：");
		for (RangeFacet.Entry entry : tt) {
			System.out.println("range from:" + entry.getFrom());
			System.out.println("range to:" + entry.getTo());
			System.out.println("Doc count:" + entry.getCount());
			System.out.println("最小值：" + entry.getMin());
			System.out.println("最大值：" + entry.getMax());
			System.out.println("平均值：" + entry.getMean());
			System.out.println("所有值之和：" + entry.getTotal());

		}	
		
	}
	/**
	 * 聚类 histogram Facet
	 */
	public void histogramFacet() {
		HistogramFacetBuilder facet = FacetBuilders.histogramFacet("f")
			    .field("price")
			    .interval(10);
		SearchResponse sr = client
				.prepareSearch("products")
				.setTypes("study")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.addFacet(facet).execute().actionGet();
		HistogramFacet f = (HistogramFacet) sr.getFacets().facetsAsMap().get("f");
		System.out.println("histogram facet结果如下：");
		for (HistogramFacet.Entry entry : f) {
			System.out.print("x轴数据:" + entry.getKey()+"\t");
			System.out.println("y轴数据:" + entry.getCount());

		}	
		client.close();
	}
}
