package com.infosoft.esjava.study;

import java.io.IOException;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class CheckCluster {

	/**
	 * @param args
	 */
	// TransportClient client;
	private Client client;
    private String indexname="pdfindex";
    private String indextype="pdftype";
	public static void main(String[] args) {

		CheckCluster cc = new CheckCluster();
		cc.initESClient();
		cc.returnStatus();
		cc.getMapping();
		cc.closeESClient();
		
	}

	/**
	 * 初始化客户端连接
	 */
	public void initESClient() {
		// 配置es,设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，好处是一般不用手动设置集群里所有集群的ip到连接客户端，它会自动添加，并且自动发现新加入集群的机器
		// Settings
		// settings=ImmutableSettings.settingsBuilder().put("client.transport.sniff",
		// true).build();
		// 连接集群的服务器
		// client=new TransportClient(settings);
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "elasticsearch").build();

		client = new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(
						"localhost", 9300));
	}

	/**
	 * 关闭客户端连接
	 */
	public void closeESClient() {
		client.close();
	}

	/**
	 * 返回集群状态
	 */
	public void returnStatus() {
		ClusterHealthResponse healthResponse = client.admin().cluster()
				.prepareHealth(indexname).setWaitForYellowStatus()
				.setTimeout("1s").execute().actionGet();
        System.out.println(healthResponse.getStatus());
        System.out.println(healthResponse.getActiveShards());
        System.out.println(healthResponse.getNumberOfNodes());
        System.out.println(healthResponse.getIndices());
		
	}
	public void getMapping(){
		ClusterState cs=client.admin().cluster().prepareState().setFilterIndices(indexname).execute().actionGet().getState();
		System.out.println("集群状态："+cs);
		IndexMetaData imd = cs.getMetaData().index(indexname);
		MappingMetaData mmd=imd.mapping(indextype);
		try {
			System.out.println("map:"+mmd.sourceAsMap());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
