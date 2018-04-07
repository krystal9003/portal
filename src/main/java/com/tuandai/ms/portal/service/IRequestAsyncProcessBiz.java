package com.tuandai.ms.portal.service;

import com.tuandai.ms.portal.request.Request;

/** 
 ********************************************** 
 *  描述：请求异步处理接口，用于路由队列并把请求加入到队列中 
 * Simba.Hua 
 * 2017年8月30日 
 ********************************************** 
**/  
public interface IRequestAsyncProcessBiz {  
    void process(Request request);  
}  