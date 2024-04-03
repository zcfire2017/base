package com.base.tools.string;


import cn.hutool.core.util.StrUtil;

/**
 * 对StringUtils做一些自定义扩展
 */
public class StringUtilsEx {

	/**
	 * 反引号( ` )
	 */
	public final static String BackTick = "`";

	/**
	 * 星星 ( * )
	 */
	public final static String Star = "*";

	/**
	 * 单引号 (')
	 */
	public final static String SingleQuote = "'";

	/**
	 * 字段两边添加反引号` 比如 `str`
	 *
	 * @param str 字段
	 */
	public static String toBackTick(String str) {
		if (StrUtil.isBlank(str))
			return str;

		if (str.startsWith(BackTick) || str.endsWith(BackTick))
			return str;
		return String.format("%s%s%s", BackTick, str, BackTick);
	}

	/**
	 * 字符串脱敏  比如 234324324 脱敏后 234*****324
	 *
	 * @param str  字符串
	 * @param font 字符串开头 可见字符数
	 * @param back 字符串结尾 可见字符数
	 */
	public static String toSensitive(String str, int font, int back) {
		if (StrUtil.isBlank(str))
			return str;

		int len = str.length();
		int size = len - font - back;
		//长度不够
		if (size <= 0)
			return str;

		//截取
		String start = str.substring(0, font);
		String end = str.substring((len - back), len);

		//组合
		StringBuilder sb = new StringBuilder();
		sb.append(start);
		for (int i = 0; i < size; i++)
			sb.append(Star);
		sb.append(end);
		return sb.toString();
	}

	/**
	 * 字符串两边加上单引号
	 *
	 * @param str 字符串
	 */
	public static String toSingleQuote(String str) {
		if (str == null)
			return str;
		return String.format("%s%s%s", SingleQuote, str, SingleQuote);
	}

	/**
	 * 校验车牌号
	 *
	 * @param str 车牌号
	 */
	public static boolean isPlateNumber(String str) {
		return true;
//		if (StringUtils.isBlank(str))
//			return false;
//		Pattern compile = Pattern.compile("^([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[a-zA-Z](([DF]((?![IO])[a-zA-Z0-9](?![IO]))[0-9]{4})|([0-9]{5}[DF]))|[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1})$");
//		Matcher matcher = compile.matcher(str);
//		return matcher.matches();
	}
}