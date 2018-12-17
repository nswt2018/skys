package com.sky.app.coder.service;

import java.util.List;

import com.sky.app.coder.model.Element;
import com.sky.app.coder.model.Systems;

public interface ICoderService{
	List<Systems> getSystems(String sqlId,String sysKey);
	Systems getSystemsOne(String sqlId,String upperSys);
	//判断模型
	Element getModuleOne(String sqlId,String moduCode);
	//获得实体类内容
	String getClassStr(String tablename, String tablepri);
	//单表模型，取得页面元素字段相关信息
	List<Element> getTagInfo(String sqlId,String moduCode);
	//单表模型
	Element getElement(String sqlId,String moduCode);
	//多表模型，取得页面元素字段相关信息
	List<Element> getMultiTagInfo(String sqlId,String moduCode);
	//多表模型，获得模块关联表主键及主键生成策略
	Element getMultiFieldOne(String sqlId,String tabCode);
	//多表模型，获得实体类内容
	String getMultiClassStr(String[] tablenames,List<String> list);
	// 多表模型，根据表名和主键字段生成实体类内容
	String getMultiClassStrBytable(String tablename, String tablepri);
	//多表模型，获得映射文件mapper中select后字段
	String getMultiMapperSelectField(String[] tablenames);
}
