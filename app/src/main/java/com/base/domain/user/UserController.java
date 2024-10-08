package com.base.domain.user;

import com.base.domain.user.dao.UserDAO;
import com.base.domain.user.services.bo.UserBO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

	@Resource
	private UserDAO userDAO;


	@GetMapping
	public void test() {
		var info = userDAO.first();

		var bo = new UserBO();
		bo.mobile = info.getMobile();
		bo.nickName = info.getNickName();
		bo.password = info.getPassword();
		bo.sex = info.getSex();
		bo.userType = info.getUserType();
		bo.groupId = info.getGroupId();
		bo.name = info.getName();
		bo.factoryId = info.getFactoryId();
		bo.roleId = info.getRoleId();

		return;
	}
}