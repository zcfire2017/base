package com.base.tools.time;


import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * 日期常量
 */
public class DateTimeConst {

	//格式化格式常量
	public static String yyyy_MM_dd = "yyyy-MM-dd";
	public static String yyyy_MM_ddHHmmss = "yyyy-MM-dd HH:mm:ss";
	public static String yyyy_MM_ddHHmmssSSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static String yyyyMMdd = "yyyyMMdd";
	public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	public static String italic_yyyyMMdd = "yyyy/MM/dd";
	public static String italic_yyyyMd = "yyyy/M/d";
	public static String italic_yyyyMMddHHmmss = "yyyy/MM/dd HH:mm:ss";
	public static String italic_yyyyMMddHHmmssSSS = "yyyy/MM/dd HH:mm:ss.SSS";
	public static String italic_yyyyMdHmmss = "yyyy/M/d H:mm:ss";

	//一些常用的DateTimeFormatter 用于LocalDateTime,LocalDate
	public static DateTimeFormatter yyyy_MM_dd_Formatter = DateTimeFormatter.ofPattern(yyyy_MM_dd);
	public static DateTimeFormatter yyyy_MM_ddHHmmss_Formatter = DateTimeFormatter.ofPattern(yyyy_MM_ddHHmmss);
	public static DateTimeFormatter yyyy_MM_ddHHmmssSSS_Formatter = DateTimeFormatter.ofPattern(yyyy_MM_ddHHmmssSSS);
	public static DateTimeFormatter yyyyMMdd_Formatter = DateTimeFormatter.ofPattern(yyyyMMdd);
	public static DateTimeFormatter yyyyMMddHHmmss_Formatter = DateTimeFormatter.ofPattern(yyyyMMddHHmmss);
	public static DateTimeFormatter yyyyMMddHHmmssSSS_Formatter = DateTimeFormatter.ofPattern(yyyyMMddHHmmssSSS);
	public static DateTimeFormatter italic_yyyyMMdd_Formatter = DateTimeFormatter.ofPattern(italic_yyyyMMdd);
	public static DateTimeFormatter italic_yyyyMMddHHmmss_Formatter = DateTimeFormatter.ofPattern(italic_yyyyMMddHHmmss);
	public static DateTimeFormatter italic_yyyyMMddHHmmssSSS_Formatter = DateTimeFormatter.ofPattern(italic_yyyyMMddHHmmssSSS);

	//一些常用的SimpleDateFormat 用于Date
	public static SimpleDateFormat yyyy_MM_dd_SimpleDateFormat = new SimpleDateFormat(yyyy_MM_dd);
	public static SimpleDateFormat yyyy_MM_ddHHmmss_SimpleDateFormat = new SimpleDateFormat(yyyy_MM_ddHHmmss);
	public static SimpleDateFormat yyyy_MM_ddHHmmssSSS_SimpleDateFormat = new SimpleDateFormat(yyyy_MM_ddHHmmssSSS);
	public static SimpleDateFormat yyyyMMdd_SimpleDateFormat = new SimpleDateFormat(yyyyMMdd);
	public static SimpleDateFormat yyyyMMddHHmmss_SimpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmss);
	public static SimpleDateFormat yyyyMMddHHmmssSSS_SimpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmssSSS);
	public static SimpleDateFormat italic_yyyyMMdd_SimpleDateFormat = new SimpleDateFormat(italic_yyyyMMdd);
	public static SimpleDateFormat italic_yyyyMMddHHmmss_SimpleDateFormat = new SimpleDateFormat(italic_yyyyMMddHHmmss);
	public static SimpleDateFormat italic_yyyyMMddHHmmssSSS_SimpleDateFormat = new SimpleDateFormat(italic_yyyyMMddHHmmssSSS);
}