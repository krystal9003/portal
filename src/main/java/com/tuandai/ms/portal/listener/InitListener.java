package com.tuandai.ms.portal.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.tuandai.ms.portal.thread.RequestProcessorThreadPool;

/**
 ********************************************** 
 * 描述：spring 启动初始化线程池类 Simba.Hua 2017年8月27日
 ********************************************** 
 **/
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() != null) {
			return;
		}
		RequestProcessorThreadPool.init();
	}
}