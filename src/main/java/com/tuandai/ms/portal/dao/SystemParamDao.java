package com.tuandai.ms.portal.dao;

import com.tuandai.ms.portal.domain.SystemParam;

public interface SystemParamDao {
	
	SystemParam selectByCode(String code);
	
}
