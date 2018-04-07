package com.tuandai.ms.portal.cache.redis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tuandai.ms.portal.domain.SystemParam;

@Component
public class CacheRedisTemplate {

	@Autowired
	private RedisTemplate redisTemplate;
	
	private ReentrantLock lock = new ReentrantLock();

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 避免緩存失效模板
	 * @param key       緩存key
	 * @param expire  失效時間
	 * @param unit      失效時間的單位
	 * @param clazz    緩存的類型
	 * @param cacheLoadable 如果緩存失效，怎麽獲取
	 * @return
	 */
	public <T> T findCache(String key, long expire, TimeUnit unit, TypeReference<T> clazz,
			CacheLoadable<T> cacheLoadable) {
		ValueOperations valueOperations = redisTemplate.opsForValue();
		String data = String.valueOf(valueOperations.get(key));
		if(null != data && !"null".equals(data)){
			logger.info("====================使用缓存查询====================");
			//redis需要对结果进行反序列化，因为获取的对象值对象实现序列化接口
			return JSON.parseObject(data, clazz);
		}else{
			//为了应对高并发的情况下，需要对redis进行双从检查
			lock.lock();
			if(lock.tryLock()){
				if((SystemParam)valueOperations.get(key) != null){
					return JSON.parseObject(data, clazz);
				}else{
					logger.info("====================使用数据库====================");
					T result = cacheLoadable.load();
					valueOperations.set(key, JSON.toJSON(result),expire,unit);
					return result;
				}
			}else{
				//没有获取到锁的线程从
				T result = cacheLoadable.load();
				valueOperations.set(key, JSON.toJSON(result),expire,unit);
				return result;
			}
		}
	}
}
