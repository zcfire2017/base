package com.base.domain.user.services.bo;

import manifold.ext.props.rt.api.var;

import java.time.LocalDateTime;

public class UserBO {

	@var String name;

	@var String password;

	@var String mobile;

	@var String eMail;

	@var String nickName;

	@var Byte sex;

	@var Byte userType;

	@var Integer roleId;

	@var Long groupId;

	@var Long factoryId;

	@var LocalDateTime createTime;

	public void getAll() {
		this.name = "123";
		this.password = "123";
		this.mobile = "123";
		this.eMail = "123";
		this.nickName = "123";
		this.sex = 1;
		this.userType = 1;
		this.roleId = 1;
	}
}