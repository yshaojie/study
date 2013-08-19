package com.infosoft.ysj.study;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;


/**
 * 数字时间显示
 * @author gd
 *
 */
public class Clock extends Canvas implements Runnable {

	MainFrame mf;
	Thread t;
	String time;
	Clock(MainFrame mf){
		this.mf=mf;
		setSize(400,40);
		setBackground(Color.white);
		t=new Thread(this);//实例化线程
		t.start(); //调用线程
	}
	public void run(){
		while(true){
			try{
				t.sleep(1000);//休眠一分钟
				
			}catch(InterruptedException e){
				System.out.println("异常");
				
			}
			this.repaint(100);
			
		}
	}
	public void paint(Graphics g){
		Font f=new Font("宋体",Font.BOLD,16);
		SimpleDateFormat SDF=new SimpleDateFormat("yyyy'年'MM'月'dd'日'HH:mm:ss");//格式化时间显示类型
		Calendar now=Calendar.getInstance();
		time=SDF.format(now.getTime()); //得到当前日期
		g.setFont(f);
		g.setColor(Color.orange);
		g.drawString(time, 100, 25);
	}
}
