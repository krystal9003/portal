package com.tuandai.ms.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuandai.ms.portal.dao.BuyerInfoDao;
import com.tuandai.ms.portal.domain.BuyerInfo;
import com.tuandai.ms.portal.service.BuyerInfoService;

@Service
public class BuyerInfoServiceImpl implements BuyerInfoService{
	
	@Autowired
	private BuyerInfoDao buyerInfoDao;
	
	@Override
	public void insertBuyerInfo(BuyerInfo buyerInfo) {
		buyerInfoDao.insertBuyerInfo(buyerInfo);
	}
	
	@Override
	public void insertTest(int productId) {
		BuyerInfo buyerInfo = new BuyerInfo();
    	buyerInfo.setAddress("武穴市石佛寺");
    	buyerInfo.setName("董伟");
    	buyerInfo.setTelephone("13567578967");
    	buyerInfo.setProductId(productId);
    	insertBuyerInfo(buyerInfo);
	}
	
}
