package com.sky.app.coder.service;

import java.util.List;

import com.sky.app.coder.model.BpTreemode;
import com.sky.app.coder.model.Element;
import com.sky.app.coder.model.Systems;

public interface ICoderService{
	List<Systems> getSystems(String sqlId,String sysKey);
	Systems getSystemsOne(String sqlId,String upperSys);
	//获得按系统生成的前端页面路径、后台路径、包和系统简码
	List<String> getSystemsInfo(List<String> sysKeyList);
	//判断模型
	Element getModuleOne(String sqlId,String moduCode);
	//单表模型,获得实体类内容
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
	
	//从表模型，获得主表或从表映射文件mapper中select字段
	String getMsMapperSelectFields(String tablename);
	
	//树模型，获取表的全部字段，并放入集合中返回
	List<String> getTreeModeTableFields(String tablename);
	//树模型，获取多个表的全部字段，并放入集合中返回
	List<String> getTreeModeTablesFields(String[] tablenames);
	//树模型，获取主表的全部字段，并放入集合中返回
	List<String> getTreeModeMsTablesFields(String tablename);
	//树模型，获取动态路由
	List<String> getTreeRouter(String tranCode);
}
