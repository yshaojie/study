package com.infosoft.guodan.study;
import java.io.*;
public class MultiThread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("主线程");
		ThreadUseExtends thread1=new ThreadUseExtends();//实例线程1
		Thread thread2=new Thread(new ThreadUseRunnable(),"SecondThread");//实例线程2
		thread1.start();
		System.out.println("主线程将挂起7s");
		try{
			Thread.sleep(7000);
		}catch(InterruptedException e){
			return;
		}
		System.out.println("主线程醒来了");
		if(thread1.isAlive()){
			thread1.stop();
			System.out.println("thread1休眠时间过长，主线程杀掉他");
		}
		else{
			System.out.println("主线程没有发现thread1,thread1已醒，顺序执行结束");
		}
			thread2.start();		
			System.out.println("主线程挂起7s");
			try{               //主线程又休眠
				Thread.sleep(7000);
			}catch(InterruptedException e){
				return;
			}
		System.out.println("又回到主线程");
		if(thread2.isAlive()){
			thread2.stop();
			System.out.println("thread2休眠时间过长，主线程杀掉他");
		}
		else{
			System.out.println("主线程没有发现thread2,thread2已醒，顺序执行结束");
		}
		System.out.println("程序已结束，按任意键继续");
		try{
			System.in.read();
		}catch(IOException e){
			System.out.println(e.toString());
		}
	}

}
 class ThreadUseExtends extends Thread{
	
	 ThreadUseExtends(){	 
	 }
	 public void run(){
		 System.out.println("thread1线程开始");
		 System.out.println("thread1线程挂起10s");
	 try {
	 sleep(100000);}
	 catch (InterruptedException e) {
		 return;
	 }
	 System.out.println("thread1回到主线程，稍等，主线程挂起有可能还没醒来");
	 }
	 
}
 class ThreadUseRunnable implements Runnable{
	 Thread thread2=new Thread();
	 ThreadUseRunnable(){
	 }
	 public void run(){
		 System.out.println("thread2线程开始");
		 System.out.println("thread2线程挂起1秒!");
		 try {
			 Thread.sleep(1000);}
			 catch (InterruptedException e) {
				 return;
			 }
			 System.out.println("thread2回到主线程，稍等，主线程挂起有可能还没醒来");
			 } 
	 }

