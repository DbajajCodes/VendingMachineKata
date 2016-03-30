package com.pillar.constants;

public enum Product {

	Cola(1.00, "Cola"), Chips(0.50, "Chips"), Candy(0.65, "Candy");

	private double price;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String description;

	private Product(double price, String description){
		this.price = price;
		this.description = description;
	}
	
}
