package com.infosoft.guodan.study;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TestIO {

	static BufferedReader BR = null;
	static long Count = 0;
	public static void main(String[] args) throws IOException {
		System.out.println("args=null?  " + args == null);
		System.out.println("args.length:  " + args.length);
		for (String string : args) {
			System.out.println(string);
		}
		String filepath = args[0];
		Trvs(new File(filepath));
		System.out.print(Count);
	}

	static void Trvs(File f) throws IOException {
		File[] childs = f.listFiles();
		for (int i = 0; i < childs.length; i++) {
		if (childs[i].isFile()) {
				if(childs[i].getPath().endsWith("log.txt"))
				{
					childs[i]=childs[i+1];
				}
				else{
				BR = new BufferedReader(new FileReader(childs[i]));
				String value;
				while ((value=BR.readLine())!= null) {
//					System.out.println(value);
					//if (value.isEmpty()) {
					//	Count += 0;
					//}	
					if(value.length()!=0&&value.startsWith("20"))
					{	
						Count += 1;
					}
					else{
						Count += 0;
					}	
				}
				}
			} else
				Trvs(childs[i]);
		}
	}

}