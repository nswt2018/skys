package com.sky.business.columnDefinition.service;

import java.util.Map;

import com.sky.business.columnDefinition.model.BpField;
import com.sky.core.base.service.IBaseService;

public interface IBpFieldService extends IBaseService<BpField> {
	int delField(String sqlId, Map<String, String> map);

	int backupTab(String sqlId, Map<String, String> map);
}

