package com.pillar.data;

import static org.junit.Assert.*;

import org.junit.Test;

import com.pillar.constants.Coin;


public class CoinInventoryTest {

	CoinInventory inventory =  new CoinInventory();
	
	@Test
	public void testUpdatesQuarterCoinInventory() throws Exception {
		inventory.update(new Coin(25,25,0.25,"Quarter"), 2);
		assertEquals(8, CoinInventory.getQuarters());
	}
	
	@Test
	public void testUpdatesDimeCoinInventory() throws Exception {
		inventory.update(new Coin(10,10,0.1,"Dime"), 4);
		assertEquals(6, CoinInventory.getDimes());
	}
	@Test
	public void testUpdatesNickelCoinInventory() throws Exception {
		inventory.update(new Coin(5,5,0.05,"Nickel"), 10);
		assertEquals(0, CoinInventory.getNickels());
		
	}
	
}
