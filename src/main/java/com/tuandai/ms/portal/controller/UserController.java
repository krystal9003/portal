package com.tuandai.ms.portal.controller;  
  

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.util.concurrent.RateLimiter;
import com.tuandai.ms.portal.domain.User;
import com.tuandai.ms.portal.model.ShowUserResponseEvent;
import com.tuandai.ms.portal.service.BuyerInfoService;
import com.tuandai.ms.portal.service.UserService;

  
@Controller  
@RequestMapping("/user")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    private UserService userService;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private BuyerInfoService buyerInfoService;
	
	 @RequestMapping("/test.do") 
	@ResponseBody
	public String Test(){
		return "hello";
	}
      
    @RequestMapping("/showUser.do") 
    @ResponseBody
    public ShowUserResponseEvent toIndex(HttpServletRequest request,Model model){
    	ShowUserResponseEvent responseEvent = new ShowUserResponseEvent();
        int userId = Integer.parseInt(request.getParameter("id"));  
        logger.info("denglufefe");
        User user = this.userService.getUserById(userId);  
        model.addAttribute("user", user);  
//        return user.getUsername()+":"+user.getPassword();  
        responseEvent.setAge("20");
        responseEvent.setUsername("dongwei");
        return responseEvent;
    }
    
    RateLimiter rateLimiter = RateLimiter.create(100);
    
    /**
     * 使用同步的方式来实现登录逻辑
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/login.do") 
    @ResponseBody
    public String loginAndSaveBuyerInfo(HttpServletRequest request,Model model){
    	//接口限流技巧，保证系统高可用
    	if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {  
            System.out.println("短期无法获取令牌，真不幸，排队也瞎排");  
            return "失败";  
        }
    	String username = request.getParameter("username"); 
        String password = request.getParameter("password");
        int productId = Integer.parseInt(request.getParameter("productId"));
//        String productId = request.getParameter("productId");
        String mobile = request.getParameter("mobile");
        long startTime = System.currentTimeMillis();
        logger.info("开始登陆================");
        User user = this.userService.getUserByUsernameAndPassword(username, password);
        logger.info("登录结束end"+(System.currentTimeMillis()-startTime));
    	try{
			//Subject subject = ShiroUtils.getSubject();
			//sha256加密
			//password = new Sha256Hash(request.getParameter("password")).toHex();
			//UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			//subject.login(token);
		}catch (UnknownAccountException e) {
//			return R.error(e.getMessage());
		}catch (IncorrectCredentialsException e) {
//			return R.error(e.getMessage());
		}catch (LockedAccountException e) {
//			return R.error(e.getMessage());
		}catch (AuthenticationException e) {
//			return R.error("账户验证失败");
		}
    	jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				User user = new User();
				user.setPassword("dongwei123");
				user.setUsername("dongwei");
				return session.createObjectMessage(user);
			}
		});
    	//buyerInfoService.insertTest(productId);
	    return "hello";
    }
    
    @RequestMapping("/loginT.do") 
    @ResponseBody
    public String login(HttpServletRequest request,Model model){
        String username = request.getParameter("username"); 
        String password = request.getParameter("password");
        int productId = Integer.parseInt(request.getParameter("productId"));
//        String productId = request.getParameter("productId");
        String mobile = request.getParameter("mobile");
        long startTime = System.currentTimeMillis();
        logger.info("开始登陆================");
        User user = this.userService.getUserByUsernameAndPassword(username, password);
        logger.info("登录结束end"+(System.currentTimeMillis()-startTime));
	    return "hello";
    }
    
    @RequestMapping("/register") 
    @ResponseBody
    public String register(HttpServletRequest request,Model model){
        String username = request.getParameter("username"); 
        String password = new Sha256Hash(request.getParameter("password")).toHex();
        User user = this.userService.getUserByUsernameAndPassword(username, password);
        if (null == user){
        	return "fail";
        } else{
        	return "success";
        }
    }
    
    private static void scan(String packageName){
    	File file = new File(packageName);
    	File[] files = file.listFiles();
    	if(files.length == 0){
    		return;
    	}
    	for (File file2 : files) {
			if(file2.isDirectory()){
				scan(packageName+file2.getName());
			}else{
				
			}
		}
    	System.out.println(files.length);
    }
    
    public static void main(String[] args){
    	scan("E:/spring-cloud/portal");
    }
    
}  