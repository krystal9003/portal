package com.tuandai.ms.portal.config;
//package com.cn.hnust.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
//@EnableAutoConfiguration 
@PropertySource(value={"classpath:applicaiton.properties"})
//@Import(value={ConfigServiceBootstrapConfiguration.class})
@ImportResource("classpath:spring-mvc.xml")
public class WebConfig {

}
