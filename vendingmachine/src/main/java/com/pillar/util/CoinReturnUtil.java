package com.pillar.util;

import java.math.BigDecimal;
import java.util.List;

import com.pillar.constants.Coin;
import com.pillar.data.CoinInventory;

public class CoinReturnUtil {

	public static void calculateNickels(List<Coin> coinsToReturn,
			BigDecimal postTransactionBalance) {
		if (postTransactionBalance.doubleValue() > 0.04) {
			BigDecimal nickels[] = postTransactionBalance
					.divideAndRemainder(new BigDecimal(0.05));

			int nickelsToReturn1 = nickels[0].intValue();
			postTransactionBalance = nickels[1];
			for (int i = 0; i < nickelsToReturn1; i++) {
				coinsToReturn.add(new Coin(5,5,0.05,"Nickel"));
				if (CoinInventory.getNickels() > 0) {
					CoinInventory.setNickels(CoinInventory.getNickels() - 1);
				}
			}

		}
	}

	public static BigDecimal calculateDimes(List<Coin> coinsToReturn,
			BigDecimal postTransactionBalance) {
		if (postTransactionBalance.doubleValue() > 0.09) {
			BigDecimal dimesAndNewBal[] = postTransactionBalance
					.divideAndRemainder(new BigDecimal(0.10));

			int dimessToReturn = dimesAndNewBal[0].intValue();
			postTransactionBalance = dimesAndNewBal[1];
			for (int i = 0; i < dimessToReturn; i++) {
				coinsToReturn.add(new Coin(10,10,0.1,"Dime"));
				if (CoinInventory.getDimes() > 0) {
					CoinInventory.setDimes(CoinInventory.getDimes() - 1);
				}
			}
		}
		return postTransactionBalance;
	}

	public static BigDecimal calculateQuarters(List<Coin> coinsToReturn,
			BigDecimal postTransactionBalance) {
		if (postTransactionBalance.doubleValue() > 0.24) {

			BigDecimal quartsAndNewBal[] = postTransactionBalance
					.divideAndRemainder(new BigDecimal(0.25));

			int quartersToReturn = quartsAndNewBal[0].intValue();
			postTransactionBalance = quartsAndNewBal[1];
			for (int i = 0; i < quartersToReturn; i++) {
				coinsToReturn.add(new Coin(25,25,0.25,"Quarter"));
				if (CoinInventory.getQuarters() > 0) {
					CoinInventory.setQuarters(CoinInventory.getQuarters() - 1);
				}
			}
		}
		return postTransactionBalance;
	}

}
