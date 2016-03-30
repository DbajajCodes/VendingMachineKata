package com.pillar.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.pillar.constants.Product;

public class ProductInventoryTest {

	ProductInventory inventory = new ProductInventory();
	
	@Test
	public void testUpdatesInventoryForEachProduct() throws Exception {
		ProductInventory.updateInventory(Product.Candy);
		assertEquals(9,ProductInventory.getCandyCount());
	}
}
