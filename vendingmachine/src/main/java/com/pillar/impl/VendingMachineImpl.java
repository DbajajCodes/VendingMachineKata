package com.pillar.impl;

	import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.pillar.constants.Coin;
import com.pillar.constants.Product;
import com.pillar.data.CoinInventory;
import com.pillar.data.ProductInventory;
import com.pillar.service.CoinProcessor;
import com.pillar.service.ProductVendor;

public class VendingMachineImpl {
	private static List<Coin> inputCoins;
	private static List<Coin> returnCoins = new ArrayList<Coin>();
	private static CoinProcessor processor;
	private static ProductVendor vendor;
	private static Product productSelected;
	private static ProductInventory inventory = new ProductInventory();
	private static CoinInventory coinInventory  = new CoinInventory();
	
	String response = "INSERT COINS";

	public VendingMachineImpl(CoinProcessor processor) {
		VendingMachineImpl.processor = new CoinProcessor();
	}

	public static List<Coin> getReturnCoins() {
		return returnCoins;
	}

	public static void setReturnCoins(List<Coin> returnCoins) {
		VendingMachineImpl.returnCoins = returnCoins;
	}

	public static void main(String[] args) {
		inputCoins = new ArrayList<Coin>();
		int userInput=0;
		System.out.println("Hello, welcome to your personal vending system, chose one of the following - please type 1 for Cola, 2 for Chips, 3 for Candy, 4 to cancel transaction");
		Scanner sc = new Scanner(System.in);
		try{
		userInput = sc.nextInt();
		}catch(InputMismatchException e){
			System.out.println("invalid input entered");
		}
		if(userInput>4){
			sc.close();
			throw new IllegalArgumentException("Invalid input, please type 1 for Cola, 2 for Chips, 3 for Candy");
		}
		setupProduct(userInput);
		System.out.println("Please enter the number of Pennies");
		try{
			userInput = sc.nextInt();
		}catch(InputMismatchException me){
			System.out.println("Invalid input entered");
		}
		addPennies(userInput);
		System.out.println("Please enter the number of Nickels");
		try{
			userInput = sc.nextInt();
		}catch(InputMismatchException me){
			System.out.println("Invalid input entered");
		}
		addNickels(userInput);
		System.out.println("Please enter the number of Dimes");
		try{
			userInput = sc.nextInt();
		}catch(InputMismatchException me){
			System.out.println("Invalid input entered");
		}
		addDimes(userInput);
		System.out.println("Please enter the number of Quarters");
		try{
			userInput = sc.nextInt();
		}catch(InputMismatchException me){
			System.out.println("Invalid input entered");
		}
		addQuarters(userInput);
		try {
			VendingMachineImpl impl = new VendingMachineImpl(processor);
			impl.process();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sc.close();
		}

	}

	private static void setupProduct(int prodNo) {
		switch (prodNo) {
		case (1):
			productSelected = Product.Cola;
			break;
		case (2):
			productSelected = Product.Chips;
			break;
		case (3):
			productSelected = Product.Candy;
			break;

		case (4):
			productSelected = null;
			break;
		}
	}

	private void process() {
		VendingMachineImpl.setReturnCoins(processor.process(inputCoins));
		vendor = new ProductVendor(productSelected, processor, inventory,coinInventory);
		response = productSelected != null ? vendor.vend() : vendor
				.initiateReturn();
		inputCoins.clear();
		System.out.println(response);
		if (VendingMachineImpl.getReturnCoins().size() > 0) {
			System.out.println("Please collect your change : ");
			for (Coin coin : VendingMachineImpl.getReturnCoins()) {
				System.out.println(coin.getDescription());
			}
		}
	}

	private static void addQuarters(int quarters) {
		for (int i = 0; i < quarters; i++) {
			inputCoins.add(new Coin(25, 25, 0.25, "Quarter"));
		}

	}

	private static void addDimes(int dimes) {
		for (int i = 0; i < dimes; i++) {
			inputCoins.add(new Coin(10, 10, 0.1, "Dime"));
		}

	}

	private static void addNickels(int nickels) throws NumberFormatException {
		for (int i = 0; i < nickels; i++) {
			inputCoins.add(new Coin(5, 5, 0.05, "Nickel"));
		}
	}

	private static void addPennies(int pennies) throws NumberFormatException {
		for (int i = 0; i < pennies; i++) {
			inputCoins.add(new Coin(1, 1, 0.01, "Penny"));
		}
	}

	public Product getProduct() {
		return productSelected;
	}

}
