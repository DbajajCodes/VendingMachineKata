package com.pillar.data;

import com.pillar.constants.Product;

public class ProductInventory {
	private static int ColaCount;

	public static int getColaCount() {
		return ColaCount;
	}

	public static void setColaCount(int colaCount) {
		ColaCount = colaCount;
	}

	public static int getChipsCount() {
		return ChipsCount;
	}

	public static void setChipsCount(int chipsCount) {
		ChipsCount = chipsCount;
	}

	public static int getCandyCount() {
		return CandyCount;
	}

	public static void setCandyCount(int candyCount) {
		CandyCount = candyCount;
	}

	private static int ChipsCount;
	private static int CandyCount;

	public ProductInventory() {
		ProductInventory.setCandyCount(10);
		ProductInventory.setChipsCount(10);
		ProductInventory.setColaCount(10);
	}

	public static void updateInventory(Product productSelected) {
		String prod = productSelected.getDescription();
		switch (prod) {
		case "Cola":
			if (ProductInventory.getColaCount() > 0) {
				ProductInventory
						.setColaCount(ProductInventory.getColaCount() - 1);
			}
			break;
		case "Chips":
			if (ProductInventory.getChipsCount() > 0) {
				ProductInventory
						.setChipsCount(ProductInventory.getChipsCount() - 1);
			}
			break;
		case "Candy":
			if (ProductInventory.getCandyCount() > 0) {
				ProductInventory
						.setCandyCount(ProductInventory.getCandyCount() - 1);
			}
			break;
		}

	}

}
