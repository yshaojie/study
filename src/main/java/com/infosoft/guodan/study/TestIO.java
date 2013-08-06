package com.infosoft.guodan.study;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.alibaba.fastjson.JSONObject;

public class TestIO {

	static BufferedReader BR = null;
	static long Count = 0;
    public static void main(String[] args) throws IOException {
		String filepath = args[0];
		Trvs(new File(filepath));
		System.out.print(Count);
	}

	static void Trvs(File f) throws IOException {
		File[] childs = f.listFiles();
		for (int i = 0; i < childs.length; i++) {
		if (childs[i].isFile()) {
			/*	if(childs[i].getPath().endsWith("log.txt"))
				{
					childs[i]=childs[i+1];
				}
				else{*/
				BR = new BufferedReader(new FileReader(childs[i]));
				String value;
				while ((value=BR.readLine())!= null) {
					if(value.length()!=0&&value.startsWith("("))
					{	
						   
					   /*  value=value.substring(1, value.length()-1);
				             String[] value1=value.split(",");
				             JSONObject json = new JSONObject();
				             json.put("userid", value1[0]);
				             json.put("username", value1[1]);
				             json.put("age", value1[2]);
				             System.out.println(json);*/
				             Count += 1;
					}
					else{
						Count += 0;
					}	
				}
				//}
			} else
				Trvs(childs[i]);
		}
	}
  
}