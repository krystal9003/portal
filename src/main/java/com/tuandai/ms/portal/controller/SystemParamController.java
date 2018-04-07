package com.tuandai.ms.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tuandai.ms.portal.domain.SystemParam;
import com.tuandai.ms.portal.service.SystemParamService;

@RestController
@RequestMapping("/param")
public class SystemParamController {
	
	@Autowired
	private SystemParamService systemParamService;
	
	@RequestMapping("/getCode.do")
	@ResponseBody
	//http://localhost:9082/portal/param/getCode.do?code=msgCode1
	public String getValue(@RequestParam("code")String code){
		SystemParam systemParam = systemParamService.getSystemParam(code);
		return systemParam.getValue();
	}
	
	@RequestMapping("/getCodeFromDB.do")
	@ResponseBody
	//http://localhost:9082/portal/param/getCodeFromDB.do?code=msgCode1
	public String getCodeFromDB(@RequestParam("code")String code){
		SystemParam systemParam = systemParamService.getSystemParamFromDB(code);
		return systemParam.getValue();
	}
	
	@RequestMapping("/getCodeFromRedis.do")
	@ResponseBody
	//http://localhost:9082/portal/param/getCodeFromRedis.do?code=msgCode1
	public String getCodeFromRedis(@RequestParam("code")String code){
		SystemParam systemParam = systemParamService.getSystemParamByCode(code);
		return systemParam.getValue();
	}
	
}
