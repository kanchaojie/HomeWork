package com.service;

import java.util.List;
import java.util.Map;

import com.pojo.User;
import com.vo.MessageVO;

public interface UserService {
	public List<User> getUserList();
	
	//登陆操作
	public MessageVO<User> getUserByUnamePassword(User user);
}
