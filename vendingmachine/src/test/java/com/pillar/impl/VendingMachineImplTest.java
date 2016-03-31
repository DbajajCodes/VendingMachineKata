package com.pillar.impl;

import static org.junit.Assert.*;
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
String userInput;

@Before
public void setup(){
	System.setOut(new PrintStream(outStream));
	penny = new Coin(1,1,0.01,"Penny");
	nickel =new Coin(5,5,0.05,"Nickel"); 
	dime = new Coin(10,10,0.1,"Dime");
	quarter = new Coin(25,25,0.25,"Quarter"); 
	PowerMockito.mockStatic(CoinInventory.class);
	PowerMockito.mockStatic(ProductInventory.class);
	userInput = "1"+System.getProperty("line.separator")+"0"+System.getProperty("line.separator")
	+"1"+System.getProperty("line.separator")+"1"+System.getProperty("line.separator")+"1";
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
	ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{userInput});
	assertFalse(VendingMachineImpl.getReturnCoins().contains(penny));
			

}

@Test
public void testRejectsInvalidCoins() throws Exception {
	userInput = "1"+System.getProperty("line.separator")+"1"+System.getProperty("line.separator")
	+"1"+System.getProperty("line.separator")+"1"+System.getProperty("line.separator")+"1";
	ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{userInput});
	assertTrue(VendingMachineImpl.getReturnCoins().contains(penny));
		
}

@Test
public void testSelectsProduct() throws Exception {
	ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{userInput});
	assertEquals(Product.Cola, impl.getProduct());
}

@Test
public void testReturnsCorrectChangePostTransaction() throws Exception {
	userInput = "2"+System.getProperty("line.separator")+"1"+System.getProperty("line.separator")
	+"1"+System.getProperty("line.separator")+"1"+System.getProperty("line.separator")+"2";
	when(CoinInventory.getDimes()).thenReturn(10);
	when(CoinInventory.getNickels()).thenReturn(10);
	when(CoinInventory.getQuarters()).thenReturn(10);
	when(ProductInventory.getCandyCount()).thenReturn(10);
	when(ProductInventory.getColaCount()).thenReturn(10);
	when(ProductInventory.getChipsCount()).thenReturn(10);
	ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{userInput});
	assertTrue(VendingMachineImpl.getReturnCoins().contains(nickel));
}

@Test
public void testCancelsTransactionAndReturnsAllMoneyOnReturn() throws Exception {
	userInput = "4"+System.getProperty("line.separator")+"1"+System.getProperty("line.separator")
	+"1"+System.getProperty("line.separator")+"1"+System.getProperty("line.separator")+"2";
	ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{userInput});
	assertTrue(VendingMachineImpl.getReturnCoins().contains(nickel));
	assertTrue(VendingMachineImpl.getReturnCoins().contains(quarter));
}

@Test
public void testSoldOut() throws Exception {
	userInput = "3"+System.getProperty("line.separator")+"1"+System.getProperty("line.separator")
	+"1"+System.getProperty("line.separator")+"1"+System.getProperty("line.separator")+"2";
	for(int i=0;i<=6;i++){
	ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{userInput});
	}
	assertTrue(outStream.toString().contains("SOLD OUT"));
}

@Test
public void testExactChangeOnly() throws Exception {
	userInput = "3"+System.getProperty("line.separator")+"0"+System.getProperty("line.separator")
	+"0"+System.getProperty("line.separator")+"0"+System.getProperty("line.separator")+"3";
	when(ProductInventory.getCandyCount()).thenReturn(10);
	when(ProductInventory.getColaCount()).thenReturn(10);
	when(ProductInventory.getChipsCount()).thenReturn(10);
	when(CoinInventory.getDimes()).thenReturn(0);
	CoinInventory.setDimes(0);
	ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
	System.setIn(inputStream);
	VendingMachineImpl.main(new String[]{userInput});
	assertTrue(outStream.toString().contains("EXACT CHANGE ONLY"));
	
}

}
