package com.pillar.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pillar.constants.Coin;
import com.pillar.model.CurrentBudget;
import com.pillar.util.CoinReturnUtil;

public class CoinProcessor {

	double totalAmount;
	CurrentBudget budget = new CurrentBudget();
	private static Coin penny = new Coin(1,1,0.01,"Penny");
	private static Coin dime= new Coin(5,5,0.05,"Dime");
	private static Coin nickel= new Coin(10,10,0.1,"Nickel");
	private static Coin quarter= new Coin(25,25,0.25,"Quarter");
	List<Coin> returnCoins = new ArrayList<Coin>();

	public CurrentBudget getBudget() {
		return budget;
	}

	public void setBudget(CurrentBudget budget) {
		this.budget = budget;
	}

	public List<Coin> process(List<Coin> inputCoins) {
		returnCoins= new ArrayList<Coin>();
		totalAmount = 0;
		budget.setCurrentAmount(totalAmount);
		for (Coin coin : inputCoins) {
			if(!coin.equals(penny)){
			totalAmount += coin.getValue();
			}
			if((coin.equals(penny)) && (Collections.frequency(inputCoins,penny)!=(Collections.frequency(returnCoins, penny))))
				returnCoins.add(coin);
			}
		budget.setCurrentAmount(totalAmount);
		return returnCoins;
	}

	public List<Coin> processPostTransaction(double transactionAmount) {
		BigDecimal postTransactionBalance = new BigDecimal(this.getBudget()
				.getCurrentAmount() - transactionAmount);
		this.getBudget().setCurrentAmount(postTransactionBalance.doubleValue());

		if (postTransactionBalance.doubleValue() > 0.24) {

			postTransactionBalance = CoinReturnUtil.calculateQuarters(
					returnCoins, postTransactionBalance);
		}
		if (postTransactionBalance.doubleValue() > 0.09) {
			postTransactionBalance = CoinReturnUtil.calculateDimes(
					returnCoins, postTransactionBalance);
		}
		if (postTransactionBalance.doubleValue() > 0.04) {
			CoinReturnUtil.calculateNickels(returnCoins,
					postTransactionBalance);

		}
		totalAmount=0;
		return returnCoins;

	}

}
	
