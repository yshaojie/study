package com.infosoft.guodan.study;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class IndexTest {

	BufferedReader BR = null;
	static long Count = 0;
/**
 * 遍历一个目录下的文本文件，将文件内容解析成json格式，并存入json数组中
 * @param f
 * @return Json数组
 * @throws IOException
 */
	public JSONArray trvs(File f) throws IOException {
		JSONArray array = new JSONArray();
		File[] childs = f.listFiles();// 文件路径数组
		for (int i = 0; i < childs.length; i++) {
			if (childs[i].isFile()) {
				BR = new BufferedReader(new FileReader(childs[i]));
				String value;
				while ((value = BR.readLine()) != null) {// 读文件，解析出文件内容并得到行数
					JSONObject json = new JSONObject();
					if (value.length() != 0 && value.startsWith("(")) {
						value = value.substring(1, value.length() - 1);
						String[] value1 = value.split(",");
						json.put("userid", value1[0]);
						json.put("username", value1[1]);
						json.put("age", value1[2]);
						array.add(json);
						Count += 1;
					} else {
						Count += 0;
					}
				}
			} else
				trvs(childs[i]);
		}
		return array;
	}
    /**
     * 根据json数组中的内容，将所有的json中每一列value参数弄成一个数组，对应不同的字段，然后循环添加索引数据
     * @param array
     * @throws IOException
     */
	public void importBulkIndex(JSONArray array) throws IOException {
		
		Node node = nodeBuilder().node();// 建一个node
		Client client = node.client();// 建一个client
		BulkRequestBuilder bulkRequest = client.prepareBulk();// 批处理请求
		JSONObject json = null;
		ArrayList<String> userids = new ArrayList<String>();// 为三个字段分别建一个数组
		ArrayList<String> usernames = new ArrayList<String>();
		ArrayList<String> ages = new ArrayList<String>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < array.size(); i++) {// 循环得到json数组中的每一个json对象，即每一行数据
			int count1=0;
			json = (JSONObject) array.get(i);
			userids.add(json.getString("userid"));// 将得到的该行userid的数据添加到数组中
			usernames.add(json.getString("username"));
			ages.add(json.getString("age"));
			bulkRequest.add(client.prepareIndex("user", "type", i + "")
					.setSource(
							jsonBuilder().startObject()
									.field("userid", userids.get(i))
									.field("username", usernames.get(i))
									.field("age", ages.get(i)).endObject()));// 批量请求中添加数据
			BulkResponse bulkResponse = bulkRequest.execute().actionGet();
			if (bulkResponse.hasFailures()) {
				System.out.print("导入索引失败！");
			}
			count1++;
			if(count1==5){
				bulkRequest.execute();
			}
		}
		System.out.print("用时：");
		System.out.print(System.currentTimeMillis() - start);
	}

	public static void main(String[] args) throws IOException {
		String filepath = args[0];
		IndexTest it = new IndexTest();
		JSONArray array = it.trvs(new File(filepath));
		System.out.println("--->array=" + array);
		System.out.print(array.size());
		it.importBulkIndex(array);

	}

}
