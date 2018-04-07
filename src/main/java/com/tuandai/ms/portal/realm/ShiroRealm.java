package com.tuandai.ms.portal.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuandai.ms.portal.domain.User;
import com.tuandai.ms.portal.service.UserService;

public class ShiroRealm extends AuthorizingRealm{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String)token.getPrincipal();
		String password = new String((char[]) token.getCredentials());
		User user = userService.getUserByUsernameAndPassword(username, password);
		if(null == user){
			
		}
		logger.info("fefe");
	   SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());
        return info;
	}

}
