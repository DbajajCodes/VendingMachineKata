package com.pillar.constants;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Coin {


	private int weight;
	private int size;
	private double value;
	private String description;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Coin(int weight, int size, double value, String description) {
		this.weight = weight;
		this.size = size;
		this.value = value;
		this.description = description;
	}
	
	@Override
	public boolean equals(Object coin) {
		if(coin!=null&&coin instanceof Coin){
			Coin other = (Coin)coin;
		return new EqualsBuilder().append(size,other.getSize()).append(weight, other.getWeight()).isEquals();
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return  new HashCodeBuilder().append(size).append(weight).append(value).append(description).hashCode();
		
	}

}
