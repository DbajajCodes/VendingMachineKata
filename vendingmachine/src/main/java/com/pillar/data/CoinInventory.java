package com.pillar.data;

import com.pillar.constants.Coin;

public class CoinInventory {
	
	public static int getNickels() {
		return Nickels;
	}

	public static void setNickels(int nickels) {
		Nickels = nickels;
	}

	public static int getDimes() {
		return Dimes;
	}

	public static void setDimes(int dimes) {
		Dimes = dimes;
	}

	public static int getQuarters() {
		return Quarters;
	}

	public static void setQuarters(int quarters) {
		Quarters = quarters;
	}

	private static int Nickels;
	private static int Dimes;
	private static int Quarters;
	
	public CoinInventory(){
		CoinInventory.setDimes(10);
		CoinInventory.setQuarters(10);
		CoinInventory.setNickels(10);
	}

	public void update(Coin coin, int i) {
		String coinType = coin.getDescription();
		switch(coinType){
		case("Nickel"):
			CoinInventory.setNickels(CoinInventory.getNickels()-i);
			break;
		case("Dime"):
			CoinInventory.setDimes(CoinInventory.getDimes()-i);
		break;
		case("Quarter"):
			CoinInventory.setQuarters(CoinInventory.getQuarters()-i);
		break;
		}
		
	}
	
	

}
