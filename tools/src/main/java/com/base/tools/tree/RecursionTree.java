package com.base.tools.tree;

import com.base.tools.tree.tree.TreeNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 树形结构处理 帮助类
 *
 * @param <T> 集合对象类型
 * @param <D> 主键类型
 * @param <P> 上级主键类型
 */
public class RecursionTree<T, D, P> {

	/**
	 * 主键的Map
	 */
	private final Map<D, T> _mainMap;

	/**
	 * 上级主键的Map
	 */
	private final Map<P, List<T>> _parentMap;

	/**
	 * 主键属性
	 */
	private final Function<T, D> _idColumn;

	/**
	 * 上级主键属性
	 */
	private final Function<T, P> _pIdColumn;

	/**
	 * 根主键的值
	 */
	private final D _rootId;

	/**
	 * 最大递归层级
	 */
	private static final Byte _maxLevel = 100;

	/**
	 * 当前递归层级
	 */
	private Byte _level = 0;

	/**
	 * 构造函数
	 *
	 * @param list      数据集合
	 * @param idColumn  主键
	 * @param pIdColumn 上级主键
	 * @param rootId    根主键的值
	 */
	public RecursionTree(Collection<T> list, Function<T, D> idColumn, Function<T, P> pIdColumn, D rootId) {
		_mainMap = list.toMap(idColumn, item -> item);
		_parentMap = list.group(pIdColumn);
		_idColumn = idColumn;
		_pIdColumn = pIdColumn;
		_rootId = rootId;
	}

	/**
	 * 构造函数
	 *
	 * @param list      数据集合
	 * @param idColumn  主键
	 * @param pIdColumn 上级主键
	 */
	public RecursionTree(Collection<T> list, Function<T, D> idColumn, Function<T, P> pIdColumn) {
		this(list, idColumn, pIdColumn, null);
	}

	//region 集合

	/**
	 * 获取子集集合
	 *
	 * @param id          主键ID
	 * @param includeSelf 是否包含自身
	 *
	 * @return 子集集合
	 */
	public List<T> getChildList(D id, Boolean includeSelf) {
		//返回
		var result = new ArrayList<T>();
		if (id == null)
			return result;

		//重置层级
		_level = 0;
		//包含自身
		if (includeSelf) {
			result.add(_mainMap.get(id));
		}
		//添加子集集合
		result.addAll(recursionChild(id));

		return result;
	}

	/**
	 * 获取子集集合
	 * <p>默认包含自身</p>
	 *
	 * @param id 主键ID
	 *
	 * @return 子集集合
	 */
	public List<T> getChildList(D id) {
		return getChildList(id, true);
	}

	/**
	 * 获取子集集合
	 * <p>默认包含自身</p>
	 *
	 * @return 子集集合
	 */
	public List<T> getChildList() {
		return getChildList(_rootId, true);
	}

	/**
	 * 递归子集
	 *
	 * @param id 父级ID
	 *
	 * @return 子集集合
	 */
	private List<T> recursionChild(D id) {
		var result = new ArrayList<T>();
		//没有根值或递归层级超过限制
		if (id == null || _level >= _maxLevel)
			return result;

		//重置层级
		_level = 0;
		//查找子集
		var childrenList = _parentMap.get(id);
		if (childrenList != null && childrenList.isNotEmpty()) {
			//循环子集查找子集集合
			for (var child : childrenList) {
				//添加自身
				result.add(child);
				//添加自身集合
				result.addAll(recursionChild(_idColumn.apply(child)));
			}
		}
		return result;
	}

	//endregion

	//region 树形集合

	/**
	 * 获取树形集合
	 *
	 * @param id 主键ID
	 *
	 * @return 树形集合
	 */
	public TreeNode<T> getTreeList(D id) {
		//返回
		var result = new TreeNode<T>();
		result.setNode(_mainMap.get(id));
		//添加子集集合
		result.setChildList(recursionTree(id));

		return result;
	}


	/**
	 * 获取树形集合
	 *
	 * @return 树形集合
	 */
	public List<TreeNode<T>> getTreeList() {
		return recursionTree(_rootId);
	}

	/**
	 * 递归子集
	 *
	 * @param id 父级ID
	 *
	 * @return 子集集合
	 */
	private List<TreeNode<T>> recursionTree(D id) {
		var result = new ArrayList<TreeNode<T>>();
		//没有根值或递归层级超过限制
		if (id == null || _level >= _maxLevel)
			return result;

		//查找子集
		var childrenList = _parentMap.get(id);
		if (childrenList != null && childrenList.isNotEmpty()) {
			//循环子集查找子集集合
			for (var child : childrenList) {
				//添加自身
				var self = new TreeNode<T>();
				self.setNode(child);
				//添加子集集合
				self.setChildList(recursionTree(_idColumn.apply(child)));
				//添加到集合
				result.add(self);
			}
		}
		return result;
	}

	//endregion
}