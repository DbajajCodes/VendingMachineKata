package com.pillar.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.pillar.constants.Coin;
import com.pillar.model.CurrentBudget;

public class CoinProcessorTest {

CoinProcessor processor = new CoinProcessor();

@Test
public void testAcceptsValidCoins() throws Exception {
	List<Coin> validCoins = Arrays.asList(new Coin(5,5,0.05,"Nickel"),new Coin(10,10,0.1,"Dime"),new Coin(25,25,0.25,"Quarter"));
	assertEquals(0,processor.process(validCoins).size());
}	

@Test
public void testRejectsInvalidCoins() throws Exception {
	List<Coin> invalidCoins = Arrays.asList(new Coin(5,5,0.05,"Nickel"),new Coin(10,10,0.1,"Dime"),new Coin(25,25,0.25,"Quarter"),new Coin(1,1,0.01,"Penny"));
	assertEquals(1,processor.process(invalidCoins).size());
	
}

@Test
public void testSetsCurrentBudgetCorrectly() throws Exception {
	List<Coin> inputCoins = Arrays.asList(new Coin(5,5,0.05,"Nickel"),new Coin(5,5,0.05,"Nickel"),new Coin(10,10,0.1,"Dime"),new Coin(25,25,0.25,"Quarter"));
	processor.process(inputCoins);
	assertEquals(0.45,processor.getBudget().getCurrentAmount(),0);
}

@Test
public void testUpdatesCurrentAmountPostTransaction() throws Exception {
	setupPositiveBalanceTransaction();
	processor.processPostTransaction(0.5);
	assertThat(processor.getBudget().getCurrentAmount(), Matchers.equalTo(0.49));
}

private void setupPositiveBalanceTransaction() {
	CurrentBudget testBudget = new CurrentBudget();
	testBudget.setCurrentAmount(0.99);
	processor.setBudget(testBudget);
}

@Test
public void testReturnsCorrectBalanceAmount() throws Exception {
	setupPositiveBalanceTransaction();
	List<Coin> coinsReturn = processor.processPostTransaction(0.5);
	assertEquals(coinsReturn.get(0).getValue(), 0.25,0);
}

}
