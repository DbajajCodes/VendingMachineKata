package com.pillar.constants;

public enum DisplayMessage {
	INSERT_COIN("INSERT COIN"),
	THANK_YOU("THANK YOU"),
	PRICE("PRICE"),
	SOLD_OUT("SOLD OUT"),
	EXACT_CHANGE("EXACT CHANGE ONLY"),
	PRICE_COLA("Price - $1.00"),
	PRICE_CHIPS("Price - $0.50"),
	PRICE_CANDY("Price - $0.65");
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	private DisplayMessage(String description){
		this.description=description;
	}

}
