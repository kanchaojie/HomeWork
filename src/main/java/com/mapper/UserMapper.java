package com.mapper;

import java.util.List;

import com.pojo.User;

public interface UserMapper {
	public List<User> GetUserList();
	
	//登陆验证
	public User getUserByUnamePassword(String username);
}
