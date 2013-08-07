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

public class Qikan {

	BufferedReader BR = null;
	public static void main(String[] args) throws IOException {
		String filepath = args[0];
		Qikan qk = new Qikan();
		JSONArray array = qk.trvs(new File(filepath));
		System.out.println(array.size());
		qk.importBulkIndex(array);

	}
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
				while ((value = BR.readLine()) != null) {// 读文件，解析出文件内容
					JSONObject json = new JSONObject();
					if (value.length() != 0 && value.startsWith("QK")) {
						value=value.replace("\\N", "Null");
						String[] value1 = value.split("\\|\\|");
						json.put("_ID", value1[0]);
						json.put("WID", value1[1]);
						json.put("VID", value1[2]);
						json.put("NID", value1[3]);
						json.put("LID", value1[4]);
						json.put("TI", value1[5]);
						json.put("TIE", value1[6]);
						json.put("AU", value1[7]);
						json.put("AUE", value1[8]);
						json.put("ORGC", value1[9]);
						json.put("ORGE", value1[10]);
						json.put("FAU", value1[11]);
						json.put("FAUE", value1[12]);
						json.put("FFORG", value1[13]);
						json.put("FORGE", value1[14]);
						json.put("FN", value1[15]);
						json.put("JOUCN", value1[16]);
						json.put("JOUEN", value1[17]);
						json.put("DATE", value1[18]);
						json.put("VOL", value1[19]);
						json.put("PER", value1[20]);
						json.put("PG", value1[21]);
						json.put("PN", value1[22]);
						json.put("LAN", value1[23]);
						json.put("ZCID", value1[24]);
						json.put("MCID", value1[25]);
						json.put("DID", value1[26]);
						json.put("CKEY", value1[27]);
						json.put("EKEY", value1[28]);
						json.put("MKEY", value1[29]);
						json.put("CAB", value1[30]);
						json.put("EAB", value1[31]);
						json.put("fab", value1[32]);
						json.put("fund", value1[33]);
						json.put("fpn", value1[34]);
						json.put("docid", value1[35]);
						json.put("SOURCE", value1[36]);
						json.put("colcn", value1[37]);
						json.put("colen", value1[38]);
						json.put("BCORE", value1[39]);
						json.put("ZCORE", value1[40]);
						json.put("NCORE", value1[41]);
						json.put("ZKCORE", value1[42]);
						json.put("SCORE", value1[43]);
						array.add(json);
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
		ArrayList<String> _ID1 = new ArrayList<String>();// 为字段分别建一个数组
		ArrayList<String> WID1 = new ArrayList<String>();
		ArrayList<String> VID1 = new ArrayList<String>();
		ArrayList<String> NID1 = new ArrayList<String>();
		ArrayList<String> LID1 = new ArrayList<String>();
		ArrayList<String> TI1 = new ArrayList<String>();
		ArrayList<String> TIE1 = new ArrayList<String>();
		ArrayList<String> AU1 = new ArrayList<String>();
		ArrayList<String> AUE1 = new ArrayList<String>();
		ArrayList<String> ORGC1 = new ArrayList<String>();
		ArrayList<String> ORGE1 = new ArrayList<String>();
		ArrayList<String> FAU1 = new ArrayList<String>();
		ArrayList<String> FAUE1 = new ArrayList<String>();
		ArrayList<String> FFORG1 = new ArrayList<String>();
		ArrayList<String> FORGE1 = new ArrayList<String>();
		ArrayList<String> FN1 = new ArrayList<String>();
		ArrayList<String> JOUCN1 = new ArrayList<String>();
		ArrayList<String> JOUEN1 = new ArrayList<String>();
		ArrayList<String> DATE1 = new ArrayList<String>();
		ArrayList<String> VOL1 = new ArrayList<String>();
		ArrayList<String> PER1 = new ArrayList<String>();
		ArrayList<String> PG1 = new ArrayList<String>();
		ArrayList<String> PN1 = new ArrayList<String>();
		ArrayList<String> LAN1 = new ArrayList<String>();
		ArrayList<String> ZCID1 = new ArrayList<String>();
		ArrayList<String> MCID1 = new ArrayList<String>();
		ArrayList<String> DID1 = new ArrayList<String>();
		ArrayList<String> CKEY1 = new ArrayList<String>();
		ArrayList<String> EKEY1 = new ArrayList<String>();
		ArrayList<String> MKEY1 = new ArrayList<String>();
		ArrayList<String> CAB1 = new ArrayList<String>();
		ArrayList<String> EAB1 = new ArrayList<String>();
		ArrayList<String> fab1 = new ArrayList<String>();
		ArrayList<String> fund1 = new ArrayList<String>();
		ArrayList<String> fpn1 = new ArrayList<String>();
		ArrayList<String> docid1 = new ArrayList<String>();
		ArrayList<String> SOURCE1= new ArrayList<String>();
		ArrayList<String> colcn1 = new ArrayList<String>();
		ArrayList<String> colen1 = new ArrayList<String>();
		ArrayList<String> BCORE1 = new ArrayList<String>();
		ArrayList<String> ZCORE1 = new ArrayList<String>();
		ArrayList<String> NCORE1 = new ArrayList<String>();
		ArrayList<String> ZKCORE1 = new ArrayList<String>();
		ArrayList<String> SCORE1 = new ArrayList<String>();
		long start = System.currentTimeMillis();
		int count1=0;
		for (int i = 0; i < array.size(); i++) {// 循环得到json数组中的每一个json对象，即每一行数据
			json = (JSONObject) array.get(i);
			_ID1.add(json.getString("_ID"));// 将得到的每一列的数据添加到数组中
			WID1.add(json.getString("WID"));
			VID1.add(json.getString("VID"));
			NID1.add(json.getString("NID"));
			LID1.add(json.getString("LID"));
			TI1.add(json.getString("TI"));
			TIE1.add(json.getString("TIE"));
			AU1.add(json.getString("AU"));
			AUE1.add(json.getString("AUE"));
			ORGC1.add(json.getString("ORGC"));
			ORGE1.add(json.getString("ORGE"));
			FAU1.add(json.getString("FAU"));
			FAUE1.add(json.getString("FAUE"));
			FFORG1.add(json.getString("FFORG"));
			FORGE1.add(json.getString("FORGE"));
			FN1.add(json.getString("FN"));
			JOUCN1.add(json.getString("JOUCN"));
			JOUEN1.add(json.getString("JOUEN"));
			DATE1.add(json.getString("DATE"));
			VOL1.add(json.getString("VOL"));
			PER1.add(json.getString("PER"));
			PG1.add(json.getString("PG"));
			PN1.add(json.getString("PN"));
			LAN1.add(json.getString("LAN"));
			ZCID1.add(json.getString("ZCID"));
			MCID1.add(json.getString("MCID"));
			DID1.add(json.getString("DID"));
			CKEY1.add(json.getString("CKEY"));
			EKEY1.add(json.getString("EKEY"));
			MKEY1.add(json.getString("MKEY"));
			CAB1.add(json.getString("CAB"));
			EAB1.add(json.getString("EAB"));
			fab1.add(json.getString("fab"));
			fund1.add(json.getString("fund"));
			fpn1.add(json.getString("fpn"));
			docid1.add(json.getString("docid"));
			SOURCE1.add(json.getString("SOURCE"));
			colcn1.add(json.getString("colcn"));
			colen1.add(json.getString("colen"));
			BCORE1.add(json.getString("BCORE"));
			ZCORE1.add(json.getString("ZCORE"));
			NCORE1.add(json.getString("NCORE"));
			ZKCORE1.add(json.getString("ZKCORE"));
			SCORE1.add(json.getString("SCORE"));	
			bulkRequest.add(client.prepareIndex("qikan", "test", i + "")
					.setSource(
							jsonBuilder().startObject()
									.field("_ID", _ID1.get(i))
									.field("WID", WID1.get(i))
									.field("VID", VID1.get(i))
									.field("NID", NID1.get(i))
									.field("LID", LID1.get(i))
									.field("TI", TI1.get(i))
									.field("TIE", TIE1.get(i))
									.field("AU", AU1.get(i))
									.field("AUE", AUE1.get(i))
									.field("ORGC", ORGC1.get(i))
									.field("ORGE", ORGE1.get(i))
									.field("FAU", FAU1.get(i))
									.field("FAUE", FAUE1.get(i))
									.field("FFORG", FFORG1.get(i))
									.field("FORGE", FORGE1.get(i))
									.field("FN", FN1.get(i))
									.field("JOUCN", JOUCN1.get(i))
									.field("JOUEN", JOUEN1.get(i))
									.field("DATE", DATE1.get(i))
									.field("VOL", VOL1.get(i))
									.field("PER", PER1.get(i))
									.field("PG", PG1.get(i))
									.field("PN", PN1.get(i))
									.field("LAN", LAN1.get(i))
									.field("ZCID", ZCID1.get(i))
									.field("MCID", MCID1.get(i))
									.field("DID", DID1.get(i))
									.field("CKEY", CKEY1.get(i))
									.field("EKEY", EKEY1.get(i))
									.field("MKEY", MKEY1.get(i))
									.field("CAB", CAB1.get(i))
									.field("EAB", EAB1.get(i))
									.field("fab", fab1.get(i))
									.field("fund", fund1.get(i))
									.field("fpn", fpn1.get(i))
									.field("docid", docid1.get(i))
									.field("SOURCE", SOURCE1.get(i))
									.field("colcn", colcn1.get(i))
									.field("colen", colen1.get(i))
									.field("BCORE", BCORE1.get(i))
									.field("ZCORE", ZCORE1.get(i))
									.field("NCORE", NCORE1.get(i))
									.field("ZKCORE", ZKCORE1.get(i))
									.field("SCORE", SCORE1.get(i))
								 .endObject()));// 批量请求中添加数据			
			count1++;
			if(count1==500){
				BulkResponse bulkResponse = bulkRequest.execute().actionGet();
				count1=0;
			}
			
	}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			System.out.print("导入索引失败！");
		}
		System.out.print("用时：");
		System.out.print(System.currentTimeMillis() - start);
		node.close();
	}


}
