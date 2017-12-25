package com.sooncode.daocodebuilder.entity.util.config;
/**
 * 一些常量 
 * @author hechen
 *
 */
public class Constant {
public static final String MIDDEL_TABLE = "_中间表";
/**
 * 数据库表和表字段的映射策略
 * 当表的生成策略为"非映射" 策略时, 不生成该表的相关代码。
 * 当表的某些字段生成策略   为"非映射" 策略时, 不生成该表的这些字段的相关代码。
 */
public static final String MAP_STRATEGY = "_非映射";
}
