package com.infosoft.guodan.study;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSONObject;

public class ParseFiles {

    public static void readTxt(String filepath) throws IOException{
    	File file=new File(filepath); 
    	if(file.exists()&&file.isFile()){
    		 InputStreamReader input=new  InputStreamReader(new FileInputStream(file));
    		 BufferedReader br=new BufferedReader(input);
    		 String lineTxt = null;
             while((lineTxt = br.readLine())!= null){
                   lineTxt=lineTxt.substring(24, lineTxt.length()-1);
           //        System.out.println(lineTxt);
                   String[] value=lineTxt.split(",");
                   JSONObject json = new JSONObject();
                   json.put("userid", value[0]);
                   json.put("username", value[1]);
                   json.put("age", value[2]);
                   System.out.println(json.get("userid"));
                   System.out.println(json.get("username"));
                   System.out.println(json.get("age"));
                   
                   
             }
    	}
    }
}