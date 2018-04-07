package com.tuandai.ms.portal.service.impl;

import java.util.concurrent.ArrayBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tuandai.ms.portal.request.Request;
import com.tuandai.ms.portal.request.RequestQueue;
import com.tuandai.ms.portal.service.IRequestAsyncProcessBiz;

/**
 ********************************************** 
 * 描述：异步处理请求,用于路由队列并把请求加入到队列中 Simba.Hua 2017年8月30日
 ********************************************** 
 **/
@Service("requestAsyncProcessService")
public class RequestAsyncProcessBizImpl implements IRequestAsyncProcessBiz {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void process(Request request) {
		// 做请求的路由，根据productId路由到对应的队列
		ArrayBlockingQueue<Request> queue = getQueueByProductId(request.getProductId());
		try {
			queue.put(request);
		} catch (InterruptedException e) {
			logger.error("产品ID{}加入队列失败", request.getProductId(), e);
		}
	}

	private ArrayBlockingQueue<Request> getQueueByProductId(Integer productId) {
		RequestQueue requestQueue = RequestQueue.getInstance();
		String key = String.valueOf(productId);
		int hashcode;
		int hash = (key == null) ? 0 : (hashcode = key.hashCode()) ^ (hashcode >>> 16);
		// 对hashcode取摸
		int index = (requestQueue.getQueueSize() - 1) & hash;
		return requestQueue.getQueueByIndex(index);
	}

}