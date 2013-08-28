package com.infosoft.esjava.study;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import com.infosoft.esjava.study.util.FileUtil;

public final class PdfIndex {
 // private Node node;
    Client client;
    private String indexname="pdfindex";
	private String indextype="pdftype";
	public static void main(String[] args) throws IOException {
		String path=args[0];
		PdfIndex pin=new PdfIndex();
		pin.setupClient();
		pin.indexPDF(path);
		//pin.closeClient();

	}
	 public void setupClient() {
		 /* node = NodeBuilder.nodeBuilder().local(true).node();
		  client = node.client();*/
		 client=new TransportClient().addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		  }
	 
	public void indexPDF(String path) throws IOException{
		 //建立mapping
		XContentBuilder map = XContentFactory.jsonBuilder().startObject()
		            .startObject(indextype)
		              .startObject("properties")
		                .startObject("file")
		                  .field("type","attachment")
		                  .startObject("fields")
		                    .startObject("title")
		                      .field("store", "yes")
		                    .endObject()
		                    .startObject("file")
		                      .field("term_vector","with_positions_offsets")
		                      .field("store","yes")
		                    .endObject()
		                  .endObject()
		                .endObject()
		              .endObject()
		         .endObject();
		 /* TermVector选项
		  TermVector.YES – 最基本的向量存储(特殊性,数量,在哪个文档)
		  TermVector.WITH_POSITIONS – TermVector.YES+位置
		  TermVector.WITH_OFFSETS – TermVector.YES+偏移
		  TermVector.WITH_POSITIONS_OFFSETS – TermVector.YES+位置+偏移
		  TermVector.NO – 不做向量存储*/
		   try { //如果已存在该名字的索引，则抛出异常
		      client.admin().indices().prepareDelete(indexname).execute().actionGet();
		    } catch (Exception ex) {
		    	ex.printStackTrace();
		    }
		    //建立索引
		    CreateIndexResponse resp = client.admin().indices().prepareCreate(indexname).setSettings(
		            ImmutableSettings.settingsBuilder()
		            .put("number_of_shards", 1)
		            .put("index.numberOfReplicas", 1))
		            .addMapping("attachment", map).execute().actionGet();    
		    BulkRequestBuilder bulkRequest = client.prepareBulk().setRefresh(true);
		    //文件所在位置url
		    List<String> filelist= FileUtil.listFileFullPath(path, "pdf", true);
		   
		    //编码为base64并进行批量建索引  
		    long start = System.currentTimeMillis();
		    int count=0;
		    for(int i=0;i<filelist.size();i++){
		    String data64 = org.elasticsearch.common.Base64.encodeFromFile(filelist.get(i));	
		    
		    XContentBuilder source = XContentFactory.jsonBuilder().startObject()
		            .field("file", data64).endObject();
		    bulkRequest.add(client.prepareIndex(indexname, indextype, i + "")
		            .setSource(source));   
		    count++;
		    if(count==2)
		    {
		    	BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		    	count=0;
		    }
		    }
		    BulkResponse bulkResponse = bulkRequest.execute().actionGet();
			if (bulkResponse.hasFailures()) {
				System.out.print("导入索引失败！");
			}
			System.out.print("用时：");
			System.out.print(System.currentTimeMillis() - start);
			
	}
	public void closeClient(){
		client.close();
	}

}
