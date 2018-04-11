package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.pojo.User;
import com.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("User/UserList.action")
	public void userlist(HttpServletResponse response) throws Exception{
		List<User> list = userService.getUserList();
		String json = JSON.toJSONString(list);
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(json);
	}
}
