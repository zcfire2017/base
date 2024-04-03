package com.base.service;


import com.base.mapper.CommonMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 通用Service 非泛型数据库实体
 */
@Service("ICommonService")
public class CommonServiceImpl implements ICommonService {

	/**
	 * 通用Mapper 非泛型数据库实体
	 */
	@Resource
	private CommonMapper commonMapper;

	/**
	 * 获取mapper对象
	 *
	 * @return mapper
	 */
	@Override
	public CommonMapper getCommonMapper() {
		return commonMapper;
	}
}