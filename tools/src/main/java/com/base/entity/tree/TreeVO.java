package com.base.entity.tree;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 树形结构
 */
@Data
@NoArgsConstructor
public class TreeVO {
	/**
	 * 唯一键值
	 */
	public int key;

	/**
	 * 名称
	 */
	public String name;

	/**
	 * 父级ID
	 */
	public int pId;

	/**
	 * 子集合
	 */
	public List<TreeVO> children;

	/**
	 * 递归 包含自身
	 *
	 * @param list 原集合
	 * @param id   自身id
	 * @return 递归后的集合
	 */
	public static List<TreeVO> RecursionWithSelf(List<TreeVO> list, int id) {
		List<TreeVO> result = new ArrayList<>();

		TreeVO model = list.stream().filter(item -> item.getKey() == id).findFirst().orElse(null);
		if (model != null) {
			//添加自己
			result.add(model);
			//查找子集
			List<TreeVO> children = list.stream().filter(item -> item.getPId() == id).collect(Collectors.toList());
			if (!children.isEmpty()) {
				for (TreeVO child : children) {
					RecursionWithSelf(list, child.getKey());
				}
			}
		}
		return result;

	}

	/**
	 * 递归 不包含自身 直接查询子集 后辈
	 *
	 * @param list 原集合
	 * @param pid  上级id
	 * @return 递归后的集合
	 */
	public static List<TreeVO> Recursion(List<TreeVO> list, int pid) {
		List<TreeVO> result = new ArrayList<>();

		List<TreeVO> children = list.stream().filter(item -> item.getPId() == pid).collect(Collectors.toList());
		if (!children.isEmpty()) {
			for (TreeVO child : children) {
				result.add(child);
				Recursion(list, child.getKey());
			}
		}
		return result;

	}
}