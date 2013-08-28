package com.infosoft.esjava.study;

public class Products {
/**
 * 实体类
 */
	private Integer id;
	private String name;
	private Integer price;
	public Products(){
		super();
	}
	public Products(Integer id,String name,Integer price){
		super();
		this.id=id;
		this.name=name;
		this.price=price;
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
}
