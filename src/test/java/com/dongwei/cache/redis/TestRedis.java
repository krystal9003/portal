package com.dongwei.cache.redis;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mvc.xml"})  
public class TestRedis {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	private static final int threadNum = 2000;
	
	private CountDownLatch latch = new CountDownLatch(threadNum);
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void testRedis(){
		for(int i=0;i<threadNum;i++){
			new Thread(new UserRequest()).start();
			latch.countDown();
		}
		ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set("lu", "20");
		String value = valueOperations.get("lu");
		System.out.println(value);
		logger.info("中华人民共和国:"+value);
	}
	
	private class UserRequest implements Runnable{
		@Override
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
