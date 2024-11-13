package com.base.domain.user;

import com.base.domain.user.dao.UserDAO;
import com.base.service_api.TestAPI;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

	@Resource
	private UserDAO userDAO;

	@Resource
	private TestAPI testAPI;


	@GetMapping
	public Object test() {
		var info = userDAO.first();
		var list = testAPI.getNameList();
		return list;
	}
}