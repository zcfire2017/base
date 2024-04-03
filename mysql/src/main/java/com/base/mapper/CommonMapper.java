package com.base.mapper;


import com.base.mybatisPlus.query.SqlProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * 通用Mapper 不包含泛型实体
 */
public interface CommonMapper {
	/**
	 * 执行语句
	 *
	 * @param parameters 参数集合
	 * @return 受影响行数
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	Integer execute(Map<String, Object> parameters);

	//region 查询

	//region 查询单个对象

	/**
	 * 执行查询语句 返回单个对象
	 *
	 * @param parameters 参数集合
	 * @return 返回单个对象
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	Object selectObj(Map<String, Object> parameters);

	/**
	 * 根据sql查询Map对象
	 *
	 * @param parameters 参数集合
	 * @return 单个Map对象
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	Map<String, Object> selectMap(Map<String, Object> parameters);

	/**
	 * 执行查询语句
	 *
	 * @param parameters 参数集合
	 * @return int
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	Integer selectInt(Map<String, Object> parameters);

	/**
	 * 执行查询语句
	 *
	 * @param parameters 参数集合
	 * @return boolean
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	Boolean selectBool(Map<String, Object> parameters);

	/**
	 * 执行查询语句
	 *
	 * @param parameters 参数集合
	 * @return long
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	Long selectLong(Map<String, Object> parameters);

	/**
	 * 执行查询语句
	 *
	 * @param parameters 参数集合
	 * @return double
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	Double selectDouble(Map<String, Object> parameters);

	/**
	 * 执行查询语句
	 *
	 * @param parameters 参数集合
	 * @return BigDecimal
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	BigDecimal selectDecimal(Map<String, Object> parameters);

	/**
	 * 执行查询语句
	 *
	 * @param parameters 参数集合
	 * @return LocalDateTime
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	LocalDateTime selectDateTime(Map<String, Object> parameters);

	/**
	 * 执行查询语句
	 *
	 * @param parameters 参数集合
	 * @return LocalDate
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	LocalDate selectDate(Map<String, Object> parameters);

	/**
	 * 执行查询语句
	 *
	 * @param parameters 参数集合
	 * @return LocalTime
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	LocalTime selectTime(Map<String, Object> parameters);

	/**
	 * 执行查询语句
	 *
	 * @param parameters 参数集合
	 * @return short
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	Short selectShort(Map<String, Object> parameters);

	/**
	 * 执行查询语句
	 *
	 * @param parameters 参数集合
	 * @return byte
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	Byte selectByte(Map<String, Object> parameters);

	//endregion

	//region 查询集合

	/**
	 * 根据sql查询Map对象集合
	 *
	 * @param parameters 参数集合
	 * @return Map对象的集合
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	List<Map<String, Object>> selectMapList(Map<String, Object> parameters);

	/**
	 * 根据sql查询Map对象集合
	 *
	 * @param parameters 参数集合
	 * @return Object集合
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	List<Object> selectObjList(Map<String, Object> parameters);

	/**
	 * 根据sql查询集合
	 *
	 * @param parameters 参数集合
	 * @return String集合
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	List<String> selectStringList(Map<String, Object> parameters);

	/**
	 * 根据sql查询集合
	 *
	 * @param parameters 参数集合
	 * @return Boolean集合
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	List<Boolean> selectBoolList(Map<String, Object> parameters);

	/**
	 * 根据sql查询集合
	 *
	 * @param parameters 参数集合
	 * @return Byte集合
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	List<Byte> selectByteList(Map<String, Object> parameters);

	/**
	 * 根据sql查询集合
	 *
	 * @param parameters 参数集合
	 * @return Short集合
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	List<Short> selectShortList(Map<String, Object> parameters);

	/**
	 * 根据sql查询集合
	 *
	 * @param parameters 参数集合
	 * @return Long集合
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	List<Long> selectLongList(Map<String, Object> parameters);

	/**
	 * 根据sql查询集合
	 *
	 * @param parameters 参数集合
	 * @return int集合
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	List<Integer> selectIntList(Map<String, Object> parameters);

	/**
	 * 根据sql查询集合
	 *
	 * @param parameters 参数集合
	 * @return double集合
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	List<Double> selectDoubleList(Map<String, Object> parameters);

	/**
	 * 根据sql查询集合
	 *
	 * @param parameters 参数集合
	 * @return BigDecimal集合
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	List<BigDecimal> selectDecimeList(Map<String, Object> parameters);

	/**
	 * 根据sql查询集合
	 *
	 * @param parameters 参数集合
	 * @return LocalDateTime集合
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	List<LocalDateTime> selectDateTimeList(Map<String, Object> parameters);

	/**
	 * 根据sql查询集合
	 *
	 * @param parameters 参数集合
	 * @return LocalDate集合
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	List<LocalDate> selectDateList(Map<String, Object> parameters);

	/**
	 * 根据sql查询集合
	 *
	 * @param parameters 参数集合
	 * @return LocalTime集合
	 */
	@SelectProvider(type = SqlProvider.class, method = SqlProvider.getSql)
	List<LocalTime> selectTimeList(Map<String, Object> parameters);

	//endregion

	//endregion
}