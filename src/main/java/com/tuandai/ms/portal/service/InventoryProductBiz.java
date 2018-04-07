package com.tuandai.ms.portal.service;

import com.tuandai.ms.portal.domain.InventoryProduct;

public interface InventoryProductBiz {

	void removeInventoryProductCache(Integer productId);

	void updateInventoryProduct(InventoryProduct inventoryProduct);

	InventoryProduct loadInventoryProductByProductId(Integer productId);

	void setInventoryProductCache(InventoryProduct inventoryProduct);

	InventoryProduct loadInventoryProductCache(Integer productId);

}
