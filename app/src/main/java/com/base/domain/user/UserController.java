package com.base.domain.user;

import com.base.domain.user.dao.UserDAO;
import com.base.service_api.TestAPI;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

	@Resource
	private UserDAO userDAO;

	@Resource
	private TestAPI testAPI;


	@GetMapping
	public Object test() {
		Map<String, Object> map = Map.of("name", "tt", "list", List.of(1, 2, 3, 4));
		var result = map.getList("list", Integer.class);
		var info = userDAO.first();
		var list = testAPI.getNameList();
		return list;
	}

	private void test(int a, int b=a) {

	}
}