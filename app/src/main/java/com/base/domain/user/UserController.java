package com.base.domain.user;

import com.base.domain.user.dao.UserDAO;
import com.base.domain.user.dao.UserPO;
import io.github.kiryu1223.expressionTree.delegate.Action1;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.function.Function;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

	@Resource
	private UserDAO userDAO;


	@GetMapping
	public void test() {
		var info = userDAO.first();

		Action1<String> action = System.out::println;
		var user = new UserPO();
		user.name = "123";

		var list = new ArrayList<UserPO>();

		var query = list.where(w -> w.getId() == 1 && w.getName() == "123")
				.group(t -> (t.name, t.getId()))
				.select(s -> (s.key.id, s.key.name, s.value))
				.toList();

		Function<String, String> function = (s) -> s + "ccc";
		return;
	}
}