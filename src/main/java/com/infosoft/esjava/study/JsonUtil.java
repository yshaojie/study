package com.infosoft.esjava.study;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

public class JsonUtil {
/**
 * 将实例对象转换为json对象
 * @param product
 * @return
 */
	public static String obj2JsonData(Products product){
		String jsondata=null;
		 try {
			XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
			jsonBuild.startObject()
			.field("id",product.getId())
			.field("name",product.getName())
			.field("price",product.getPrice())
			.endObject();
			jsondata=jsonBuild.string();		
			System.out.println(jsondata);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return jsondata;
	}
}
