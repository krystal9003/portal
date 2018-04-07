package com.tuandai.ms.portal.service.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.tuandai.ms.portal.cache.redis.CacheLoadable;
import com.tuandai.ms.portal.cache.redis.CacheRedisTemplate;
import com.tuandai.ms.portal.dao.SystemParamDao;
import com.tuandai.ms.portal.domain.SystemParam;
import com.tuandai.ms.portal.service.SystemParamService;

@Service
public class SystemParamServiceImpl implements SystemParamService {

	@Autowired
	private SystemParamDao systemParamDao;

	@Autowired
	private CacheRedisTemplate cacheRedisTemplate;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public SystemParam getSystemParam(String code) {
		final String finalCode = code;
		return cacheRedisTemplate.findCache(code, 1000, TimeUnit.DAYS, new TypeReference<SystemParam>() {
		}, new CacheLoadable<SystemParam>() {
			@Override
			public SystemParam load() {
				return systemParamDao.selectByCode(finalCode);
			}
		});
	}

	@Override
	public SystemParam getSystemParamByCode(String code) {
			SystemParam param = systemParamDao.selectByCode(code);
			return param;
	}
	
	@Override
	public SystemParam getSystemParamTemplate(String code) {
		final String finalCode = code;
		return cacheRedisTemplate.findCache(code, 1000, TimeUnit.DAYS, new TypeReference<SystemParam>() {
		}, new CacheLoadable<SystemParam>() {
			@Override
			public SystemParam load() {
				return systemParamDao.selectByCode(finalCode);
			}
		});
	}

	@Override
	public SystemParam getSystemParamFromDB(String code) {
		return systemParamDao.selectByCode(code);
	}

	@Override
	public SystemParam getSystemParamUseQueue(String code) {
		
		return null;
	}

	

}
