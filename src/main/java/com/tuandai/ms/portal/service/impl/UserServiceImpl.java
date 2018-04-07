package com.tuandai.ms.portal.service.impl;  

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tuandai.ms.portal.dao.UserDao;
import com.tuandai.ms.portal.domain.User;
import com.tuandai.ms.portal.service.UserService;
  
@Service("userService")  
public class UserServiceImpl implements UserService {
	
    @Resource  
    private UserDao userDao;
    
    @Override  
    public User getUserById(int userId) {  
        return this.userDao.selectByPrimaryKey(userId);  
    }
    
	@Override
	public User getUserByUsernameAndPassword(String username, String password) {
		return this.userDao.selectByUsernameAndPassword(username, password);
	}  
	
}  