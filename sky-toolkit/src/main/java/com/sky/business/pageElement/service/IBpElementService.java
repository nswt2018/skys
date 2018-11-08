package com.sky.business.pageElement.service;

import java.util.Map;

import com.sky.business.pageElement.model.BpElement;
import com.sky.core.base.service.IBaseService;

public interface IBpElementService extends IBaseService<BpElement> {
	
	int delData(String sqlId, Map<String, String> map);
}

