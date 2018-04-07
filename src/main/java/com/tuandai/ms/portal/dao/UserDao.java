package com.tuandai.ms.portal.dao;

import org.apache.ibatis.annotations.Param;

import com.tuandai.ms.portal.domain.User;

public interface UserDao {
	
	User selectByPrimaryKey(int userId);
	
	User selectByUsernameAndPassword(@Param("username")String username,@Param("password")String password);
}
