package com.tuandai.ms.portal.service;  
  
import com.tuandai.ms.portal.domain.User;  
  
public interface UserService {
	
    User getUserById(int userId); 
    
    User getUserByUsernameAndPassword(String username,String password);
}  

