package com.tuandai.ms.portal.request;

import com.tuandai.ms.portal.domain.InventoryProduct;
import com.tuandai.ms.portal.service.InventoryProductBiz;

/**
 ********************************************** 
 * 描述：查询缓存数据 
 * 1、从数据库中查询 
 * 2、从数据库中查询后插入到缓存中
 ********************************************** 
 **/
public class InventoryQueryCacheRequest implements Request {
	
	private InventoryProductBiz inventoryProductBiz;
	private Integer productId;
	private boolean isForceFefresh;

	public InventoryQueryCacheRequest(Integer productId, InventoryProductBiz inventoryProductBiz,
			boolean isForceFefresh) {
		this.productId = productId;
		this.inventoryProductBiz = inventoryProductBiz;
		this.isForceFefresh = isForceFefresh;
	}

	@Override
	public void process() {
		InventoryProduct inventoryProduct = inventoryProductBiz.loadInventoryProductByProductId(productId);
		inventoryProductBiz.setInventoryProductCache(inventoryProduct);
	}

	@Override
	public Integer getProductId() {
		return productId;
	}

	public boolean isForceFefresh() {
		return isForceFefresh;
	}

	public void setForceFefresh(boolean isForceFefresh) {
		this.isForceFefresh = isForceFefresh;
	}

}