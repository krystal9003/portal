package com.tuandai.ms.portal.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tuandai.ms.portal.request.Request;
import com.tuandai.ms.portal.request.RequestQueue;

/**
 ********************************************** 
 * 描述：请求处理线程池，初始化队列数及每个队列最多能处理的数量 Simba.Hua 2017年8月27日
 ********************************************** 
 **/
public class RequestProcessorThreadPool {
	private static final int blockingQueueNum = 100;
	private static final int queueDataNum = 300;
	private ExecutorService threadPool = Executors.newFixedThreadPool(blockingQueueNum);

	private RequestProcessorThreadPool() {
		for (int i = 0; i < blockingQueueNum; i++) {// 初始化队列
			ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(queueDataNum);// 每个队列中放100条数据
			RequestQueue.getInstance().addQueue(queue);
			threadPool.submit(new RequestProcessorThread(queue));// 把每个queue交个线程去处理，线程会处理每个queue中的数据
		}
	}

	public static class Singleton {
		private static RequestProcessorThreadPool instance;
		static {
			instance = new RequestProcessorThreadPool();
		}

		public static RequestProcessorThreadPool getInstance() {
			return instance;
		}
	}

	public static RequestProcessorThreadPool getInstance() {
		return Singleton.getInstance();
	}

	/**
	 * 初始化线程池
	 */
	public static void init() {
		getInstance();
	}
}