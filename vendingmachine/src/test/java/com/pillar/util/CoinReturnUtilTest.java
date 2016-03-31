package com.pillar.util;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.pillar.constants.Coin;

public class CoinReturnUtilTest {

	CoinReturnUtil util;
	Coin Nickel = new Coin(5,5,0.05,"Nickel");
	Coin Dime= new Coin(10,10,0.1,"Dime");
	Coin Quarter= new Coin(25,25,0.25,"Quarter");
	
	List<Coin> coinsToReturn = new ArrayList<Coin>();
	BigDecimal postTransactionBalance;
	
	@Test
	public void testCalculateNickels() {
		postTransactionBalance  = new BigDecimal(0.05);
		CoinReturnUtil.calculateNickels(coinsToReturn, postTransactionBalance);
		assertTrue(coinsToReturn.contains(Nickel));
	}
	
	@Test
	public void testCalculateDimes() {
		postTransactionBalance  = new BigDecimal(0.11);
		CoinReturnUtil.calculateDimes(coinsToReturn, postTransactionBalance);
		assertTrue(coinsToReturn.contains(Dime));
	}

	@Test
	public void testCalculateQuarters() {
		postTransactionBalance  = new BigDecimal(0.26);
		CoinReturnUtil.calculateQuarters(coinsToReturn, postTransactionBalance);
		assertTrue(coinsToReturn.contains(Quarter));
	}
	
}
