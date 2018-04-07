package com.dongwei.database.mybatis;

import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tuandai.ms.portal.domain.SystemParam;
import com.tuandai.ms.portal.service.SystemParamService;
import com.tuandai.ms.portal.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mvc.xml" })
public class TestMyBatis {

	private static Logger logger = Logger.getLogger(TestMyBatis.class);

	@Resource
	private UserService userService = null;

	private static final int threadNum = 200;

	@Resource
	private SystemParamService systemParamService;

	private CountDownLatch cd1 = new CountDownLatch(threadNum);

	@Test
	public void test1() {
		for (int i = 0; i < threadNum; i++) {
			new Thread(new UserRequest()).start();
			cd1.countDown();
		}
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class UserRequest implements Runnable {
		@Override
		public void run() {
			try {
				cd1.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long startTime = System.currentTimeMillis();
			// User user =
			// userService.getUserByUsernameAndPassword("dongwei12879","krystal12879");
			SystemParam param1 = systemParamService.getSystemParamByCode("msgCode1");
			// SystemParam param2 = systemParamService.getSystemParamByCode("msgCode1");
			System.out.println("耗时为:" + (System.currentTimeMillis() - startTime));
			// logger.info(JSON.toJSONString(user));
		}
	}

}