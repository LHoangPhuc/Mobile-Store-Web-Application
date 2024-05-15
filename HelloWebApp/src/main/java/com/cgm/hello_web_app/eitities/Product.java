package com.cgm.hello_web_app.eitities;


public class Product {
	private int id;
	private String image;
	private String name;
	private double price;
	private String detail;
	public Product() {
		super();
		// TODO Auto-generated constructor stub
 	}
	public Product(int id,String image, String name, double price , String detail) {
		super();
		this.id=id;
		this.image = image;
		this.name = name;
		this.price = price;
		this.detail = detail;
	}
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	//them moi
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (id != other.id)
            return false;
        return true;
    }  
	
}
