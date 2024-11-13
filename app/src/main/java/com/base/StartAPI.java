package com.base;

import com.base.tools.log.LogHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.base", "com.base.domain", "com.base.service", "com.base.api.auth_service"})
@MapperScan(basePackages = {"com.base.domain", "com.base.mapper"})
@EnableFeignClients
public class StartAPI {
	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> LogHelper.error(e, "【Application】报错"));
		LogHelper.starting("Application");
		SpringApplication.run(StartAPI.class, args);
		LogHelper.started("Application");
	}
}