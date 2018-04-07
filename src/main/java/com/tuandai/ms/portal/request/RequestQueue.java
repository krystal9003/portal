package com.tuandai.ms.portal.request;  
  
import java.util.ArrayList;  
import java.util.List;  
import java.util.Map;  
import java.util.concurrent.ArrayBlockingQueue;  
import java.util.concurrent.ConcurrentHashMap;

import com.tuandai.ms.portal.request.Request;  
  
/** 
 ********************************************** 
 *  描述：请求队列 
 * Simba.Hua 
 * 2017年8月27日 
 ********************************************** 
**/  
public class RequestQueue {  
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();  
      
    private Map<Integer,Boolean> flagMap = new ConcurrentHashMap<>();  
    private RequestQueue(){  
          
    }  
    private static class Singleton{  
        private static RequestQueue queue;  
        static{  
            queue = new RequestQueue();  
        }  
        public static RequestQueue getInstance() {  
            return queue;  
        }  
    }  
      
    public static RequestQueue getInstance(){  
        return Singleton.getInstance();  
    }  
    public void addQueue(ArrayBlockingQueue<Request> queue) {  
        queues.add(queue);  
    }  
      
    public int getQueueSize(){  
        return queues.size();  
    }  
    public ArrayBlockingQueue<Request> getQueueByIndex(int index) {  
        return queues.get(index);  
    }  
      
    public Map<Integer,Boolean> getFlagMap() {  
        return this.flagMap;  
    }  
}  