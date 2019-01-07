package com.sky.factory.service;

import java.util.List;
import java.util.Map;

import com.sky.core.base.service.IBaseService;
import com.sky.factory.model.BpModuleModel;
import com.sky.factory.model.BpSystems;

public interface IBpSystemsService extends IBaseService<BpSystems> {

	List<String> findMaxKey(String sqlId, String parameter);

	int deleteBySysKey(String sqlId, String parameter);
	
	public boolean flushRouter(List<BpSystems> sList);

	int updChildren(String sqlId, Map<String, String> map);
	
	List<BpModuleModel> getTreeRouter(String sqlId, String parameter);
	
	public boolean flushOtherRouter(List<BpModuleModel> sList);
}

