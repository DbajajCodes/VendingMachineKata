package com.pillar.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.pillar.constants.Coin;
import com.pillar.constants.Product;
import com.pillar.data.CoinInventory;
import com.pillar.data.ProductInventory;
import com.pillar.service.CoinProcessor;
import com.pillar.service.ProductVendor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CoinInventory.class, ProductInventory.class})
public class VendingMachineImplTest {

@InjectMocks
VendingMachineImpl impl;

private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();

@Mock
CoinProcessor processor;

@Mock
ProductVendor vendor;

@Mock
BufferedReader reader;

CoinInventory coinInventory;
ProductInventory inventory;

Coin penny;
Coin nickel;
Coin dime;
Coin quarter;

@Before
public void setup(){
	System.setOut(new PrintStream(outStream));
	penny = new Coin(1,1,0.01,"Penny");
	nickel =new Coin(5,5,0.05,"Nickel"); 
	dime = new Coin(10,10,0.1,"Dime");
	quarter = new Coin(25,25,0.25,"Quarter"); 
	PowerMockito.mockStatic(CoinInventory.class);
	PowerMockito.mockStatic(ProductInventory.class);
}

@After
public void cleanup(){
	System.setOut(null);
}

@Test(expected = IllegalArgumentException.class)
public void testAcceptsCorrectUserInputForProduct() throws Exception {
	ByteArrayInputStream inputStream = new ByteArrayInputStream("5".getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{"5"});
	
}

@Test
public void testAcceptsValidCoins() throws Exception {
	ByteArrayInputStream inputStream = new ByteArrayInputStream("0,1,1,1,Cola".getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{"0","1","1","1","Cola"});
	assertFalse(VendingMachineImpl.getReturnCoins().contains(penny));
			

}

@Test
public void testRejectsInvalidCoins() throws Exception {
	String userInput = "1"+System.getProperty("line.separator")+"1";
	ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{userInput});
	assertTrue(VendingMachineImpl.getReturnCoins().contains(penny));
		
}

@Test
public void testSelectsProduct() throws Exception {
	ByteArrayInputStream inputStream = new ByteArrayInputStream("1,1,1,1,Cola".getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{"1","1","1","1","Cola"});
	assertEquals(Product.Cola, impl.getProduct());
}

@Test
public void testReturnsCorrectChangePostTransaction() throws Exception {
	when(CoinInventory.getDimes()).thenReturn(10);
	when(CoinInventory.getNickels()).thenReturn(10);
	when(CoinInventory.getQuarters()).thenReturn(10);
	when(ProductInventory.getCandyCount()).thenReturn(10);
	when(ProductInventory.getColaCount()).thenReturn(10);
	when(ProductInventory.getChipsCount()).thenReturn(10);
	ByteArrayInputStream inputStream = new ByteArrayInputStream("1,1,1,2,Chips".getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{"1","1","1","2","Chips"});
	assertTrue(VendingMachineImpl.getReturnCoins().contains(nickel));
}

@Test
public void testCancelsTransactionAndReturnsAllMoneyOnReturn() throws Exception {
	ByteArrayInputStream inputStream = new ByteArrayInputStream("1,1,1,2,Return".getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{"1","1","1","2","Return"});
	assertTrue(VendingMachineImpl.getReturnCoins().contains(nickel));
	assertTrue(VendingMachineImpl.getReturnCoins().contains(quarter));
}

@Test
public void testSoldOut() throws Exception {
	for(int i=0;i<=6;i++){
	ByteArrayInputStream inputStream = new ByteArrayInputStream("1,1,1,2,Candy".getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{"1","1","1","2","Candy"});
	}
	assertTrue(outStream.toString().contains("SOLD OUT"));
}

@Test
public void testExactChangeOnly() throws Exception {
	when(ProductInventory.getCandyCount()).thenReturn(10);
	when(ProductInventory.getColaCount()).thenReturn(10);
	when(ProductInventory.getChipsCount()).thenReturn(10);
	when(CoinInventory.getDimes()).thenReturn(0);
	CoinInventory.setDimes(0);
	ByteArrayInputStream inputStream = new ByteArrayInputStream("0,0,0,3,Candy".getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{"0","0","0","3","Candy"});
	assertTrue(outStream.toString().contains("EXACT CHANGE ONLY"));
	
}
}
