package com.base.domain.user.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author zc
 * @since 2023-11-15
 */
@Getter
@Setter
@TableName("user")
public class UserPO {

	/**
	 * 自增ID  用户ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 集团ID
	 */
	@TableField("group_id")
	private Long groupId;

	/**
	 * 工厂ID
	 */
	@TableField("factory_id")
	private Long factoryId;

	/**
	 * 角色ID
	 */
	@TableField("role_id")
	private Integer roleId;

	/**
	 * 用户类型（0：系统用户，1：普通用户）
	 */
	@TableField("user_type")
	private Byte userType;

	/**
	 * 用户名
	 */
	@TableField("`name`")
	public String name;

	/**
	 * 用户性别
	 */
	@TableField("sex")
	private Byte sex;

	/**
	 * 昵称
	 */
	@TableField("nick_name")
	private String nickName;

	/**
	 * 密码
	 */
	@TableField("`password`")
	private String password;

	/**
	 * 联系电话
	 */
	@TableField("mobile")
	private String mobile;

	/**
	 * 邮箱
	 */
	@TableField("e_mail")
	private String eMail;

	/**
	 * 头像URL地址
	 */
	@TableField("avatar")
	private String avatar;
	/**
	 * 职位
	 */
	@TableField("post")
	private String post;
	/**
	 * 是否启用
	 */
	@TableField("is_enabled")
	private Boolean isEnabled;

	/**
	 * 是否删除
	 */
	@TableField("is_deleted")
	private Boolean isDeleted;

	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private LocalDateTime createTime;
}