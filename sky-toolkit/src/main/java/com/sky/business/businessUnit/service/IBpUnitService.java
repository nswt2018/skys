package com.sky.business.businessUnit.service;

import java.util.Map;

import com.sky.business.businessUnit.model.BpUnit;
import com.sky.core.base.service.IBaseService;

public interface IBpUnitService extends IBaseService<BpUnit> {

	int delUnit(String sqlId, Map<String, String> map);
}

