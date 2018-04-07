package com.tuandai.ms.portal.cache.redis;

public interface CacheLoadable<T>{
	
	public T load();
	
}
