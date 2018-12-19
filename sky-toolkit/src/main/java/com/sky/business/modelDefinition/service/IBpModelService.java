package com.sky.business.modelDefinition.service;

import java.util.List;
import java.util.Map;

import com.sky.business.modelDefinition.model.BpModel;
import com.sky.business.systemModule.model.BpModule;
import com.sky.core.base.service.IBaseService;

public interface IBpModelService extends IBaseService<BpModel> {

	List<BpModule> findModuForPageList(String sqlId, Map<String, Object> map);

	int countModu(String sqlId, Map<String, Object> map);

}

