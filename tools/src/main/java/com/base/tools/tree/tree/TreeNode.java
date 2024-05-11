package com.base.tools.tree.tree;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构
 */
@Getter
@Setter
@NoArgsConstructor
public class TreeNode<T> {
	/**
	 * 当前节点
	 */
	private T node;

	/**
	 * 子集合
	 */
	private List<TreeNode<T>> childList = new ArrayList<>();
}