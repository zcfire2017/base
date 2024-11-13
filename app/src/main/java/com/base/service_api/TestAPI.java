package com.base.service_api;

import com.base.config.auth.AuthFeignConfig;
import com.base.entity.key_value.IDName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "test", url = "http://127.0.0.1:30120/api/v1/glass-type", configuration = AuthFeignConfig.class)
public interface TestAPI {

	@GetMapping("list")
	List<IDName<Integer>> getNameList();
}