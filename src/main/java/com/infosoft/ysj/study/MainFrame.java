package com.infosoft.ysj.study;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;


/**万年历主类
	 * @param args
	 */
public class MainFrame extends JFrame{

	JPanel panel=new JPanel(new BorderLayout());
	JPanel panel1=new JPanel();
	JPanel panel2=new JPanel(new GridLayout(7,7));
	JPanel panel3=new JPanel();
	JLabel[] label=new JLabel[49];
	JLabel y_label=new JLabel("年份");
	JLabel m_label=new JLabel("月份");
	JComboBox com1=new JComboBox();
	JComboBox com2=new JComboBox();
	JButton button=new JButton("查看");
	int re_year,re_month;
	int x_size,y_size;
	String year_num;
	Calendar now=Calendar.getInstance();	//实例化Calendar
	MainFrame(){
		super("万年历");
		setSize(300,350);
		x_size=(int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		y_size=(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		setLocation((x_size-300)/2,(y_size-350)/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel1.add(y_label);
		panel1.add(com1);
		panel1.add(m_label);
		panel1.add(com2);
		panel1.add(button);
		for(int i=0;i< 49;i++){
			label[i]=new JLabel("",JLabel.CENTER);//将显示的字符设置为居中
			panel2.add(label[i]);
		}
		panel3.add(new Clock(this));
		panel.add(panel1,BorderLayout.NORTH);
		panel.add(panel2,BorderLayout.CENTER);
		panel.add(panel3,BorderLayout.SOUTH);
		panel.setBackground(Color.white);
		panel1.setBackground(Color.white);
		panel2.setBackground(Color.white);
		panel3.setBackground(Color.white);
		Init();
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int c_year,c_month,c_week;
				c_year=Integer.parseInt(com1.getSelectedItem().toString());	//得到当前所选年份
				c_month=Integer.parseInt(com2.getSelectedItem().toString())-1;
				c_week=use(c_year,c_month);		//调用函数use，得到星期几
				Resetday(c_week,c_year,c_month);		//调用函数Resetday
			}});
		setContentPane(panel);
		setVisible(true);
		setResizable(false);
	}
	public void Init(){
	int year,month_num,first_day_num;
	String log[]={"日","一","二","三","四","五","六"};
	for(int i=0;i< 7;i++){
	label[i].setText(log[i]);
	}
	for(int i=0;i< 49;i=i+7){
		label[i].setForeground(Color.red);	//将星期日的日期设置为红色
	}
	for(int i=6;i< 49;i=i+7){
		label[i].setForeground(Color.green);//将星期六的日期设置为绿色
	}
	for(int i=1;i< 10000;i++){
		com1.addItem(""+i);
	}
	for(int i=1;i< 13;i++){
		com2.addItem(""+i);
	}
	month_num=(int)(now.get(Calendar.MONTH));	//得到当前时间的月份
	year=(int)(now.get(Calendar.YEAR));			//得到当前时间的年份
	com1.setSelectedIndex(year-1);				//设置下拉列表显示为当前年
	com2.setSelectedIndex(month_num);			//设置下拉列表显示为当前月
	first_day_num=use(year,month_num);
	Resetday(first_day_num,year,month_num);
	}
	public int use(int reyear,int remonth){
		int week_num;
		now.set(reyear,remonth,1);		//设置时间为所要查询的年月的第一天
		week_num= (int)(now.get(Calendar.DAY_OF_WEEK));//得到第一天的星期
		return week_num;
	}
	public void Resetday(int week_log,int year_log,int month_log){
		int month_score_log;	//判断是否是闰年的标记
		int month_day_score;	//存储月份的天数
		int count;
		month_score_log=0;
		month_day_score=0;
		count=1;
		if(year_log%4==0&&year_log%100!=0||year_log%400==0){//判断是否为闰年
			month_score_log=1;
		}
	month_log=month_log+1;	//将传来的月份数加1
	switch(month_log){
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
		month_day_score=31;
		break;
		case 4:
		case 6:
		case 9:
		case 11:
		month_day_score=30;
		break;
		case 2:
		if(month_score_log==1){
		month_day_score=29;	
		}
		else{
		month_day_score=28;
		}
		break;
		}
		for(int i=7;i< 49;i++){		//初始化标签
			label[i].setText("");
		}
		week_log=week_log+6;		//将星期数加6，使显示正确
		month_day_score=month_day_score+week_log;
		for(int i=week_log;i< month_day_score;i++,count++){
			label[i].setText(count+"");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame.setDefaultLookAndFeelDecorated(true);
		MainFrame start=new MainFrame();
	}

}
