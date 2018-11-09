package com.sky.business.systemModule.service;

import java.util.List;
import java.util.Map;

import com.sky.business.systemModule.model.BpModule;
import com.sky.core.base.service.IBaseService;

public interface IBpModuleService extends IBaseService<BpModule> {
	
	List<String> getTabInfo(String sqlId, String parameter);
	int delData(String sqlId, Map<String, String> map);
}

