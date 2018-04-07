package com.tuandai.ms.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuandai.ms.portal.domain.InventoryProduct;
import com.tuandai.ms.portal.request.InventoryQueryCacheRequest;
import com.tuandai.ms.portal.request.Request;
import com.tuandai.ms.portal.service.IRequestAsyncProcessBiz;
import com.tuandai.ms.portal.service.InventoryProductBiz;

/**
 ********************************************** 
 * 描述：提交查询请求 
 * 1、先从缓存中取数据 
 * 2、如果能从缓存中取到数据，则返回
 * 3、如果不能从缓存取到数据，则等待20毫秒，然后再次去数据，
 * 直到200毫秒，如果超过200毫秒还不能取到数据，则从数据库中取，并强制刷新缓存数据
 ********************************************** 
 **/
@Controller
public class InventoryQueryCacheController {
	
	@Autowired
	private  InventoryProductBiz inventoryProductBiz;
	
	@Autowired
	private  IRequestAsyncProcessBiz requestAsyncProcessBiz;

	@RequestMapping("/queryInventoryProduct")
	public InventoryProduct queryInventoryProduct(Integer productId) {
		Request request = new InventoryQueryCacheRequest(productId, inventoryProductBiz, false);
		requestAsyncProcessBiz.process(request);// 加入到队列中
		long startTime = System.currentTimeMillis();
		long allTime = 0L;
		long endTime = 0L;
		InventoryProduct inventoryProduct = null;
		while (true) {
			if (allTime > 200) {// 如果超过了200ms,那就直接退出，然后从数据库中查询
				break;
			}
			try {
				inventoryProduct = inventoryProductBiz.loadInventoryProductCache(productId);
				if (inventoryProduct != null) {
					return inventoryProduct;
				} else {
					Thread.sleep(20);// 如果查询不到就等20毫秒
				}
				endTime = System.currentTimeMillis();
				allTime = endTime - startTime;
			} catch (Exception e) {
			}
		}
		/**
		 * 代码执行到这来，只有以下三种情况 
		 * 1、缓存中本来有数据，由于redis内存满了，redis通过LRU算法清除了缓存，导致数据没有了
		 * 2、由于之前数据库查询比较慢或者内存太小处理不过来队列中的数据，导致队列里挤压了很多的数据，所以一直没有从数据库中获取数据然后插入到缓存中
		 * 3、数据库中根本没有这样的数据，这种情况叫数据穿透，一旦别人知道这个商品没有，如果一直执行查询，就会一直查询数据库，如果过多，那么有可能会导致数据库瘫痪
		 */
		inventoryProduct = inventoryProductBiz.loadInventoryProductByProductId(productId);
		if (inventoryProduct != null) {
			Request forcRrequest = new InventoryQueryCacheRequest(productId, inventoryProductBiz, true);
			requestAsyncProcessBiz.process(forcRrequest);// 这个时候需要强制刷新数据库，使缓存中有数据
			return inventoryProduct;
		}
		return null;

	}
}
