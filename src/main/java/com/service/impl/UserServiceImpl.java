package com.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapper.UserMapper;
import com.pojo.User;
import com.service.UserService;
import com.utils.HomeworkConstant;
import com.vo.MessageVO;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<User> getUserList() {
		List<User> list = userMapper.GetUserList();
		return list;
	}

	
	@Override
	public MessageVO<User> getUserByUnamePassword(User user) {
		MessageVO<User> mseMessageVO = new MessageVO<User>();
		//验证用户名、密码不为空
		String username = user.getUname().trim();
		String password = user.getPassWord().trim();
		
		if (username == null || username.equals("")) {
			mseMessageVO.setmContent("用户名不能为空");
		} else if (password == null || password.equals("")) {
			mseMessageVO.setmContent("密码不能为空");
		} else {
			User user1  = userMapper.getUserByUnamePassword(username);
			if(user1 == null){
				mseMessageVO.setmContent("用户不存在");
			}else if(!user1.getPassWord().equals(password)) {
				mseMessageVO.setmContent("密码不正确，请重新输入");
			}else {
				mseMessageVO.setDataModel(user1);
			}
		}
		
		return mseMessageVO;
	}

}
