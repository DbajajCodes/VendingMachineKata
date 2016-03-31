package com.pillar.service;

import com.pillar.constants.DisplayMessage;
import com.pillar.constants.Product;
import com.pillar.data.CoinInventory;
import com.pillar.data.ProductInventory;

public class ProductVendor {

	private Product productSelected;
	private CoinProcessor processor;
	public CoinProcessor getProcessor() {
		return processor;
	}

	public Product getProductSelected() {
		return productSelected;
	}

	public void setProductSelected(Product productSelected) {
		this.productSelected = productSelected;
	}

	public ProductVendor(Product selection, CoinProcessor processor, ProductInventory inventory, CoinInventory coinInventory) {
		this.productSelected = selection;
		this.processor = processor;
	}

	public String vend() {
		double limit = this.processor.getBudget().getCurrentAmount();
		if(checkProductInventory()){
			return DisplayMessage.SOLD_OUT.getDescription();
		}
		if(checkCoinInventory()){
			return DisplayMessage.EXACT_CHANGE.getDescription();
		}
		if (limit >= this.productSelected.getPrice()) {
			this.processor.processPostTransaction(productSelected.getPrice());
			ProductInventory.updateInventory(productSelected);
			return DisplayMessage.THANK_YOU.getDescription();
		}
		String productDescription = productSelected.getDescription();
		switch (productDescription) {
		case "Cola":
			this.processor.processPostTransaction(0);
			return DisplayMessage.PRICE_COLA.getDescription();
		case "Chips":
			this.processor.processPostTransaction(0);
			return DisplayMessage.PRICE_CHIPS.getDescription();
		case "Candy":
			this.processor.processPostTransaction(0);
			return DisplayMessage.PRICE_CANDY.getDescription();
		}
		return null;

	}

	private boolean checkCoinInventory() {
		return CoinInventory.getDimes()==0||CoinInventory.getNickels()==0||CoinInventory.getQuarters()==0;
	}

	private boolean checkProductInventory() {
		return ((productSelected == Product.Candy && ProductInventory.getCandyCount() == 0)||(productSelected == Product.Cola && ProductInventory.getColaCount() == 0)||(productSelected == Product.Chips && ProductInventory.getChipsCount() == 0)); 
	}

	public String initiateReturn() {
		this.processor.processPostTransaction(0);
		return DisplayMessage.INSERT_COIN.getDescription();

	}

}
