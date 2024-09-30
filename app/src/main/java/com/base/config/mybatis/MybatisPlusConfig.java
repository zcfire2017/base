package com.base.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis 拦截器
 */
@Configuration
public class MybatisPlusConfig {
	/**
	 * 构建拦截器
	 *
	 * @return 拦截器信息
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		//拦截器
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		//开启分页插件
		PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
		//设置默认最大查询数量
		//paginationInnerInterceptor.setMaxLimit(1000L);
		interceptor.addInnerInterceptor(paginationInnerInterceptor);
		// 配置防止全表更新与删除的插件
		BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
		interceptor.addInnerInterceptor(blockAttackInnerInterceptor);
		return interceptor;
	}
}