package com.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pojo.User;
import com.service.UserService;
import com.utils.HomeworkConstant;
import com.vo.MessageVO;


@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/login.action")
	public ModelAndView login(User user,HttpServletRequest request) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView();
		MessageVO<User> msg = userService.getUserByUnamePassword(user);
		if (msg.getmContent() !=null) {
			modelAndView.addObject("message", msg.getmContent());
		}
		if (msg.getDataModel()!=null) {
			request.getSession().setAttribute("loginUser", msg.getDataModel());
			modelAndView.setViewName("jsp/index");
		}
		return modelAndView;
	}
}
