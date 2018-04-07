package com.tuandai.ms.portal.controller;

import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.util.concurrent.RateLimiter;


@RestController
@RequestMapping("/product")
public class ProductController {
	

	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	RateLimiter rateLimiter = RateLimiter.create(10);
	
	@RequestMapping("/buy")
	@ResponseBody
	public String buy(@RequestParam("id")String id){
		if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {  
            System.out.println("短期无法获取令牌，真不幸，排队也瞎排");  
            return "失败";  
        }  
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				session.createObjectMessage("");
				return null;
			}
		});
		return null;
	}
	
}
