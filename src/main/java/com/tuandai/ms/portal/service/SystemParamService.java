package com.tuandai.ms.portal.service;

import com.tuandai.ms.portal.domain.SystemParam;

public interface SystemParamService {
	/**
	 * 
	 * @param code
	 * @return
	 */
	SystemParam getSystemParam(String code);
	/**
	 * 会导致缓存雪崩
	 * @param code
	 * @return
	 */
	SystemParam getSystemParamByCode(String code);
	
	SystemParam getSystemParamTemplate(String code);
	/**
	 * 直接从数据库中查询
	 * @param code
	 * @return
	 */
	SystemParam getSystemParamFromDB(String code);
	
	SystemParam getSystemParamUseQueue(String code);
}
