package com.sky.factory.service;

import java.util.List;

import com.sky.core.base.service.IBaseService;
import com.sky.factory.model.BpSystems;

public interface IBpSystemsService extends IBaseService<BpSystems> {

	List<String> findMaxKey(String sqlId, String parameter);

	int deleteBySysKey(String sqlId, String parameter);
}

