package com.pillar.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.pillar.constants.DisplayMessage;
import com.pillar.constants.Product;
import com.pillar.data.CoinInventory;
import com.pillar.data.ProductInventory;
import com.pillar.model.CurrentBudget;

@RunWith(MockitoJUnitRunner.class)
public class ProductVendorTest {

	@InjectMocks
	ProductVendor vendor;

	CoinProcessor processor;

	ProductInventory inventory = new ProductInventory();
	CoinInventory coinInventory = new CoinInventory();

	public void setUpColaTrans() {
		processor = new CoinProcessor();
		CurrentBudget budget = new CurrentBudget();
		budget.setCurrentAmount(10.00);
		processor.setBudget(budget);
		vendor = new ProductVendor(Product.Cola, processor, inventory,coinInventory);
	}

	private void setUpChipsTrans() {
		processor = new CoinProcessor();
		CurrentBudget mockBudget = new CurrentBudget();
		mockBudget.setCurrentAmount(0.50);
		processor.setBudget(mockBudget);
		vendor = new ProductVendor(Product.Chips, processor, inventory,coinInventory);
	}
	
	private void setUpNegativeCandyTrans() {
		processor = new CoinProcessor();
		CurrentBudget mockBudget = new CurrentBudget();
		mockBudget.setCurrentAmount(0.50);
		processor.setBudget(mockBudget);
		vendor = new ProductVendor(Product.Candy, processor, inventory,coinInventory);
	}

	@Test
	public void testChecksCurrentAmountAgainstProductSelected()
			throws Exception {
		setUpColaTrans();
		assertEquals(DisplayMessage.THANK_YOU.getDescription(), vendor.vend());
	}

	@Test
	public void testUpdatesCurrentAmountPostTransaction() throws Exception {
		setUpChipsTrans();
		vendor.vend();
		assertEquals(0.00,
				vendor.getProcessor().getBudget().getCurrentAmount(), 0);

	}
	
	@Test
	public void testDoesNotVendIfNotEnoughBalance() throws Exception {
		setUpNegativeCandyTrans();
		assertEquals(DisplayMessage.PRICE_CANDY.getDescription(), vendor.vend());
	}
	
	@Test
	public void testInitiatesCorrectReturn() throws Exception {
		CoinProcessor mockProcessor = setUpReturn();
		vendor.initiateReturn();
		verify(mockProcessor).processPostTransaction(0);
	
	}

	private CoinProcessor setUpReturn() {
		setUpChipsTrans();
		CurrentBudget mockBudget = mock(CurrentBudget.class);
		when(mockBudget.getCurrentAmount()).thenReturn(0.50);
		CoinProcessor mockProcessor = mock(CoinProcessor.class);
		when(mockProcessor.getBudget()).thenReturn(mockBudget);
		vendor = new ProductVendor(Product.Chips, mockProcessor, inventory,coinInventory);
		return mockProcessor;
	}
	
	@Test
	public void testReturnsCorrectMessagePostReturn() throws Exception {
		setUpReturn();
		assertEquals(DisplayMessage.INSERT_COIN.getDescription(),vendor.initiateReturn());
		
	}
	
	@Test
	public void testUpdatesInventoryPostSuccessfullTransaction() throws Exception {
		inventory = new ProductInventory();
		setUpChipsTrans();
		vendor.vend();
		assertEquals(9,ProductInventory.getChipsCount());
		assertEquals(10,ProductInventory.getColaCount());
		
	}
	
	@Test
	public void testReturnsSoldOut() throws Exception {
		inventory = new ProductInventory();
		setUpColaTrans();
		for(int i=0;i<10;i++){
		coinInventory=new CoinInventory();	
		vendor.vend();
		}
		assertEquals(DisplayMessage.SOLD_OUT.getDescription(), vendor.vend());
	}
	
	@Test
	public void testChecksIfCoinsArePresent() throws Exception {
		inventory = new ProductInventory();
		setUpChipsTrans();
		CoinInventory.setDimes(0);
		assertEquals(DisplayMessage.EXACT_CHANGE.getDescription(), vendor.vend());
	}
	
}
