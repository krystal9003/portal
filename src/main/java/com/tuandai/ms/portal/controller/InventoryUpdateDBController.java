package com.tuandai.ms.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuandai.ms.portal.domain.InventoryProduct;
import com.tuandai.ms.portal.request.InventoryUpdateDBRequest;
import com.tuandai.ms.portal.request.Request;
import com.tuandai.ms.portal.service.IRequestAsyncProcessBiz;
import com.tuandai.ms.portal.service.InventoryProductBiz;

/**
 ********************************************** 
 * 描述：提交更新请求 Simba.Hua 2017年9月1日
 ********************************************** 
 **/
@Controller
public class InventoryUpdateDBController {
	
	@Autowired
	private  InventoryProductBiz inventoryProductBiz;
	
	@Autowired
	private  IRequestAsyncProcessBiz requestAsyncProcessBiz;

	
	@ResponseBody
	@RequestMapping("/updateDBInventoryProduct")
	public String updateDBInventoryProduct(InventoryProduct inventoryProduct) {
		Request request = new InventoryUpdateDBRequest(inventoryProduct, inventoryProductBiz);
		requestAsyncProcessBiz.process(request);
		return "更新成功";
	}
}