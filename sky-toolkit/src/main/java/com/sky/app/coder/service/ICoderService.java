package com.sky.app.coder.service;

import java.util.List;

import com.sky.app.coder.model.Element;
import com.sky.app.coder.model.Systems;

public interface ICoderService{
	List<Systems> getSystems(String sqlId,String sysKey);
	List<Element> getTagInfo(String sqlId,String moduCode);
	Element getElement(String sqlId,String moduCode);
	Systems getSystemsOne(String sqlId,String upperSys);
	String getClassStr(String tablename, String tablepri);
}
