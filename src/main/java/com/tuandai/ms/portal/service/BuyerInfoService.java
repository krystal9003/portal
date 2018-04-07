package com.tuandai.ms.portal.service;

import com.tuandai.ms.portal.domain.BuyerInfo;

public interface BuyerInfoService {
	
	void insertBuyerInfo(BuyerInfo buyerInfo);
	
	/**
	 * 专门测试
	 */
	void insertTest(int productId);
	
}
