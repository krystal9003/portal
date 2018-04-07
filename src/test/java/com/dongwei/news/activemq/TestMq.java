package com.dongwei.news.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mvc.xml"})  
public class TestMq {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Test
	public void testMq(){
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
//				User user = new User();
//				user.setPassword("dongwei");
//				user.setUsername("krystal");
//				return session.createObjectMessage(user);
//				session.create
				return session.createTextMessage("good");
			}
		});
	}
	
}	
