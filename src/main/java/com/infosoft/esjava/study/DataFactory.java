package com.infosoft.esjava.study;

import java.util.ArrayList;
import java.util.List;

public class DataFactory {
/**
 * 数据集
 */
	public static DataFactory dataFactory=new DataFactory();
	private DataFactory(){
		
	}
	public DataFactory getInstance(){
		return dataFactory;
	}
	public static List<String> getInitJsonData(){
		List<String> list=new ArrayList<String>();
		String data1=JsonUtil.obj2JsonData(new Products(1,"洗面奶",40));
		String data2=JsonUtil.obj2JsonData(new Products(2,"爽肤水",60));
		String data3=JsonUtil.obj2JsonData(new Products(3,"沐浴露",50));
		String data4=JsonUtil.obj2JsonData(new Products(4,"乳液",100));
		String data5=JsonUtil.obj2JsonData(new Products(5,"BB霜",90));
		list.add(data1);
		list.add(data2);
		list.add(data3);
		list.add(data4);
		list.add(data5);
		return list;
	}
}
